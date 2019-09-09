package com.example.admin.applazada.Presenter.DangNhap;

import android.content.Context;

import com.example.admin.applazada.Model.DangNhap_DangKy.ModelDangNhap;
import com.example.admin.applazada.View.DangNhap_DangKy.ViewDangNhap;

public class PresenterLogicDangNhap implements iPresenterDangNhap {
    ViewDangNhap viewDangNhap;
    ModelDangNhap modelDangNhap;
    Context context;
    public PresenterLogicDangNhap(ViewDangNhap viewDangNhap,Context context){
        this.viewDangNhap=viewDangNhap;
        this.context=context;
        modelDangNhap=new ModelDangNhap();
    }
    @Override
    public void KiemTraDangNhap(String tendangnhap,String matkhau) {
        boolean kiemtra=modelDangNhap.KiemTraDangNhap(context,tendangnhap,matkhau);
        if(kiemtra) viewDangNhap.DangNhapThanhCong();
        else viewDangNhap.DangNhapThatBai();
    }
}
