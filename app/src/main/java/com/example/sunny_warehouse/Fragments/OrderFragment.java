package com.example.sunny_warehouse.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sunny_warehouse.Activity.MainAppActivity;
import com.example.sunny_warehouse.Adapters.itemAdapterBasket;
import com.example.sunny_warehouse.Adapters.itemAdapterOrder;
import com.example.sunny_warehouse.Classes.Order;
import com.example.sunny_warehouse.Classes.Product;
import com.example.sunny_warehouse.Classes.Seller;
import com.example.sunny_warehouse.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class OrderFragment extends Fragment {
    RecyclerView recyclerView;
    List<Order> list;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference user_ref = db.collection("User");
    CollectionReference seller_ref = db.collection("Seller");
    CollectionReference order_ref = db.collection("Order");
    CollectionReference product_ref = db.collection("Product");
    String login;

    String isSeller;
    String name;

    Product product;
    List<String> id;
    List<String> id_product;

    List<Product> pr_list;
    List<Seller> sel_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        list = new ArrayList<Order>(){};
        pr_list = new ArrayList<Product>(){};
        sel_list = new ArrayList<Seller>(){};

        recyclerView = view.findViewById(R.id.rv);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isSeller = bundle.getString("seller", "");
            name = bundle.getString("name", "");
        }
        //id_product = new ArrayList<String>(){};
        //Toast.makeText( ((MainAppActivity) getActivity()), name, Toast.LENGTH_SHORT).show();
        //Toast.makeText( ((MainAppActivity) getActivity()), isSeller, Toast.LENGTH_SHORT).show();



        order_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    if (Objects.equals(isSeller, "YES")){
                        //Toast.makeText( ((MainAppActivity) getActivity()), "seller", Toast.LENGTH_SHORT).show();
                        if (Objects.equals(name, documentSnapshot.getString("seller"))){
                            //Toast.makeText( ((MainAppActivity) getActivity()), name, Toast.LENGTH_SHORT).show();
                            if (Objects.equals(documentSnapshot.getString("seller"), name)){
                                Order order = new Order(documentSnapshot.getString("product"), documentSnapshot.getString("login"), documentSnapshot.getString("count"), documentSnapshot.getString("seller"));
                                //id_product.add(documentSnapshot.getId());
                                list.add(order);
                            }

                        }
                    }
                    else {
                        //Toast.makeText(((MainAppActivity) getActivity()), documentSnapshot.getString("login") + " " + name, Toast.LENGTH_SHORT).show();
                        if (Objects.equals(documentSnapshot.getString("login"), name)){
                            Order order = new Order(documentSnapshot.getString("product"), documentSnapshot.getString("login"), documentSnapshot.getString("count"), documentSnapshot.getString("seller"));
                            //id_product.add(documentSnapshot.getId());
                            list.add(order);
                        }
                    }
                }
                //Toast.makeText(((MainAppActivity) getActivity()), list.get(0).getLogin() + " asd", Toast.LENGTH_SHORT).show();



            }
        });

        product_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshots : task.getResult()){
                    for (Order o : list){
                        if (documentSnapshots.getId().equals(o.getProduct())){
                            product = new Product(documentSnapshots.getString("productName"),
                                    documentSnapshots.getString("category"),
                                    documentSnapshots.getString("seller"),
                                    documentSnapshots.getString("fotoPath"),
                                    documentSnapshots.getString("coast"),
                                    documentSnapshots.getString("info"));
                            pr_list.add(product);
                        }
                    }

                }

            }
        });


        seller_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    for (Order o : list){
                        if (documentSnapshot.getId().equals(o.getSeller())){
                            Seller product = new Seller(documentSnapshot.getString("login"),
                                    documentSnapshot.getString("marketName"),
                                    documentSnapshot.getString("address"));
                            sel_list.add(product);
                        }
                    }
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                itemAdapterOrder adapter = new itemAdapterOrder(getContext(), list, pr_list, sel_list);
                recyclerView.setAdapter(adapter);
            }
        });



        return view;
    }
}