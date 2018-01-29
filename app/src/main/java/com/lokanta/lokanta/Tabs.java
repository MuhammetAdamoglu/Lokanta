
package com.lokanta.lokanta;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lokanta.lokanta.AlertDiyaloglar.AlerDialog_MusteriEkle;
import com.lokanta.lokanta.AlertDiyaloglar.AlerDialog_Tümünü_Sil;
import com.lokanta.lokanta.Fragmentler.Fragment_SiparisVerenler;
import com.lokanta.lokanta.Fragmentler.Fragment_SonArayanlar;
import com.lokanta.lokanta.Fragmentler.Fragment_TeslimatYapılmayanlar;
import com.lokanta.lokanta.Fragmentler.Fragment_TümMüsteriler;

import java.util.ArrayList;
import java.util.List;


public class Tabs extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private static ViewPager viewPager;


    public static void setCurrentItem (int item, boolean smoothScroll) {
        viewPager.setCurrentItem(item, smoothScroll);
    }

    Fragment_TeslimatYapılmayanlar fragment_teslimatYapılmayanlar;
    Fragment_SiparisVerenler fragment_siparisVerenler;
    Fragment_SonArayanlar fragment_sonArayanlar;
    Fragment_TümMüsteriler fragment_tümMüsteriler;

    Tabs tabs=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        //Musteri ekleme butonu
        ImageView musteri_ekle = findViewById(R.id.musteri_ekle);
        musteri_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlerDialog_MusteriEkle(getApplicationContext(),tabs);
                Toast.makeText(Tabs.this, "Girdi", Toast.LENGTH_SHORT).show();
            }
        });

        //Tümünü silme butonu
        ImageView tümünü_sil = findViewById(R.id.tümünü_sil);
        tümünü_sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlerDialog_Tümünü_Sil(getApplicationContext(),tabs);
            }
        });

        //Yenileme butonu
        ImageView yenile = findViewById(R.id.yenile);
        yenile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VeriGüncelle();
            }
        });

        fragment_teslimatYapılmayanlar=new Fragment_TeslimatYapılmayanlar();
        fragment_teslimatYapılmayanlar.setActivity(this);

        fragment_siparisVerenler=new Fragment_SiparisVerenler();
        fragment_siparisVerenler.setActivity(this);

        fragment_sonArayanlar=new Fragment_SonArayanlar();
        fragment_sonArayanlar.setActivity(this);

        fragment_tümMüsteriler=new Fragment_TümMüsteriler();
        fragment_tümMüsteriler.setActivity(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();


    }

    public void DiziSil(String numara, String adress, String sepet, long eklenme_tarih, long sonarama_tarih){
        //Fragmentlerdeki dizi sil metodlarını cagırır
        fragment_teslimatYapılmayanlar.DiziSil(numara,adress,sepet,eklenme_tarih,sonarama_tarih);
        fragment_siparisVerenler.DiziSil(numara,adress,sepet,eklenme_tarih,sonarama_tarih);
        fragment_sonArayanlar.DiziSil(numara,adress,sepet,eklenme_tarih,sonarama_tarih);
        fragment_tümMüsteriler.DiziSil(numara,adress,sepet,eklenme_tarih,sonarama_tarih);

    }

    public void TeslimatDiziSil(String numara, String adress, String sepet, long eklenme_tarih, long sonarama_tarih){
        //Teslimat dizisindeki teslim edilen ürünü kaldırır
        fragment_teslimatYapılmayanlar.DiziSil(numara,adress,sepet,eklenme_tarih,sonarama_tarih);
    }

    public void DiziGüncelle(String numara, EditText adress_ekle, EditText ürün_ekle, long eklenme_tarih, long sonarama_tarih){
        //Fragmentlerdeki dizi güncelle metodlarını cagırır
        fragment_teslimatYapılmayanlar.DiziGüncelle(numara,adress_ekle,ürün_ekle,eklenme_tarih,sonarama_tarih);
        fragment_siparisVerenler.DiziGüncelle(numara,adress_ekle,ürün_ekle,eklenme_tarih,sonarama_tarih);
        fragment_sonArayanlar.DiziGüncelle(numara,adress_ekle,ürün_ekle,eklenme_tarih,sonarama_tarih);
        fragment_tümMüsteriler.DiziGüncelle(numara,adress_ekle,ürün_ekle,eklenme_tarih,sonarama_tarih);

    }

    public void VeriGüncelle(){
        //Fragmentlerdeki veri güncelle metodlarını cagırır
        fragment_teslimatYapılmayanlar.VeriGüncelle();
        fragment_siparisVerenler.VeriGüncelle();
        fragment_sonArayanlar.VeriGüncelle();
        fragment_tümMüsteriler.VeriGüncelle();
    }

    private void setupTabIcons() {
            //tabLayout.getTabAt(2).setIcon(R.drawable.icon_settings);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(fragment_teslimatYapılmayanlar, "1");
        adapter.addFragment(fragment_siparisVerenler, "2");
        adapter.addFragment(fragment_sonArayanlar, "3");
        adapter.addFragment(fragment_tümMüsteriler, "4");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}