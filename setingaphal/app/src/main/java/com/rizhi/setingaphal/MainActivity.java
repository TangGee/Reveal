package com.rizhi.setingaphal;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {



    private RevealHorizontalView rh;

    private  ImageView bk;

    private int imgs[][]={
            {R.mipmap.ic_cake_black_48dp,
            R.mipmap.ic_cake_white_48dp},

            {R.mipmap.ic_domain_black_48dp,
            R.mipmap.ic_domain_white_48dp},

            {R.mipmap.ic_group_black_48dp,
            R.mipmap.ic_group_white_48dp},

            {R.mipmap.ic_mood_black_48dp,
            R.mipmap.ic_mood_white_48dp},

            {R.mipmap.ic_notifications_black_48dp,
            R.mipmap.ic_notifications_none_white_48dp}
    };

    private List<RevealDrawable> revealDrawableList=new ArrayList<>();

    private List<ImageView> imageViewList=new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        init();


        rh= (RevealHorizontalView) findViewById(R.id.rh);

        init();



    }

    private void init() {
        for (int i=0;i<imgs.length;i++)
        {
            Drawable  unSelectDraw=getResources().getDrawable(imgs[i][0]);
            Drawable  selectDraw=getResources().getDrawable(imgs[i][1]);

            ImageView iv=new ImageView(this);
            iv.setImageDrawable(new RevealDrawable(unSelectDraw, selectDraw, RevealDrawable.HORIZONTAL));

            imageViewList.add(iv);
        }
        rh.addMyChild(imageViewList);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
