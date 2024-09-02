package com.example.sunny_warehouse.Adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sunny_warehouse.Activity.MainAppActivity;
import com.example.sunny_warehouse.Classes.Category;
import com.example.sunny_warehouse.Classes.Product;
import com.example.sunny_warehouse.Fragments.AddProductFragment;
import com.example.sunny_warehouse.Fragments.OrderFragment;
import com.example.sunny_warehouse.Fragments.ProductFragment;
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

import java.util.List;
import java.util.Objects;

public class itemAdapterAccount extends RecyclerView.Adapter<itemAdapterAccount.ViewHolder>{
    private Context context;
    private List<String> list;

    private String namel;

    private String isSeller;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference seller_ref = db.collection("Category");



    public itemAdapterAccount(Context context, List<String> list, String name, String isSeller) {
        this.context = context;
        this.list = list;
        this.namel = name;
        this.isSeller = isSeller;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_search, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String pr = list.get(position);

        //StorageReference img = storageReference.child(pr.getFotoPath().toString());


        holder.name.setText(pr.toString());


    }
    //
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.tv);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (Objects.equals(list.get(position), "Мои продукты")){
                        ((MainAppActivity) context).SetFragment(new AddProductFragment(), namel, "YES", "all");
                    }
                    if (Objects.equals(list.get(position), "Заказы")){
                        ((MainAppActivity) context).SetFragment(new OrderFragment(), namel, isSeller, "all");
                    }



                }
            });

        }
    }




}