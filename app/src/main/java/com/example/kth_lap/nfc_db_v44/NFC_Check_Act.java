package com.example.kth_lap.nfc_db_v44;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by KTH_LAP on 2017-01-22.
 */
public class NFC_Check_Act extends AppCompatActivity implements View.OnClickListener {
    DBContactHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_check_activity);

        db = new DBContactHelper(this);

        /**
         * CRUD Operations
         * */
        // 샘플데이타 넣기
//        Log.d("Insert: ", "Inserting ..");
//        db.addContact(new Contact("Ravi", "9100000000"));
//        db.addContact(new Contact("Srinivas", "9199999999"));
//        db.addContact(new Contact("Tommy", "9522222222"));
//        db.addContact(new Contact("Karthik", "9533333333"));

        // 집어넣은 데이타 다시 읽어들이기
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Content : " + cn.getContent() + " ,ImgPath : " + cn.getImgPath();
            Log.d("LogValues: ", log);
//            if (cn.getID() == 1)
//                db.deleteContact(cn);
        }
        // 스캔된 nfc 컨텐트 목록과 사진 가져오기


        // 수정 버튼
        ImageButton modify_btn = (ImageButton)findViewById(R.id.modify_btn);
        modify_btn.setOnClickListener(this);
        // 삭제 버튼
        ImageButton delete_btn = (ImageButton)findViewById(R.id.delete_btn);
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.modify_btn:
                finish();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
