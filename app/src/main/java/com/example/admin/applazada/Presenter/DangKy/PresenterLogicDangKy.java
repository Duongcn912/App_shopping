package com.example.admin.applazada.Presenter.DangKy;

import com.example.admin.applazada.Model.DangNhap_DangKy.ModelDangKy;
import com.example.admin.applazada.Model.ObjectClass.NhanVien;
import com.example.admin.applazada.View.DangNhap_DangKy.ViewDangKy;

public class PresenterLogicDangKy implements iPresenterDangKy {
    ViewDangKy viewDangKy;
    ModelDangKy modelDangKy;
    public PresenterLogicDangKy(ViewDangKy viewDangKy){
        this.viewDangKy=viewDangKy;
        modelDangKy=new ModelDangKy();
    }
    @Override
    public void ThucHienDangKy(NhanVien nhanVien) {
        boolean kiemtra=modelDangKy.DangKyThanhVien(nhanVien);
        if(kiemtra){
            viewDangKy.DangKyThanhCong();
        }
        else viewDangKy.DangKyThatBai();
    }
}
