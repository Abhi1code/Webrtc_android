package com.socket.webrtc.util;

import android.content.Context;
import android.content.SharedPreferences;


public class Login_session {

    private Context context;
    private SharedPreferences user_session;
    private SharedPreferences.Editor editor;

    public Login_session(Context context) {
        this.context = context;
        user_session = context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
        editor = user_session.edit();
    }

    public void setName(final String name){

        editor.putString("name",name);
        editor.commit();
    }


    public void setSession(){
        editor.putBoolean("session",true);
        editor.commit();
    }

    public String getName(){
        return user_session.getString("name",null);
    }

    public boolean getSession(){
        return user_session.getBoolean("session",false);
    }

    public void reset_session(){

        editor.remove("name");
        editor.putBoolean("session",false);
    }
}
