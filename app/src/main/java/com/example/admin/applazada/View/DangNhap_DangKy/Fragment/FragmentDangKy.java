package com.example.admin.applazada.View.DangNhap_DangKy.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.applazada.Model.ObjectClass.NhanVien;
import com.example.admin.applazada.Presenter.DangKy.PresenterLogicDangKy;
import com.example.admin.applazada.R;
import com.example.admin.applazada.View.DangNhap_DangKy.ViewDangKy;

public class FragmentDangKy extends Fragment implements ViewDangKy, View.OnClickListener, View.OnFocusChangeListener {
    PresenterLogicDangKy presenterLogicDangKy;
    Button btnDangKy;
    EditText edHoTen,edMatKhau,edNhapLaiMatKhau,edDiaChiEmail;
    SwitchCompat sEmailDocQuyen;
    TextInputLayout input_edHoTen;
    TextInputLayout input_edMatKhau;
    TextInputLayout input_edNhapLaiMatKhau;
    TextInputLayout input_edDiaChiEmail;
    Boolean kiemtrathongtin = false;
    String emaildocquyen;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_fragment_dangky,container,false);
        Context context=view.getContext();

        initData(view);

        return view;
    }
    void initData(View view){
        btnDangKy = (Button) view.findViewById(R.id.btnDangKy);
        edHoTen = (EditText) view.findViewById(R.id.edHoTenDK);
        edMatKhau = (EditText) view.findViewById(R.id.edMatKhauDK);
        edNhapLaiMatKhau = (EditText) view.findViewById(R.id.edNhapLaiMatKhauDK);
        edDiaChiEmail = (EditText) view.findViewById(R.id.edDiaChiEmailDK);
        sEmailDocQuyen = (SwitchCompat) view.findViewById(R.id.sEmailDocQuyen);
        input_edHoTen = (TextInputLayout) view.findViewById(R.id.input_edHoTenDK);
        input_edMatKhau = (TextInputLayout) view.findViewById(R.id.input_edMatKhauDK);
        input_edNhapLaiMatKhau = (TextInputLayout) view.findViewById(R.id.input_edNhapLaiMatKhauDK);
        input_edDiaChiEmail = (TextInputLayout)view.findViewById(R.id.input_edDiaChiEmailDK);
        presenterLogicDangKy=new PresenterLogicDangKy(this);
        edHoTen.setOnFocusChangeListener(this);
        edDiaChiEmail.setOnFocusChangeListener(this);
        edMatKhau.setOnFocusChangeListener(this);
        edNhapLaiMatKhau.setOnFocusChangeListener(this);
        btnDangKy.setOnClickListener(this);
    }
    @Override
    public void DangKyThanhCong() {
        Toast.makeText(getContext(),"Đăng ký thành công !!!!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DangKyThatBai() {
        Toast.makeText(getContext(),"Đăng ký thất bại",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.btnDangKy:{
                btnDangky();
                break;
            }
        }
    }
    private void btnDangky(){
        String hoten=edHoTen.getText().toString();
        String matkhau=edMatKhau.getText().toString();
        String nhaplaimatkhau=edNhapLaiMatKhau.getText().toString();
        String email=edDiaChiEmail.getText().toString();
        sEmailDocQuyen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                emaildocquyen=isChecked+"";
                Log.d("emaildocquyen",emaildocquyen+"");
            }
        });
        if(kiemtrathongtin){
            NhanVien nhanVien=new NhanVien();
            nhanVien.setTenNV(hoten);
            nhanVien.setTenDN(email);
            nhanVien.setMatKhau(matkhau);
            nhanVien.setEmailDocQuyen(emaildocquyen);
            nhanVien.setMaLoaiNV(2);// khach hang co ma loai nv tren localhost la 2
            presenterLogicDangKy.ThucHienDangKy(nhanVien);
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id=v.getId();
        switch (id){
            case R.id.edHoTenDK:{
                if(!hasFocus){
                    String chuoi=((EditText)v).getText().toString().trim();
                    if(chuoi.equals("")==true||chuoi.equals(null)==true){
                        input_edHoTen.setErrorEnabled(true);
                        input_edHoTen.setError("Bạn cần phải nhập mục này !");
                    }
                    else {
                        input_edHoTen.setErrorEnabled(false);
                        input_edHoTen.setError("");
                    }
                }

                break;
            }
            case R.id.edDiaChiEmailDK:{
                if(!hasFocus){
                    String chuoi=((EditText)v).getText().toString().trim();
                    if(chuoi.equals("")==true||chuoi.equals(null)==true){
                        input_edHoTen.setErrorEnabled(true);
                        input_edHoTen.setError("Bạn cần phải nhập mục này !");
                    }
                    else{
                        Boolean kiemtra= Patterns.EMAIL_ADDRESS.matcher(chuoi).matches();/// hàm kiểm tra đây có phải email k
                        if(!kiemtra){
                            input_edDiaChiEmail.setErrorEnabled(true);
                            input_edDiaChiEmail.setError("Đây không phải là địa chỉ Email !");
                            kiemtrathongtin = false;
                        }
                        else{
                            input_edDiaChiEmail.setErrorEnabled(false);
                            input_edDiaChiEmail.setError("");
                            kiemtrathongtin = true;
                        }
                    }

                }
                break;
            }
            case R.id.edNhapLaiMatKhauDK: {
                if (!hasFocus) {
                    String chuoi = ((EditText) v).getText().toString().trim();
                    String matkhau = edMatKhau.getText().toString();
                    if (!chuoi.equals(matkhau)) {
                        input_edNhapLaiMatKhau.setErrorEnabled(true);
                        input_edNhapLaiMatKhau.setError("Mật khẩu không trùng khớp !");
                        kiemtrathongtin = false;
                    } else {
                        input_edNhapLaiMatKhau.setErrorEnabled(false);
                        input_edNhapLaiMatKhau.setError("");
                        kiemtrathongtin = true;
                    }
                }

                ;
                break;
            }
        }
    }
}
