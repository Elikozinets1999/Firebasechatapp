package il.co.appschool.firebasechatapp;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by elili on 3/14/2018.
 */
@IgnoreExtraProperties
public class FCM_Device_Tokens {
    private String token;

    public FCM_Device_Tokens() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
