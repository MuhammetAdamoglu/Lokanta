package com.lokanta.lokanta;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;


public class Sorgula {
    private static String sorgu;
    public static boolean bul(Context context, String Numara){

        SQLite sqLite = new SQLite(context);

        sorgu=sqLite.VeriAra(Numara);//numara veritabanında sorgulanır

        //sorgu bos ise false, dolu ise true döndürür
        return !sorgu.equals("");
    }

    static String getSorgu() {
        //Sorguyu almak icin
        return sorgu;
    }
}
