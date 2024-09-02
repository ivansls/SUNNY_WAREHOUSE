package com.example.sunny_warehouse.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sunny_warehouse.Classes.Product;
import com.example.sunny_warehouse.Fragments.AccountFragment;
import com.example.sunny_warehouse.Fragments.BasketFragment;
import com.example.sunny_warehouse.Fragments.MainFragment;
import com.example.sunny_warehouse.R;
import com.example.sunny_warehouse.Fragments.SearchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainAppActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_ref = db.collection("User");
    CollectionReference seller_ref = db.collection("Seller");

    BottomNavigationView bottomNavigationView;

    BottomNavigationView bottomNavigationView_seller;

    String login = "";
    String name = "";
    String filter = "";
    boolean isSeller = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        bottomNavigationView = findViewById(R.id.bottommenu);
        bottomNavigationView_seller = findViewById(R.id.bottommenuseller);
        SetFragment(new MainFragment(), "", "", "all");

        Bundle arguments = getIntent().getExtras();
        login = arguments.get("login").toString();


        user_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                    if (Objects.equals(documentSnapshot.getString("login").toString(), login)){
                        bottomNavigationView.setVisibility(View.VISIBLE);
                        name = documentSnapshot.getString("login");
                    }
                }
            }
        });

        seller_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                    if (Objects.equals(documentSnapshot.getString("login").toString(), login)){
                        bottomNavigationView_seller.setVisibility(View.VISIBLE);
                        //bottomNavigationView.setActivated(false);
                        //Toast.makeText(MainAppActivity.this, "logined", Toast.LENGTH_SHORT).show();
                        name = documentSnapshot.getId();
                        isSeller = true;
                        break;
                    }
                }

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home){
                    SetFragment(new MainFragment(), name, "NO", "all");
                    return true;
                }
                else if (id == R.id.search){
                    SetFragment(new SearchFragment(), name, "NO", "all");
                    return true;
                }
                else if (id == R.id.basket){
                    SetFragment(new BasketFragment(), name, "NO", "all");
                    return true;
                }
                else if (id == R.id.profile){
                    SetFragment(new AccountFragment(), name, "NO", "all");
                    return true;
                }

                return false;
            }

        });

        bottomNavigationView_seller.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {int id = item.getItemId();

                if (id == R.id.home){
                    SetFragment(new MainFragment(), "", "", "all");
                    return true;
                }
                else if (id == R.id.search){
                    SetFragment(new SearchFragment(), name, "YES", "all");
                    return true;
                }

                else if (id == R.id.profile){
                    SetFragment(new AccountFragment(), name, "YES", "all");
                    return true;
                }

                return false;
            }
        });
    }

    public void SetFragment(Fragment fragment, String name, String seller, String filter){
        Bundle bundle = new Bundle();
        bundle.putString("login", login);
        bundle.putString("name", name);
        bundle.putString("seller", seller);
        bundle.putString("filter", filter);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frgm, fragment, null)
                .commit();


    }
}