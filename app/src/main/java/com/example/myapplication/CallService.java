package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.telecom.InCallService;

import androidx.annotation.Nullable;

public class CallService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final ICallService.Stub binder = new ICallService.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String getMessage(String message){
            return "Hello saurabh!";
        }

        @Override
        public int getResult(int a, int b){
            return a+b;
        }
    };

}
