package com.example.admin.applazada.View.TrangChu;


import com.example.admin.applazada.Model.ObjectClass.DienTu;
import com.example.admin.applazada.Model.ObjectClass.SanPham;
import com.example.admin.applazada.Model.ObjectClass.ThuongHieu;

import java.util.List;

/**
 * Created by Lenovo S410p on 7/21/2016.
 */
public interface ViewDienTu {
    void HienThiDanhSach(List<DienTu> dienTus);
    void HienThiLogoThuongHieu(List<ThuongHieu> thuongHieus);
    void LoiLayDuLieu();
    void HienThiSanPhamMoiVe(List<SanPham> sanPhams);
}
