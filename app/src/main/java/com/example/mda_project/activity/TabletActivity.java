package com.example.mda_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mda_project.R;
import com.example.mda_project.adapter.SmartPhoneAdapter;
import com.example.mda_project.adapter.TabletAdapter;
import com.example.mda_project.model.Product;
import com.example.mda_project.util.CheckConnection;
import com.example.mda_project.util.Server;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabletActivity extends AppCompatActivity {
    Toolbar toolbarSP;
    ListView listViewSP;
    TabletAdapter tabletAdapter;
    ArrayList<Product> listTablet;
    int proID = 0;
    int page = 1;
    View footerview;
    boolean isLoading = false;
    boolean limitData = false;
    MHandler mHandler;
    SearchView searchClickTablet;
    ArrayList<Product> filterdListTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);
        getProperties();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            getIdProType();
            actionToolBar();
            getData(page);
            loadMoreData();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Please check your connection!");
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadMoreData() {
        listViewSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), DetailProduct.class);
                intent.putExtra("detailProduct", listTablet.get(position));
                startActivity(intent);
            }
        });
        listViewSP.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitData == false) {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void getData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String linkSp = Server.linkItem + page;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkSp, response -> {
            int proId;
            String proName;
            int price;
            String proImage;
            String description;
            String youtubeId;
            int proTypeId;
            if (response != null && response.length() != 2) {
                listViewSP.removeFooterView(footerview);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        proId = jsonObject.getInt("proId");
                        proName = jsonObject.getString("proName");
                        price = jsonObject.getInt("price");
                        proImage = jsonObject.getString("proImage");
                        description = jsonObject.getString("description");
                        youtubeId = jsonObject.getString("youtubeId");
                        proTypeId = jsonObject.getInt("typeProId");
                        listTablet.add(new Product(proId, proName, price, proImage, description, youtubeId, proTypeId));
                        tabletAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                limitData = true;
                listViewSP.removeFooterView(footerview);
                CheckConnection.showToast_Short(getApplicationContext(), "Out of product");
            }

        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<>();
                param.put("typeProId", String.valueOf(proID));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSP.setNavigationOnClickListener(v -> finish());

    }

    private void getIdProType() {
        proID = getIntent().getIntExtra("typeProId", -1);
        Log.d("Type of product value", proID + "");
    }

    private void getProperties() {
        toolbarSP = findViewById(R.id.toolbarTablet);
        listViewSP = findViewById(R.id.listviewTablet);
        listTablet = new ArrayList<>();
        tabletAdapter = new TabletAdapter(getApplicationContext(), listTablet);
        listViewSP.setAdapter(tabletAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.processbar, null);
        mHandler = new MHandler();
        searchClickTablet = findViewById(R.id.searchClickTablet);
        searchClickTablet.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterdListTablet(newText);
                return false;
            }
        });

    }

    private void filterdListTablet(String searchText) {
        filterdListTablet = new ArrayList<>();

        for(Product product: listTablet) {
            if(product.getProName().toLowerCase().contains(searchText.toLowerCase())) {
                filterdListTablet.add(product);
            }
        }

        tabletAdapter.setFilterdList(filterdListTablet);
    }

    public class MHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    listViewSP.addFooterView(footerview);
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;

            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

}