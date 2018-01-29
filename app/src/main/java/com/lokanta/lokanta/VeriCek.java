package com.lokanta.lokanta;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;


public class VeriCek {
    private ArrayList<String> Numaralar = new ArrayList<>();
    private ArrayList<String> Adress = new ArrayList<>();
    private ArrayList<String> Sepet = new ArrayList<>();
    private ArrayList<Long> EklenmeTarihi = new ArrayList<>();
    private ArrayList<Long> SonAramaTarihi = new ArrayList<>();

    public void VerileriCek(Context context){
        //Verileri veritabanından ceker

        SQLite sqLite = new SQLite(context);

        final Cursor res = sqLite.VerileriAl();

        Numaralar.clear();
        Adress.clear();
        Sepet.clear();
        EklenmeTarihi.clear();
        SonAramaTarihi.clear();
        if(res.getCount()!=0){
            while(res.moveToNext()){
                Numaralar.add(res.getString(0));
                Adress.add(res.getString(1));
                Sepet.add(res.getString(2));
                EklenmeTarihi.add(res.getLong(3));
                SonAramaTarihi.add(res.getLong(4));
            }
        }
    }

    public void SonArananVeriCek(Context context){
        //Verileri son aranan üstte olacak sekilde veritabanından ceker

        SQLite sqLite = new SQLite(context);

        final Cursor res = sqLite.SonArananSiraliCek();

        Numaralar.clear();
        Adress.clear();
        Sepet.clear();
        EklenmeTarihi.clear();
        SonAramaTarihi.clear();
        if(res.getCount()!=0){
            while(res.moveToNext()){
                Numaralar.add(res.getString(0));
                Adress.add(res.getString(1));
                Sepet.add(res.getString(2));
                EklenmeTarihi.add(res.getLong(3));
                SonAramaTarihi.add(res.getLong(4));
            }
        }
    }

    public void TeslimatOlmayanVerileriCek(Context context){
        //Teslim Edilmeyen Verileri veritabanından ceker

        SQLite sqLite = new SQLite(context);

        final Cursor res = sqLite.TeslimatYapilmayanCek();

        Numaralar.clear();
        Adress.clear();
        Sepet.clear();
        EklenmeTarihi.clear();
        SonAramaTarihi.clear();

        if(res.getCount()!=0){
            while(res.moveToNext()){
                Numaralar.add(res.getString(0));
                Adress.add(res.getString(1));
                Sepet.add(res.getString(2));
                EklenmeTarihi.add(res.getLong(3));
                SonAramaTarihi.add(res.getLong(4));
            }
        }
    }

    public void SiparisVerenCek(Context context){
        //Siparişde olan Verileri veritabanından ceker

        SQLite sqLite = new SQLite(context);

        final Cursor res = sqLite.SiparisVerenCek();

        Numaralar.clear();
        Adress.clear();
        Sepet.clear();
        EklenmeTarihi.clear();
        SonAramaTarihi.clear();
        if(res.getCount()!=0){
            while(res.moveToNext()){
                Numaralar.add(res.getString(0));
                Adress.add(res.getString(1));
                Sepet.add(res.getString(2));
                EklenmeTarihi.add(res.getLong(3));
                SonAramaTarihi.add(res.getLong(4));
            }
        }
    }


    public ArrayList<String> NumaralarıCek(){
        return Numaralar;
    }
    public ArrayList<String> AdressCek(){
        return Adress;
    }
    public ArrayList<String> SepetCek(){
        return Sepet;
    }
    public ArrayList<Long> EklenmeTarihiCek(){
        return EklenmeTarihi;
    }
    public ArrayList<Long> SonAramaTarihiCek(){
        return SonAramaTarihi;
    }
}
