package com.example.sunny_warehouse.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sunny_warehouse.Activity.MainAppActivity;
import com.example.sunny_warehouse.Classes.Product;
import com.example.sunny_warehouse.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class AddSellerProductFragment extends Fragment {

    Button addPhoto, save;
    ImageView tv;

    Spinner spinner;

    TextView name, coast, info;

    String id_categ;
    String select_item;
    String path_photo;


    StorageReference storageReference;
    LinearProgressIndicator progress;

    Uri image;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference categ_ref = db.collection("Category");

    CollectionReference prod_ref = db.collection("Product");


    List<String> list;
    List<String> list_id;

    String seller;


    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == RESULT_OK){
                if (o.getData() != null){
                    image = o.getData().getData();

                    Glide.with(((MainAppActivity) getActivity()).getApplicationContext()).load(image).into(tv);

                }
            }else {
                Toast.makeText(((MainAppActivity) getActivity()), "error", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_seller_product, container, false);


        FirebaseApp.initializeApp(((MainAppActivity) getActivity()));
        storageReference = FirebaseStorage.getInstance().getReference();

        //addPhoto = view.findViewById(R.id.addPhoto);
        save = view.findViewById(R.id.save);
        tv = view.findViewById(R.id.photo_product);
        spinner = view.findViewById(R.id.category);
        name = view.findViewById(R.id.name);
        coast = view.findViewById(R.id.coast);
        info = view.findViewById(R.id.info);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            seller = bundle.getString("name", "");
        }

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                activityResultLauncher.launch(intent);
            }
        });


        list = new ArrayList<String>();
        list_id = new ArrayList<String>();
        categ_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                    list.add(documentSnapshot.getString("Name"));
                    list_id.add(documentSnapshot.getId());
                }

                //String[] countries = { "Бразилия", "Аргентина", "Колумбия", "Чили", "Уругвай"};
                ArrayAdapter<String> adapter = new ArrayAdapter(((MainAppActivity) getActivity()), android.R.layout.simple_spinner_item, list.toArray());
                // Определяем разметку для использования при выборе элемента
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Применяем адаптер к элементу spinner
                spinner.setAdapter(adapter);
            }
        });




        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                select_item = (String)parent.getItemAtPosition(position);
                id_categ = list_id.get(position).toString();
                //selection.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage(image);
                Product product = new Product(name.getText().toString(), id_categ, seller, path_photo, coast.getText().toString(), info.getText().toString());
                prod_ref.add(product);
                //Toast.makeText((MainAppActivity) getActivity(), name.getText() + " " + id_categ + " " + seller, Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


    private void uploadImage(Uri image){
        path_photo = String.format("%s/", "Bread") + UUID.randomUUID().toString()+ ".jpeg";
        StorageReference reference = storageReference.child(path_photo);
        reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(((MainAppActivity) getActivity()), "Image class suc", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(((MainAppActivity) getActivity()), "Image class error", Toast.LENGTH_SHORT).show();
            }
        });
    }




}