package com.k4ench.pswrd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Desc extends AppCompatActivity {
    TextView name1,desc1;
    ImageButton accept,reject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name1=(TextView)findViewById(R.id.textView3);
        desc1=(TextView)findViewById(R.id.textView4);
        accept=(ImageButton)findViewById(R.id.accept);
        reject=(ImageButton)findViewById(R.id.reject);
        final String name=getIntent().getStringExtra("name");
        String invoice=getIntent().getStringExtra("invoice");
        String desc=getIntent().getStringExtra("desc");
        name1.setText(name);
        desc1.setText(desc);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String typ="accept";
                String status="1";
                insertme(typ,status,name);
                Toast.makeText(Desc.this,name,Toast.LENGTH_LONG).show();
            }
            private void insertme(final String typ, final String status,final String name) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://kiran0407.tk/status.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String,String> params =new HashMap<String, String>();

                        params.put("typ",typ);
                        params.put("status",status);
                        params.put("name1",name);
                        return params;

                    }
                };
                AppController.getInstance().addToRequestQueue(stringRequest);
            }
        });




        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String typ="reject";
                String status="1";
                insertme(typ,status,name);
                Toast.makeText(Desc.this,"Successfully added in reject list",Toast.LENGTH_LONG).show();
            }
            private void insertme(final String typ, final String status,final String name) {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://kiran0407.tk/status.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String,String> params =new HashMap<String, String>();

                        params.put("typ",typ);
                        params.put("status",status);
                        params.put("name1",name);
                        return params;

                    }
                };
                AppController.getInstance().addToRequestQueue(stringRequest);
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
