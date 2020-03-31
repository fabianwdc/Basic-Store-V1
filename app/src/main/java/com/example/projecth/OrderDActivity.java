package com.example.projecth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Date;

public class OrderDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_d);

        Bundle b = getIntent().getExtras();
        String oid = b.getString("oid");
        String title = b.getString("prodtitle");
        String prc = b.getString("price");
        String adres = b.getString("address");
        String dofod = b.getString("date");

        TextView tv1 = findViewById(R.id.textView2);
        TextView tv2 = findViewById(R.id.textView3);
        TextView tv3 = findViewById(R.id.textView4);
        TextView tv4 = findViewById(R.id.textView5);
        TextView tv5 = findViewById(R.id.textView6);

        tv1.setText(oid);
        tv2.setText(adres);
        tv3.setText(dofod);
        tv4.setText(title);
        tv5.setText(prc);



    }
}
