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

public class TabletAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> listTablet;

    public TabletAdapter(Context context, ArrayList<Product> listTablet) {
        this.context = context;
        this.listTablet = listTablet;
    }

    @Override
    public int getCount() {
        return listTablet.size();
    }

    @Override
    public Object getItem(int position) {
        return listTablet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView textSPName, textSPPrice, textSPDescription;
        public ImageView imgTablet;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_tablet, null);
            viewHolder.textSPName = view.findViewById(R.id.textviewNameTablet);
            viewHolder.textSPPrice = view.findViewById(R.id.textviewPriceTablet);
            viewHolder.textSPDescription = view.findViewById(R.id.textviewDescripTablet);
            viewHolder.imgTablet = view.findViewById(R.id.imageviewTablet);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.textSPName.setText(product.getProName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textSPPrice.setText("Price: " + decimalFormat.format(product.getPrice()) + "đ");
        viewHolder.textSPDescription.setMaxLines(3);
        viewHolder.textSPDescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textSPDescription.setText(product.getDescription());
        Picasso.with(context).load(product.getProImage()).
                placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.imgTablet);
        return view;
    }

    public void setFilterdList(ArrayList<Product> listProducts) {
        this.listTablet = listProducts;

        notifyDataSetChanged();
    }
}
