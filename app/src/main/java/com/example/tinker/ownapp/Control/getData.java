package com.example.tinker.ownapp.Control;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by tinker on 2018/4/15.
 */

public class getData {

    public  static String getJsonDataFromUrl(String dataUrl){
        String  result= null;
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();

        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(dataUrl)
                .post(requestBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
