package com.example.longinapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.lights.Light;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    ShapeableImageView imgProfile;
    TextView tvName;
    BottomNavigationView bottomNavigationView;
    ArrayList<HashMap<String,String>>arrayList = new ArrayList<>();
    HashMap<String,String> hashMap = new HashMap<>();
    RecyclerView recyclerView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-----------app bar color code start-----------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Window window = getWindow();
            window.setStatusBarColor(getResources().getColor(android.R.color.holo_red_dark));
        }
        //-----------app bar color code finish-----------------

        sharedPreferences = getSharedPreferences("myApp",MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");

        imgProfile = findViewById(R.id.imgProfile);
        tvName = findViewById(R.id.tvName);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        recyclerView = findViewById(R.id.recyclerView);








        //-------------server Key start----------
        try {
            MyMethods.MY_KEY = MyMethods.encryptData("udoy@das");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (email.length()<=0){
            startActivity(new Intent(MainActivity.this, LongIn.class));
            finish();
        }else {
            JsonObjectRequest();
        }
        //-------------server Key finish----------







        /*
        //--------------btn code start----------------
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email","");
                editor.apply();

                startActivity(new Intent(MainActivity.this,LongIn.class));
                finish();

            }
        });
        //--------------btn code finish----------------

         */



        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId()==R.id.home){

                } else if (item.getItemId()==R.id.oder){

                }

                return false;
            }
        });










        //---------------------------------------------
    }
        //---------------------------------------------





    //------------JsonObjectRequest code start--------
    private void JsonObjectRequest(){

        String url = "https://udoydas.xyz/app/smartApp/smartHome.php";
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("key",MyMethods.MY_KEY);
            jsonObject.put("email",sharedPreferences.getString("email",""));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {

                    String name = jsonObject.getString("name");
                    String email = jsonObject.getString("email");
                    String image = jsonObject.getString("image");

                    tvName.setText(name);
                    //tvEmail.setText(email);
                    Glide.with(MainActivity.this).load(image).into(imgProfile);



                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Response")
                        .setMessage(volleyError.getMessage())
                        .setPositiveButton("OK",null)
                        .create()
                        .show();

            }
        });
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(jsonObjectRequest);



    }
    //------------JsonObjectRequest code finish --------



    //------------ProductListFast code start--------
    private void ProductListFast(){

        String url = "";
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {


                try {

                    for (int i=0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String product_name = jsonObject.getString("product_name");
                        String product_price = jsonObject.getString("product_price");
                        String product_image = jsonObject.getString("product_image");
                        String product_description = jsonObject.getString("product_description");

                        hashMap = new HashMap<>();
                        hashMap.put("product_name",product_name);
                        hashMap.put("product_price",product_price);
                        hashMap.put("product_image",product_image);
                        hashMap.put("product_description",product_description);
                        arrayList.add(hashMap);


                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(jsonArrayRequest);

    }
    //------------ProductListFast code finish--------



    //------------ProductListFast code finish--------
    private class ProductListFastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


        private class ViewHolder extends RecyclerView.ViewHolder{

            ImageView product_image;
            TextView product_name, product_price;
            Button button;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                product_image = itemView.findViewById(R.id.product_image);
                product_name = itemView.findViewById(R.id.product_name);
                product_price = itemView.findViewById(R.id.product_price);
                button = itemView.findViewById(R.id.button);

            }
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            LayoutInflater layoutInflater = getLayoutInflater();
            View MyView = layoutInflater.inflate(R.layout.product_item,parent,false);


            return new ViewHolder(MyView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {




        }

        @Override
        public int getItemCount() {
            return arrayList.size() ;
        }



    }
    //------------ProductListFast code finish--------








}