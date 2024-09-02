package com.example.sunny_warehouse.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sunny_warehouse.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    Button btn_sign, btn_reg;

    EditText login_ed, password_ed;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_sign = findViewById(R.id.button3);

        btn_reg = findViewById(R.id.button2);

        login_ed = findViewById(R.id.login_tv);

        password_ed = findViewById(R.id.password_tv);
        firebaseAuth = FirebaseAuth.getInstance();


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                //Intent intent = new Intent(MainActivity.this, CodeActivity.class);
                startActivity(intent);

            }
        });


        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent = new Intent(MainActivity.this, MainAppActivity.class);
//                startActivity(intent);
                validData(login_ed.getText().toString().trim(), password_ed.getText().toString().trim());
            }
        });
    }



    private void validData(String login, String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()){
            login_ed.setError("Неверный формат");
        }
        else if (password.length() < 6){
            password_ed.setError("<6");
        }
        else{
            registrationUser(login, password);
        }
    }

    private void registrationUser(String login, String password) {
        //Toast.makeText(this, login + " " + password, Toast.LENGTH_SHORT).show();
        firebaseAuth.signInWithEmailAndPassword(login,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                //Toast.makeText(MainActivity.this, "User login!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainAppActivity.class);
                //Intent intent = new Intent(MainActivity.this, CodeActivity.class);
                intent.putExtra("login", login_ed.getText().toString());
                startActivity(intent);
            }
            else {
                Toast.makeText(MainActivity.this, "Error occurred!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}