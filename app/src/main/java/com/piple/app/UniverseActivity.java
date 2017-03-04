package com.piple.app;



import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.piple.res.User;
import com.piple.res.Window;


/**
 * Class UniverseActivity
 *      extends AppCompatActivity
 *      implements GoogleApiClient.OnConnectionFailedListener
 *
 * Creates the activity displaying one universe.
 */
public class UniverseActivity
        extends
            AppCompatActivity
        implements
            GoogleApiClient.OnConnectionFailedListener
{



    /// RESOURCES ///

    private static final String TAG = "UniverseActivity";
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    private String mUsername;
    public static final String ANONYMOUS = "anonymous";

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Window mywindow;

    //our different views


    private User yourself;




    /// METHODS ///

    /**
     * Method onCreate
     * Implements the behavior of the activity when it is created.
     *
     * @param savedInstanceState saved state from the program
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mywindow=new Window(this.getApplicationContext());
        setContentView(mywindow);

        //Set preferences and defaults
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = ANONYMOUS;

        // Initialize Firebase authentication
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

        //Check if user is identified
        if (mFirebaseUser == null) {
            //If no, launch LoginActivity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        //If yes, get his email and welcome him
        mUsername = mFirebaseUser.getEmail();
        yourself = new User(mFirebaseUser.getUid(), mFirebaseUser.getEmail());


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                                .enableAutoManage(this, this /* OnConnectionFailedListener */)
                                .addApi(Auth.GOOGLE_SIGN_IN_API)
                                .build();


    }
    /**
     * Method onStart
     *      overrides method from AppCompatActivity
     *
     * Implements the behavior of the activity when it is started.
     */
    @Override
    public void onStart() {
        super.onStart();
        //TODO: add function to get user, contact, ad messages of the universe chosen

        //on récupère la fonction qui get l'utilisateur
        Intent intent = getIntent();
        String universeId = intent.getExtras().getString("currentUniverse");
        Toast.makeText(this, universeId, Toast.LENGTH_SHORT).show();
        //TODO if DBmodification, use all the function from the views to recalculate everything.




    }



    /**
     * Method onConnectionFailed
     *      overrides method from GoogleApiClient.OnConnectionFailedListener
     *
     * Implements the behavior of the activity when the connection to Google APIs failed.
     *
     * @param connectionResult ???
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Check if user is signed in.

    }

}
