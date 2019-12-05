package com.example.aidlserver;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextNum1, editTextNum2;
    TextView resultView;
    Button addBtn;
    protected ICallService callService = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               add(v);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        editTextNum1 = findViewById(R.id.num1);
        editTextNum2 = findViewById(R.id.num2);
        resultView = findViewById(R.id.result);
        if(callService == null){
            //for services need to have explicit intent, as implicits are considered unsecured.
            Intent implicitIntent = new Intent();
            implicitIntent.setAction("addService");
            Context context = getApplicationContext();
            Intent explicitIntent = convertImplicitIntentToExplicitIntent(implicitIntent, context);
            if(explicitIntent != null){
                bindService(explicitIntent, connection, Context.BIND_AUTO_CREATE);
            }
        }
    }

    public static Intent convertImplicitIntentToExplicitIntent(Intent implicitIntent, Context context) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryIntentServices(implicitIntent, 0);

        if (resolveInfoList == null || resolveInfoList.size() != 1) {
            return null;
        }
        ResolveInfo serviceInfo = resolveInfoList.get(0);
        ComponentName component = new ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name);
        Intent explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    public void add(View v){
        switch(v.getId()){
            case R.id.add_btn:
                int num1 = Integer.parseInt(editTextNum1.getText().toString());
                int num2 = Integer.parseInt(editTextNum2.getText().toString());
                int result = 0;
                try {
                    result = callService.getResult(num1, num2);
                } catch (RemoteException e) {
                   Log.e("exception occured: ", e.getMessage());
                }
                resultView.setText(String.valueOf(result));
                break;
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            callService = ICallService.Stub.asInterface(iBinder);
            Toast.makeText(getApplicationContext(), "Service connected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            callService = null;
            Toast.makeText(getApplicationContext(), "Service Disconnected", Toast.LENGTH_SHORT).show();
        }
    };

}