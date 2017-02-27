package com.piple.app;



import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.piple.res.Contact;
import com.piple.res.Universe;
import com.piple.res.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import static android.R.attr.id;


/**
 * Class HomeActivity
 *      extends AppCompatActivity
 *      implements View.OnClickListener
 *      implements GoogleApiClient.OnConnectionFailedListener
 *
 * First activity of the app.
 * If the user is not logged in, redirects on LoginActivity.
 * Displays the home page (containing the different universes) of the user.
 */
public class HomeActivity
        extends
            AppCompatActivity
        implements
            View.OnClickListener,
            GoogleApiClient.OnConnectionFailedListener
{



    /// RESOURCES ///

    private static final String TAG = "HomeActivity";
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    private String mUsername;
    public static final String ANONYMOUS = "anonymous";

    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private DatabaseReference myRefUniverse;
    private DatabaseReference myRefUser;
    private FirebaseDatabase database;
    private User yourself;

    private String m_Universename = "";



    /// METHODS ///

    /**
     * Method onCreate
     *      overrides method from AppCompatActivity
     *
     * Implements the behavior of the activity when it is created.
     *
     * @param savedInstanceState saved state from the program
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try {
            getSupportActionBar().setTitle("PiPle - Universe");
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        setContentView(R.layout.home);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.buttonnewuniverse).setOnClickListener(this);
        database = FirebaseDatabase.getInstance();

        myRefUniverse = database.getReference("Universe");
        myRefUser = database.getReference("User");

        //Set preferences and defaults
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername = ANONYMOUS;

        //Initialize Firebase authentication
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


        //Check if user is identified
        if (mFirebaseUser == null) {
            System.out.println("\n user : "+mFirebaseUser);
            //If no, launch LoginActivity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            System.out.println("\n user : "+mFirebaseUser);
            return;
        }
            //If yes, get his email and welcome him
            mUsername = mFirebaseUser.getEmail();
            Toast.makeText(HomeActivity.this, "Hey there, " + mUsername, Toast.LENGTH_SHORT).show();
            yourself = new User(mFirebaseUser.getUid(), mFirebaseUser.getEmail());





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
        //TODO : get user's contacts ( later)


        //TODO : get user's universes and display them and set buttons on thoses

        myRefUniverse.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> Universemap = (HashMap<String, Object>) dataSnapshot.getValue();
                System.out.println("value gotten");
                if(Universemap.get("Id")==yourself.getId()) {
                   // Universe aUniverse = toUniverse(Universemap);
                    //createNewButton(aUniverse);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
    @Override
    public void onStop(){
        super.onStop();

        // Remove post value event listener

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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


    public boolean createNewButton(Universe aUniverse){
        TextView name = new TextView(this);
        name.setText(aUniverse.getName());
        name.setWidth(90);
        name.setHeight(24);
        View thisview = findViewById(R.id.main);
        return true;

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
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.buttonnewuniverse) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Universe name :");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
            builder.setView(input);


            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //TODO : do the same with an universe that i a child (in the DB ) for them not to erase each other at every instanciation
                    m_Universename = input.getText().toString();
                    Universe myuniverse = new Universe(new Contact(mFirebaseUser.getEmail(),mFirebaseUser.getUid()), m_Universename, m_Universename);
                    yourself.setUniverselist(myuniverse);
                    myuniverse.setCurrentUniverse(false);

                    Toast.makeText(HomeActivity.this, "damn.", Toast.LENGTH_SHORT).show();
                    //TODO look if it work with a regular name
                    /*String key = myRefUser.push().getKey();
                    Map<String, Object> UserValues = yourself.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(key, UserValues);
                    myRefUser.updateChildren(childUpdates);*/ //pas besoin de faire ça ...
                    String key = myRefUniverse.push().getKey();
                    Map<String, Object> UniverseValues = myuniverse.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(key, UniverseValues);
                    myRefUniverse.updateChildren(childUpdates);
                    Toast.makeText(HomeActivity.this, "hooo.", Toast.LENGTH_SHORT).show();
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
        }else {
            //TODO :get the universe corresponding to the button and set it as current universe in the DB usins the createbutton method
            //that we have just u pof this comment;


            //TODO start universe activity

        }


        }


}

