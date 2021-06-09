package com.example.mda_project.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mda_project.R;
import com.example.mda_project.model.Cart;
import com.example.mda_project.model.Product;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class DetailProduct extends YouTubeBaseActivity {

    Toolbar toolbarDetailProduct;
    ImageView imgDetailProduct;
    TextView txtNamePro, txtPrice, txtDescription;
    Spinner spinner;
    Button buttonAddToCart;
    int proId;
    String detailName = "";
    int detailPrice = 0;
    String detailImg = "";
    String detailDescription = "";
    int proTypeId = 0;
    String youtubeId = "";
    YouTubePlayerView youtubePlayerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        getProperties();
        actionToolBar();
        getInformation();
        catchEventSpinner();
        eventButton();

    }

    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        //initialize youtube player view
        youTubePlayerView.initialize("AIzaSyCJh7syd32PmoBGJhWl0qdqaHusDXxdiEU",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

    private void eventButton() {
        buttonAddToCart.setOnClickListener(v -> {
            if (MainActivity.listCart.size() > 0) {
                int listSpinner1 = Integer.parseInt(spinner.getSelectedItem().toString());
                boolean exist = false;
                for (int i = 0; i < MainActivity.listCart.size(); i++) {
                    if (MainActivity.listCart.get(i).getProId() == proId) {
                        MainActivity.listCart.get(i).setProNumber(MainActivity.listCart.get(i).getProNumber() + listSpinner1);
                        if (MainActivity.listCart.get(i).getProNumber() >= 10) {
                            MainActivity.listCart.get(i).setProNumber(10);
                        }
                        MainActivity.listCart.get(i).setPrice(detailPrice * MainActivity.listCart.get(i).getProNumber());
                        exist = true;
                    }
                }
                if (!exist) {
                    int listSpinner = Integer.parseInt(spinner.getSelectedItem().toString());
                    long newPrice = listSpinner * detailPrice;
                    MainActivity.listCart.add(new Cart(proId, detailName, newPrice, detailImg, listSpinner));
                }
            } else {
                int listSpinner = Integer.parseInt(spinner.getSelectedItem().toString());
                long newPrice = listSpinner * detailPrice;
                MainActivity.listCart.add(new Cart(proId, detailName, newPrice, detailImg, listSpinner));
            }
            Intent intent = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(intent);

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void catchEventSpinner() {
        Integer[] listSpinner = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listSpinner);
        spinner.setAdapter(arrayAdapter);
    }

    //lấy dữ liệu từ màn hình khác truyền vào
    private void getInformation() {
        Product product = (Product) getIntent().getSerializableExtra("detailProduct");
        proId = product.getProId();
        detailName = product.getProName();
        detailPrice = product.getPrice();
        detailImg = product.getProImage();
        detailDescription = product.getDescription();
        youtubeId = product.getYoutubeId();
        playVideo(youtubeId, youtubePlayerView);
//        Log.d("YoutubeId", youtubeId);
        proTypeId = product.getTypeProId();
        txtNamePro.setText(detailName);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtPrice.setText("Price: " + decimalFormat.format(detailPrice) + "đ");
        txtDescription.setText(detailDescription);
        Picasso.with(getApplicationContext()).load(detailImg).placeholder(R.drawable.noimage).error(R.drawable.error).into(imgDetailProduct);
    }

    private void actionToolBar() {
//        setSupportActionBar(toolbarDetailProduct);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDetailProduct.setNavigationOnClickListener(v -> finish());

    }

    private void getProperties() {
        toolbarDetailProduct = findViewById(R.id.toolbarDetailProduct);
        imgDetailProduct = findViewById(R.id.imageviewDetailProduct);
        txtNamePro = findViewById(R.id.textviewDetailProductName);
        txtPrice = findViewById(R.id.textviewDetailPrice);
        txtDescription = findViewById(R.id.textviewDetailDescriptionProduct);
        youtubePlayerView = findViewById(R.id.youtubePlayerView);
        spinner = findViewById(R.id.spinner);
        buttonAddToCart = findViewById(R.id.buttonAddToCart);
    }
}