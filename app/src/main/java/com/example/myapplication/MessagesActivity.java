package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesActivity extends AppCompatActivity {
    private String token;
    ListView list;
    MessageAdaptater adaptater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        list = findViewById(R.id.list);
        token =this.getIntent().getStringExtra("jwt");
        if ( token==null ){
            Toast.makeText(MessagesActivity.this, "erreur token", Toast.LENGTH_LONG).show();
        } else {
            new MessagesAsyncTask().execute(token);
            adaptater = new MessageAdaptater(this);
            list.setAdapter(adaptater);
        }

    }
    class MessagesAsyncTask extends AsyncTask<String, Void, HttpResult> {
        @Override
        protected HttpResult doInBackground(String... strings) {
            if (!NetworkHelper.isInternetAvailable(MessagesActivity.this)) {
                return null;
            }
            try {
                String url = MessagesActivity.this.getString(R.string.base_url) + "/messages";
                HttpResult result = NetworkHelper.doGet(url, null, token);
                return result;
            }
            catch (Exception e){
                Log.e("SinginAsyncTask", "error on calling sigin", e);
                return null;
            }
        }

          @Override
            public void onPostExecute(final HttpResult result) {
                if (result != null && result.json != null) {
                        // Convert the InputStream into a string
                    try {
                        adaptater.setMessages(JasonParser.getMessages(result.json));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

    }
}
