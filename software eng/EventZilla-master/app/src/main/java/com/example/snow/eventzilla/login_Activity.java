package com.example.snow.eventzilla;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;


public class login_Activity extends Activity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    private EditText userName, userPassword;
    private Toast mToast;
    private ProgressBar mProgressBar;
    private ArrayList<String> event;
    private ArrayList<ArrayList<String>> two_d;
    private ProgressDialog dialog;
    private List eventList;
    private final static String TAG = "Login_Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.loginlayout);
        getActionBar().hide();



        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            Log.d(TAG, "Already logged in" + ParseUser.getCurrentUser().getSessionToken());
            getinfo();
        }


        mToast = Toast.makeText(getApplicationContext(), "invalid user/password", Toast.LENGTH_SHORT);

        // Enable Local Datastore.

        mProgressBar = (ProgressBar)findViewById(R.id.login_progress_bar);
        mProgressBar.setVisibility(View.GONE);

        //get text fields
        userName = (EditText) findViewById(R.id.editText);
        userName.setHint(R.string.username_text);
        userPassword = (EditText) findViewById(R.id.editText2);
        userPassword.setHint(R.string.password_text);

        Button button1 = (Button) findViewById(R.id.button);


        //try to log in
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //disables the touch on the activity and turns on the progress bar
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                mProgressBar.setVisibility(View.VISIBLE);
                //try to login to parse
                Log.d(TAG, "Trying to login");
                userName.refreshDrawableState();
                userPassword.refreshDrawableState();


                ParseUser.logInInBackground(userName.getText().toString(), userPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, com.parse.ParseException e) {

                        //Activity goes back to normal
                        mProgressBar.setVisibility(View.GONE);
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        if (user != null) {
                            // Hooray! The user is logged in.
                            mProgressBar.setVisibility(View.GONE);
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Log.d(TAG,"User logged in");
                            getinfo();
                        } else {
                                //check a query for the employee first login ever attempt

                            ParseQuery<ParseObject> queryEmployeetable = ParseQuery.getQuery("EmployeeTable");

                            queryEmployeetable.whereEqualTo("username", userName.getText().toString());
                            queryEmployeetable.whereEqualTo("password", userPassword.getText().toString());

                            queryEmployeetable.getFirstInBackground(new GetCallback<ParseObject>() {


                                public void done(ParseObject object, ParseException e) {
                                    if (object != null) {
                                        Log.d(TAG, "success checking employee table");

                                        //

                                        String role = object.getString("Role");
                                        String email = object.getString("Email");

                                        //create user
                                        ParseUser newUser = new ParseUser();
                                        newUser.setUsername(userName.getText().toString());
                                        newUser.setPassword(userName.getText().toString());
                                        newUser.setEmail(email);
                                        Log.d(TAG, email);
                                        newUser.put("Role", role);

                                        object.deleteInBackground(new DeleteCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                if(e == null)
                                                {
                                                    Toast.makeText(getApplicationContext(), "Deleted from table", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(getApplicationContext(), "Didn't work", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                        newUser.signUpInBackground(new SignUpCallback() {
                                            @Override
                                            public void done(ParseException e) {

                                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                                mProgressBar.setVisibility(View.GONE);
                                                if (e == null) {
                                                // Start an intent for the logged in activity
                                                    Log.d(TAG, "Already logged in");
                                                    Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                                                    getinfo();
                                                } else {
                                                    switch (e.getCode()) {
                                                        case ParseException.USERNAME_MISSING:
                                                            Toast.makeText(getApplicationContext(), "Missing Username", Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case ParseException.USERNAME_TAKEN:
                                                            Toast.makeText(getApplicationContext(), "Username Already Taken", Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case ParseException.INVALID_EMAIL_ADDRESS:
                                                            Toast.makeText(getApplicationContext(), "Invalid Email Address", Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case ParseException.EMAIL_TAKEN:
                                                            Toast.makeText(getApplicationContext(), "Email Address Already Taken", Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case ParseException.EMAIL_MISSING:
                                                            Toast.makeText(getApplicationContext(), "Missing Email Address", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
                                        });

                                    }
                                    else
                                    {
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        mProgressBar.setVisibility(View.GONE);
                                        Log.d(TAG, "User failed to log in from employee table");
                                        Toast.makeText(getApplicationContext(), "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }


        });
    }

    /**
     * Callback method from {@link com.example.snow.eventzilla.EventItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */

    /* fills the table before it goes into the main page */
    private void getinfo() {
        final Intent intent = new Intent(getApplicationContext(), mainView_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        event = new ArrayList<String>();
        two_d = new ArrayList<ArrayList<String>>();


        dialog = new ProgressDialog(login_Activity.this);
        dialog.setMessage("Loading Content");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        ParseQuery<ParseObject> queryEventTable = ParseQuery.getQuery("EventTable");
        queryEventTable.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if (e == null) {
                    eventList = parseObjects;
                    for (int i = 0; i < eventList.size(); i++) {
                        ParseObject p = (ParseObject) eventList.get(i);
                        event.add(p.getString("eventName"));
                        event.add(p.getString("eventDate"));
                        event.add(p.getString("eventEndDate"));
                        event.add(p.getString("time"));
                        event.add(p.getString("endTime"));
                        event.add(p.getString("location"));
                        two_d.add(event);
                    }
                    dialog.dismiss();
                    Log.d(TAG, "2d ListArray has " + two_d.size());
                    intent.putExtra("saved_data", two_d);
                    finish();
                    startActivity(intent);
                    Log.d(TAG, "Retrieved " + parseObjects.size() + " scores " + event.size());
                } else {
                    Log.d(TAG, "Error: " + e.getMessage());
                    ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                        @Override
                        public void done(ParseException e) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Error Try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}
