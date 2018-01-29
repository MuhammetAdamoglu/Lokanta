package com.lokanta.lokanta;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Adaptor extends ArrayAdapter<String> {
    //Bu Sınıf, Verileri ListView e Aktarır

    private final Context context;
    private final ArrayList values_Numara;
    private final ArrayList values_Adress;
    private final ArrayList<Long> values_Tarih;


    public Adaptor(Context context, ArrayList values_download, ArrayList values_adress, ArrayList<Long> values_tarih) {
        super(context, R.layout.listview, values_download);
        this.context = context;
        values_Numara = values_download;
        values_Adress = values_adress;
        values_Tarih = values_tarih;


    }


    @NonNull
    @SuppressLint({"ViewHolder", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;

        assert inflater != null;
        rowView = inflater.inflate(R.layout.listview, parent, false);

        TextView textView_numara = rowView.findViewById(R.id.numara);
        TextView textView_adress = rowView.findViewById(R.id.mesaj);
        TextView textView_tarih = rowView.findViewById(R.id.textView_time);

        textView_numara.setText(values_Numara.get(position).toString());
        textView_adress.setText(values_Adress.get(position).toString());
        textView_tarih.setText(MilisecondToString(values_Tarih.get(position)));


        return rowView;
    }


    private static String MilisecondToString(long date){
        //Gelen Milisaniyeyi String e Çevirir
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy  hh:mm:ss a");
        if(date==0)
            return "";
        else
            return sdf.format(date);
    }

}