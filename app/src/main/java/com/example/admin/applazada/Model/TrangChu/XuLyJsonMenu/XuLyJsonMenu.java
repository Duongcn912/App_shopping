package com.example.admin.applazada.Model.TrangChu.XuLyJsonMenu;

import android.os.Bundle;
import android.util.Log;

import com.example.admin.applazada.ConnectInternet.DownLoadJson;
import com.example.admin.applazada.Model.ObjectClass.LoaiSanPham;
import com.example.admin.applazada.TrangChuActivity;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

//ham xu li josn sau khi download co dc json

public class XuLyJsonMenu {
    String tennguoidung="";
    public List<LoaiSanPham> ParserJsonMenu(String dulieuJson){
         List<LoaiSanPham> sanPhamList=new ArrayList<LoaiSanPham>();
        try {
            Log.d("Jsondulieu",dulieuJson);
            JSONObject jsonObject=new JSONObject(dulieuJson);
            JSONArray jsonArray=jsonObject.getJSONArray("loaisanpham");
            int  count=jsonArray.length();
            Log.d("count la ",count+"");
            for(int i=0;i<count;i++){
                JSONObject value=jsonArray.getJSONObject(i);
                LoaiSanPham dataLoaiSanPham=new LoaiSanPham();
                dataLoaiSanPham.setMALOAISP(Integer.valueOf(value.getString("MALOAISP")));
                dataLoaiSanPham.setMALOAICHA(Integer.valueOf(value.getString("MALOAI_CHA")));
                dataLoaiSanPham.setTENLOAISP(value.getString("TENLOAISP"));

                sanPhamList.add(dataLoaiSanPham);
            }
        } catch (JSONException e) {
                e.printStackTrace();
        }
        Log.d("kiemtrasize",sanPhamList.size()+"");
        return sanPhamList;
    }
    public List<LoaiSanPham> LayDanhSachLoaiSanPhamTheoMaLoai(int maloaisp) {// ham lay litscon theo ma loai sp

        List<LoaiSanPham> sanPhamList = new ArrayList<>();
        String dataJSON = "";
        String duongdan = TrangChuActivity.SERVER_NAME+"/loaisanpham.php";
        List<HashMap<String, String>> attrs = new ArrayList<>();// noi chua cac thu can gui vao output stream

        HashMap<String,String> hsHam=new HashMap<>();
        hsHam.put("ham","LayDanhSachMenu");// put ham dung cai ten laydanhsachmenu
        HashMap<String, String> hsMaLoaiCha = new HashMap<>();
        hsMaLoaiCha.put("maloaicha", String.valueOf(maloaisp));
        attrs.add(hsMaLoaiCha);
        attrs.add(hsHam);
        DownLoadJson downloadJSON = new DownLoadJson(duongdan, attrs);
        downloadJSON.execute();
        try {
            dataJSON = downloadJSON.get();
            XuLyJsonMenu xuLyJSONMenu = new XuLyJsonMenu();
            sanPhamList = xuLyJSONMenu.ParserJsonMenu(dataJSON);
            Log.d("dataJson",dataJSON.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            }
            return sanPhamList;
//        List<LoaiSanPham>sanPhamList=new ArrayList<>();
//        String dataJSON="";
//        String duongdan = TrangChuActivity.SERVER_NAME ;
//        List<HashMap<String,String>> attrs = new ArrayList<>();
//
////        HashMap<String,String> hsHam = new HashMap<>();
////        hsHam.put("ham","LayDanhSachMenu");
//
//        HashMap<String,String> hsMaLoaiCha = new HashMap<>();
//        hsMaLoaiCha.put("maloaicha",String.valueOf(maloaisp));
//
//        attrs.add(hsMaLoaiCha);
//        //   attrs.add(hsHam);
//
//        DownLoadJson downloadJSON = new DownLoadJson(duongdan,attrs);
//        //end phương thức post
//        downloadJSON.execute();
//        try {
//            dataJSON = downloadJSON.get();
//            XuLyJsonMenu xuLyJSONMenu = new XuLyJsonMenu();
//            sanPhamList = xuLyJSONMenu.ParserJsonMenu(dataJSON);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//        return sanPhamList;
        //}
//    public String LayTenNguoiDungFaceBook(AccessToken accessToken){
//
//        GraphRequest graphRequest=GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                try {
//                    tennguoidung=object.getString("name");
//                    Log.d("tennguoidung",tennguoidung);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        Bundle paramater = new Bundle();
//        paramater.putString("fields","name");
//        graphRequest.setParameters(paramater);
//        graphRequest.executeAsync();// la 1 asynctask, la 1 tien trinh , 1 luonjg chay soong song
//
//        return tennguoidung;
//
//    }
    }
}
