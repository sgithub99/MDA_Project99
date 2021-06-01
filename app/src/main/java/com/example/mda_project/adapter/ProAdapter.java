package com.example.mda_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mda_project.R;
import com.example.mda_project.activity.DetailProduct;
import com.example.mda_project.model.Product;
import com.example.mda_project.util.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProAdapter extends RecyclerView.Adapter<ProAdapter.ItemHolder>{
    Context context;
    ArrayList<Product> listProducts;

    public ProAdapter(Context context, ArrayList<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
    }

    @Override
    public ItemHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_newestproduct, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Product product = listProducts.get(position);
        holder.textProName.setText(product.getProName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.textPrice.setText("Price: " + decimalFormat.format(product.getPrice()) + "đ");
        Picasso.with(context).load(product.getProImage()).placeholder(R.drawable.noimage)
                .error(R.drawable.error).into(holder.proImage);
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView proImage;
        public TextView textProName, textPrice;

        public ItemHolder(View itemView){
            super(itemView);
            proImage = itemView.findViewById(R.id.imageviewPro);
            textPrice = itemView.findViewById(R.id.textviewPrice);
            textProName = itemView.findViewById(R.id.textviewProName);
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailProduct.class);
                intent.putExtra("detailProduct", listProducts.get(getLayoutPosition()) );
//                CheckConnection.showToast_Short(context, listProducts.get(getLayoutPosition()).getProName());
                context.startActivity(intent);
            });
        }
    }
}
