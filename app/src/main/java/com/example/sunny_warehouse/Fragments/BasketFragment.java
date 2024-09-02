package com.example.sunny_warehouse.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sunny_warehouse.Adapters.itemAdapter;
import com.example.sunny_warehouse.Adapters.itemAdapterBasket;
import com.example.sunny_warehouse.Classes.Category;
import com.example.sunny_warehouse.Classes.Order;
import com.example.sunny_warehouse.Classes.Product;
import com.example.sunny_warehouse.Classes.Users;
import com.example.sunny_warehouse.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BasketFragment#} factory method to
 * create an instance of this fragment.
 */
public class BasketFragment extends Fragment {

    RecyclerView recyclerView;
    List<Product> list;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_ref = db.collection("User");
    CollectionReference basket_ref = db.collection("Basket");
    CollectionReference product_ref = db.collection("Product");
    CollectionReference order_ref = db.collection("Order");
    String login;

    Button add;

    List<String> id;
    List<String> id_product;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        recyclerView = view.findViewById(R.id.rv);
        add = view.findViewById(R.id.add);
        list = new ArrayList<Product>(){};
        id = new ArrayList<String>(){};
        id_product = new ArrayList<String>(){};

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            login = bundle.getString("login", "");
            //Log.d(Constants.LOG_TAG, "" + i);
        }




        basket_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){

                    if (Objects.equals(login, documentSnapshot.getString("login"))){
                        id.add(documentSnapshot.getId());
                        product_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task1) {

                                for (QueryDocumentSnapshot documentSnapshot1 : task1.getResult()){
                                    if (Objects.equals(documentSnapshot1.getId(), documentSnapshot.getString("product"))){
                                        Product pr = new Product(documentSnapshot1.getString("productName"),
                                                documentSnapshot1.getString("category"), documentSnapshot1.getString("seller"),
                                                documentSnapshot1.getString("fotoPath"), documentSnapshot1.getString("coast"),documentSnapshot1.getString("info"));
                                        id_product.add(documentSnapshot1.getId());
                                        list.add(pr);

                                    }

                                }

                                //Toast.makeText(getContext(), Integer.toString(list.size()), Toast.LENGTH_SHORT).show();
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                recyclerView.setHasFixedSize(true);
                                itemAdapterBasket adapter = new itemAdapterBasket(getContext(), list, id);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                        //list.add(documentSnapshot.getString("Product"));
                        //Toast.makeText(getContext(), documentSnapshot.getString("Product"), Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                basket_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            Order order = new Order(documentSnapshot.getString("product"), documentSnapshot.getString("login"), documentSnapshot.getString("count"), documentSnapshot.getString("seller"));
                            order_ref.add(order);
                        }
                        list = new ArrayList<>();
                        id= new ArrayList<>();
                        itemAdapterBasket adapter = new itemAdapterBasket(getContext(), list, id);
                        recyclerView.setAdapter(adapter);
                    }
                });


            }
        });



        return view;
    }
}