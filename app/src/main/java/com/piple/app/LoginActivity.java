package com.piple.app;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;



/**
 * Class LoginActivity
 *      extends FragmentActivity
 *      implements View.OnClickListener
 *      implements GoogleApiClient.OnConnectionFailedListener
 *
 * Allows user to sign in if already existing account, sign up if not.
 * Using either mail and password or google's account (more secured)
 *
 */
public class LoginActivity
        extends
            FragmentActivity
        implements
            View.OnClickListener,
            GoogleApiClient.OnConnectionFailedListener
{



    /// RESOURCES ///
    /**
     * use to facilitate connections
     *
     * @see google firebase
     */
    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;
    /**
     * button to clickon for a google signin
     *
     */
    private SignInButton mSignInButton;
    /**
     *
     * firebase info
     */
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    /**
     *
     * User's API to connect to his google account and receive credentials
     *
     */
    private GoogleApiClient mGoogleApiClient;

    /**
     *
     * textview to receive email
     *
     */
    private TextView mailTextView;
    /**
     *
     * textview to receive password
     *
     */
    private TextView passTextView;



    /// METHODS ///

    /**
     * Method onCreate
     *      overrides method from AppCompatActivity
     *
     * Implements the behavior of the activity when it is created.
     * instanciate all the button, options, credentials and database
     *
     * @param savedInstanceState saved state from the program
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //Recover sign in button and make it detect clicks
        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(this);

        //Recover input textviews
        mailTextView = (TextView) findViewById(R.id.email);
        passTextView = (TextView) findViewById(R.id.password);

        //Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.email_sign_up_button).setOnClickListener(this);

        //Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestIdToken(getString(R.string.default_web_client_id))
                                        .requestEmail()
                                        .build();

        //Access Google APIs
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                                .enableAutoManage(this, this)
                                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                                .build();

        //Get Firebase authentication
        mFirebaseAuth = FirebaseAuth.getInstance();

        //Create listener for authentication
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };
    }



    /**
     * Method createAccount
     *
     * Allows an unregistered user to create himself an account.
     *
     * @param email mail address used for his account
     * @param password password to access his account
     */
    private void createAccount(String email, String password)
    {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        }
                    });
    }



    /**
     * Method signIn
     *
     * Allows a registered user to sign in.
     *
     * @param email mail address used for his account
     * @param password password to access his account
     */
    private void signIn(String email, String password)
    {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                        }
                    });

    }



    /**
     * Method onStart
     *      overrides method from AppCompatActivity
     *
     * Implements the behavior of the activity when it is started.
     */
    @Override
    public void onStart()
    {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }



    /**
     * Method onStop
     *      overrides method from AppCompatActivity
     *
     * Implements the behavior of the activity when it is stopped.
     */
    @Override
    public void onStop()
    {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }



    /**
     * Method onActivityResult
     *      overrides method from AppCompatActivity
     *
     * Implements the behavior of the activity when it gets a result.
     * and send to
     * @see FirebaseAuth
     *
     * or send an error message
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Log.e(TAG, "Google Sign In failed.");
            }
        }
    }



    /**
     * Method firebaseAuthWithGoogle
     *
     * Authenticates in Firebase through a Google Account.
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        }
                    }
                });
    }



    /**
     * Method googleSignIn
     *
     * Used to connect through Google Account.
     */
    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    /**
     * Method onConnectionFailed
     *      overrides method from GoogleApiClient.OnConnectionFailedListener
     *
     * Implements the behavior of the activity when the connection to Google APIs failed.
     *
     * @param connectionResult the result of your connectionned returned by the API
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }



    /**
     * Method onClick
     *      overrides method from View.OnClickListener
     *
     * Implements the behavior of the activity when the user clicks on it.
     *
     * @param v view touched
     */
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == com.piple.app.R.id.sign_in_button) {
            googleSignIn();
        }
        if( i == R.id.email_sign_up_button){
            createAccount(mailTextView.getText().toString(), passTextView.getText().toString());
        }
        if( i == R.id.email_sign_in_button){
            signIn(mailTextView.getText().toString(), passTextView.getText().toString());
        }
    }

}