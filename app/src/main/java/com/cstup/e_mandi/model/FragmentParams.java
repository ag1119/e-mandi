package com.cstup.e_mandi.model;

import android.content.Context;

import androidx.fragment.app.Fragment;

public class FragmentParams {
    private Fragment fragment;
    private String tag;
    private Context context;
    private int frame_id;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getFrame_id() {
        return frame_id;
    }

    public void setFrame_id(int frame_id) {
        this.frame_id = frame_id;
    }
}
