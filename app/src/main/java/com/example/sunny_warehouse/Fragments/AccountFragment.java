package com.example.sunny_warehouse.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sunny_warehouse.Activity.MainAppActivity;
import com.example.sunny_warehouse.Adapters.ItemAdapterSearch;
import com.example.sunny_warehouse.Adapters.itemAdapterAccount;
import com.example.sunny_warehouse.Classes.Category;
import com.example.sunny_warehouse.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    RecyclerView recyclerView;
    List<String> list;
    String isSeller;
    String name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        recyclerView = view.findViewById(R.id.rv);

        list = new ArrayList<String>(){};

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            isSeller = bundle.getString("seller", "");
            name = bundle.getString("name", "");
        }


        if (Objects.equals(isSeller, "YES")){
            list.add("Мои продукты");
            list.add("Заказы");
        }
        else {
            list.add("Заказы");
        }


        //Toast.makeText( ((MainAppActivity) getActivity()), name, Toast.LENGTH_SHORT).show();


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        itemAdapterAccount adapter = new itemAdapterAccount(getContext(), list, name, isSeller);
        recyclerView.setAdapter(adapter);
        return view;
    }
}