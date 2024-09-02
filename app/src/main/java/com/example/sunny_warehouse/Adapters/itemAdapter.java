package com.example.sunny_warehouse.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sunny_warehouse.Activity.MainAppActivity;
import com.example.sunny_warehouse.Classes.Product;
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

public class itemAdapter extends RecyclerView.Adapter<itemAdapter.ViewHolder>{
    private Context context;
    private List<Product> list;

    private String filter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference seller_ref = db.collection("Seller");



    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();


    public itemAdapter(Context context, List<Product> list, String filter) {
        this.context = context;
        this.list = list;
        this.filter = filter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product pr = list.get(position);


        if (Objects.equals(pr.getCategory(), filter) || Objects.equals(filter, "all")){
            //Toast.makeText(context, "dkdk", Toast.LENGTH_SHORT).show();
            StorageReference img = storageReference.child(pr.getFotoPath());

            img.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //
                    //
                    // Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show();
                    Glide.with(context)
                            .load(uri)
                            .into(holder.iv);
                }
            });

            holder.name.setText(pr.getProductName());
            holder.coast.setText(pr.getCoast());

            seller_ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        if (documentSnapshot.getId().equals(pr.getSeller())){
                            //holder.provider.setText(pr.getSeller().toString());
                            holder.provider.setText(documentSnapshot.getString("marketName"));
                        }



                    }
                }
            });
        }












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




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            iv = itemView.findViewById(R.id.item_iv);

            name = itemView.findViewById(R.id.name_tv);
            coast = itemView.findViewById(R.id.coast_tv);
            provider = itemView.findViewById(R.id.provider_tv);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    ((MainAppActivity) context).SetFragment(new ProductFragment(), list.get(position).getProductName(), list.get(position).getSeller(), "all");



                }
            });

        }
    }
}
