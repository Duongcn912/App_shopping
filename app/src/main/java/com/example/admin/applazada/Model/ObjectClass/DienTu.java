package com.example.admin.applazada.Model.ObjectClass;

import android.widget.ImageView;

import java.util.List;

/**
 * Created by Lenovo S410p on 7/21/2016.
 */
public class DienTu {
    List<ThuongHieu> thuongHieus;
    List<SanPham> sanPhams;
    boolean thuonghieu;
    String tennoibat,tentopnoibat;

    public boolean isThuonghieu() {
        return thuonghieu;
    }

    public void setThuonghieu(boolean thuonghieu) {
        this.thuonghieu = thuonghieu;
    }



    public String getTennoibat() {
        return tennoibat;
    }

    public void setTennoibat(String tennoibat) {
        this.tennoibat = tennoibat;
    }

    public String getTentopnoibat() {
        return tentopnoibat;
    }

    public void setTentopnoibat(String tentopnoibat) {
        this.tentopnoibat = tentopnoibat;
    }



    public List<ThuongHieu> getThuongHieus() {
        return thuongHieus;
    }

    public void setThuongHieus(List<ThuongHieu> thuongHieus) {
        this.thuongHieus = thuongHieus;
    }

    public List<SanPham> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(List<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }


}
