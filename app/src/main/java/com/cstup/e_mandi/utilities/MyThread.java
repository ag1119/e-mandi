package com.cstup.e_mandi.utilities;

import android.widget.ImageView;

public class MyThread implements Runnable{
    Thread thread;
    public MyThread(ImageView imageView , int position){
        thread = new Thread(this , position+"");
    }
    @Override
    public void run() {

    }
}
