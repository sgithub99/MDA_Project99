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

public class SmartPhoneAdapter extends BaseAdapter {
    Context context;
    ArrayList<Product> listSmartPhone;

    public SmartPhoneAdapter(Context context, ArrayList<Product> listSmartPhone) {
        this.context = context;
        this.listSmartPhone = listSmartPhone;
    }

    @Override
    public int getCount() {
        return listSmartPhone.size();
    }

    @Override
    public Object getItem(int position) {
        return listSmartPhone.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView textSPName, textSPPrice, textSPDescription;
        public ImageView imgSmartPhone;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_smartphone, null);
            viewHolder.textSPName = view.findViewById(R.id.textviewNameSmartPhone);
            viewHolder.textSPPrice = view.findViewById(R.id.textviewPriceSmartPhone);
            viewHolder.textSPDescription = view.findViewById(R.id.textviewDescripSmartPhone);
            viewHolder.imgSmartPhone = view.findViewById(R.id.imageviewSmartPhone);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = (Product) getItem(position);
        viewHolder.textSPName.setText(product.getProName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.textSPPrice.setText("Price: " + decimalFormat.format(product.getPrice()) + "đ");
        viewHolder.textSPDescription.setMaxLines(2);
        viewHolder.textSPDescription.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.textSPDescription.setText(product.getDescription());
        Picasso.with(context).load(product.getProImage()).
                placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.imgSmartPhone);
        return view;
    }
}
