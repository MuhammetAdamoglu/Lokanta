package com.lokanta.lokanta.AlertDiyaloglar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lokanta.lokanta.R;
import com.lokanta.lokanta.SQLite;
import com.lokanta.lokanta.Tabs;


public class AlertDialog_TeslimatYap {
    //Bu diyalog, ürün teslim edilip edilmediğini sorar

    @SuppressLint("SetTextI18n")
    public AlertDialog_TeslimatYap(final Context context, final Tabs tabs, final String numara, final String adress, final String sepet, final long eklenme_tarih, final long sonarama_tarih){
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

        alertDialog.setView(dialoglayout);

        musteri_akibeti.setText("Ürünü Teslim Edildimi?");
        musteri_adress.setText("Ürünü Teslim Et");

        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, "Teslim Et", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Teslim Et butonuna tıklanınca
                sqLite.TeslimEdildi(numara);//veri tabanında teslim edildi bilgisi verilir
                tabs.TeslimatDiziSil(numara,adress,sepet,eklenme_tarih,sonarama_tarih);//teslim edilmeyenler listesinden kaldırılır

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
