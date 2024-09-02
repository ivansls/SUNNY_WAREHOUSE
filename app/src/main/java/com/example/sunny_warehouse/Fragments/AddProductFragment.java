package com.example.sunny_warehouse.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sunny_warehouse.Activity.MainAppActivity;
import com.example.sunny_warehouse.Adapters.itemAdapter;
import com.example.sunny_warehouse.Classes.Product;
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


public class AddProductFragment extends Fragment {

    RecyclerView rv;
    Button add;
    String isSeller;
    List<Product> list;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference prod_ref = db.collection("Product");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isSeller = bundle.getString("name", "");
        }

        rv = view.findViewById(R.id.rv);
        add = view.findViewById(R.id.addProd);
        list = new ArrayList<Product>(){};


        prod_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                    if (Objects.equals(documentSnapshot.getString("seller"), isSeller)){
                        Product pr = new Product(documentSnapshot.getString("ProductName"),
                                documentSnapshot.getString("category"), documentSnapshot.getString("seller"),
                                documentSnapshot.getString("fotoPath"), documentSnapshot.getString("coast"),documentSnapshot.getString("info"));
                        list.add(pr);
                    }

                }

                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                rv.setHasFixedSize(true);
                itemAdapter adapter = new itemAdapter(getContext(), list, "all");
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

                rv.setLayoutManager(layoutManager);
                rv.setAdapter(adapter);
            }
        });

        //Toast.makeText(getContext(), isSeller, Toast.LENGTH_SHORT).show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainAppActivity) getActivity()).SetFragment(new AddSellerProductFragment(), isSeller, "YES", "all");
            }
        });

        return view;
    }
}