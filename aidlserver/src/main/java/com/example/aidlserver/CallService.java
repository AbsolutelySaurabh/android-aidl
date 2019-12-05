package com.example.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class CallService extends Service {
    public CallService() {
    }

    private MyImpl myImpl = new MyImpl();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myImpl;
    }

    private class MyImpl extends ICallService.Stub{

        @Override
        public String getMessage(String getMessage) throws RemoteException {
            return "Hey Saurabh!";
        }

        @Override
        public int getResult(int a, int b) throws RemoteException {
            return a+b;
        }
    }

}
