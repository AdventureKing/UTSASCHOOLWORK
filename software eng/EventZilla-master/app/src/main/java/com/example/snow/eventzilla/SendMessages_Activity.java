package com.example.snow.eventzilla;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SendMessages_Activity extends Activity {

    private Button sendMessage;
    private Spinner mUserSpinner;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> userArrayListForSpinner;
    private ArrayList<String> userIdList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.user_send_message);

        sendMessage = (Button) findViewById(R.id.send_messages_sendButton);
        mUserSpinner = (Spinner) findViewById(R.id.role_spinner);
        userArrayListForSpinner = new ArrayList<String>();
        userIdList = new ArrayList<String>();


        fillUserArray();


        mUserSpinner = (Spinner) findViewById(R.id.sendMessage_userSpinner);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userArrayListForSpinner);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUserSpinner.setAdapter(mAdapter);


        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = mUserSpinner.getSelectedItemPosition();
                String currUserId = ParseUser.getCurrentUser().getObjectId();
                String toUserId = userIdList.get(x);

                String message = findViewById(R.id.user_send_message_field).toString();

                ParseObject gameScore = new ParseObject("UserMessages");
                gameScore.put("toUser", toUserId);
                gameScore.put("fromUser", "");
                gameScore.put("messageContent", message);
                gameScore.saveInBackground();

                startActivity(new Intent(SendMessages_Activity.this, UserMessages_Activity.class));
            }
        });
    }

    public void fillUserArray(){

        int userCount = 0;

        ParseQuery<ParseObject> queryUsers = ParseQuery.getQuery("EmployeeTable");

        queryUsers.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                if (e == null) {
                    Log.d("success", "Queried Users in sendMessages" + userList);
                    for (ParseObject user : userList) {

                        // This does not require a network access.
                        String userName = user.getString("username");
                        String userRole = user.getString("Role");
                        String userId = user.getObjectId();

                        String temp = userName + " : " + userRole;
                        userArrayListForSpinner.add(temp);
                        userIdList.add(userId);
                        Log.d("user", "USER: " + temp);
                    }
                } else {
                    Log.d("error", "Error querying users in sendMessages: " + e.getMessage());
                }
            }
        });
    }
}
