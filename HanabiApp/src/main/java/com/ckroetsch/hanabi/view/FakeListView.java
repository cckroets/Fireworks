package com.ckroetsch.hanabi.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

/**
 * @author curtiskroetsch
 */
public class FakeListView extends LinearLayout {

    ListAdapter mAdapter;

    LayoutParams mLayoutParams;

    public FakeListView(Context context) {
        super(context);
    }

    public FakeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FakeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(ListAdapter adapter) {
        mAdapter = adapter;
        bindToAdapter();
        postInvalidate();
    }

    private LayoutParams getItemLayoutParams() {
        if (mLayoutParams == null) {
            Resources r = getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    8,
                    r.getDisplayMetrics()
            );
            mLayoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            mLayoutParams.setMargins(px, px, px, px);
        }
        return mLayoutParams;
    }

    private void bindToAdapter() {
        removeAllViews();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final View item = mAdapter.getView(i, null, null);
            item.setLayoutParams(getItemLayoutParams());
            addView(item);
        }
    }
}
