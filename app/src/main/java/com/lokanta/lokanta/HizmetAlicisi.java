package com.lokanta.lokanta;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

import com.lokanta.lokanta.AlertDiyaloglar.Bildirim;

public class HizmetAlicisi extends BroadcastReceiver {
    //Bu Sınıf, gelen aramadaki numarayı alır

    Context context;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(final Context context, Intent intent) {
        this.context=context;

        String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);//arama türünü alır(arıyor,gelen arama,iptal edildi ..)
        String numara = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);//gelen aramnın numarasınıalır

        SQLite sqLite = new SQLite(context);


        assert stateStr != null;
        if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){//eger alıyorsa
            if(Sorgula.bul(context,numara)){//numara veri tabanında sorgulanır
                Bildirim.Gönder(context,numara," Bulundu...",Sorgula.getSorgu(),"Tamam",null,IDs.MüsteriBulundu);//Diyalogu cagırır
                sqLite.SonAramaTarihGüncelle(numara,System.currentTimeMillis());//Son Arama Tarihini günceller
                BilgiTut.Bulundu=true;//Bulundugunu belirler
            }else{//Bulunmadıysa
                Bildirim.Gönder(context,numara," Bulunamadı...","Adres Bilgilerini\nİstemeyi Unutmayınız!!","Tamam",null,IDs.MüsteriBulunamadı);//Diyalogu cagır
                BilgiTut.Bulundu=false;//bulunmadı diye belirler
            }

            BilgiTut.GelenArama =true;//Gelen Arama oldugunu belirler

        }else if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE) && BilgiTut.GelenArama){//Eger arama kapandıysa ve gelen arama olduysa
            Bildirim.setCancel();//diyalog iptal edilir

            if(BilgiTut.Bulundu){//Bulunduysa diyalog cagırır ve siparisi ister
                Bildirim.Gönder(context,numara," Müşteri Ne Sipariş Verdi...",Sorgula.getSorgu(),"Siparis Ver","Iptal",IDs.MüsteriSiparisAl);
            }
            else{//Bulunmadıysa diyalog cagırır ve musteriyi eklemesini isiter
                Bildirim.Gönder(context,numara," Ekle","","Ekle","Ekleme",IDs.MüsteriEkle);
            }

            BilgiTut.GelenArama =false;//gelen aramayı resetler
        }else {
            Bildirim.setCancel();
        }


    }

}