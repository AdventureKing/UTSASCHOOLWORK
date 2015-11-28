package com.example.snow.eventzilla; /**
 * Created by snow on 4/4/2015.
 */
import android.app.Application;
import com.parse.Parse;

public class App_Application extends Application {

    // two-time authentication keys
    // used to access database

    @Override public void onCreate() {

        Parse.initialize(this, "BO3RiDOPRRlyBGcAoEJJL0WqCtyDj5CUPNDvllCM", "JAZsZn3KXYfEM5lccUxF0AaUqGIfJidH5DYvxSf4"); // Your Application ID and Client Key are defined elsewhere
    }
}
