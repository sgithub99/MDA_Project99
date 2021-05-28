package com.example.mda_project.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mda_project.R;
import com.example.mda_project.adapter.CartAdapter;
import com.example.mda_project.model.Cart;
import com.example.mda_project.util.CheckConnection;

import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;


public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    TextView textViewNotification;
    static TextView texttotalMoney;
    Button btnPayToCart, btnKeepBuying;
    Toolbar toolbarCart;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        anhXa();
        actionToolBar();
        checkData();
        eventUltil();
        catchOnItemListView();
        eventButton();
    }

    private void eventButton() {
        btnKeepBuying.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
        btnPayToCart.setOnClickListener(v -> {
            if(MainActivity.listCart.size() > 0){
                Intent intent = new Intent(getApplicationContext(), InforCustomerActiviry.class);
                startActivity(intent);
            }else{
                CheckConnection.showToast_Short(getApplicationContext(), "Your cart is empty");
            }
        });
    }

    private void catchOnItemListView() {
        listViewCart.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
            builder.setTitle("Confirm to delete product");
            builder.setMessage("Are you sure for deleting this product ?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                if (MainActivity.listCart.size() <= 0) {
                    textViewNotification.setVisibility(View.VISIBLE);
                } else {
                    MainActivity.listCart.remove(position);
                    cartAdapter.notifyDataSetChanged();
                    eventUltil();
                    if(MainActivity.listCart.size() <= 0){
                        textViewNotification.setVisibility(View.VISIBLE);
                    }else{
                        textViewNotification.setVisibility(View.INVISIBLE);
                        cartAdapter.notifyDataSetChanged();
                        eventUltil();
                    }
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cartAdapter.notifyDataSetChanged();
                    eventUltil();
                }
            });
            builder.show();
            return true;
        });
    }

    public static void eventUltil() {
        long totalMoney = 0;
        for (int i = 0; i < MainActivity.listCart.size(); i++) {
            totalMoney += MainActivity.listCart.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        texttotalMoney.setText(decimalFormat.format(totalMoney) + "đ");

    }

    private void checkData() {
        if (MainActivity.listCart.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            textViewNotification.setVisibility(View.VISIBLE);
            listViewCart.setVisibility(View.INVISIBLE);
        } else {
            cartAdapter.notifyDataSetChanged();
            textViewNotification.setVisibility(View.INVISIBLE);
            listViewCart.setVisibility(View.VISIBLE);
        }
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCart.setNavigationOnClickListener(v -> finish());
    }

    private void anhXa() {
        listViewCart = findViewById(R.id.listviewCart);
        textViewNotification = findViewById(R.id.textviewNotification);
        texttotalMoney = findViewById(R.id.textviewTotalMoney);
        btnPayToCart = findViewById(R.id.buttonPayToCart);
        btnKeepBuying = findViewById(R.id.buttonKeepBuying);
        toolbarCart = findViewById(R.id.toolbarCart);
        cartAdapter = new CartAdapter(CartActivity.this, MainActivity.listCart);
        listViewCart.setAdapter(cartAdapter);
    }
}