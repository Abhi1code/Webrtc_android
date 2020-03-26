package com.socket.webrtc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.socket.webrtc.util.Login_session;
import com.socket.webrtc.util.Type_converter;
import com.socket.webrtc.util.Utils;
import com.socket.webrtc.websocket.Socketconnection;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements Socketconnection.ServerListener{

    private EditText name;
    private Button login;
    private Socketconnection socketconnection;
    private Login_session login_session;
    private String user_name;
    private static boolean conn_status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        Toast.makeText(this, "" + BuildConfig.POSTS_URL, Toast.LENGTH_SHORT).show();
        Intent mServiceIntent = new Intent(getBaseContext(), LocationService.class);
        //mServiceIntent.setAction("com.bootservice.test.DataService");
        startService(mServiceIntent);

//        ComponentName receiver = new ComponentName(this, ServiceManager.class);
//        PackageManager pm = this.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);

//          FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
//          Job myJob = firebaseJobDispatcher.newJobBuilder()
//                .setService(Jobservice.class) // the JobService that will be called
//                  .setLifetime(Lifetime.FOREVER)
//                  .setRecurring(true)
//                .setTag("my-unique-tag")// uniquely identifies the job
//                  .setTrigger(Trigger.executionWindow(5,5))
//                  .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
//                  .setReplaceCurrent(false)
//                  .setConstraints(Constraint.ON_ANY_NETWORK)
//                .build();
//
//          firebaseJobDispatcher.mustSchedule(myJob);


        name = findViewById(R.id.name);
        login = findViewById(R.id.login);
        login_session = new Login_session(this);
        socketconnection = new Socketconnection(Utils.socket_conn_url);
        socketconnection.connect(this);

        login.setOnClickListener(view -> {
            user_name = this.name.getText().toString().trim();
            if (TextUtils.isEmpty(user_name) || user_name == null){
                return;
            }

            if (!conn_status){
                Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
                return;
            }
            login.setEnabled(false);
            String message = Type_converter.string_to_json(user_name, "login");
            socketconnection.sendMessage(message);

        });
    }

    @Override
    public void onNewMessage(String message) {
        try {
            JSONObject jObj = new JSONObject(message);
            if (!Type_converter.get_meta(jObj).equals("login")){
                return;
            }
            if (Type_converter.get_status(jObj).equals("false")){
                login.setEnabled(true);
                Toast.makeText(this, "username already exist", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d("ABHI","2ND");
            login_session.setName(Type_converter.get_name(jObj));
            login_session.setSession();
            socketconnection.disconnect();
            Intent intent = new Intent(this, Video_room.class);
            startActivity(intent);
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChange(Socketconnection.ConnectionStatus status) {

        if (status == Socketconnection.ConnectionStatus.CONNECTED){

            Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
            conn_status = true;
        } else {

            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            login.setEnabled(true);
            conn_status = false;
        }
    }
}
