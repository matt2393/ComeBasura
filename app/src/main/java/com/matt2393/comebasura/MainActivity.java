package com.matt2393.comebasura;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    Control_Pages control_pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setTitle("Otro titulo");

        viewPager=(ViewPager)findViewById(R.id.contenedor);

        Adapter_ViewPaper adp=new Adapter_ViewPaper(getSupportFragmentManager());
        adp.add(new FragmentLector());
        adp.add(new FragmentEstadisticas());

        viewPager.setAdapter(adp);
      //  control_pages=(Control_Pages) getSupportFragmentManager().getFragments().get(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("TAMAÑO",""+getSupportFragmentManager().getFragments().size());
                control_pages=(Control_Pages)getSupportFragmentManager().getFragments().get(0);

                if(position==1){
                    //getSupportFragmentManager().getFragments().get(0).onPause();
                    control_pages.pause();
                    Log.i("FRAG","pause");
                }
                else if (position==0){
                    //getSupportFragmentManager().getFragments().get(0).onResume();
                    control_pages.resume();
                }
                Log.i("FRAGMENTO",""+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

             }
        });

       // getSupportFragmentManager().beginTransaction().replace(R.id.conten,new FragmentLector()).commit();
    }

    private class Adapter_ViewPaper extends FragmentStatePagerAdapter{

        List<Fragment> fragmentos=new ArrayList<>();

        public Adapter_ViewPaper(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }
        public void add(Fragment frag){
            fragmentos.add(frag);
        }
    }
}
