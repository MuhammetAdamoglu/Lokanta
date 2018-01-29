package com.lokanta.lokanta.AlertDiyaloglar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lokanta.lokanta.R;
import com.lokanta.lokanta.SQLite;
import com.lokanta.lokanta.Sorgula;
import com.lokanta.lokanta.Tabs;


public class AlerDialog_MusteriEkle {
    //Bu Sınıf Müşteri Ekleme Ekranını Çalıştırır

    @SuppressLint("SetTextI18n")
    public AlerDialog_MusteriEkle(final Context context, final Tabs tabs){
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();

        alertDialog.setCancelable(false);

        //Bu ekran uygulama dışında açılacagı için izin verilmesi gerekir
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        /////////////////////////////////////////////////////////////////

        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.CENTER;
        //Diyalogun Ekranın Üstünde Olması İçin


        final SQLite sqLite = new SQLite(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View dialoglayout = inflater.inflate(R.layout.bildirim_musteri, null);

        final TextView musteri_akibeti= dialoglayout.findViewById(R.id.baslik);
        final TextView musteri_adress= dialoglayout.findViewById(R.id.mesaj);
        final TextView tv_numara_ekle= dialoglayout.findViewById(R.id.textView_numaraekle);
        final EditText adress_ekle= dialoglayout.findViewById(R.id.adress_ekle);
        final EditText ürün_ekle= dialoglayout.findViewById(R.id.ürün_ekle);
        final EditText numara_ekle= dialoglayout.findViewById(R.id.numara_ekle);

        alertDialog.setView(dialoglayout);

        musteri_akibeti.setText("Yeni Müsteri Ekle");
        musteri_adress.setVisibility(View.GONE);
        tv_numara_ekle.setVisibility(View.VISIBLE);
        adress_ekle.setVisibility(View.VISIBLE);
        ürün_ekle.setVisibility(View.VISIBLE);
        numara_ekle.setVisibility(View.VISIBLE);


        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Ekle Butonuna Basılınca

                if(!numara_ekle.getText().toString().equals(""))//Numara Boş Değilse
                  if(!adress_ekle.getText().toString().equals("")){//Adress Boş Değilse

                    if(Sorgula.bul(context,"+90"+numara_ekle.getText().toString().trim())){//Girilen Numara Veri Tabanında Olup Olmadığı Kontrol Edilir
                        Toast.makeText(context, "Bu Numara Zaten Var", Toast.LENGTH_SHORT).show();
                    }else {
                        String PutPoint="●";
                        if(ürün_ekle.getText().toString().equals(""))//Ürün Eklenmemiş ise
                            PutPoint="";

                        sqLite.Ekle("+90"+numara_ekle.getText().toString().trim(),adress_ekle.getText().toString().trim(),PutPoint+ürün_ekle.getText().toString().trim(),System.currentTimeMillis(),0);
                        //Müşteri Veri Tabanına Eklenir

                        if(!PutPoint.equals("")){//Ürün Eklenmiş İse
                            sqLite.SiparisGüncelle("+90"+numara_ekle.getText().toString().trim(),true);
                            sqLite.TeslimatVer("+90"+numara_ekle.getText().toString().trim());
                            //Sipariş Verilir
                            //Teslim Edilmedi Olarak İşaretlenir
                        }

                        tabs.VeriGüncelle();//Yapılan Değişiklikler Güncellenir
                    }


                  }
            }
        });

        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //İptale Basıldıysa
            }
        });

        alertDialog.show();//Diyalog gösterilir

        Button buton_negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buton_negative.setTextColor(Color.GRAY);
        Button buton_positive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buton_positive.setTextColor(Color.GRAY);
        Button buton_neutral = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        buton_neutral.setTextColor(Color.GRAY);

        //Butonların rengi Verilir
    }
}
