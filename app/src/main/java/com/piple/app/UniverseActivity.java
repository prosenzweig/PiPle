package com.piple.app;



import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.piple.res.Contact;
import com.piple.res.Universe;
import com.piple.res.User;
import com.piple.res.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;


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
            GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener
{



    /// RESOURCES ///

    private static final String TAG = "UniverseActivity";
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    private String mUsername;
    public static final String ANONYMOUS = "anonymous";

    private DatabaseReference mFirebaseDatabaseReference;
    private DatabaseReference mRefMessages;
    private DatabaseReference mRefUniverse;
    private DatabaseReference mRefUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private Window mywindow;
    private Universe currentUniverse;

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
        mRefUniverse = FirebaseDatabase.getInstance().getReference("Universe");
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
        final String universeId = intent.getExtras().getString("currentUniverse");
        Toast.makeText(this, universeId, Toast.LENGTH_SHORT).show();

        //ICI on initialise juste l'univers dans lequel on est avec tout ses messages
        mRefUniverse.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //on chope l'univers sur lequel on est et on le met en mode objet
                if(dataSnapshot.getKey().equals(universeId)){
                    Map<String, Object> universeMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    System.out.println("universe gotten");
                    currentUniverse = new Universe();
                    currentUniverse = currentUniverse.toUniverse(universeMap);
                    mywindow.setTheuniverse(currentUniverse);
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

        mRefMessages= mRefUniverse.child(universeId);
        // on ne s'interessera pour l'instant qu'aux nouveaux messages et pas au modif de user et autre
        //TODO : Faire ces ajouts pour l'ajout de partenaire a la conv et autre.
        // ici on instancie les nouveaux messages qui arrivent en faisant attention à ne pas prendre ceux de l'instanciation du début...
        mRefMessages.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            // on l'a deja added donc il ne s'agit maintenant que de get les modifs
            }

            //SI il y a un changement dans l'arraylist MOI checker :
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // si les deux objets ont réellement changé
                if(dataSnapshot.getKey().equals("MOIList")) {
                    //TODO checker quel moi a changé et le modifier en conséquence

                    mywindow.setTheuniverse(currentUniverse);
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
        //TODO if DBmodification, use all the function from the views to recalculate everything.



        mywindow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("LONG CLICK");
                return false;
            }
        });



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


    @Override
    public void onClick(View v) {
        System.out.println("BBBOOONNNOUUR");
    }
}
