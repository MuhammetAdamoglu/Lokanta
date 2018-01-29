package com.lokanta.lokanta.Fragmentler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lokanta.lokanta.Adaptor;
import com.lokanta.lokanta.AlertDiyaloglar.AlertDialog;
import com.lokanta.lokanta.IDs;
import com.lokanta.lokanta.R;
import com.lokanta.lokanta.Tabs;
import com.lokanta.lokanta.VeriCek;

import java.util.ArrayList;

public class Fragment_SonArayanlar  extends Fragment {

    Tabs tabs;
    public void setActivity(Tabs tabs){
        this.tabs=tabs;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ArrayList<String> Numaralar = new ArrayList<>();
    ArrayList<String> Adress = new ArrayList<>();
    ArrayList<String> Sepet = new ArrayList<>();
    ArrayList<Long> EklenmeTarihi = new ArrayList<>();
    ArrayList<Long> SonAramaTarihi = new ArrayList<>();

    ListView listView;
    Adaptor adaptor;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragmet, container, false);


        listView=view.findViewById(R.id.listView);
        TextView title =view.findViewById(R.id.textView_Title);
        title.setText("Son Arayan Müşteriler");


        VeriGüncelle();

        TextView veriyok =view.findViewById(R.id.textView_veriyok);
        if(Numaralar.size()==0){
            veriyok.setVisibility(View.VISIBLE);
            veriyok.setText("Son Arayan Müşteri Bulunmamakta");
        }else {
            veriyok.setVisibility(View.GONE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                //Müsteriye tıkalnırsa diyalog cagırılır
                new AlertDialog(getContext(),tabs,Numaralar.get(position),Numaralar.get(position),Adress.get(position),Sepet.get(position),EklenmeTarihi.get(position),SonAramaTarihi.get(position), IDs.Sor_Güncelle_Sil);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Müsteriye basılı tutarsa arama ekranına gidilir
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+Numaralar.get(position)));
                startActivity(intent);
                return true;
            }
        });

        return view;
    }

    public void DiziSil(String numara, String adress, String sepet, long eklenme_tarih, long sonarama_tarih){
        //Dizideki Elemanı Siler
        Numaralar.remove(numara);
        Adress.remove(adress);
        Sepet.remove(sepet);
        EklenmeTarihi.remove(eklenme_tarih);
        SonAramaTarihi.remove(sonarama_tarih);

        if(adaptor!=null)
         adaptor.notifyDataSetChanged();
    }

    public void DiziGüncelle(String numara, EditText adress_ekle, EditText ürün_ekle, long eklenme_tarih, long sonarama_tarih){
        //Dizideki Elemanı Günceller
        try {
            Adress.set(Numaralar.indexOf(numara),adress_ekle.getText().toString().trim());
            Sepet.set(Numaralar.indexOf(numara),ürün_ekle.getText().toString().trim());
            EklenmeTarihi.set(Numaralar.indexOf(numara),eklenme_tarih);
            SonAramaTarihi.set(Numaralar.indexOf(numara),sonarama_tarih);

            if(adaptor!=null)
                adaptor.notifyDataSetChanged();
        }catch (Exception ignored){}

    }

    public void VeriGüncelle(){
        //Veritabanından bilgi cekip diziye aktarır
        if(getContext()!=null){
            VeriCek veriCek = new VeriCek();

            veriCek.SonArananVeriCek(getContext());
            Numaralar=veriCek.NumaralarıCek();
            Adress =veriCek.AdressCek();
            Sepet = veriCek.SepetCek();
            EklenmeTarihi = veriCek.EklenmeTarihiCek();
            SonAramaTarihi=veriCek.SonAramaTarihiCek();

            adaptor = new Adaptor(getContext(),Numaralar, Adress,SonAramaTarihi);
            listView.setAdapter(adaptor);
        }

    }

}
