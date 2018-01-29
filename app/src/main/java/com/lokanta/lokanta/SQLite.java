package com.lokanta.lokanta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ImageView;

import java.util.Arrays;

public class SQLite extends SQLiteOpenHelper {

    private static final String VERITABANI = "Lokanta";
    private static final String TABLE = "lokanta";
    private static String NUMARA = "numara";
    private static String ADRESS = "adress";
    private static String SEPET = "sepet";
    private static String TARIH_EKLENME = "tarih_ekleme";
    private static String TARIH_SONARAMA = "tarih_sonarama";
    private static String TESLIMAT = "teslimat";
    private static String SIPARIS = "siparis";
    private static String ID = "ID";


    public SQLite(Context context) {
        super(context, VERITABANI, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Günlük veri için
        db.execSQL(
                "CREATE TABLE " + TABLE + " ( "

                        + NUMARA + " TEXT, "
                        + ADRESS + " TEXT, "
                        + SEPET + " TEXT, "
                        + TARIH_EKLENME + " LONG, "
                        + TARIH_SONARAMA + " LONG, "
                        + TESLIMAT + " INTEGER, "
                        + SIPARIS + " INTEGER, "
                        + ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
                        + " )"
        );



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXITS" + TABLE);
        onCreate(db);

    }


    public boolean Ekle(String numara, String adress, String sepet, long tarih_eklenme, long tarih_sonarama) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues veriler = new ContentValues();

        veriler.put(NUMARA, numara.trim());
        veriler.put(ADRESS, adress.trim());
        veriler.put(SEPET, sepet.trim());
        veriler.put(TARIH_EKLENME, tarih_eklenme);
        veriler.put(TARIH_SONARAMA, tarih_sonarama);
        veriler.put(TESLIMAT, 0);
        veriler.put(SIPARIS, 0);

        long result = db.insert(TABLE, null, veriler);
        if (result == -1)
            return false;
        else
            return true;

    }


    Cursor VerileriAl() {
        //Verileri Sırasıyle Ceker

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE +
                " ORDER BY " + TARIH_EKLENME +
                " ASC", null);
        return cursor;
    }

    Cursor SonArananSiraliCek(){
        //Verileri son aranma sırasına göre ceker

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE +
                " WHERE "+TARIH_SONARAMA+" <> 0 ORDER BY " + TARIH_SONARAMA +
                " DESC", null);
        return cursor;
    }

    Cursor TeslimatYapilmayanCek() {
        //Teslimatı yapılmayan ve siparişi verilen müşterileri ceker

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE +
                " WHERE "+TESLIMAT+" = 0 AND "+SIPARIS+" = 1 ", null);

        return cursor;
    }

    Cursor SiparisVerenCek() {
        //Siparişde olan müşterileri çeker

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE +
                " WHERE "+SIPARIS+" = 1", null);

        return cursor;
    }


    public void VeriGüncelle(String numara, String adress,String sepet) {
        //Verilen numaradaki adresi ve siparişi günceller

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(ADRESS, adress);
        contentValues.put(SEPET, sepet);

        db.update(TABLE, contentValues, NUMARA+" = ?", new String[]{numara});
    }

    void SonAramaTarihGüncelle(String numara, long sonarama) {
        //Son Arama tarihini günceller

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TARIH_SONARAMA, sonarama);

        db.update(TABLE, contentValues, NUMARA+" = ?", new String[]{numara});
    }

    public void TeslimEdildi(String numara) {
        //Teslim edildigini veri tabanına ekler

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TESLIMAT, 1);

        db.update(TABLE, contentValues, NUMARA+" = ?", new String[]{numara});
    }
    public void TeslimatVer(String numara) {
        //Teslim edilmedi diye veri tabanına ekler

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(TESLIMAT, 0);

        db.update(TABLE, contentValues, NUMARA+" = ?", new String[]{numara});
    }

    public void SiparisGüncelle(String numara,boolean siparis) {
        //siparisi ginceller

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if(siparis)
            contentValues.put(SIPARIS, 1);
        else
            contentValues.put(SIPARIS, 0);

        db.update(TABLE, contentValues, NUMARA+" = ?", new String[]{numara});
    }

    String VeriAra(String Numara){
        //Verilen numaranın veritabanında olup olmadıgına bakar
        Numara=Numara.trim();
        String temp_address="";

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT "+ADRESS+" FROM "+TABLE+" WHERE "+NUMARA+"=?";
        Cursor c = db.rawQuery(selectQuery, new String[] { Numara });
        if (c.moveToFirst()) {
            temp_address = c.getString(c.getColumnIndex(ADRESS));
        }
        c.close();

        return temp_address;
    }


    public Integer VeriSil(String numara) {
        //Numarası Verilen müşteriyi siler
        numara=numara.trim();

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE, NUMARA+" = ?", new String[]{numara});
    }

    public void VerileriSil(){
        //Tüm veritabanını siler
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE,null,null);
        db.close();

    }


}