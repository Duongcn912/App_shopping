package com.example.admin.applazada.Model.ObjectClass;

import java.util.List;

/**
 * Created by Lenovo S410p on 9/13/2016.
 */
public class KhuyenMai {

    int MAKM,MALOAISP;
    String TENKM,NGAYBATDAU,NGAYKETTHUC,HINHKHUYENMAI,TENLOAISP;
    List<SanPham> DanhSachSanPhamKhuyenMai;

    public int getMAKM() {
        return MAKM;
    }

    public void setMAKM(int MAKM) {
        this.MAKM = MAKM;
    }

    public int getMALOAISP() {
        return MALOAISP;
    }

    public void setMALOAISP(int MALOAISP) {
        this.MALOAISP = MALOAISP;
    }

    public String getTENKM() {
        return TENKM;
    }

    public void setTENKM(String TENKM) {
        this.TENKM = TENKM;
    }

    public String getNGAYBATDAU() {
        return NGAYBATDAU;
    }

    public void setNGAYBATDAU(String NGAYBATDAU) {
        this.NGAYBATDAU = NGAYBATDAU;
    }

    public String getNGAYKETTHUC() {
        return NGAYKETTHUC;
    }

    public void setNGAYKETTHUC(String NGAYKETTHUC) {
        this.NGAYKETTHUC = NGAYKETTHUC;
    }

    public String getHINHKHUYENMAI() {
        return HINHKHUYENMAI;
    }

    public void setHINHKHUYENMAI(String HINHKHUYENMAI) {
        this.HINHKHUYENMAI = HINHKHUYENMAI;
    }

    public List<SanPham> getDanhSachSanPhamKhuyenMai() {
        return DanhSachSanPhamKhuyenMai;
    }

    public void setDanhSachSanPhamKhuyenMai(List<SanPham> danhSachSanPhamKhuyenMai) {
        DanhSachSanPhamKhuyenMai = danhSachSanPhamKhuyenMai;
    }


    public String getTENLOAISP() {
        return TENLOAISP;
    }

    public void setTENLOAISP(String TENLOAISP) {
        this.TENLOAISP = TENLOAISP;
    }


}
