package com.example.snow.eventzilla;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static java.util.Calendar.APRIL;
import static java.util.Calendar.AUGUST;
import static java.util.Calendar.FEBRUARY;
import static java.util.Calendar.JANUARY;
import static java.util.Calendar.JULY;
import static java.util.Calendar.JUNE;
import static java.util.Calendar.MARCH;
import static java.util.Calendar.MAY;
import static java.util.Calendar.NOVEMBER;
import static java.util.Calendar.OCTOBER;
import static java.util.Calendar.SEPTEMBER;
import static java.util.Calendar.getInstance;


/**
 * Created by Brandon on 5/6/2015.
 */
public class UserMessages_Activity extends Activity {

    private Button refreshButton;
    private Button sendButton;
    private ListView listView;
    private ArrayList<String> messageList;

    private Spinner mUserSpinner;
    private ArrayAdapter<String> mAdapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.user_message_screen);

        refreshButton = (Button)findViewById(R.id.message_button_refresh);
        sendButton = (Button)findViewById(R.id.user_message_send);

        listView = (ListView) findViewById(R.id.userMessages_list);

        messageList = new ArrayList<String>();


        refreshMessageList();


        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              refreshMessageList();


            }
        });

        sendButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserMessages_Activity.this, SendMessages_Activity.class));
            }
        }));
    }

    public void refreshMessageList(){
        ParseQuery<ParseObject> queryMsgs = ParseQuery.getQuery("UserMessages");
        ParseUser currUser = ParseUser.getCurrentUser();

        queryMsgs.whereEqualTo("toUser", currUser.getObjectId());
        Log.d("user", currUser.getObjectId());

        queryMsgs.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> userList, ParseException e) {
                if (e == null) {
                    Log.d("success", "Queried Users in sendMessages" + userList);
                    for (ParseObject user : userList) {

                        // This does not require a network access.
                        String fromUser = user.getString("fromUser");
                        String message = user.getString("messageContent");


                        String temp = "From User " + fromUser + " : " + message;
                        if(!(messageList.contains(temp))) {
                            messageList.add(temp);
                        }
                    }
                } else {
                    Log.d("error", "Error querying users in sendMessages: " + e.getMessage());
                }
            }
        });
        Log.d("check", "The List: " + messageList);
        if(messageList.size() == 0){
            messageList.add("No Messages To Display");
        }else{
            messageList.remove("No Messages To Display");
        }
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, messageList);
        //mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listView.setAdapter(mAdapter);

    }
}
