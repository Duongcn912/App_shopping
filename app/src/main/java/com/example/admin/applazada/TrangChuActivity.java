package com.example.admin.applazada;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.example.admin.applazada.Adapter.ExpandAdapter;
import com.example.admin.applazada.Adapter.ViewPagerAdapter;
import com.example.admin.applazada.Model.DangNhap_DangKy.ModelDangNhap;
import com.example.admin.applazada.Model.ObjectClass.LoaiSanPham;
import com.example.admin.applazada.Presenter.TrangChu.XuLyMenu.PresenterLogicXuLyMenu;
import com.example.admin.applazada.View.DangNhap_DangKy.DangNhapActivity;
import com.example.admin.applazada.View.TrangChu.ViewXuLyMenu;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TrangChuActivity extends AppCompatActivity implements ViewXuLyMenu,OnOffsetChangedListener{
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInResult googleSignInResult;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    GoogleSignInAccount mGoogleSignInAccount;
    ExpandableListView expandableListView;
    ActionBarDrawerToggle drawerToggle;
    PresenterLogicXuLyMenu presenterLogicXuLyMenu;
    String tennguoidung;
    ModelDangNhap modelDangNhap;
    AccessToken accessToken;
    Menu menu;
    MenuItem itDangNhap,itDangXuat;
    public static final String SERVER_NAME = "http://10.0.3.2/weblazada";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_trang_chu);

        InitData();
        presenterLogicXuLyMenu.LayTenNguoiDungFaceBook();
    }

    private void InitData(){
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,5);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        tabLayout=(TabLayout) findViewById(R.id.tabs);
        viewPager=(ViewPager) findViewById(R.id.viewpager);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        expandableListView=(ExpandableListView)findViewById(R.id.epMenu);
        appBarLayout=(AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbarLayout=(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //Vì toolbar nằm dưới drawerToggle  nên drawerToggle phải nằm dưới
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);// cho phep mo drawlayout
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle.syncState();
        appBarLayout.addOnOffsetChangedListener(this);

        modelDangNhap=new ModelDangNhap();

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        presenterLogicXuLyMenu=new PresenterLogicXuLyMenu(this);
        presenterLogicXuLyMenu.LayDanhSachMenu();
        mGoogleSignInClient=presenterLogicXuLyMenu.LayGoogleSignInClient(this);
//        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//        mGoogleSignInClient= GoogleSignIn.getClient(this, gso);

     //   modelDangNhap=new ModelDangNhap();
//        mGoogleApiClient = modelDangNhap.LayGoogleApiClient(this, (GoogleApiClient.OnConnectionFailedListener) this);


    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menutrangchu,menu);
        this.menu=menu;
        itDangNhap=menu.findItem(R.id.itDangNhap);
        itDangXuat=menu.findItem(R.id.itDangXuat);
        accessToken=presenterLogicXuLyMenu.LayTenNguoiDungFaceBook();
        mGoogleSignInAccount=GoogleSignIn.getLastSignedInAccount(this);

       // googleSignInResult = modelDangNhap.LayThongDangNhapGoogle(mGoogleApiClient);

        if(accessToken!=null) {

            GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        tennguoidung = object.getString("name");
                        itDangNhap.setTitle("Xin chào " + tennguoidung);
                        //                    Log.d("tennguoidung",tennguoidung);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            Bundle paramater = new Bundle();
            paramater.putString("fields", "name");
            graphRequest.setParameters(paramater);
            graphRequest.executeAsync();// la 1 asynctask, la 1 tien trinh , 1 luonjg chay soong song


//            if(googleSignInResult != null){
//                itDangNhap.setTitle(googleSignInResult.getSignInAccount().getDisplayName());
//                Log.d("goo",googleSignInResult.getSignInAccount().getDisplayName());
//            }

//            String tennv = modelDangNhap.LayCachedDangNhap(this);

//            if(!tennv.equals("")){
//                itDangNhap.setTitle(tennv);
//            }
            }
            else if(mGoogleSignInAccount!=null){
                tennguoidung=mGoogleSignInAccount.getDisplayName();
                Log.d("tennguoidung",tennguoidung);
                itDangNhap.setTitle(tennguoidung);
            }
            String tennv=modelDangNhap.LayCachedDangNhap(this);
            if(!tennv.equals("")){
                itDangNhap.setTitle("Xin Chào "+ tennv);


            }
            if(accessToken!=null||mGoogleSignInAccount!=null||!tennv.equals("")){
                itDangXuat.setVisible(true);
            }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;

        }
        int id=item.getItemId();
        switch (id){
            case R.id.itDangNhap:{
               if(mGoogleSignInAccount==null&&(accessToken==null||accessToken.getToken()==null||accessToken.getToken()=="")
                       &&modelDangNhap.LayCachedDangNhap(this).equals("")){
                    Intent i=new Intent(this, DangNhapActivity.class);
                    startActivity(i);
               }
                if(googleSignInResult != null){
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    this.menu.clear();
                    this.onCreateOptionsMenu(this.menu);

                }

//                if(!modelDangNhap.LayCachedDangNhap(this).equals("")){
//                    modelDangNhap.CapNhatCachedDangNhap(this,"");
//                    this.menu.clear();
//                    this.onCreateOptionsMenu(this.menu);
//                }
                break;

            }
            case R.id.itDangXuat:{
                LoginManager.getInstance().logOut();
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                tennguoidung=null;

                            }
                        });
                String tennv=modelDangNhap.LayCachedDangNhap(this);
                if(!tennv.equals("")){
                    modelDangNhap.CapNhatCachedDangNhap(this,"");
                }
                this.menu.clear();
                this.onCreateOptionsMenu(menu);

                break;
            }
        }
        return true;
    }

    @Override
    public void HienThiDanhSachMenu(List<LoaiSanPham> loaiSanPhamList) {
        ExpandAdapter expandAdapter=new ExpandAdapter(this,loaiSanPhamList);

        expandAdapter.notifyDataSetChanged();
        expandableListView.setAdapter(expandAdapter);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        //i la chieu  cao
        //lay chieu cao nho nhat ViewCompat.getMinimumHeight(collapsingToolbarlayout)
        //setalpha : cho an di
        if(collapsingToolbarLayout.getHeight()+i <=1.5  * ViewCompat.getMinimumHeight(collapsingToolbarLayout)){
            LinearLayout linearLayout=appBarLayout.findViewById(R.id.lnSearch);
            linearLayout.animate().alpha(0).setDuration(2000);
        }
        else{
            LinearLayout linearLayout=appBarLayout.findViewById(R.id.lnSearch);
            linearLayout.animate().alpha(1).setDuration(2000);;
        }
    }



}
