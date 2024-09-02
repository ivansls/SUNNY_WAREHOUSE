package com.example.sunny_warehouse.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sunny_warehouse.Activity.MainAppActivity;
import com.example.sunny_warehouse.Classes.Basket;
import com.example.sunny_warehouse.Classes.Order;
import com.example.sunny_warehouse.Classes.Product;
import com.example.sunny_warehouse.Classes.Seller;
import com.example.sunny_warehouse.Classes.Users;
import com.example.sunny_warehouse.Fragments.BasketFragment;
import com.example.sunny_warehouse.Fragments.ProductFragment;
import com.example.sunny_warehouse.ProductClass;
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

public class itemAdapterOrder extends RecyclerView.Adapter<itemAdapterOrder.ViewHolder>{
    private Context context;
    private List<Order> list;
    private List<Product> list_prod;
    private List<Seller> list_sel;
    Product product;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference order_ref = db.collection("Order");
    CollectionReference product_ref = db.collection("Product");



    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    public itemAdapterOrder(Context context, List<Order> list, List<Product> lstp, List<Seller> sellst) {
        this.context = context;
        this.list = list;
        list_prod = lstp;
        list_sel = sellst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try{
            Order pr = list.get(position);
            Product prod = list_prod.get(position);
            Seller sel = list_sel.get(position);
            //Toast.makeText(context, "fgh", Toast.LENGTH_SHORT).show();



            StorageReference img = storageReference.child(prod.getFotoPath().toString());

            img.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show();
                    Glide.with(context)
                            .load(uri)
                            .into(holder.iv);
                }
            });
//
            holder.name.setText(prod.getProductName().toString());
            holder.coast.setText(prod.getCoast().toString());
            holder.provider.setText(sel.getMarketName().toString());
            holder.adres.setText(sel.getAddress().toString());
        }
        catch (Exception e){

        }

//
//
//        basket_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
//                    if (Objects.equals(documentSnapshot.getId(), list_id.get(position))){
//                        holder.count.setText(documentSnapshot.getString("count").toString());
//                    }
//                }
//            }
//        });














    }
    //
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView iv;

        TextView coast;

        TextView adres;
        TextView provider;




        String id;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            name = itemView.findViewById(R.id.name_tv);
            iv = itemView.findViewById(R.id.item_iv);
            coast = itemView.findViewById(R.id.coast_tv);
            adres = itemView.findViewById(R.id.adres);
            provider = itemView.findViewById(R.id.provider_tv);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                  int position = getAdapterPosition();
//                  Product product = list.get(position);

//
//                    Kino kino = list.items.get(position);
//
//                    Intent intent = new Intent(context, InfoActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("id", kino.kinopoiskId);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("id", manga.getId());
//                    intent.putExtra("name", manga.getName());
//                    intent.putExtra("img", manga.getImg());
//                    intent.putExtra("info", manga.getDetail());
//                    intent.putExtra("author", manga.getFio());
//                    intent.putExtra("score", manga.getScore());
//                    context.startActivity(intent);
//                    MainActivity activity = (MainActivity) context;
//                    activity.overridePendingTransition(R.anim.animation,R.anim.anim2);



//                }
//            });

        }
    }
}
