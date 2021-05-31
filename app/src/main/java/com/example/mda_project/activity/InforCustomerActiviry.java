package com.example.mda_project.activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mda_project.R;
import com.example.mda_project.util.CheckConnection;
import com.example.mda_project.util.Server;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InforCustomerActiviry extends AppCompatActivity {

    EditText editTextCusName, editTextEmail, editTextPhone;
    Button btnConfirm, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_customer_activiry);
        getProperties();
        getClickCancel();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            eventButton();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Please check your connection!");
        }
    }

    private void eventButton() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cusName = editTextCusName.getText().toString().trim();
                String phone = editTextPhone.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                if (cusName.length() > 0 && phone.length() > 0 && email.length() > 0) {
                    //gửi request lên server
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.linkOrder, OrderId -> {
                        Log.d("idOrder", OrderId);
                        if (Integer.parseInt(OrderId) > 0) {
                            RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Server.linkDetailOrder, response -> {
                                Log.d("ketqua la:", response);
                                if (response.equals("1")) {
                                    MainActivity.listCart.clear();
                                    CheckConnection.showToast_Short(getApplicationContext(), "You added your cart successfully!");
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    CheckConnection.showToast_Short(getApplicationContext(), "Please keep buying!");
                                } else {
                                    CheckConnection.showToast_Short(getApplicationContext(), "Your data of cart is error!");
                                }

                            }, error -> {

                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    JSONArray jsonArray = new JSONArray();
                                    for (int i = 0; i < MainActivity.listCart.size(); i++) {
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("orderId", OrderId);
                                            jsonObject.put("proId", MainActivity.listCart.get(i).getProId());
                                            jsonObject.put("proName", MainActivity.listCart.get(i).getProName());
                                            jsonObject.put("price", MainActivity.listCart.get(i).getPrice());
                                            jsonObject.put("proNumber", MainActivity.listCart.get(i).getProNumber());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        jsonArray.put(jsonObject);
                                    }
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    hashMap.put("jsonDetailOrder", jsonArray.toString());
                                    return hashMap;
                                }
                            };
                            requestQueue1.add(stringRequest1);
                        }
                    }, error -> {

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("cusName", cusName);
                            hashMap.put("phone", phone);
                            hashMap.put("email", email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.showToast_Short(getApplicationContext(), "Please check your information!");
                }
            }
        });
    }

    private void getClickCancel() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getProperties() {
        editTextCusName = findViewById(R.id.editTextNameCustomer);
        editTextEmail = findViewById(R.id.editTextNameEmail);
        editTextPhone = findViewById(R.id.editTextNamePhoneCustomer);
        btnConfirm = findViewById(R.id.buttonConfirm);
        btnCancel = findViewById(R.id.buttonCancel);
    }
}