package com.example.sunny_warehouse.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sunny_warehouse.Classes.Basket;
import com.example.sunny_warehouse.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

/**

 */
public class ProductFragment extends Fragment {


    TextView name_, seller_, coast, info;
    ImageView img;

    Button btn;
    String login, name, seller;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference product_ref = db.collection("Product");

    CollectionReference basket_ref = db.collection("Basket");

    FirebaseStorage storage = FirebaseStorage.getInstance();

    String id;
    StorageReference storageReference = storage.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);


        name_ = view.findViewById(R.id.name);
        seller_ = view.findViewById(R.id.seller);
        coast = view.findViewById(R.id.coast);
        info = view.findViewById(R.id.info);
        img = view.findViewById(R.id.image);
        btn = view.findViewById(R.id.add);


        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            login = bundle.getString("login", "");
            name = bundle.getString("name", "");
            seller = bundle.getString("seller", "");
            //Log.d(Constants.LOG_TAG, "" + i);
        }

        product_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    if (Objects.equals(documentSnapshot.getString("productName"), name) &&
                            Objects.equals(documentSnapshot.getString("seller"), seller)){

                        StorageReference imeg = storageReference.child(documentSnapshot.getString("fotoPath").toString());

                        imeg.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show();
                                Glide.with(getContext())
                                        .load(uri)
                                        .into(img);
                            }
                        });

                        name_.setText(documentSnapshot.getString("productName").toString());
                        coast.setText(documentSnapshot.getString("coast").toString());
                        info.setText(documentSnapshot.getString("info").toString());

                        id = documentSnapshot.getId();
                    }
                }
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Basket basket = new Basket(id, login, "1", seller);
                basket_ref.add(basket);
            }
        });


        return view;
    }


}