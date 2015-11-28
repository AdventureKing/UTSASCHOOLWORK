package com.example.snow.eventzilla;

import com.parse.ParseUser;

/**
 * Created by snow on 4/8/2015.
 */
public class SessionState {
    private int accessAllow = 1;
    private int accessNotAllow = 0;

    public String getRole() {
        return role;
    }

    private String role = ParseUser.getCurrentUser().get("Role").toString();

    public SessionState(){

    }

    public int eventCreationAccess(){
        if(role.equals("Admin") || role.equals("Event Manager"))
            return accessAllow;
        else
            return accessNotAllow;
    }
    public int userCreationAccess(){
        if(role.equals("Admin"))
            return accessAllow;
        else
            return accessNotAllow;
    }
    public int editTeam(){
        return accessNotAllow;
    }
    public int editEvent(){
        return accessNotAllow;
    }


}
