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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
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

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
            String returnStr = null;
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder builder = new FormBody.Builder();
            builder.add("name","123");
            builder.add("password","123");

            RequestBody requestBody = builder.build();
            Request request = new Request.Builder()
                    .url("http://localhost:8080/cloud/test")
                    .post(requestBody)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                returnStr = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return returnStr;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s == null){
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(s);
                System.out.println(jsonObject.get("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);

        }
    }
    public static String readParse(String urlPath) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        URL url = new URL(urlPath);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        InputStream inStream = conn.getInputStream();
        while ((len = inStream.read(data)) != -1) {
            outStream.write(data, 0, len);
        }
        inStream.close();
        //通过out.Stream.toByteArray获取到写的数据
        return new String(outStream.toByteArray());
    }

}
