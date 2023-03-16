package com.example.wsd.factory;

import com.example.wsd.service.AtmService;
import com.example.wsd.service.impl.AtmServiceImpl;

public class AtmFactory {

    public static AtmService getService(){
        return new AtmServiceImpl();
    }
}
