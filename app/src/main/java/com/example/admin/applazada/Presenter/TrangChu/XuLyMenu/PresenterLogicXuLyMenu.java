package com.example.admin.applazada.Presenter.TrangChu.XuLyMenu;



import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.admin.applazada.ConnectInternet.DownLoadJson;
import com.example.admin.applazada.Model.DangNhap_DangKy.ModelDangNhap;
import com.example.admin.applazada.Model.ObjectClass.LoaiSanPham;
import com.example.admin.applazada.Model.TrangChu.XuLyJsonMenu.XuLyJsonMenu;
import com.example.admin.applazada.TrangChuActivity;
import com.example.admin.applazada.View.TrangChu.ViewXuLyMenu;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Lenovo S410p on 6/25/2016.
 */
public class PresenterLogicXuLyMenu implements IPresenterXuLyMenu {

    ViewXuLyMenu viewXuLyMenu;
    String tennguoidung = "";

    public PresenterLogicXuLyMenu(ViewXuLyMenu viewXuLyMenu){
        this.viewXuLyMenu = viewXuLyMenu;
    }

    @Override
    public void LayDanhSachMenu() {
        List<LoaiSanPham> loaiSanPhamList;
        String dataJSON = "";
        List<HashMap<String,String>> attrs = new ArrayList<>();


        //Lấy bằng phương thức get
//        String duongdan = "http://192.168.1.9/weblazada/loaisanpham.php?maloaicha=0";

//        DownloadJSON downloadJSON = new DownloadJSON(duongdan);
        // end phương thức get

        //Lấy bằng phương thức post
        String duongdan = TrangChuActivity.SERVER_NAME+"/loaisanpham.php" ;

//        HashMap<String,String> hsHam = new HashMap<>();
//        hsHam.put("ham","LayDanhSachMenu");
        HashMap<String,String> hsHam=new HashMap<>();
        hsHam.put("ham","LayDanhSachMenu");
        HashMap<String,String> hsMaLoaiCha = new HashMap<>();
        hsMaLoaiCha.put("maloaicha","0");

        attrs.add(hsMaLoaiCha);
        attrs.add(hsHam);
     //   attrs.add(hsHam);

        DownLoadJson downloadJSON = new DownLoadJson(duongdan,attrs);
        //end phương thức post
        downloadJSON.execute();

        try {
            dataJSON = downloadJSON.get();
            Log.d("DataJson",dataJSON);
            XuLyJsonMenu xuLyJSONMenu = new XuLyJsonMenu();
            loaiSanPhamList = xuLyJSONMenu.ParserJsonMenu(dataJSON);
            viewXuLyMenu.HienThiDanhSachMenu(loaiSanPhamList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public AccessToken LayTenNguoiDungFaceBook() {
        ModelDangNhap modelDangNhap=new ModelDangNhap();
        AccessToken accessToken=modelDangNhap.LayTokenFaceBookHienTai();

        return accessToken;

//        XuLyJsonMenu xuLyJsonMenu=new XuLyJsonMenu();
//        Log.d("token",accessToken.toString());
//        return xuLyJsonMenu.LayTenNguoiDungFaceBook(accessToken);

    }

    public GoogleSignInClient LayGoogleSignInClient(Context context){
        ModelDangNhap modelDangNhap=new ModelDangNhap();
        GoogleSignInOptions gso=modelDangNhap.LayGoogleSignInOption();
        GoogleSignInClient mGoogleSignInClient=modelDangNhap.LayGoogleSignInClient(context,gso);
        return mGoogleSignInClient;

    }



}
