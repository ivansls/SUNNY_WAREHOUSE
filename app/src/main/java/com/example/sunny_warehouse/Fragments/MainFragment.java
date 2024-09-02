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
import android.widget.Toast;

import com.example.sunny_warehouse.Classes.Product;
import com.example.sunny_warehouse.R;
import com.example.sunny_warehouse.Adapters.itemAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    RecyclerView recyclerView;
    List<Product> list;


    String isSeller;
    String name;
    String filter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference prod_ref = db.collection("Product");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = view.findViewById(R.id.rv);
        list = new ArrayList<Product>(){};

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isSeller = bundle.getString("seller", "");
            name = bundle.getString("name", "");
            filter = bundle.getString("filter", "");
        }

        prod_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //Toast.makeText(getActivity(), task.getResult().toString(), Toast.LENGTH_SHORT).show();
                for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                    Product pr = new Product(documentSnapshot.getString("productName"),
                            documentSnapshot.getString("category"), documentSnapshot.getString("seller"),
                            documentSnapshot.getString("fotoPath"), documentSnapshot.getString("coast"),documentSnapshot.getString("info"));
                    list.add(pr);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                itemAdapter adapter = new itemAdapter(getContext(), list, filter);
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}