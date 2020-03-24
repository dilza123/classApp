package com.example.classapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classapp.Database.DBHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView name,password;
    EditText uname,pass;
    Button addBtn,deleteBtn,updateBtn,viewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        uname = findViewById(R.id.ename);
        pass = findViewById(R.id.epassword);
        addBtn = findViewById(R.id.add);
        deleteBtn = findViewById(R.id.delete);
        updateBtn = findViewById(R.id.update);
        viewBtn = findViewById(R.id.selectall);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                dbHandler.addInfo(uname.getText().toString(),pass.getText().toString());

                Toast.makeText(MainActivity.this, "user added", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DBHandler dbHandler = new DBHandler(getApplicationContext());
               List user = dbHandler.readAllInfo(uname.getText().toString());

               if (user.isEmpty()){
                   Toast.makeText(MainActivity.this, "No user", Toast.LENGTH_SHORT).show();
                   uname.setText(null);
               }
               else{
                   uname.setText(user.get(0).toString());
                   pass.setText(user.get(1).toString());
               }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());

                Boolean status = dbHandler.updateInfo(uname.getText().toString(),pass.getText().toString()) ;
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHandler dbHandler = new DBHandler(getApplicationContext());
                dbHandler.deleteInfo(uname.getText().toString());

                uname.setText(null);
                pass.setText(null);
            }
        });
    }


}
