package com.example.fahad.pointofsale;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        i++;
        if(i>1 && i<3){
            Toast.makeText(getApplicationContext(), "Again backpress will close application", Toast.LENGTH_SHORT).show();
        }
        if(i>2){
            onDestroy();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
    }
}
