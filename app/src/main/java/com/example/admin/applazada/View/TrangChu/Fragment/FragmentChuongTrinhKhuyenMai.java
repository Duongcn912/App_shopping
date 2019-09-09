package com.example.admin.applazada.View.TrangChu.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.admin.applazada.R;
import com.example.admin.applazada.View.TrangChu.ViewKhuyenMai;

import java.util.List;

/**
 * Created by Lenovo S410p on 6/23/2016.
 */
public class FragmentChuongTrinhKhuyenMai extends Fragment {

    LinearLayout lnHinhKhuyenMai;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_chuongtrinhkhuyenmai,container,false);

        lnHinhKhuyenMai = (LinearLayout) view.findViewById(R.id.lnHinhKhuyenMai);


        return view;
    }


}
