package com.example.fahad.pointofsale;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends AppCompatActivity{

    TabLayout tabLayout;
    ViewPager viewPager;
    PagerAdapter pagerAdapter;



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu);

        viewPager = (ViewPager)findViewById(R.id.vpPager);
        pagerAdapter =new FixedTabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Fragment fragment=((FixedTabsPagerAdapter)viewPager.getAdapter()).getFragment(position);
                if(fragment!=null){
                    fragment.onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
       
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(this, "This function is disabled", Toast.LENGTH_SHORT).show();
    }

    class FixedTabsPagerAdapter extends FragmentPagerAdapter{
        Map<Integer,String> mFragmentTag;
        FragmentManager mFragmentManager;
        public FixedTabsPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager=fm;
            mFragmentTag=new HashMap<Integer, String>();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new ScanFragment();
                case 1: return new CartFragment();
                case 2: return new CheckoutFragment();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return getString(R.string.scan_items);
                case 1: return getString(R.string.cart);
                case 2: return getString(R.string.checkout);
                default: return null;
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj=super.instantiateItem(container, position);
            if(obj instanceof Fragment){
                Fragment f=(Fragment)obj;
                String tag=f.getTag();
                mFragmentTag.put(position,tag);
            }
            return obj;
        }

        public Fragment getFragment(int position){
            String tag=mFragmentTag.get(position);
            if(tag==null){
                return null;
            }
            return mFragmentManager.findFragmentByTag(tag);
        }
    }
}

