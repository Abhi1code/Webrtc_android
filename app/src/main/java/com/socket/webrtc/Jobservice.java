package com.socket.webrtc;

import android.os.AsyncTask;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class Jobservice extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {
        new Background(){
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(Jobservice.this, "Hello "+s, Toast.LENGTH_SHORT).show();
                jobFinished(job,false);
            }
        }.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }

    public static class Background extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            return "hello";
        }
    }
}
