package com.example.longinapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SingUp extends AppCompatActivity {


    ImageView imgProfile;
    EditText etName, etNumber, etEmail, etPassword;
    Button btnSignUp, btnLogIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);


        //------------app bar code start---------------
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.setStatusBarColor(getResources().getColor(android.R.color.holo_blue_dark));
        }
        //------------app bar code finish---------------

        //--- Initialize ID Components ---
        imgProfile = findViewById(R.id.imgProfile);
        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);









        //--------------btnSignUp code start--------------

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String mobile = etNumber.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                BitmapDrawable bitmapDrawable = (BitmapDrawable) imgProfile.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,80,outputStream);
                byte[] imageByte = outputStream.toByteArray();
                String image = Base64.encodeToString(imageByte,Base64.DEFAULT);

                //--------------singRequest code --------------
                StringRequest(name,mobile,email,password,image);




            }
        });

        //--------------btnSignUp code finish--------------






        //--------------imgProfile code start--------------
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SingUp.this,LongIn.class));
                finish();

            }
        });


        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode()==RESULT_OK) {

                    Intent intent = result.getData();
                    Uri uri = intent.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(SingUp.this.getContentResolver(),uri);
                        imgProfile.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }else {


                }

            }
        });


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.with(SingUp.this)
                        .maxResultSize(1000,1000)
                        .compress(1024)
                        .createIntent(new Function1<Intent, Unit>() {
                            @Override
                            public Unit invoke(Intent intent) {
                                launcher.launch(intent);
                                return null;
                            }
                        });

            }
        });








        //--------------------------------------------
    }
        //--------------------------------------------



    //--------------singRequest code start--------------

    private void StringRequest(String name, String mobile, String email, String password, String image) {


        String url = "https://udoydas.xyz/app/smartApp/smartSing.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.contains("SingUp Success")){

                    SharedPreferences sharedPreferences = getSharedPreferences("myApp",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email",email);
                    editor.apply();


                    startActivity(new Intent(SingUp.this,MainActivity.class));
                    finish();

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                new AlertDialog.Builder(SingUp.this)
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
                myMap.put("name",name);
                try {

                    myMap.put("mobile",MyMethods.encryptData(mobile));
                    myMap.put("email",MyMethods.encryptData(email));
                    myMap.put("password",MyMethods.encryptData(password));

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                myMap.put("image",image);


                return myMap;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(SingUp.this);
        queue.add(stringRequest);

    }

    //-------------singRequest code finish--------------

















}