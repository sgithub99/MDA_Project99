package com.example.mda_project.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mda_project.R;
import com.example.mda_project.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> listLaptop;

    public LaptopAdapter(Context context, ArrayList<Product> listLaptop) {
        this.context = context;
        this.listLaptop = listLaptop;
    }

    @Override
    public int getCount() {
        return listLaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return listLaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView textLaptopName, textLaptopPrice, textLaptopDescription;
        public ImageView imgLaptop;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new LaptopAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_laptop, null);
            viewHolder.textLaptopName = view.findViewById(R.id.textviewNameLaptop);
            viewHolder.textLaptopPrice = view.findViewById(R.id.textviewPriceLaptop);
            viewHolder.textLaptopDescription = view.findViewById(R.id.textviewDescripLaptop);
            viewHolder.imgLaptop = view.findViewById(R.id.imageviewLaptop);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.textLaptopName.setText(product.getProName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textLaptopPrice.setText("Price: " + decimalFormat.format(product.getPrice()) + "đ");
        viewHolder.textLaptopDescription.setMaxLines(2);
        viewHolder.textLaptopDescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textLaptopDescription.setText(product.getDescription());
        Picasso.with(context).load(product.getProImage()).
                placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.imgLaptop);
        return view;
    }
}
