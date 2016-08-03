package com.rrtoyewx.giftlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rrtoyewx.giftlibrary.GiftItem;
import com.rrtoyewx.giftlibrary.GiftLayout;

public class MainActivity extends AppCompatActivity {

    GiftLayout layoutAnimator;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutAnimator = (GiftLayout) findViewById(R.id.layoutanimator);
        layoutAnimator.setOnLoadSameListener(new GiftLayout.OnLoadSameItemListener() {
            @Override
            public void onLoadSame(GiftItem showItem, GiftItem unloadItem) {
                TextView tv = (TextView) showItem.getView();
                tv.append("+1");
            }
        });
    }

    public void add(View view) {
        id++;
        TextView tv = new TextView(this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        tv.setText(id + " 用户送出了礼物+1");
        GiftItem item = new GiftItem(3000, id, tv);
        layoutAnimator.addItem(item);
    }

    public void addSame(View view) {
        TextView tv = new TextView(this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        tv.setText("content" + id);
        GiftItem item = new GiftItem(3000, id, tv);
        layoutAnimator.addItem(item);
    }

}
