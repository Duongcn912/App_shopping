package com.example.admin.applazada.CustomView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.admin.applazada.R;

public class ClearEditText extends EditText {
    Drawable crossx,nonecrossx;
    Boolean visible=false;
    Drawable drawable;
    public ClearEditText(Context context) {
        super(context);
        khoitao();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        khoitao();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        khoitao();
}
    private void khoitao(){
        crossx= ContextCompat.getDrawable(getContext(), R.drawable.ic_clear_black_24dp).mutate();
        nonecrossx=ContextCompat.getDrawable(getContext(),android.R.drawable.screen_background_dark_transparent);// bgtrong suot cua android
        cauhinh();
    }

    private void cauhinh() {
        setInputType(InputType.TYPE_CLASS_TEXT);
        Drawable[] drawables=getCompoundDrawables();
        drawable=visible? crossx:nonecrossx;// neu bang true thi crossx, none crossx;
        setCompoundDrawablesWithIntrinsicBounds(drawables[0],drawables[1],drawable,drawables[3]);
    }
    //su kien go vao edt;
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(lengthAfter==0&& start==0){ // neu do dai edt ==0 hoac no dang bat dau thi k cho hien thi
            visible=false;
            cauhinh();
        }
        else{
            visible=true;
            cauhinh();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_UP&& event.getX()>=(getRight()-drawable.getBounds().width())){
            //visible=!visible;
            setText(""); //vi cai text change mac dinh cap nhat do dai nen k can phai set visible, cau hinh va invalidate
            //cauhinh();
            //invalidate();
        }
        return super.onTouchEvent(event);
    }
}
