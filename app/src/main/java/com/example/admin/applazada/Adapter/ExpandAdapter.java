package com.example.admin.applazada.Adapter;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.applazada.Model.ObjectClass.LoaiSanPham;
import com.example.admin.applazada.Model.TrangChu.XuLyJsonMenu.XuLyJsonMenu;
import com.example.admin.applazada.R;

import java.util.List;

public class ExpandAdapter extends BaseExpandableListAdapter {
    Context context;
    List<LoaiSanPham> sanPhamList;
    ViewHolderMenu viewHolderMenu;
    public ExpandAdapter(Context context, List<LoaiSanPham> sanPhamList){
        this.context=context;
        this.sanPhamList=sanPhamList;
        XuLyJsonMenu xuLyJsonMenu=new XuLyJsonMenu();
        int count=sanPhamList.size();
        for(int i=0;i<count;i++){
            int maloaisp=sanPhamList.get(i).getMALOAISP();
            // trong th cha co th con, tao 1 cai list con vao, add vao bang cai ma loai san pham da dc hien thi tren tgg
            sanPhamList.get(i).setListCon(xuLyJsonMenu.LayDanhSachLoaiSanPhamTheoMaLoai(maloaisp));
        }
    }
    @Override
    public int getGroupCount() {
        return sanPhamList.size();
    }

    @Override
    public int getChildrenCount(int vitriGroupcha) {
        if(sanPhamList.get(vitriGroupcha).getListCon().size()!=0) return 1;
        return 0 ;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return sanPhamList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return sanPhamList.get(groupPosition).getListCon().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return sanPhamList.get(groupPosition).getMALOAISP();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return sanPhamList.get(groupPosition).getListCon().get(childPosition).getMALOAISP();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
    public class ViewHolderMenu{
        TextView txtLoaiSP;
        ImageView hinhMenu;

    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View viewGroupcha = convertView;
        //i
        if(viewGroupcha==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewGroupcha=inflater.inflate(R.layout.custom_layout_group_cha,parent,false);
            viewHolderMenu=new ViewHolderMenu();
            viewHolderMenu.txtLoaiSP=(TextView)viewGroupcha.findViewById(R.id.txtTenLoaiSP);
            viewHolderMenu.hinhMenu=(ImageView)viewGroupcha.findViewById(R.id.imMenuPlus);
            viewGroupcha.setTag(viewHolderMenu);
        }
        else{
            viewHolderMenu=(ViewHolderMenu)viewGroupcha.getTag();
        }




        int demSPListcon=sanPhamList.get(groupPosition).getListCon().size();
        if(demSPListcon==0) viewHolderMenu.hinhMenu.setVisibility(View.INVISIBLE);
        else{
            viewHolderMenu.hinhMenu.setVisibility(View.VISIBLE);
            if(isExpanded){
                viewHolderMenu.hinhMenu.setImageResource(R.drawable.ic_remove_black_24dp);
                viewGroupcha.setBackgroundResource(R.color.colorGray);
            }
            else {
                viewGroupcha.setBackgroundResource(R.color.colorWhite);
                viewHolderMenu.hinhMenu.setImageResource(R.drawable.ic_add_black_24dp);
            }
        }


       viewHolderMenu.txtLoaiSP.setText(sanPhamList.get(groupPosition).getTENLOAISP());
       viewGroupcha.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               Log.d("Maloaisp",sanPhamList.get(groupPosition).getMALOAISP()+"");
               return false;
           }
       });




        return viewGroupcha;

    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View viewGroupcha=inflater.inflate(R.layout.custom_layout_group_cha,parent,false);
//        TextView txtTenLoaiSP=viewGroupcha.findViewById(R.id.txtTenLoaiSP);
//
//        txtTenLoaiSP.setText(sanPhamList.get(groupPosition).getListCon().get(childPosition).getTENLOAISP());
//        return viewGroupcha;
//        SecondExpandable secondExpandable=new SecondExpandable(context);
////        SecondAdapter secondAdapter=new SecondAdapter(sanPhamList.get(groupPosition).getListCon());
//        ExpandAdapter secondAdapter=new ExpandAdapter(context,sanPhamList.get(groupPosition).getListCon());
//        secondExpandable.setAdapter(secondAdapter);
//        secondExpandable.setGroupIndicator(null);
//        notifyDataSetChanged();
        SecondExpandable secondExpandable=new SecondExpandable(context);
        ExpandAdapter secondAdapter=new ExpandAdapter(context,sanPhamList.get(groupPosition).getListCon());
        secondAdapter.notifyDataSetChanged();
        secondExpandable.setAdapter(secondAdapter);
        secondExpandable.setGroupIndicator(null);
        return secondExpandable;


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    public class SecondExpandable extends ExpandableListView{// de tranh su cach nhau qua rong custom lại expandablelistview

        public SecondExpandable(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);// lay man hình window
            Display display=windowManager.getDefaultDisplay();// lấy màn hình
            Point  size=new Point();
            display.getSize(size);
            int width= size.x;
            int height=size.y;
//            widthMeasureSpec=MeasureSpec.makeMeasureSpec(width,MeasureSpec.AT_MOST);//dat chieu rong tôi đa là chiều rộng màn hình
           heightMeasureSpec=MeasureSpec.makeMeasureSpec(height,MeasureSpec.AT_MOST); // đặt chiều dài tối đa là chiều dài của màn hình
           // nếu cmt phần trên thì view tự động đẩy ra toàn màn hình

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
//    public class SecondAdapter extends  BaseExpandableListAdapter{
//        List<LoaiSanPham> listCon;
//        public SecondAdapter(List<LoaiSanPham> listCon){
//            this.listCon = listCon;
//
//            XuLyJsonMenu xuLyJsonMenu = new XuLyJsonMenu();
//
//            int count = listCon.size();
//            for (int i=0;i<count;i++){
//                int maloaisp = listCon.get(i).getMALOAISP();
//                listCon.get(i).setListCon(xuLyJsonMenu.LayDanhSachLoaiSanPhamTheoMaLoai(maloaisp));
//        }
//        }
//
//        @Override
//        public int getGroupCount() {
//            return listCon.size();
//        }
//
//        @Override
//        public int getChildrenCount(int vitriGroupCha) {
//            return listCon.get(vitriGroupCha).getListCon().size();
//        }
//
//        @Override
//        public Object getGroup(int vitriGroupCha) {
//            return listCon.get(vitriGroupCha);
//        }
//
//        @Override
//        public Object getChild(int vitriGroupCha, int vitriGroupCon) {
//            return listCon.get(vitriGroupCha).getListCon().get(vitriGroupCon);
//        }
//
//        @Override
//        public long getGroupId(int vitriGroupCha) {
//            return listCon.get(vitriGroupCha).getMALOAISP();
//        }
//
//        @Override
//        public long getChildId(int vitriGroupCha, int vitriGroupCon) {
//            return listCon.get(vitriGroupCha).getListCon().get(vitriGroupCon).getMALOAISP();
//        }
//
//        @Override
//        public boolean hasStableIds() {
//            return false;
//        }
//
//        @Override
//        public View getGroupView(int vitriGroupCha, boolean isExpanded, View view, ViewGroup viewGroup) {
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View viewGroupCha = layoutInflater.inflate(R.layout.custom_layout_group_cha,viewGroup,false);
//            TextView txtTenLoaiSP = (TextView) viewGroupCha.findViewById(R.id.txtTenLoaiSP);
//            txtTenLoaiSP.setText(listCon.get(vitriGroupCha).getTENLOAISP());
//
//            return viewGroupCha;
//        }
//
//        @Override
//        public View getChildView(int vitriGroupCha, int vitriGroupCon, boolean isExpanded, View view, ViewGroup viewGroup) {
//            TextView tv = new TextView(context);
//            tv.setText(listCon.get(vitriGroupCha).getListCon().get(vitriGroupCon).getTENLOAISP());
//            tv.setPadding(15, 5, 5, 5);
//            tv.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            return tv;
//        }
//
//        @Override
//        public boolean isChildSelectable(int i, int i1) {
//            return false;
//        }
//    }

}
