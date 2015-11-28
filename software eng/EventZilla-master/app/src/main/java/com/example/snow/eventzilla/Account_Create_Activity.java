package com.example.snow.eventzilla;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

/**
 * Created by Jason on 4/4/15.
 */
public class Account_Create_Activity extends Activity {

    private EditText userName = null;
    private EditText userPassword = null;
    private EditText userEmail = null;
    private EditText userRoll = null;
    private Spinner mRoleSpinner;
    private ArrayAdapter<CharSequence> mAdapter;
    private ProgressBar mProgressBar;

    private Button Save;

    protected void onCreate(final Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.account_creation);

        //does loading circle when account is being created
        mProgressBar = (ProgressBar)findViewById(R.id.account_creation_progressbar);
        mProgressBar.setVisibility(View.GONE);

        /* copy references to CreateEvent.java */
        userName = (EditText) findViewById(R.id.username_text_edit);
        userPassword = (EditText) findViewById(R.id.password_text_edit);
        userEmail = (EditText) findViewById(R.id.email_text_edit);

        /* used to select the role of the user*/
        mRoleSpinner = (Spinner) findViewById(R.id.role_spinner);
        mAdapter = ArrayAdapter.createFromResource(this,R.array.spinner_array, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRoleSpinner.setAdapter(mAdapter);


        Button button = (Button) findViewById(R.id.create_account_button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("test","User pressed to add user to database");

                     /* Create employee and set fields */
                   ParseObject employee = new ParseObject("EmployeeTable");
                   employee.put("username", userName.getText().toString());
                   employee.put("Email", userEmail.getText().toString());
                   employee.put("password", userPassword.getText().toString());
                   employee.put("Role", mRoleSpinner.getSelectedItem().toString());


                if(userName.getText().toString().length() == 0) Toast.makeText(getApplicationContext(), "Missing Username", Toast.LENGTH_SHORT).show();
                else if(userEmail.getText().toString().length() == 0) Toast.makeText(getApplicationContext(), "Missing Email Address", Toast.LENGTH_SHORT).show();
                else if(userPassword.getText().toString().length() == 0) Toast.makeText(getApplicationContext(), "Missing Password", Toast.LENGTH_SHORT).show();
                else
                {
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    mProgressBar.setVisibility(View.VISIBLE);

                    employee.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            mProgressBar.setVisibility(View.GONE);
                            if(e == null)
                            {
                                Toast.makeText(getApplicationContext(), "Account Saved!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
            }
            //}
            //adding something

            public int checkFields(){
                if((userName.getText().toString().length() > 5) && (userPassword.getText().toString().length() > 5)
                   && (userRoll.getText().toString().length() > 1)
                   && (userEmail.getText().toString().length() > 3)){
                    return 0;

                }else{
                    return 1;
                }


            }
        });
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.logout_button:
                ParseUser.logOut();
                Intent intent = new Intent(getApplicationContext(), login_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
