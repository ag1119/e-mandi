package com.cstup.e_mandi.controllers;

public class orderController {
    public interface EventListener{

    }

    private EventListener listener;

    public orderController(EventListener listener){
        this.listener = listener;
    }


}
