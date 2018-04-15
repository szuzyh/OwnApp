package com.example.tinker.ownapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.tinker.ownapp.Module.MSG;
import com.example.tinker.ownapp.Module.Path;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.tinker.ownapp.Control.getData.getJsonDataFromUrl;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        handler = new uiHandle();
        handler.sendEmptyMessage(MSG.Test.MSG_GET_TEST_DATE);
        new getTestDateTask().execute();

    }

    public static class uiHandle extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG.Test.MSG_GET_TEST_DATE:

                    break;
                default:
                    break;
            }
        }
    }

    private class getTestDateTask extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {

            return getJsonDataFromUrl(Path.basePath+Path.testPath);
        }

        @Override
        protected void onPostExecute(String s) {
            if(s == null){
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                mTextMessage.setText("get user:"+jsonObject.get("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);

        }
    }
}
