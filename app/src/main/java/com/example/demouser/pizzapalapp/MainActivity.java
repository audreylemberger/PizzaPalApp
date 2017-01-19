package com.example.demouser.pizzapalapp;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Indexables;
import com.google.firebase.appindexing.builders.PersonBuilder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_NEW_ITEM = 101;
    public static final String KEY_VIEW = "KEY_VIEW";
    private Pizza itemToEditHolder;
    private int itemToEditPosition = -1;

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView itemNameView;
        private TextView priceView;
        private ImageView ivIcon;

        private Button viewButton;

        public MessageViewHolder(View v) {
            super(v);
            itemNameView = (TextView) itemView.findViewById(R.id.itemNameView);
            priceView = (TextView) itemView.findViewById(R.id.priceView);
            ivIcon = (ImageView) itemView.findViewById(R.id.imageNote);
            viewButton = (Button) itemView.findViewById(R.id.viewItem);
            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), PizzaViewActivity.class);
                    intent.putExtra(KEY_VIEW, getAdapterPosition());
                    v.getContext().startActivity(intent);

                }
            });
        }
    }

    private static final String TAG = "MainActivity";
    public static final String MESSAGES_CHILD = "messages";
    private static final int REQUEST_INVITE = 1;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    private static final String MESSAGE_URL = "http://friendlychat.firebase.google.com/message/";



    private RecyclerView mMessageRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;


    // Firebase instance variables

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDataReference;
    private FirebaseRecyclerAdapter<Pizza, MessageViewHolder> mFirebaseadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = ANONYMOUS;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if(mFirebaseUser == null){
            startActivity(new Intent(this,SignInActivity.class));
            finish();
            return;
        }
        else{
            mUsername = mFirebaseUser.getDisplayName();
            if(mFirebaseUser.getPhotoUrl() != null){
                mPhotoUrl = mFirebaseUser.getProviderId().toString();
            }
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        // Initialize ProgressBar and RecyclerView.
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageRecyclerView = (RecyclerView) findViewById(R.id.messageRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);

        configureFirebaseAdapater();



//        mAddButton = (Button) findViewById(R.id.addPizzaButton);
//        mAddButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Send messages on click.
////                Pizza message = new FriendlyMessage(mMessageEditText.getText().toString(),
////                        mUsername,mPhotoUrl);
////                mFirebaseDataReference.child(MESSAGES_CHILD).push().setValue(message);
////                mMessageEditText.setText("");
//
//
//                //change to add pizza activity
//            }
//        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent mainIntent = new Intent(MainActivity.this,AddActivity.class);
            MainActivity.this.startActivity(mainIntent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        configureFirebaseAdapater();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void configureFirebaseAdapater(){
        mFirebaseDataReference = FirebaseDatabase.getInstance().getReference();
        //inner class of MainActivity
        mFirebaseadapter = new FirebaseRecyclerAdapter<Pizza, MessageViewHolder>(Pizza.class, R.layout.note_row, MessageViewHolder.class, mFirebaseDataReference.child("pizza")) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Pizza model, int position) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                viewHolder.itemNameView.setText(model.getBuilding() + " " +model.getRoom());
                viewHolder.priceView.setText(model.getToppings());


            }
        };

        mMessageRecyclerView.setLayoutManager(mLinearLayoutManager);
        mMessageRecyclerView.setAdapter(mFirebaseadapter);
    }

    //how to get position in list recycler
    public void showViewPizzaActivity(Pizza pizzaToView, int position) {
        //how to get position of recyclerview item?
        mMessageRecyclerView.getChildAdapterPosition(mMessageRecyclerView.getFocusedChild());
        //TODO
        Intent intent = new Intent(this, PizzaViewActivity.class);
        intent.putExtra(KEY_VIEW, position);
        startActivity(intent);
    }

    //get the correct name of the image resource given the dietary restrictions
    //TODO: integrate this somehow
    public String getPicName(boolean isVegan, boolean isVeg, boolean isKosher, boolean isGF) {
        StringBuilder builder = new StringBuilder();
        if(isVegan) {
            builder.append("vgn");
        }
        if(isVeg) {
            if(builder.length() == 0) {
                builder.append("veg");
            }
        }
        if(isKosher) {
            if(builder.length() > 0) {
                builder.append("_");
            }
            builder.append("ksh");
        }
        if(isGF) {
            if(builder.length() > 0) {
                builder.append("_");
            }
            builder.append("gf");
        }
        if(builder.length() == 0)
        {
            builder.append("none");
        }
        builder.append(".png");
        return builder.toString();
    }
}

