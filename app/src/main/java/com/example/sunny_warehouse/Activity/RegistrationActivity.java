package com.example.sunny_warehouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.sunny_warehouse.Classes.Seller;
import com.example.sunny_warehouse.Classes.Users;
import com.example.sunny_warehouse.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RegistrationActivity extends AppCompatActivity {

    EditText mail, pass, name, adres;

    Button next;

    RadioButton bauer, seller;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_ref = db.collection("User");
    CollectionReference seller_ref = db.collection("Seller");
    FirebaseAuth firebaseAuth;

    boolean isSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mail = findViewById(R.id.login_tv);
        pass = findViewById(R.id.password_tv);
        name = findViewById(R.id.name_tv);
        adres = findViewById(R.id.addres_id);

        next = findViewById(R.id.next);

        bauer = findViewById(R.id.byuer);
        seller = findViewById(R.id.seller);


        firebaseAuth = FirebaseAuth.getInstance();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bauer.isChecked()){
                    Toast.makeText(RegistrationActivity.this, "Покупатель", Toast.LENGTH_SHORT).show();
                    isSeller = false;
                    validData(mail.getText().toString(), pass.getText().toString(), isSeller);
                }
                else if (seller.isChecked()){
                    Toast.makeText(RegistrationActivity.this, "Продавец", Toast.LENGTH_SHORT).show();
                    isSeller = true;
                    validData(mail.getText().toString(), pass.getText().toString(), isSeller);
                }
            }
        });
    }

    private void validData(String login, String password, boolean isSeller) {
        if (!Patterns.EMAIL_ADDRESS.matcher(login).matches()){
            mail.setError("Неверный формат");
        }
        else if (password.length() < 6){
            pass.setError("<6");
        }
        else{
            registrationUser(login, password, isSeller);
        }
    }

    private void registrationUser(String login, String password, boolean isSeller) {
        firebaseAuth.createUserWithEmailAndPassword(login, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if (isSeller){
                            Seller users = new Seller(mail.getText().toString() ,name.getText().toString(), adres.getText().toString());
                            seller_ref.add(users);
                        }
                        else{
                            Users users = new Users(mail.getText().toString(), name.getText().toString());
                            user_ref.add(users);
                        }

                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String curentUserEmail = firebaseUser.getEmail();
                        //Toast.makeText(RegistrationActivity.this, "1 - " + curentUserEmail, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrationActivity.this, MainAppActivity.class);
                        //Intent intent = new Intent(MainActivity.this, CodeActivity.class);
                        intent.putExtra("login", mail.getText().toString());
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(RegistrationActivity.this, "2 - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}