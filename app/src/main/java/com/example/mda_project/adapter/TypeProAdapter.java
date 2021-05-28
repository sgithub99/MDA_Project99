package com.example.mda_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mda_project.R;
import com.example.mda_project.model.TypeProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TypeProAdapter extends BaseAdapter {
    ArrayList<TypeProduct> typeProducts;
    Context context;

    public TypeProAdapter(ArrayList<TypeProduct> typeProducts, Context context) {
        this.typeProducts = typeProducts;
        this.context = context;
    }

    @Override
    public int getCount() {
        return typeProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return typeProducts.get(position);
    }

    public class ViewHolder {
        TextView textTypeProName;
        ImageView imgTypePro;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_listview_typeproduct, null);
            viewHolder.textTypeProName = view.findViewById(R.id.textviewTypePro);
            viewHolder.imgTypePro = view.findViewById(R.id.imageviewTypePro);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        TypeProduct typeProduct = (TypeProduct) getItem(position);
        viewHolder.textTypeProName.setText(typeProduct.getProName());
        Picasso.with(context).load(typeProduct.getProImage()).placeholder(R.drawable.noimage)
                .error(R.drawable.error).into(viewHolder.imgTypePro);
        return view;
    }
}
