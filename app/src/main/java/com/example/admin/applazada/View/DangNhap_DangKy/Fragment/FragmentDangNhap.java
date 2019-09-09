package com.example.admin.applazada.View.DangNhap_DangKy.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.applazada.Model.DangNhap_DangKy.ModelDangNhap;
import com.example.admin.applazada.Presenter.DangNhap.PresenterLogicDangNhap;
import com.example.admin.applazada.R;
import com.example.admin.applazada.TrangChuActivity;
import com.example.admin.applazada.View.DangNhap_DangKy.ViewDangNhap;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class FragmentDangNhap extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener,ViewDangNhap{


    Button btnDangNhapFaceBook,btnDangNhapGoogle,btnDangNhap;
    EditText edtDiaChiDangNhap,edtMatKhauDangNhap;
    CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInClient mGoogleSignInClient;
    ProgressDialog progressDialog;
    ModelDangNhap modelDangNhap;
    PresenterLogicDangNhap presenterLogicDangNhap;
    public static int SIGN_IN_GOOGLE_PLUS=111;
    GoogleSignInOptions gso;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view =inflater.inflate(R.layout.layout_fragment_dangnhap,container,false);
        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(getContext())
                        .enableAutoManage(getActivity(),this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                        .build();
        mGoogleSignInClient=GoogleSignIn.getClient(getContext(),gso);
        FacebookSdk.sdkInitialize(getContext().getApplicationContext());
        callbackManager=CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent iTrangChu=new Intent(getActivity(),TrangChuActivity.class);
                startActivity(iTrangChu);// su kien dang nhap thanh cong
//                Log.d("Kiem tra dang nhap","thanh cong");
            }

            @Override
            public void onCancel() {
                Log.d("Kiem tra dang nhap","Thoat");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Kiem tra dang nhap","loi");
            }
        });
        initData(view);
        setAction();
//        try {
//            PackageInfo info = getActivity().getPackageManager().getPackageInfo(// packageinfo la thong tin package app
//                    "com.example.admin.applazada",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("kiemtra", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
        return view;
    }
    private  void initData(View view){
        btnDangNhapFaceBook=view.findViewById(R.id.btnDangNhapFaceBook);
        btnDangNhapGoogle=view.findViewById(R.id.btnDangNhapGoogle);
        btnDangNhap=view.findViewById(R.id.btnDangNhap);
        edtDiaChiDangNhap=view.findViewById(R.id.edtDiaChiEmailDangNhap);
        edtMatKhauDangNhap=view.findViewById(R.id.edtMatKhauDangNhap);
        presenterLogicDangNhap=new PresenterLogicDangNhap(this,getContext());
    }
    private void setAction(){
        btnDangNhapFaceBook.setOnClickListener(this);
        btnDangNhapGoogle.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btnDangNhapFaceBook:{
                LoginManager.getInstance().logInWithReadPermissions(FragmentDangNhap.this, Arrays.asList("public_profile","email"));//"user_status"));
                break;
            }
            case R.id.btnDangNhapGoogle:{
              //  Intent iGooglePlus= Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                Intent iGooglePlus=mGoogleSignInClient.getSignInIntent();
                startActivityForResult(iGooglePlus,SIGN_IN_GOOGLE_PLUS);
             //   showProcessDialog();
                break;
            }
            case R.id.btnDangNhap:{
                ThucHienDangNhap();
            }
        }
    }

    private void ThucHienDangNhap() {
        String tendangnhap=edtDiaChiDangNhap.getText().toString().trim();
        String matkhau=edtMatKhauDangNhap.getText().toString().trim();
        presenterLogicDangNhap.KiemTraDangNhap(tendangnhap,matkhau);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_GOOGLE_PLUS){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
    private void showProcessDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        progressDialog.cancel();

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent iTrangChu=new Intent(getActivity(),TrangChuActivity.class);
            startActivity(iTrangChu);
            // Signed in successfully, show authenticated UI.
         //   updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("mmmm", "signInResult:failed code=" + e.getStatusCode());
          //  updateUI(null);
        }
    }

    @Override
    public void DangNhapThanhCong() {
        Toast.makeText(getContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(getActivity(),TrangChuActivity.class);
        startActivity(i);
    }

    @Override
    public void DangNhapThatBai() {
        Toast.makeText(getContext(),"Sai tên tài khoản hoặc mật khẩu",Toast.LENGTH_SHORT).show();
    }
}
