package com.example.admin.applazada.Model.DangNhap_DangKy;

import android.util.Log;

import com.example.admin.applazada.ConnectInternet.DownLoadJson;
import com.example.admin.applazada.Model.ObjectClass.NhanVien;
import com.example.admin.applazada.TrangChuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelDangKy {
    public Boolean DangKyThanhVien(NhanVien nhanVien){
        String duongdan= TrangChuActivity.SERVER_NAME+"/loaisanpham.php";
        boolean kiemtra=false;
        HashMap<String,String> hsHam=new HashMap<>();
        hsHam.put("ham","DangKyThanhVien");
        List<HashMap<String,String>> attrs=new ArrayList<>();

        HashMap<String,String> hsTenNV=new HashMap<>();
        hsTenNV.put("tennv",nhanVien.getTenNV());

        HashMap<String,String>hsTenDN=new HashMap<>();
        hsTenDN.put("tendangnhap",nhanVien.getTenDN());

//        Log.d("Ten Email",nhanVien.getTenDN());

        HashMap<String,String>hsMatKhau=new HashMap<>();
        hsMatKhau.put("matkhau",nhanVien.getMatKhau());

        HashMap<String,String>hsMaLoaiNV=new HashMap<>();
        hsMaLoaiNV.put("maloainv",String.valueOf(nhanVien.getMaLoaiNV()));

        HashMap<String,String>hsEmailDocQuyen=new HashMap<>();
        hsEmailDocQuyen.put("emaildocquyen",nhanVien.getEmailDocQuyen());

        attrs.add(hsTenNV);
        attrs.add(hsTenDN);
        attrs.add(hsMatKhau);
        attrs.add(hsMaLoaiNV);
        attrs.add(hsEmailDocQuyen);
        attrs.add(hsHam);
        DownLoadJson downLoadJson=new DownLoadJson(duongdan,attrs);
        downLoadJson.execute();
        try {
            String dulieu=downLoadJson.get();
            Log.d("dulieutrave",dulieu);
            JSONObject jsonObject=new JSONObject(dulieu);
            String ketqua=jsonObject.getString("ketqua");
            Log.d("ketqua",ketqua);
            if(ketqua=="true"){
                kiemtra=true;

            }
            else kiemtra=false;

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return kiemtra;
    }
}
