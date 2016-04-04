package com.example.daddyz.turtleboys;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.daddyz.turtleboys.EventDetail.eventDetailFragment;
import com.example.daddyz.turtleboys.friendFeed.followListFragment;
import com.example.daddyz.turtleboys.friendFeed.followerListFragment;
import com.example.daddyz.turtleboys.my_experiences.statspagefragment;
import com.example.daddyz.turtleboys.my_experiences.myEvents;
import com.example.daddyz.turtleboys.settings.SettingsFragment;
import com.example.daddyz.turtleboys.subclasses.GigUser;
import com.example.daddyz.turtleboys.subclasses.User_Icon_Animation;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;


/**
 * Created by Jinbir on 6/13/2015.
 */
public class  experience_activity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView userName;
    private TextView userEmail;
    private ProgressBar mProgressBar;
    private boolean AnimationFlag;
    private boolean onceAnimateFlag;
    private SharedPreferences preferences;
    private FragmentManager fragManager;
    private ParseImageView imageView;
    private final GigUser currentUser = ParseUser.createWithoutData(GigUser.class, ParseUser.getCurrentUser().getObjectId());
    private final Animation User_Icon = new User_Icon_Animation(com.example.daddyz.turtleboys.subclasses.User_Icon_Animation.Rotate.RIGHT, User_Icon_Animation.Angle.TO_DEGREES_0, 500, false);
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        setContentView(R.layout.myexperiences1);
        // Enable Local Datastore.

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Experiences");



        //Retrieve preferences and prepare fragment listener to allow dynamic changes to preferences while in fragments
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        AnimationFlag = preferences.getBoolean("animation_preference", false);
        
        //Setup the Fragment Manager
        fragManager = getFragmentManager();
        fragManager.addOnBackStackChangedListener(getListener());

        //set username and email in the header and user image
        userName = (TextView) findViewById(R.id.username);
        userName.setText(currentUser.getUsername().toString());
        userEmail = (TextView) findViewById(R.id.email);
        userEmail.setText(currentUser.getEmail().toString());

        //get ParseImageView from xml
        ParseFile image = (ParseFile) currentUser.getUserImage();
        imageView = (ParseImageView) findViewById(R.id.profile_image);
        imageView.setParseFile(image);

        //load the image from the parse database
        imageView.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, com.parse.ParseException e) {
                // The image is loaded and displayed!
                int oldHeight = imageView.getHeight();
                int oldWidth = imageView.getWidth();
                Log.v("LOG!!!!!!", "imageView height = " + oldHeight);      // DISPLAYS 90 px
                Log.v("LOG!!!!!!", "imageView width = " + oldWidth);        // DISPLAYS 90 px

            }
        });

        //Animate the imageview if preferences permit
        if(AnimationFlag) {
            //hide user image until drawer is fully open if animations are enabled
            imageView.setVisibility(View.INVISIBLE);
        }

        if (!AnimationFlag) {
            statspagefragment fragment = new statspagefragment();
            fragManager.beginTransaction().replace(R.id.frame, fragment,"statspageFragment").addToBackStack("statspageFragment").commit();
        } else
            onceAnimateFlag = true;

        //this is the drawer

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.userStats:
                        statspagefragment fragment = new statspagefragment();
                        fragManager.beginTransaction().replace(R.id.frame, fragment,"statspageFragment").addToBackStack("statspageFragment").commit();
                        return true;
                    // For rest of the options we just show a toast on click
                    case R.id.main_page:
                        Intent intent = new Intent(getApplicationContext(), maindrawer.class);
                        startActivity(intent);
                        return true;
                    case R.id.myEvents:
                        myEvents eventHist = new myEvents();
                        fragManager.beginTransaction().replace(R.id.frame, eventHist,"myEventsFragment").addToBackStack("myEventsFragment").commit();
                        //Toast.makeText(getApplicationContext(), "Events Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.myFriends:
                        followListFragment fragment2 = new followListFragment();
                        fragManager.beginTransaction().replace(R.id.frame, fragment2,"followListFragment").addToBackStack("followListFragment").commit();
                        return true;
                    case R.id.myFollowers:
                        followerListFragment fragment3 = new followerListFragment();
                        fragManager.beginTransaction().replace(R.id.frame, fragment3,"followerListFragment").addToBackStack("followerListFragment").commit();
                        return true;
                    case R.id.logoutDrawer:
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(experience_activity.this);
                        alertDialog.setTitle("Logout");
                        alertDialog.setMessage("Are you sure you want to logout?");
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                mProgressBar.setVisibility(View.VISIBLE);
                                GigUser.logOutInBackground(new LogOutCallback() {
                                    @Override
                                    public void done(com.parse.ParseException e) {
                                        finish();
                                        Intent intent = new Intent(getApplicationContext(), maindrawer.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(), "Logout Successful!", Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                        mProgressBar.setVisibility(View.GONE);
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    }
                                });
                            }
                        });
                        //alertDialog.setIcon(R.drawable.icon);
                        AlertDialog alert = alertDialog.create();
                        alert.show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.myexperiencesdrawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                if(AnimationFlag)
                    imageView.setVisibility(View.INVISIBLE);
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                if(AnimationFlag) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.startAnimation(User_Icon);
                }
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        //Setup Progress Bar
        mProgressBar = (ProgressBar)findViewById(R.id.login_progress_bar);
        mProgressBar.setVisibility(View.GONE);



    }

    //logout menu and settings menu probably need to take out

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Fragment fragment = new SettingsFragment();
            fragManager.beginTransaction().replace(R.id.myexperiencesdrawer, fragment,"SettingsFragment").addToBackStack("SettingsFragment").commit();
            Toast.makeText(getApplicationContext(), "Settings Selected", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_gallery) {
            Intent intent = new Intent(getApplicationContext(), gallery1.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d("Test","This is being called in my_experiences");

        if(getFragmentManager().getBackStackEntryCount() != 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

        FrameLayout frame =(FrameLayout) findViewById(R.id.frame);
        if(frame.getVisibility() == View.INVISIBLE){
            frame.setVisibility(View.VISIBLE);

        }
    }

    private OnBackStackChangedListener getListener(){
        //This is when a fragment is added or popped off the stack, use this section to update anything when a fragment is changed,
        //such as updating preferences on a fragment view after the settings fragment is finished
        OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                //Save old animation flag
                Boolean oldAnimationFlag = AnimationFlag;
                Boolean userInfoUpdateFlag = ((App_Application) getApplication()).getUserInfoUpdateFlag();

                AnimationFlag = preferences.getBoolean("animation_preference", false);
                if(AnimationFlag){
                    imageView.setVisibility(View.INVISIBLE);
                }else{
                    imageView.setVisibility(View.VISIBLE);
                }

                //Check if flag changed in case tab view needs to be updated
                if(userInfoUpdateFlag){
                    Fragment frag = fragManager.findFragmentByTag("statspageFragment");
                    fragManager.beginTransaction().hide(frag).commit();
                    //Update User Information
                    imageView.setParseFile(currentUser.getUserImage());
                    //load the image from the parse database
                    imageView.loadInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, com.parse.ParseException e) {
                            // The image is loaded and displayed!
                            int oldHeight = imageView.getHeight();
                            int oldWidth = imageView.getWidth();
                            Log.v("LOG!!!!!!", "imageView height = " + oldHeight);      // DISPLAYS 90 px
                            Log.v("LOG!!!!!!", "imageView width = " + oldWidth);        // DISPLAYS 90 px

                            statspagefragment fragment = new statspagefragment();
                            fragManager.beginTransaction().replace(R.id.frame, fragment,"statspageFragment").addToBackStack("statspageFragment").commit();
                            ((App_Application) getApplication()).setUserInfoUpdateFlag(false);
                        }
                    });

                    userName.setText(currentUser.getUsername());
                    userEmail.setText(currentUser.getEmail());

                }else if (AnimationFlag != oldAnimationFlag){
                    Fragment frag = fragManager.findFragmentByTag("statspageFragment");
                    fragManager.beginTransaction().hide(frag).commit();
                    statspagefragment fragment = new statspagefragment();
                    fragManager.beginTransaction().replace(R.id.frame, fragment,"statspageFragment").addToBackStack("statspageFragment").commit();
                    ((App_Application) getApplication()).setUserInfoUpdateFlag(false);
                }

                eventDetailFragment myFragment = (eventDetailFragment)fragManager.findFragmentByTag("EventDetailFragment");
                SettingsFragment myFragment2 = (SettingsFragment) fragManager.findFragmentByTag("SettingsFragment");
                if ((myFragment != null && myFragment.isVisible()) || (myFragment2 != null && myFragment2.isVisible())) {
                    // add your code here
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }else{
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
            }
        };
        return result;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){

        if (hasFocus){
            if (AnimationFlag) {
                if (onceAnimateFlag) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //launch statspage
                            statspagefragment fragment = new statspagefragment();
                            fragManager.beginTransaction().replace(R.id.frame, fragment,"statspageFragment").addToBackStack("statspageFragment").commit();
                        }
                    }, 500);
                    onceAnimateFlag = false;
                }
            }
        }
    }

}
