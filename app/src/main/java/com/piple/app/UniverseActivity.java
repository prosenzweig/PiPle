package com.piple.app;



import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.piple.res.Contact;
import com.piple.res.MOI;
import com.piple.res.Message;
import com.piple.res.Universe;
import com.piple.res.User;
import com.piple.res.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


/**
 * Class UniverseActivity
 *      extends AppCompatActivity
 *      implements GoogleApiClient.OnConnectionFailedListener
 *
 * Creates the activity displaying one universe.
 * and send the database information to window
 *
 * @see Window
 */
public class UniverseActivity
        extends
            AppCompatActivity
        implements
            GoogleApiClient.OnConnectionFailedListener
{



    /// RESOURCES ///



    /**
     * TAG to the DB
     */
    private static final String TAG = "UniverseActivity";

    /**
     * the reference of the universe in the DB
     *
     */
    private DatabaseReference mRefUniverse;
    /**
     * Firebase informations
     */
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    /**
     * The window everything will be displayed on ( the view )
     *
     */
    private Window mywindow;
    //our different views







    /// METHODS ///

    /**
     * Method onCreate
     * Implements the behavior of the activity when it is created.
     *Here it set the window with its basic parameters
     * and the DB connection and credentials
     *
     *
     * @param savedInstanceState saved state from the program
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        mywindow=new Window(this.getApplicationContext());
        mywindow.setTheuniverse(new Universe());
        setContentView(mywindow);

        int uiOptions = this.getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        this.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        //Set preferences and defaults

        // Initialize Firebase authentication
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        mRefUniverse = FirebaseDatabase.getInstance().getReference("Universe");
        //Check if user is identified
        if (mFirebaseUser == null) {
            //If no, launch LoginActivity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        //If yes, get his email and welcome him
       User  yourself = new User(mFirebaseUser.getUid(), mFirebaseUser.getEmail());


        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                                .enableAutoManage(this, this /* OnConnectionFailedListener */)
                                .addApi(Auth.GOOGLE_SIGN_IN_API)
                                .build();




    }
    /**
     * Method onStart
     *      overrides method from AppCompatActivity
     *
     * Implements the behavior of the activity when it is started.
     * and receive the DB information and update the universe and the window
     */
    @Override
    public void onStart() {
        super.onStart();
        //TODO: add function to get user, contact, ad messages of the universe chosen

        //on récupère la fonction qui get l'utilisateur
        Intent intent = getIntent();
        final String universeId = intent.getExtras().getString("currentUniverse");
        Toast.makeText(this, universeId, Toast.LENGTH_SHORT).show();
        mywindow.setmRef(mRefUniverse.child(universeId));
        //ICI on initialise juste l'univers dans lequel on est avec tout ses messages
        mRefUniverse.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //on chope l'univers sur lequel on est et on le met en mode objet
                if (dataSnapshot.getKey().equals(universeId)) {
                    Map<String, Object> universeMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    System.out.println("universe gotten");
                    mywindow.getTheuniverse().toUniverse(universeMap);

                }
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals(universeId)) {
                    Map<String, Object> universeMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    System.out.println("universe gotten");
                    mywindow.getTheuniverse().toUniverse(universeMap);
                    mywindow.invalidate();
                    Log.d("toMOI","ONconverti");
                }
            }


            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }




    /**
     * Method onConnectionFailed
     *      overrides method from GoogleApiClient.OnConnectionFailedListener
     *
     * Implements the behavior of the activity when the connection to Google APIs failed.
     *
     * @param connectionResult connection resuls from the API
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
