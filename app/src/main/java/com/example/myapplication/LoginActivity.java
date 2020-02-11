package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;



public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username = findViewById(R.id.signin_username);
        final EditText pwd = findViewById(R.id.signin_pwd);
        findViewById(R.id.signin_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Premier test d'action", Toast.LENGTH_LONG).show();
                doLogin(username.getText().toString(), pwd.getText().toString());
            }
        });
        findViewById(R.id.singup_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "s'enregister", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void doLogin(String username, String pwd) {
        new SinginAsyncTask().execute(username, pwd);
    }

    protected class SinginAsyncTask extends AsyncTask<String, Void, HttpResult> {
        @Override
        protected HttpResult doInBackground(String... strings) {
            if (!NetworkHelper.isInternetAvailable(LoginActivity.this)) {
                return null;
            }
            try {
                Map<String, String> p = new HashMap<>();
                p.put("username", strings[0]);
                p.put("pwd", strings[1]);
                String url = LoginActivity.this.getString(R.string.base_url) + "/signin";
                HttpResult result = NetworkHelper.doPost(url, p, null);

                return result;

                }catch(Exception e){
                    Log.e("SinginAsyncTask", "error on calling sigin", e);
                    return null;
                }

            }

            @Override
            public void onPostExecute ( final HttpResult result){
                if (result != null && result.json != null) {
                    Toast.makeText(LoginActivity.this, result.json, Toast.LENGTH_LONG).show();
                Intent i = new Intent(LoginActivity.this, MessagesActivity.class);
                try {
                    i.putExtra("jwt", JasonParser.getToken(result.json));
                    startActivity(i);
                } catch (JSONException e) {
                    Toast.makeText(LoginActivity.this, "Parse jwt", Toast.LENGTH_LONG).show();
                }

                }
                else {
                    Toast.makeText(LoginActivity.this, "Error encoding message",Toast.LENGTH_LONG).show();
                }
            }

        }

    }


