package com.ckroetsch.hanabi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.List;

/**
 * @author curtiskroetsch
 */
public class FakeListView extends LinearLayout {

    private static final int DEFAULT_MARGIN = 8;

    FakeListAdapter mAdapter;

    LayoutParams mLayoutParams;

    int mMargin;

    public FakeListView(Context context) {
        this(context, null);
    }

    public FakeListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FakeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mMargin = DEFAULT_MARGIN;
    }

    public FakeListAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(FakeListAdapter adapter) {
        mAdapter = adapter;
        bindToAdapter();
        postInvalidate();
    }

    public void setMargin(int margin) {
        mMargin = margin;
    }

    private LayoutParams getItemLayoutParams() {
        if (mLayoutParams == null) {
            final int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    mMargin,
                    getResources().getDisplayMetrics()
            );
            mLayoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            mLayoutParams.setMargins(px, px, px, px);
        }
        return mLayoutParams;
    }

    private void bindToAdapter() {
        removeAllViews();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            final View item = mAdapter.getView(i);
            addView(item, getItemLayoutParams());
        }
    }

    public static abstract class FakeListAdapter<T> extends ArrayAdapter<T> {

        public FakeListAdapter(Context context, List<T> objects) {
            super(context, 0, objects);
        }

        public FakeListAdapter(Context context, T[] objects) {
            super(context, 0, objects);
        }

        public final View getView(int position) {
            return getView(position, getItem(position));
        }

        @Override
        public final View getView(int position, View convertView, ViewGroup parent) {
            throw new UnsupportedOperationException("Fake List view does not recyle views");
        }

        protected abstract View getView(int position, T item);
    }
}
