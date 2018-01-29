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

import com.lokanta.lokanta.IDs;
import com.lokanta.lokanta.R;
import com.lokanta.lokanta.SQLite;
import com.lokanta.lokanta.Tabs;


public class AlertDialog {
    //Bu Sınıf veri silme ve güncelleme için kullanılır

    @SuppressLint("SetTextI18n")
    public AlertDialog(final Context context, final Tabs tabs, final String numara, final String baslik, final String adress, final String sepet, final long eklenme_tarih, final long sonarama_tarih, final int id){
        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(context).create();


        alertDialog.setCancelable(false);

        //Bu ekran uygulama dışında açılacagı için izin verilmesi gerekir
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        /////////////////////////////////////////////////////////////////


        final SQLite sqLite = new SQLite(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View dialoglayout = inflater.inflate(R.layout.bildirim_musteri, null);

        final TextView musteri_akibeti= dialoglayout.findViewById(R.id.baslik);
        final TextView musteri_adress= dialoglayout.findViewById(R.id.mesaj);
        final EditText adress_ekle= dialoglayout.findViewById(R.id.adress_ekle);
        final EditText ürün_ekle= dialoglayout.findViewById(R.id.ürün_ekle);

        alertDialog.setView(dialoglayout);

        musteri_akibeti.setText(baslik);
        musteri_adress.setText(adress+"\n\n"+sepet);

        if(id== IDs.VeriGüncelle){
            //Eger Veri Güncellenecek İse
            musteri_adress.setVisibility(View.GONE);
            adress_ekle.setVisibility(View.VISIBLE);
            ürün_ekle.setVisibility(View.VISIBLE);
            adress_ekle.setText(adress);
            ürün_ekle.setText(sepet);

            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();

            wmlp.gravity = Gravity.TOP | Gravity.CENTER;
        }

        alertDialog.setButton(android.app.AlertDialog.BUTTON_POSITIVE,"Sil", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Sil Butonuna Tıklanınca
                if(id==IDs.VeriSil){
                    sqLite.VeriSil(numara);//Veritabanından seçilen veri silinir
                    tabs.DiziSil(numara,adress,sepet,eklenme_tarih,sonarama_tarih);//Diziden seçilen veri silinir
                }else {
                    //Emin Olup Olmadıgı Tekrar Sorulur
                    new AlertDialog(context,tabs,numara,numara+" Silmek İstediğinize Eminmisiniz?",adress,sepet,eklenme_tarih,sonarama_tarih,1);
                }
            }
        });

        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "Düzenle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Düzenle butonuna tıklayınca

                if(id==IDs.VeriGüncelle){
                    //Eger Veri Güncellenecek İse
                    if(!adress_ekle.getText().toString().equals("")){//Adress bos degil ise
                        sqLite.VeriGüncelle(numara,adress_ekle.getText().toString().trim(),ürün_ekle.getText().toString().trim());//adress ve ürünler veritabanında güncellenir
                        tabs.DiziGüncelle(numara,adress_ekle,ürün_ekle,eklenme_tarih,sonarama_tarih);//adress ve ürünler dizide güncellenir

                    }else {
                        //Eger adress boş ise tekrar sorulur
                        new AlertDialog(context,tabs,numara,baslik,adress,sepet,eklenme_tarih,sonarama_tarih,IDs.VeriGüncelle);
                    }
                }else {
                    //Düzenleye tıklanınce Düzenleme Ekranı çagırılır
                    new AlertDialog(context,tabs,numara,baslik,adress,sepet,eklenme_tarih,sonarama_tarih,IDs.VeriGüncelle);
                }
            }
        });

        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //İptal butonuna tıklanınca
            }
        });

        alertDialog.show();

        Button buton_negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buton_negative.setTextColor(Color.GRAY);
        Button buton_positive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buton_positive.setTextColor(Color.GRAY);
        Button buton_neutral = alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        buton_neutral.setTextColor(Color.GRAY);
    }

}
