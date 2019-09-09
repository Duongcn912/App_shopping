package com.example.admin.applazada.View.DangNhap_DangKy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.admin.applazada.Adapter.ViewPagerAdapterDangNhap;
import com.example.admin.applazada.R;
import com.example.admin.applazada.View.DangNhap_DangKy.Fragment.FragmentDangNhap;

public class DangNhapActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolBar;
    FragmentDangNhap fragmentDangNhap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_dangnhap);
        Initdata();

    }
    void Initdata(){
        tabLayout=(TabLayout) findViewById(R.id.tabDangNhap);
        viewPager=(ViewPager)findViewById(R.id.viewPagerDangNhap);
        toolBar=(Toolbar) findViewById(R.id.toolBarDangNhap);
        setSupportActionBar(toolBar);

        ViewPagerAdapterDangNhap viewPagerAdapterDangNhap=new ViewPagerAdapterDangNhap(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapterDangNhap);
        viewPagerAdapterDangNhap.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);
    }

}
