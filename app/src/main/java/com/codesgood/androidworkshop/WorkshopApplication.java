package com.codesgood.androidworkshop;

import android.app.Application;

import com.codesgood.androidworkshop.data.network.NetworkManager;

//TODO: [3] Add application name to androidmanifest
public class WorkshopApplication extends Application {


    //TODO: [4] Add onCreate method.
    @Override
    public void onCreate() {
        super.onCreate();
        //TODO: [28] init NetworkManager and run the app
        NetworkManager.init();

        //TODO: [29] Create activity for MovieDetailActivity
    }
}
