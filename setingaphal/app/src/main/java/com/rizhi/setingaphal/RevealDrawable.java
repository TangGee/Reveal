package com.rizhi.setingaphal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;

/**
 * Created by tangtang on 15/5/25.
 */
public class RevealDrawable extends Drawable{


    public static  final int VERTICAL=0;
    public static final int HORIZONTAL=1;


    final private  Rect mRect=new Rect();




    private Drawable unSelected;
    private Drawable selected;

    /**
     * 方向
     */
    private int orientation;

    public  RevealDrawable(Drawable unSelected,Drawable selected,int orientation)
    {
        this.unSelected=unSelected;
        this.selected=selected;
        this.orientation=orientation;
    }



    @Override
    public void draw(Canvas canvas) {

        int level=getLevel();

        if (level==10000||level==0)
            unSelected.draw(canvas);
        else if(level==5000)
            selected.draw(canvas);
        else{
            //混合效果的drawable
            //剪切成左边和右边两块
//            canvas.save();
//
//            Rect rect;
//            if (level<5000)
//            {
//                rect=new Rect(0,0,getIntrinsicWidth()-getIntrinsicWidth()/5000*level,getIntrinsicHeight());
//
//            }else {
//                rect=new Rect(getIntrinsicWidth()*(10000-level)/5000,0,getIntrinsicWidth(),getIntrinsicHeight());
//            }
//
//
//            canvas.clipRect(rect);
//            unSelected.draw(canvas);
//            canvas.restore();
//
//
//            canvas.save();
//            if(level<5000)
//            {
//                rect=new Rect(getIntrinsicWidth()-getIntrinsicWidth()*(5000-level)/5000,0,getIntrinsicWidth(),getIntrinsicHeight());
//            }else{
//                rect=new Rect(0,0,getIntrinsicWidth()*(10000-level)/5000,getIntrinsicHeight());
//            }
//            canvas.clipRect(rect);
//            selected.draw(canvas);
//            canvas.restore();

            /**
             * 灰色部分＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝
             */

            canvas.save();

            Rect bounds=getBounds();
            Rect outRect=mRect;
            int w=bounds.width();

            int h=bounds.height();

            float ratio=level/5000f-1f;
            if ((orientation&HORIZONTAL)!=0){
                w= (int) (w*Math.abs(ratio));
            }else {
                h=(int) (h*Math.abs(ratio));
            }

            int gravity=ratio<0?Gravity.LEFT:Gravity.RIGHT;

            /**
             * gravity 从左边开始裁减 还是从右边开始裁减
             */
            Gravity.apply(gravity,w,h,bounds,outRect);

            canvas.clipRect(outRect);
            unSelected.draw(canvas);



            canvas.restore();


            /**
             * 画彩色部分
             */
            canvas.save();

            w=bounds.width();

            h=bounds.height();

            ratio=level/5000f;

            if (ratio>1) {
                ratio = 2 - ratio;
                gravity=Gravity.LEFT;
            }else{
                gravity=Gravity.RIGHT;
            }
            if ((orientation&HORIZONTAL)!=0){


                Log.i("BBBB",ratio+"");

                w= (int) (w*Math.abs(ratio));


            }else {
                h=(int) (h*Math.abs(ratio));
            }


            /**
             * gravity 从左边开始裁减 还是从右边开始裁减
             */
            Gravity.apply(gravity,w,h,bounds,outRect);


            canvas.clipRect(outRect);
            selected.draw(canvas);
            canvas.restore();


        }

    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;

    }

    /**
     * 定好边界
     * @param bounds
     */
    @Override
    protected void onBoundsChange(Rect bounds) {
        selected.setBounds(bounds);
        unSelected.setBounds(bounds);
    }

    /**
     * 得到drawable的实际高度
     * @return
     */
    @Override
    public int getIntrinsicHeight() {
        return Math.max(unSelected.getIntrinsicHeight(),selected.getIntrinsicHeight());
    }

    /**
     * 得到实际宽度
     * @return
     */
    @Override
    public int getIntrinsicWidth() {
        return Math.max(unSelected.getIntrinsicWidth(),selected.getIntrinsicWidth());

    }
}
