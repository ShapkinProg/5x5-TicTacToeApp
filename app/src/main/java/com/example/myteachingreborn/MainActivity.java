package com.example.myteachingreborn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonstart;
    Button normal;
    Button hard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonstart = (Button) findViewById(R.id.buttonstart);
        normal = (Button) findViewById(R.id.Normal);
        hard = (Button) findViewById(R.id.Hard);
        buttonstart.setOnClickListener(this);
        normal.setOnClickListener(this);
        hard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(this, Aboard.class);
        if(view.getId() == R.id.Hard)
            intent.putExtra("reg", 2);
        else if(view.getId() == R.id.Normal)
            intent.putExtra("reg", 1);
        else if (view.getId() == R.id.buttonstart)
            intent.putExtra("reg", 0);
        startActivity(intent);
    }
}

