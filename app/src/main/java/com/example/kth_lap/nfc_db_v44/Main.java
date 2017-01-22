package com.example.kth_lap.nfc_db_v44;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

/**
 * Created by KTH_LAP on 2017-01-22.
 */

public class Main extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ImageButton picture = (ImageButton) findViewById(R.id.pic_btn);
        ImageButton register = (ImageButton) findViewById(R.id.reg_btn);
        ImageButton main_nfc = (ImageButton) findViewById(R.id.nfc_btn);
        picture.setOnClickListener(this);
        register.setOnClickListener(this);
        main_nfc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pic_btn: // 사진 촬영 액티비티
                Intent first_intent = new Intent(getApplicationContext(), Photo_Act.class);
                startActivity(first_intent);
                break;
            case R.id.reg_btn: // 물품 등록 액티비티
                Intent sec_intent = new Intent(getApplicationContext(), Data_insert_Act.class);
                startActivity(sec_intent);
                break;
            case R.id.nfc_btn: // 물품 확인 액티비티
                Intent third_intent = new Intent(getApplicationContext(), NFC_Check_Act.class);
                startActivity(third_intent);
                break;
        }
    }
}