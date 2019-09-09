package com.example.admin.applazada.Model.DangNhap_DangKy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.admin.applazada.ConnectInternet.DownLoadJson;
import com.example.admin.applazada.Model.ObjectClass.NhanVien;
import com.example.admin.applazada.TrangChuActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelDangNhap {
    AccessToken accessToken; // tao bien cuc bo khien minh kp dung mang
    AccessTokenTracker accessTokenTracker;
    public AccessToken LayTokenFaceBookHienTai(){

         accessTokenTracker=new AccessTokenTracker() {// thay doi thi chay token nay`
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken=currentAccessToken;
            }
        };
         accessToken=AccessToken.getCurrentAccessToken(); // neu co san chi chay code nay`
//        Log.d("Token",accessToken.toString());
        return accessToken;
    }
    public GoogleApiClient LayGoogleApiClient(Context context, GoogleApiClient.OnConnectionFailedListener failedListener){
        GoogleApiClient mGoogleApiClient;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(((AppCompatActivity)context),failedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        return mGoogleApiClient;
    }


    public String LayCachedDangNhap(Context context){
        SharedPreferences cachedDangNhap = context.getSharedPreferences("dangnhap",Context.MODE_PRIVATE);
        String tennv = cachedDangNhap.getString("tennv","");

        return tennv;
    }

    public boolean KiemTraDangNhap(Context context,String tendangnhap,String matkhau ){
        String duongdan= TrangChuActivity.SERVER_NAME +"/loaisanpham.php";
        List<HashMap<String,String>> attrs=new ArrayList<>();
        boolean kiemtra=false;

        HashMap<String,String> hsHam=new HashMap<>();
        hsHam.put("ham","KiemTraDangNhap");

        HashMap<String,String> hsTenDangNhap=new HashMap<>();
        hsTenDangNhap.put("tendangnhap",tendangnhap);

        HashMap<String,String> hsMatKhau=new HashMap<>();
        hsMatKhau.put("matkhau",matkhau);

        attrs.add(hsHam);
        attrs.add(hsTenDangNhap);
        attrs.add(hsMatKhau);

        DownLoadJson downLoadJson=new DownLoadJson(duongdan,attrs);
        downLoadJson.execute();

        try {
            String dulieu=downLoadJson.get();

            JSONObject jsonObject=new JSONObject(dulieu);
            String ketqua=jsonObject.getString("ketqua");

            if(ketqua=="true"){
                kiemtra=true;
                String tennv=jsonObject.getString("tennv");

                CapNhatCachedDangNhap(context,tennv);
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


    public void CapNhatCachedDangNhap(Context context,String tenv){
        SharedPreferences cachedDangNhap = context.getSharedPreferences("dangnhap",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cachedDangNhap.edit();
        editor.putString("tennv",tenv);

        editor.commit();
    }
    public GoogleSignInResult LayThongDangNhapGoogle(GoogleApiClient googleApiClient){
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            return opr.get();
        }else{
            return null;
        }
    }
    public void onDestroy() {

        accessTokenTracker.stopTracking();
    }
    public GoogleSignInOptions LayGoogleSignInOption(){
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .build();
        return gso;
    }
    public GoogleSignInClient LayGoogleSignInClient(Context context,GoogleSignInOptions mGoogleSignInOptions){
        GoogleSignInClient mGoogleSignInClient= GoogleSignIn.getClient(context,mGoogleSignInOptions);
        return mGoogleSignInClient;

    }
    public GoogleSignInAccount LayGoogleSignInAcount(Context context){
        GoogleSignInAccount mGoogleSignInAccount= GoogleSignIn.getLastSignedInAccount(context);
        return mGoogleSignInAccount;
    }

}
