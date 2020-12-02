package com.example.weatherapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RetrieveDataActivity extends AppCompatActivity {
    ListView myListView;
    List<Weather> weatherList;

    DatabaseReference weatherDbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);

        myListView=findViewById(R.id.myListView);
        weatherList=new ArrayList<>();

        weatherDbRef= FirebaseDatabase.getInstance().getReference("Weather");

        weatherDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                weatherList.clear();

                for(DataSnapshot weatherDataSnap:dataSnapshot.getChildren()){
                    Weather weather=weatherDataSnap.getValue(Weather.class);
                    weatherList.add(weather);
                }
                ListAdapter adapter= new ListAdapter(RetrieveDataActivity.this,weatherList);
                myListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}