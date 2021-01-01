package com.example.finalworks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.finalworks.entity.ActivityManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class AboutAuthorActivity extends AppCompatActivity  {


    TextView txvName, txvSno, txvDirection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_author);
        initiate();
        ActivityManager.getInstance().add(this);
    }

    public void initiate(){
        txvName = findViewById(R.id.txvName);
        txvDirection = findViewById(R.id.txvDirection);
        txvSno = findViewById(R.id.txvSno);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            ActivityManager.getInstance().remove(this);
            finish();
        }
        return true;
    }

}