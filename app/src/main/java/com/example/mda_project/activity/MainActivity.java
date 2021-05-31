package com.example.mda_project.activity;

//import androidx.appcompat.app.ActionBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
//import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mda_project.R;
import com.example.mda_project.adapter.ProAdapter;
import com.example.mda_project.adapter.TypeProAdapter;
import com.example.mda_project.model.Cart;
import com.example.mda_project.model.Product;
import com.example.mda_project.model.TypeProduct;
import com.example.mda_project.util.CheckConnection;
import com.example.mda_project.util.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewHome;
    NavigationView navigationView;
    ListView listViewHome;
    DrawerLayout drawerLayout;
    ArrayList<TypeProduct> listTypePro;
    TypeProAdapter typeProAdapter;
    int id = 0;
    String typeProName = "";
    String typeProImage = "";
    ArrayList<Product> listProduct;
    ProAdapter proAdapter;
    public static ArrayList<Cart> listCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getProperties();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            actionBar();
            actionViewFlipper();
            getDataTypePro();
            getNewestPro();
            catchOnItemListView();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Retry your connection !");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    //lấy dữ liệu trên thanh menu
    private void catchOnItemListView() {
        listViewHome.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0:
                    if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        CheckConnection.showToast_Short(getApplicationContext(), "Please check your connection!");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case 1:
                    if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                        Intent intent = new Intent(MainActivity.this, SmartPhoneActivity.class);
                        intent.putExtra("typeProId", listTypePro.get(position).getTypeProId());
                        startActivity(intent);
                    } else {
                        CheckConnection.showToast_Short(getApplicationContext(), "Please check your connection!");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case 2:
                    if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                        Intent intent = new Intent(MainActivity.this, LaptopActivity.class);
                        intent.putExtra("typeProId", listTypePro.get(position).getTypeProId());
                        startActivity(intent);
                    } else {
                        CheckConnection.showToast_Short(getApplicationContext(), "Please check your connection!");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case 3:
                    if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                        Intent intent = new Intent(MainActivity.this, TabletActivity.class);
                        intent.putExtra("typeProId", listTypePro.get(position).getTypeProId());
                        startActivity(intent);
                    } else {
                        CheckConnection.showToast_Short(getApplicationContext(), "Please check your connection!");
                    }
                    Log.d("Name Tablet:", listTypePro.get(position).getProName());
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case 4:
                    if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                        Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                        startActivity(intent);
                    } else {
                        CheckConnection.showToast_Short(getApplicationContext(), "Please check your connection!");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case 5:
                    if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                        Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                        startActivity(intent);
                    } else {
                        CheckConnection.showToast_Short(getApplicationContext(), "Please check your connection!");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case 6:
                    if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                        Intent intent = new Intent(MainActivity.this, Setting.class);
                        startActivity(intent);
                    } else {
                        CheckConnection.showToast_Short(getApplicationContext(), "Please check your connection!");
                    }
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

            }
        });
    }

    private void getNewestPro() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkNewestPro, response -> {
            if (response != null) {
                int proId = 0;
                String proName = "";
                Integer price = 0;
                String proImage = "";
                String description = "";
                int typeProId = 0;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        proId = jsonObject.getInt("proId");
                        proName = jsonObject.getString("proName");
                        price = jsonObject.getInt("price");
                        proImage = jsonObject.getString("proImage");
                        description = jsonObject.getString("description");
                        typeProId = jsonObject.getInt("typeProId");
                        listProduct.add(new Product(proId, proName, price, proImage, description, typeProId));
                        proAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, error -> CheckConnection.showToast_Short(getApplicationContext(), error.toString()));
        requestQueue.add(jsonArrayRequest);
    }

    private void getDataTypePro() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.linkTypePro, response -> {
            if (response != null) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        typeProName = jsonObject.getString("typeOfProductName");
                        typeProImage = jsonObject.getString("typeOfProductImage");
                        listTypePro.add(new TypeProduct(id, typeProName, typeProImage));
                        typeProAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("tag", response.toString());
                listTypePro.add(4, new TypeProduct(0, "Contact", "https://icon-library.com/images/contact-icon-png/contact-icon-png-1.jpg"));
                listTypePro.add(5, new TypeProduct(0, "About us", "https://images.squarespace-cdn.com/content/v1/51d1b303e4b05d425c862fb3/1380296194530-I3NSAG45PSHLV02AFYXB/ke17ZwdGBToddI8pDm48kGfiFqkITS6axXxhYYUCnlRZw-zPPgdn4jUwVcJE1ZvWQUxwkmyExglNqGp0IvTJZUJFbgE-7XRK3dMEBRBhUpxQ1ibo-zdhORxWnJtmNCajDe36aQmu-4Z4SFOss0oowgxUaachD66r8Ra2gwuBSqM/iconmonstr-info-2-icon.png"));
                listTypePro.add(6, new TypeProduct(0, "Setting", "http://simpleicon.com/wp-content/uploads/setting2.png"));
            }
        }, error -> CheckConnection.showToast_Short(getApplicationContext(), error.toString()));
        requestQueue.add(jsonArrayRequest);
    }

    private void actionViewFlipper() {
        List<String> banners = new ArrayList<>();
        banners.add("https://cdn.tgdd.vn/2021/05/banner/big-samsung-830-300-830x300.png");
        banners.add("https://cdn.tgdd.vn/2021/05/banner/iphone-12-830-300-830x300.png");
        banners.add("https://cdn.tgdd.vn/2021/05/banner/830-300-830x300-11.png");
        banners.add("https://cdn.tgdd.vn/2021/05/banner/reno5-830-300-830x300.png");
        for (int i = 0; i < banners.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(banners.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
//        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
//        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }

    private void getProperties() {
        toolbar = findViewById(R.id.toolbarhome);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewHome = findViewById(R.id.recycleview);
        navigationView = findViewById(R.id.navigationviewHome);
        listViewHome = findViewById(R.id.listviewHome);
        drawerLayout = findViewById(R.id.drawerLayout);
        listTypePro = new ArrayList<>();
        listTypePro.add(0, new TypeProduct(0, "Home", "https://www.nicepng.com/png/full/14-142284_png-file-svg-home-icon-black-circle.png"));
        typeProAdapter = new TypeProAdapter(listTypePro, getApplicationContext());
        listViewHome.setAdapter(typeProAdapter);
        listProduct = new ArrayList<>();
        proAdapter = new ProAdapter(getApplicationContext(), listProduct);
        recyclerViewHome.setHasFixedSize(true);
        recyclerViewHome.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewHome.setAdapter(proAdapter);


        if(listCart != null){

        }else {
            listCart = new ArrayList<>();
        }
    }
}