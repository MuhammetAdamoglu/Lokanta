package com.lokanta.lokanta.AlertDiyaloglar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

public class Bildirim {
    //Bu sınıf, gelen aramalar hakkında bilgi verme ekranı olarak kullanılır

    private static AlertDialog alertDialog;
    @SuppressLint("StaticFieldLeak")
    private static SQLite sqLite;

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    public static void Gönder(final Context context, final String numara, final String baslik, final String adress, final String Positif_Buton, final String Negatif_Buton, final int id) {

        sqLite= new SQLite(context);

        alertDialog = new AlertDialog.Builder(context).create();

        //Bu ekran uygulama dışında açılacagı için izin verilmesi gerekir
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        /////////////////////////////////////////////////////////////////

        alertDialog.setCancelable(false);

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialoglayout = inflater.inflate(R.layout.bildirim_musteri, null);

        final TextView musteri_akibeti= dialoglayout.findViewById(R.id.baslik);
        final TextView muster_adress= dialoglayout.findViewById(R.id.mesaj);
        final EditText adress_ekle= dialoglayout.findViewById(R.id.adress_ekle);
        final EditText ürün_ekle= dialoglayout.findViewById(R.id.ürün_ekle);

        alertDialog.setView(dialoglayout);

        musteri_akibeti.setText(numara+baslik);
        muster_adress.setText(adress);

        if(id== IDs.MüsteriBulunamadı || id==IDs.MüsteriEkle){//Müsteri bulunamadı veya musteri ekle ekranında mesaj yazısı büyütülür
            muster_adress.setTextSize(20);
        }

        if(id==IDs.MüsteriEkle){//Eger musteri eklenecek ise
            muster_adress.setVisibility(View.GONE);
            adress_ekle.setVisibility(View.VISIBLE);
            ürün_ekle.setVisibility(View.VISIBLE);

            //Ekranın üst kısmında acılması için
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
            wmlp.gravity = Gravity.TOP | Gravity.CENTER;

            /////////////////////////////////////

        }else if(id==IDs.MüsteriSiparisAl){//Eger siparis alınacak ise
            ürün_ekle.setVisibility(View.VISIBLE);

            //Ekranın üst kısmında acılması için
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams wmlp = alertDialog.getWindow().getAttributes();
            wmlp.gravity = Gravity.TOP | Gravity.CENTER;
            ////////////////////////////////////
        }

        if(Positif_Buton!=null)//Buton varsa
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, Positif_Buton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    String PutPoint="●";
                    if(ürün_ekle.getText().toString().equals(""))//ürün yoksa
                        PutPoint="";

                    if(id==IDs.MüsteriEkle){//Müsteri eklenecek ise

                        if(!adress_ekle.getText().toString().equals("")){//adres var ise
                            sqLite.Ekle(numara.trim(),adress_ekle.getText().toString().trim(),PutPoint+ürün_ekle.getText().toString().trim(),System.currentTimeMillis(),System.currentTimeMillis());

                            if(!PutPoint.equals(""))//ürün var ise
                                sqLite.SiparisGüncelle(numara,true);//siparis verilir
                            else
                                sqLite.SiparisGüncelle(numara,false);//siparis verilmez

                        }else {//adress yok ise
                            //Tekrar sorulur
                            Gönder(context,numara,baslik,adress,Positif_Buton,Negatif_Buton,id);

                        }
                    }else if(id==IDs.MüsteriSiparisAl){//Müsteri Siparisi Alınacaka ise
                        sqLite.VeriGüncelle(numara,adress,PutPoint+ürün_ekle.getText().toString().trim());//ürünler ve adres güncellenir

                        if(!PutPoint.equals("")){//ürün var ise
                            sqLite.SiparisGüncelle(numara,true);//siparis verilir
                            sqLite.TeslimatVer(numara);//teslim edilmedi bilgisi verilir
                        }else
                            sqLite.SiparisGüncelle(numara,false);//siparis verilmez

                    }

                }
            });

        if(Negatif_Buton!=null)
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, Negatif_Buton, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sqLite.SiparisGüncelle(numara,false);//iptal butonuna basılırsa siparis iptal edilir yada siparis verilmez
                }
            });

        alertDialog.show();

        Button buton_negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buton_negative.setTextColor(Color.GRAY);
        Button buton_positive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        buton_positive.setTextColor(Color.GRAY);

    }

    public static void setCancel(){
        if(alertDialog!=null)
        alertDialog.cancel();
    }


}
