package com.example.admin.applazada.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.admin.applazada.View.DangNhap_DangKy.Fragment.FragmentDangKy;
import com.example.admin.applazada.View.DangNhap_DangKy.Fragment.FragmentDangNhap;

public class ViewPagerAdapterDangNhap extends FragmentPagerAdapter {

    public ViewPagerAdapterDangNhap(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:{
                FragmentDangNhap frag=new FragmentDangNhap();

                return frag;

            }
            case 1:{
                FragmentDangKy frag=new FragmentDangKy();
                return frag;
            }
            default:{
                return null;
            }
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:{
                return "Đăng nhập";
            }
            case 1:{
                return "Đăng ký";
            }
            default:{
                return "";
            }
        }
    }
}
