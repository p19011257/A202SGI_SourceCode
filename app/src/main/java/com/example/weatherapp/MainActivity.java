package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView country,citys,temp,longitude,latitude,humidity,feels,pressure,wind;
    Button button;
    EditText editText;
    Button btnSaveData;
    Button btnRetrieveData;
    DatabaseReference weatherDbRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        country=findViewById(R.id.country);
        citys=findViewById(R.id.city);
        temp=findViewById(R.id.tempt);
        longitude=findViewById(R.id.Longitude);
        latitude=findViewById(R.id.Latitude);
        feels=findViewById(R.id.Feels);
        pressure=findViewById(R.id.Pressure);
        humidity=findViewById(R.id.Humidity);
        wind=findViewById(R.id.Wind);
        button=findViewById(R.id.button);
        editText=findViewById(R.id.editTextTextPersonName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findWeather();

            }
        });


        btnSaveData=findViewById(R.id.btnSaveData);

        weatherDbRef = FirebaseDatabase.getInstance().getReference().child("Weather");

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertWeatherData();

            }
        });



        btnRetrieveData=findViewById(R.id.btnRetrieveData);
        btnRetrieveData.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main_menu,menu);
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String citysName= citys.getText().toString();
        String temparature=temp.getText().toString();
       switch (item.getItemId()){
           case R.id.share_button:
               Intent sharingIntent= new Intent(Intent.ACTION_SEND);
               sharingIntent.setType("text/plain");
               String shareBody="Country:"+" "+citysName+" "+"Temperature:"+" "+temparature;
               String shareSubject="Your Subject";

               sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
               sharingIntent.putExtra(Intent.EXTRA_SUBJECT,shareSubject);
               startActivity(Intent.createChooser(sharingIntent,"Share Using"));
               break;


       }
       return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builer= new AlertDialog.Builder(this);

        builer.setMessage("Are you sure you want to Exit WeatherApp?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                });
        AlertDialog alertDialog=builer.create();
        alertDialog.show();
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnRetrieveData:
                startActivity(new Intent(MainActivity.this,RetrieveDataActivity.class));
                break;
        }
    }


    private void insertWeatherData(){

        String citysName= citys.getText().toString();
        String temparature=temp.getText().toString();
        String feeling=feels.getText().toString();


        Weather weather= new Weather(citysName,temparature,feeling);
        weatherDbRef.push().setValue(weather);
        Toast.makeText(MainActivity.this,"Inserted Successfully", Toast.LENGTH_SHORT).show();
    }


    public void findWeather(){
        final String city=editText.getText().toString();
        String url="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=462f445106adc1d21494341838c10019&units=metric";
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject object1= jsonObject.getJSONObject("sys");
                    String country_find= object1.getString("country");
                    country.setText(country_find);

                    String city_find= jsonObject.getString("name");
                    citys.setText(city_find);

                    JSONObject object2= jsonObject.getJSONObject("main");
                    double tempt= object2.getDouble("temp");
                    temp.setText(tempt+" °C");

                    JSONObject object3= jsonObject.getJSONObject("coord");
                    double lant= object3.getDouble("lat");
                    latitude.setText(lant+" °N");

                    JSONObject object4= jsonObject.getJSONObject("coord");
                    double lon= object4.getDouble("lon");
                    longitude.setText(lon+" °E");

                    JSONObject object5= jsonObject.getJSONObject("main");
                    Double feel= object5.getDouble("feels_like");
                    feels.setText(feel+" °C");

                    JSONObject object6= jsonObject.getJSONObject("main");
                    int press= object6.getInt("pressure");
                    pressure.setText(press+" hPa");

                    JSONObject object7= jsonObject.getJSONObject("main");
                    int humid= object7.getInt("humidity");
                    humidity.setText(humid+" %");

                    JSONObject object8= jsonObject.getJSONObject("wind");
                    double windspeed= object8.getDouble("speed");
                    wind.setText(windspeed+" km/h");
















                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}