package com.piple.app;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Class HomeActivity
 *      extends Activity
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.crash.FirebaseCrash;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.PersonBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.piple.res.Contact;
import com.piple.res.Universe;
import com.piple.res.User;
import com.piple.res.Window;

/**
 * Class MainActivity
 *      extends Activity
 *      implements View.OnTouchListener
 *
 * Creates the main activity.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener , GoogleApiClient.OnConnectionFailedListener {


    //jerem for auth
    private static final String TAG = "HomeActivity";
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    private String mUsername;
    private String mPhotoUrl;
    public static final String ANONYMOUS = "anonymous";

    // Firebase instance variables
    private DatabaseReference mFirebaseDatabaseReference;
    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference myRefUniverse;
    private DatabaseReference myRefUser;
    private FirebaseDatabase database;
    private User yourself;

    private String m_Universename = "";


    /**
     * Method onCreate
     * Implements the behavior of the activity when it is created.
     *
     * @param savedInstanceState saved state from the program
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.buttonnewuniverse).setOnClickListener(this);
        database = FirebaseDatabase.getInstance();
        myRefUniverse = database.getReference("Universe");
        myRefUser = database.getReference("User");


        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = ANONYMOUS;
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        //mFirebaseDatabaseReference = FirebaseAuth.getInstance().getReference();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getEmail();
            Toast.makeText(HomeActivity.this, "coool" + mUsername,
                    Toast.LENGTH_SHORT).show();
           /* if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }*/

        }
        yourself = new User(mFirebaseUser.getUid(),mFirebaseUser.getEmail());


    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
    }


    private void causeCrash() {
        throw new NullPointerException("Fake null pointer exception");
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonnewuniverse) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Universe name :");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            builder.setView(input);


            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    m_Universename = input.getText().toString();
                    Universe myuniverse = new Universe(new Contact(mFirebaseUser.getEmail(),mFirebaseUser.getUid()), m_Universename, m_Universename);
                    yourself.setUniverselist(myuniverse);
                    myuniverse.setCurrentUniverse(true);
                    Toast.makeText(HomeActivity.this, "damn.",
                            Toast.LENGTH_SHORT).show();
                    myRefUser.setValue(yourself);
                    myRefUniverse.setValue(myuniverse);
                    Toast.makeText(HomeActivity.this, "damn.",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity.this, UniverseActivity.class));
                    finish();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();



            }
        if (i == R.id.button2) {
            startActivity(new Intent(HomeActivity.this, UniverseActivity.class));
           // myRef.setvalue();
        }
    }
}

