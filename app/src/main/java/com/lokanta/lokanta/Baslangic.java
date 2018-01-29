package com.lokanta.lokanta;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class Baslangic extends AppCompatActivity {
    //Uygulama ilk açıldıgında girilen sınıf

    TextView textView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baslangic);

        textView=findViewById(R.id.textView);


        if (! Settings.canDrawOverlays(Baslangic.this)) {//Eger izin verimediyse
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent,10);

            textView.setText("Uygulamanın Çalışması İçin İzin Vermelisiniz");
        }
        else {//izin verildiyse
            //2 saniye bekleyip uygulamayı acar
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DevamEt();
                }
            }, 2000);
        }


    }

    private void DevamEt(){
        //uygulamaya yönlendirir
        Intent intent = new Intent(Baslangic.this,Tabs.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {

            case 3: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //İzinler Verilirse
                    DevamEt();
                } else {
                    textView.setText("Uygulama Devam Edemiyor");
                }
                return;
            }
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                //Verisyon Marsmallow ve üzeri ise
                if (Settings.canDrawOverlays(this)) {

                    if (ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED) {
                        //İzin verilmemiş ise
                        ActivityCompat.requestPermissions(this, new String[]
                                {Manifest.permission.READ_PHONE_STATE},3);
                        textView.setText("Uygulamanın Çalışması İçin İzin Vermelisiniz");

                    }
                }
            }
        }
    }
}
