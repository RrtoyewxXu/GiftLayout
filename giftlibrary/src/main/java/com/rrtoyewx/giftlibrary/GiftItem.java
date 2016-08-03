package com.rrtoyewx.giftlibrary;

import android.view.View;

/**
 * Created by Rrtoyewx on 16/8/3.
 */
public class GiftItem {
    private long duration;
    private long startTime;
    private int id;
    private int type;

    private View view;

    public GiftItem() {
    }

    public GiftItem(long duration, int id, View view) {
        this.duration = duration;
        this.id = id;
        this.view = view;
    }

    public GiftItem(long duration, long startTime, int id, int type, View view) {
        this.duration = duration;
        this.startTime = startTime;
        this.id = id;
        this.type = type;
        this.view = view;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
