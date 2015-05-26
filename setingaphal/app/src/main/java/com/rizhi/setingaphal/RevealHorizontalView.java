package com.rizhi.setingaphal;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by tangtang on 15/5/26.
 */
public class RevealHorizontalView extends HorizontalScrollView implements View.OnTouchListener
{

    private  int currentX;
//    private View cursorView;

    private LinearLayout ll_gropp;

//    private static final int DEFAULT_CURSOR_LEFT=100;
//
//    private static final int DEFAULT_CURSOR_WITH_DP=56;

//    private static final int DEFAULT_CURSOR_RIGTH=100+DEFAULT_CURSOR_WITH_DP;


    private int xCenterPix;

    private int  icon_width;



//    private int cursorLeft=DEFAULT_CURSOR_LEFT;
//    private int cursorRight=DEFAULT_CURSOR_RIGTH;



    public RevealHorizontalView(Context context) {
        this(context,null,0);
    }

    public RevealHorizontalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RevealHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);


    }

    private void init(Context context) {
        setFillViewport(false);
        setHorizontalScrollBarEnabled(false);

        ll_gropp=new LinearLayout(context);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll_gropp.setLayoutParams(params);
        ll_gropp.setOrientation(LinearLayout.HORIZONTAL);


        setOnTouchListener(this);

        addView(ll_gropp);

    }


//    @Override
//    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        super.onScrollChanged(l, t, oldl, oldt);
//
//
//
//        int childCount=ll_gropp.getChildCount();
//
//        if(childCount>0)
//        {
//            for (int i=0;i<childCount;i++)
//            {
//                View view=ll_gropp.getChildAt(i);
//                int childLeft=view.getLeft();
//                int childRight=view.getRight();
//                int childWidth=view.getWidth();
//
//                if(view instanceof  ImageView){
//                    if (childLeft>cursorRight||childRight<cursorLeft)
//                    {
//
//
//                        continue;
//                    }else{
//
//
//                        try {
//                            RevealDrawable revealDrawable= (RevealDrawable) ((ImageView)view).getDrawable();
//
//                            if(cursorLeft-childLeft==childWidth)
//                            {
//                                revealDrawable.setLevel(0);
//
//                            }else if(childRight-cursorRight==childWidth)
//                            {
//                                revealDrawable.setLevel(10000);
//
//                            }else if(childLeft>cursorLeft&&childRight<cursorRight){
//                                revealDrawable.setLevel(5000);
//
//                            }else {
//
//                                int level=0;
//
//                                if(cursorLeft>childLeft)
//                                    level=5000*(view.getWidth()-(cursorLeft-view.getLeft())/getWidth());
//                                if (view.getRight()>cursorRight){
//                                    level=10000-5000*(getWidth()-view.getRight()+cursorRight)/getWidth();
//                                }
//
//                                Log.i("AAAA","11111  "+level);
//
//
//                                revealDrawable.setLevel(level);
//                            }
//
//                        }catch (Exception e)
//                        {
//                            continue;
//                        }
//                    }
//                }
//            }
//        }
//    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        //相对坐标
        View view=ll_gropp.getChildAt(0);
        icon_width=view.getWidth();
        xCenterPix=getWidth()/2-icon_width/2;

        ll_gropp.setPadding(xCenterPix,0,xCenterPix,0);

    }

    public void addMyChild(List<ImageView> ivs){

        for(int i=0;i<ivs.size();i++) {
            ImageView iv=ivs.get(i);
            if (i==0)
                iv.getDrawable().setLevel(5000);
            ll_gropp.addView(iv);

        }

    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        revel();
    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction())
        {

            case MotionEvent.ACTION_UP:
                post(scrollRunnable);

                break;


        }
        return false;
    }

    private void onUp() {
        int x=getScrollX();
        //找到两张渐变图片下标
        int leftIndex=x/icon_width;
        int rightIndex=leftIndex+1;

        ImageView left= (ImageView) ll_gropp.getChildAt(leftIndex);

        float ratio=5000/icon_width;

        int level=left.getDrawable().getLevel();
        /**
         * 说明要滚动到一个下一个
         */
        if (level<5000/2){
            float sx=level/ratio;
            Log.i("XXXX",sx+"xxxxx");
            scrollBy((int) (sx),0);
        }else{

            scrollBy((int) -((5000-level)/ratio),0);
        }

    }


    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);

//        onUp();

        Log.i("CCCC","1111");
    }


    private void revel() {

        int x=getScrollX();
        //找到两张渐变图片下标
        int leftIndex=x/icon_width;
        int rightIndex=leftIndex+1;




        /**
         * 设置level
         */

        for (int i=0;i<ll_gropp.getChildCount();i++)
        {

            if (i==leftIndex){
                float ratio=5000f/icon_width;
                ImageView left= (ImageView) ll_gropp.getChildAt(leftIndex);
                left.setImageLevel((int) (5000-x%icon_width*ratio));

            }else if (i==rightIndex)
            {
                float ratio=5000f/icon_width;
                ImageView right = (ImageView) ll_gropp.getChildAt(rightIndex);
                right.setImageLevel((int) (10000-x%icon_width*ratio));


            }else{
                ImageView iv= (ImageView) ll_gropp.getChildAt(i);
                iv.setImageLevel(0);
            }

        }




    }



    enum ScrollType{IDLE,TOUCH_SCROLL,FLING};
    private int scrollDealy = 30;

    private Runnable scrollRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if(getScrollX()==currentX){
                //滚动停止  取消监听线程
                Log.d("DDDD", "停止滚动");

                onUp();

                return;
            }

            currentX = getScrollX();
            postDelayed(this, scrollDealy);
        }
    };


}
