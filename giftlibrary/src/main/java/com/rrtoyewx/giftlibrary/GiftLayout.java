package com.rrtoyewx.giftlibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.CheckResult;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Rrtoyewx on 16/8/3.
 */
public class GiftLayout extends LinearLayout {
    private List<GiftItem> mShowGiftItems = new ArrayList<>();
    private Deque<GiftItem> mWaitingGiftItems = new LinkedList<>();

    private OnLoadSameItemListener mOnLoadSameItemListener;
    private AnimatorHandler handler = new AnimatorHandler();


    public interface OnLoadSameItemListener {
        void onLoadSame(GiftItem showItem, GiftItem unloadItem);
    }

    public GiftLayout(Context context) {
        this(context, null);
    }

    public GiftLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
    }

    public void addItem(GiftItem item) {
        mWaitingGiftItems.offer(item);

        if (mWaitingGiftItems.size() != 0) {
            autoLoadItem();
        }
    }

    public void addItemList(List<GiftItem> itemList) {
        for (GiftItem item : itemList) {
            mWaitingGiftItems.offer(item);
        }

        if (mWaitingGiftItems.size() != 0) {
            autoLoadItem();
        }
    }

    private void autoLoadItem() {
        GiftItem item = mWaitingGiftItems.poll();
        if (item != null) {
            if (!checkSameIdShowing(item)) {
                if (mShowGiftItems.size() < 3) {
                    mShowGiftItems.add(item);
                    addView(item.getView());
                    startEnterAnimator(item.getView());
                    item.setStartTime(System.currentTimeMillis());
                    handler.sendMessageDelayed(Message.obtain(handler, item.getId()), item.getDuration());
                } else {
                    mWaitingGiftItems.offerFirst(item);
                }
            } else {
                autoLoadItem();
            }
        }
    }

    @CheckResult
    private boolean checkSameIdShowing(GiftItem item) {
        for (GiftItem giftItem : mShowGiftItems) {
            if (giftItem.getId() == item.getId()) {

                giftItem.setDuration(item.getDuration() + giftItem.getDuration()
                        + System.currentTimeMillis() - giftItem.getStartTime());
                giftItem.setStartTime(System.currentTimeMillis());

                handler.removeMessages(giftItem.getId());
                handler.sendMessageDelayed(Message.obtain(handler, giftItem.getId()), giftItem.getDuration());

                if (mOnLoadSameItemListener != null) {
                    mOnLoadSameItemListener.onLoadSame(giftItem, item);
                }
                return true;
            }
        }
        return false;
    }

    private void startEnterAnimator(View targetView) {
        ObjectAnimator enterAnimator = ObjectAnimator.ofFloat(targetView, View.TRANSLATION_Y, 400, 0);
        enterAnimator.setDuration(300);
        enterAnimator.start();
    }

    private void startExitAnimator(final GiftItem item) {
        ObjectAnimator exitAnimator = ObjectAnimator.ofFloat(item.getView(), View.TRANSLATION_X, 0, -300);
        exitAnimator.setDuration(300);
        exitAnimator.start();
        exitAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mShowGiftItems.remove(item);
                removeView(item.getView());
                autoLoadItem();
            }
        });
    }

    public void setOnLoadSameListener(OnLoadSameItemListener listener) {
        this.mOnLoadSameItemListener = listener;
    }

    class AnimatorHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int id = msg.what;
            for (GiftItem item : mShowGiftItems) {
                if (item.getId() == id) {
                    startExitAnimator(item);
                }
            }

            super.handleMessage(msg);
        }
    }
}
