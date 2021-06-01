package com.example.mda_project.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mda_project.R;
import com.example.mda_project.activity.CartActivity;
import com.example.mda_project.activity.MainActivity;
import com.example.mda_project.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Cart> listCart;

    public CartAdapter(Context context, ArrayList<Cart> listCart) {
        this.context = context;
        this.listCart = listCart;
    }

    @Override
    public int getCount() {
        return listCart.size();
    }

    @Override
    public Object getItem(int position) {
        return listCart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView txtCartName, txtCartPrice;
        public ImageView imgCart;
        public Button btnminus, btnplus, btnvalues;

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.line_cart, null);
            viewHolder.txtCartName = view.findViewById(R.id.textviewCartName);
            viewHolder.txtCartPrice = view.findViewById(R.id.textviewCartPrice);
            viewHolder.imgCart = view.findViewById(R.id.imageviewCart);
            viewHolder.btnminus = view.findViewById(R.id.buttonminus);
            viewHolder.btnvalues = view.findViewById(R.id.buttonvalues);
            viewHolder.btnplus = view.findViewById(R.id.buttonplus);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Cart cart = (Cart) getItem(position);
        viewHolder.txtCartName.setText(cart.getProName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtCartPrice.setText(decimalFormat.format(cart.getPrice()) + "đ");
        Picasso.with(context).load(cart.getProImage()).placeholder(R.drawable.noimage).error(R.drawable.error).into(viewHolder.imgCart);
        viewHolder.btnvalues.setText(cart.getProNumber() + "");
        int number = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if (number >= 10) {
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        } else if (number <= 1) {
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.btnminus.setVisibility(View.VISIBLE);
            viewHolder.btnplus.setVisibility(View.VISIBLE);
        }
        viewHolder.btnplus.setOnClickListener(v -> {
            int newNumber = Integer.parseInt(viewHolder.btnvalues.getText().toString()) + 1;
            int nowNumber = MainActivity.listCart.get(position).getProNumber();
            long nowPrice = MainActivity.listCart.get(position).getPrice();
            MainActivity.listCart.get(position).setProNumber(newNumber);
            long newPrice = (nowPrice * newNumber) / nowNumber;
            MainActivity.listCart.get(position).setPrice(newPrice);
            DecimalFormat decimalFormat1 = new DecimalFormat("###,###,###");
            viewHolder.txtCartPrice.setText(decimalFormat1.format(newPrice) + "đ");
            CartActivity.eventUltil();
            if (newNumber > 9) {
                viewHolder.btnplus.setVisibility(View.INVISIBLE);
                viewHolder.btnminus.setVisibility(View.VISIBLE);
                viewHolder.btnvalues.setText(String.valueOf(newNumber));
            } else {
                viewHolder.btnplus.setVisibility(View.VISIBLE);
                viewHolder.btnminus.setVisibility(View.VISIBLE);
                viewHolder.btnvalues.setText(String.valueOf(newNumber));
            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newNumber = Integer.parseInt(viewHolder.btnvalues.getText().toString()) - 1;
                int nowNumber = MainActivity.listCart.get(position).getProNumber();
                long nowPrice = MainActivity.listCart.get(position).getPrice();
                MainActivity.listCart.get(position).setProNumber(newNumber);
                long newPrice = (nowPrice * newNumber) / nowNumber;
                MainActivity.listCart.get(position).setPrice(newPrice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtCartPrice.setText(decimalFormat.format(newPrice) + "đ");
                CartActivity.eventUltil();
                if (newNumber < 2) {
                    viewHolder.btnplus.setVisibility(View.VISIBLE);
                    viewHolder.btnminus.setVisibility(View.INVISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(newNumber));
                } else {
                    viewHolder.btnplus.setVisibility(View.VISIBLE);
                    viewHolder.btnminus.setVisibility(View.VISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(newNumber));
                }
            }
        });
        return view;
    }
}
