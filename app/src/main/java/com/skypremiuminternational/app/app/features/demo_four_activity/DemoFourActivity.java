package com.skypremiuminternational.app.app.features.demo_four_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.skypremiuminternational.app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DemoFourActivity extends AppCompatActivity {
    ListView lvSP;
    Button btnThongTin;

    public static void startMe(Context context){
        Intent intent  = new Intent(context, DemoFourActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_four2);
        binding();
            btnThongTin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(DemoFourActivity.this,SecondActivity.class);
                    startActivity(intent);
                }

            });

    }

    //Camel    myBestFunction   MyClass
    private void binding() {
        lvSP = (ListView)findViewById(R.id.listViewSP);
        btnThongTin=(Button)findViewById(R.id.buttonThongTin);
    }
}