package com.example.paul.ronds;

import android.app.Activity;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import java.util.HashMap;
import java.util.Map;
import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;


/**
 * Class MainActivity
 *      extends Activity
 *      implements View.OnTouchListener
 *
 * Creates the main activity.
 */
public class MainActivity extends Activity implements View.OnTouchListener , GoogleApiClient.OnConnectionFailedListener
{
    private Window myWin;
    private float x_drag_start, y_drag_start;
    private float x_win_start, y_win_start;
    private boolean first = true;

    //jerem for auth
    private static final String TAG = "MainActivity";
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
    private FirebaseAnalytics mFirebaseAnalytics;
    private EditText mMessageEditText;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private static final String MESSAGE_URL = "http://piple.firebase.google.com/message/";
    /**
     * Method onCreate
     * Implements the behavior of the activity when it is created.
     *
     * @param savedInstanceState saved state from the program
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        myWin = new Window(this);
        setContentView(myWin);
        //jerem auth
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = ANONYMOUS;
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        } else {
           /* mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }*/
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        ViewGroup.LayoutParams myParams = myWin.getLayoutParams();
        myParams.width =8000;
        myParams.height =18000;
        myWin.setLayoutParams(myParams);

        myWin.setOnTouchListener(this);
    }

    /**
     * Method onTouch
     * Implements the behavior of the activity on an user touch event.
     *
     * @param v ?
     * @param e touch event
     * @return true
     */
    @Override
    public boolean onTouch(View v, MotionEvent e){

        int action = e.getAction();
        switch (action){

            case ACTION_DOWN :
                if(first==true){
                    x_drag_start=e.getX();
                    y_drag_start=e.getY();
                    x_win_start = myWin.getX();
                    y_win_start = myWin.getY();
                }
                else{
                    ((ViewGroup) myWin.getParent()).removeView(myWin);
                    myWin.setX(x_win_start+e.getX()-x_drag_start);
                    myWin.setY(y_win_start+e.getY()-y_drag_start);
                    setContentView(myWin);
                }

                break;
            case ACTION_UP :
                first=false;
                break;
        }

        return true;
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
}


