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
import com.example.sunny_warehouse.Classes.Product;
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

public class itemAdapterBasket extends RecyclerView.Adapter<itemAdapterBasket.ViewHolder>{
    private Context context;
    private List<Product> list;

    List<String> list_id;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference basket_ref = db.collection("Basket");
    CollectionReference product_ref = db.collection("Product");



    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    public itemAdapterBasket(Context context, List<Product> list, List<String> list_id) {
        this.context = context;
        this.list = list;
        this.list_id = list_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_basket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product pr = list.get(position);
        //Toast.makeText(context, "fgh", Toast.LENGTH_SHORT).show();
        StorageReference img = storageReference.child(pr.getFotoPath().toString());

        img.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show();
                Glide.with(context)
                        .load(uri)
                        .into(holder.iv);
            }
        });

        holder.name.setText(pr.getProductName().toString());
        holder.coast.setText(pr.getCoast().toString());


        basket_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                    if (Objects.equals(documentSnapshot.getId(), list_id.get(position))){
                        holder.count.setText(documentSnapshot.getString("count").toString());
                    }
                }
            }
        });














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

        TextView provider;

        Button plus;

        Button minus;

        Button delete;

        TextView count;



        String id;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            iv = itemView.findViewById(R.id.item_iv);

            name = itemView.findViewById(R.id.name_tv);
            coast = itemView.findViewById(R.id.coast_tv);
            provider = itemView.findViewById(R.id.provider_tv);

            plus = itemView.findViewById(R.id.plus);

            minus = itemView.findViewById(R.id.minus);

            delete = itemView.findViewById(R.id.delete);

            count = itemView.findViewById(R.id.count);



            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    int pl = Integer.valueOf((String) count.getText()) + 1;
                    count.setText(Integer.toString(pl).toString());
                    //Toast.makeText(context, Integer.toString(pl), Toast.LENGTH_SHORT).show();


                    basket_ref.document(list_id.get(position).toString())
                            .update("count", Integer.toString(pl).toString());




                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    int mi = Integer.valueOf((String) count.getText()) - 1;
                    count.setText(Integer.toString(mi).toString());

                    basket_ref.document(list_id.get(position).toString())
                            .update("count", Integer.toString(mi).toString());
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    basket_ref.document(list_id.get(position).toString())
                            .delete();

                    ((MainAppActivity) context).SetFragment(new BasketFragment(), "", "", "all");

                }
            });


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
