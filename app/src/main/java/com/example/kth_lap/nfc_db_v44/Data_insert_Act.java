package com.example.kth_lap.nfc_db_v44;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static android.R.attr.bitmap;

public class Data_insert_Act extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageView iv_UserPhoto;
    private int id_view;
    private String absolutePath;

    DBContactHelper db;

    EditText edit_content = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_activity);
        db = new DBContactHelper(this);

        /*Cotent 입력 부분*/
        edit_content = (EditText) findViewById(R.id.input_txt);

        /*nfc 스캔 부분*/
        TextView blank_txt = (TextView) findViewById(R.id.blank_txt);

        /*사진 선택 부분*/
        iv_UserPhoto = (ImageView) findViewById(R.id.imageView);
        Button choice_photo = (Button) findViewById(R.id.picture_btn);
        choice_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertChocie();
            }
        });
        // 데이터 입력
        Button add = (Button) findViewById(R.id.add_btn);
        add.setOnClickListener(this);

        // 액티비티 취소
        Button canel = (Button) findViewById(R.id.cancel_btn);
        canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // 사진 선택 및 촬영
    public void alertChocie() {
        DialogInterface.OnClickListener cameraList = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakePhotoAction();
            }
        };
        DialogInterface.OnClickListener albumListner = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };
        DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        new AlertDialog.Builder(Data_insert_Act.this)
                .setTitle("업로드 할 이미지 선택")
                .setPositiveButton("사진 촬영", cameraList)
                .setNeutralButton("앨범 선택", albumListner)
                .setNegativeButton("취소", cancelListener)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    // 카메라 사진 촬영
    public void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 임시 사용할 파일 경로 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new
                File(Environment.getExternalStorageDirectory(), url));
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    // 앨범 이미지 가져오기
    public void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case PICK_FROM_ALBUM: // 이후의 처리가 카메라와 같으므로 break없이 진행
                mImageCaptureUri = data.getData();
                Log.d("NFC_box : ", mImageCaptureUri.getPath().toString());
            case PICK_FROM_CAMERA: // 크롭 할 이미지 결정
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                // 크롭 크기 지정
                intent.putExtra("outputX", 1000); //이미지 x,y축
                intent.putExtra("outputY", 1000);
                intent.putExtra("aspectX", 1000); // crop 박스 x,y 축
                intent.putExtra("aspectX", 1000);
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_iMAGE);
                break;
            case CROP_FROM_iMAGE: // 크롭 된 이미지를 넘겨 받음
                if (resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();
                // crop 된 이미지 저장 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/NFC_box/" + System.currentTimeMillis() + ".jpg";
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data"); // 크롭된 bitmap
                    iv_UserPhoto.setImageBitmap(photo); // 레이아웃의 이미칸에 crop된 bitmap을 보여줌
                    storeCropImage(photo, filePath); // crop된 이미지를 외부저장소, 앨범에 저장
                    absolutePath = filePath;
                    break;
                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
        }
    }

    private void storeCropImage(Bitmap photo, String filePath) {
        // NFC_box폴더생성
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/NFC_box";
        File directory_NFC_box = new File(dirPath);

        if (!directory_NFC_box.exists())
            directory_NFC_box.mkdir();
        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, out);

            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_btn:
                Log.d("Insert: ", "Inserting ..");
                Log.d("텍스트 입력 확인 : ",edit_content.getText().toString());
                db.addContact(new Contact(edit_content.getText().toString()));
                Intent main_intent = new Intent(getApplicationContext(), Main.class);
                startActivity(main_intent);
                finish();
        }
    }
}
