package com.example.longinapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LongIn extends AppCompatActivity {


    TextView tvGoToSignUp;
    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_in);

        //-----------app bar color code start-----------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(android.R.color.darker_gray));
        }
        //-----------app bar color code finish-----------------

        //--- Initialize ID Components ---
        tvGoToSignUp = findViewById(R.id.tvGoToSignUp);
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);











        //--------------tvGoToSignUp code start--------------
        tvGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LongIn.this, SingUp.class));
                finish();

            }
        });






        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String url = "https://udoydas.xyz/app/smartApp/smartLog.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        if (response.contains("LONG")){

                            SharedPreferences sharedPreferences = getSharedPreferences("myApp",MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email",etLoginEmail.getText().toString() );
                            editor.apply();


                            startActivity(new Intent(LongIn.this,MainActivity.class));
                            finish();

                        }



                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        new AlertDialog.Builder(LongIn.this)
                                .setTitle("Response")
                                .setMessage(volleyError.getMessage())
                                .setPositiveButton("OK",null)
                                .create()
                                .show();

                    }
                }){


                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map myMap = new HashMap<String, String>();
                        myMap.put("key",MyMethods.MY_KEY);
                        try {

                            myMap.put("email",MyMethods.encryptData(""+etLoginEmail.getText().toString() ));
                            myMap.put("password",MyMethods.encryptData(""+etLoginPassword.getText().toString() ));

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }



                        return myMap;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(LongIn.this);
                queue.add(stringRequest);


            }
        });






        //--------------------------------------------
    }
        //--------------------------------------------





















}