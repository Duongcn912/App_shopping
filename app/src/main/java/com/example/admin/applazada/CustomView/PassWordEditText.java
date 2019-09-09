package com.example.admin.applazada.CustomView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.admin.applazada.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("AppCompatCustomView")
public class PassWordEditText extends EditText {
    Drawable eye,eyeStrike;// 2 drawable của con mắt
    Boolean visible=false; // ban đầu password k hiển thị
    Boolean useStrike=false; // mặc định con mắt là k hiển thị
    Boolean useValidate=false;
    Drawable drawable;

    String MATCHER_PATERN="((?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).{6,20})"; //TRUYỀN VÀO CÁI PATEERN, SAU ĐÓ TRUYỀN VÀO 1 CÁI MATCHER, DÙNG ĐỂ KIỂM TRA CHUỖI ĐÓ CÓ GIỐNG PATTERN MÌNH QUY ĐỊNH K
    Pattern pattern;
    Matcher matcher;
    //kiem tra xem it nhat 1 so, 1 chu hoa, 1 chu thuong, 6-20 ki tu hay k
    int Alpha=(int)(255 *.55f); // do duc 55%
    public PassWordEditText(Context context) {
        super(context);
        khoitao(null);
    }

    public PassWordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        khoitao(attrs);
    }

    public PassWordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        khoitao(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PassWordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)


    private void khoitao(AttributeSet attrs){ // nhan thuoctinh xml=> can attrs
        this.pattern=Pattern.compile(MATCHER_PATERN);// khai báo

        if(attrs!=null){
            TypedArray array= getContext().getTheme().obtainStyledAttributes(attrs,R.styleable.PassWordEditText,0,0);
            useStrike= array.getBoolean(R.styleable.PassWordEditText_useStrike,false);
            useValidate=array.getBoolean(R.styleable.PassWordEditText_useValidate,false);
        }
        eye=ContextCompat.getDrawable(getContext(),R.drawable.ic_visibility_black_24dp).mutate();
        eyeStrike=ContextCompat.getDrawable(getContext(),R.drawable.ic_visibility_off_black_24dp).mutate();
        if(useValidate==true){//nếu đc forcus
            setOnFocusChangeListener(new OnFocusChangeListener() {// hàm kiểm tra xem nó có đnag đc forcus k
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                   if(!hasFocus){
                       String chuoi=getText().toString();
                       matcher=pattern.matcher(chuoi);
                       if(!matcher.matches()){// nêu sk đúng vs pattern

                           TextInputLayout textInputLayout= (TextInputLayout) v.getParent().getParent();// lay th cha cua pwEditText la textInputlayout
                           textInputLayout.setErrorEnabled(true);
                           textInputLayout.setError("Mật khẩu phải bao gồm ít nhất 6 kí tự và 1 chữ hoa");
                       }
                       else{
                           TextInputLayout textInputLayout= (TextInputLayout) v.getParent().getParent();// lay th cha cua pwEditText la textInputlayout
                           textInputLayout.setErrorEnabled(false);
                           textInputLayout.setError("");
                       }

                   }
                }
            });
        }
        caidat();
    }

    private void caidat(){// set syle, visible = false la khong hien thi password
//        setInputType(InputType.TYPE_CLASS_TEXT |(visible? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
//        Drawable[] drawables = getCompoundDrawables();// lay drwaable cedittext
//        drawable = useStrike && !visible? eyeStrike: eye; // neu nhu su dung usetrike(nhap thuoc tinh usetrike) va khong hien thi password
//        drawable.setAlpha(Alpha);
//        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],drawable,drawables[3]);
        setInputType(InputType.TYPE_CLASS_TEXT|(visible? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD :InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawables=getCompoundDrawables();
        drawable=useStrike&& !visible? eyeStrike :eye;
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],drawable,drawables[3]);
}


    @Override
    public boolean onTouchEvent(MotionEvent event) {// kieem tra ng dung nhan vao man hinh
        //vi actionDown chong vs su kien click vao edtText len phai de actionUP
//        if(event.getAction()==MotionEvent.ACTION_UP&& event.getX() >= (getRight()-drawable.getBounds().width())){
//            visible=!visible;// gan cho no la ngc lai
//            caidat();
//            invalidate(); // kiem tra su kien click cho man hinh
//        }; // lon hon hoac bang chieu rong edt-cr drawable
        if(event.getAction()==MotionEvent.ACTION_UP&&event.getX()>=(getRight()-(drawable.getBounds().width()))){
            visible=!visible;
            caidat();
            invalidate();
        }
        return super.onTouchEvent(event);// su kien ke thua cua onTouchEvent
    }
}
