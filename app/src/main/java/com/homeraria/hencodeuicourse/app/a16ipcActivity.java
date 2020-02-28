package com.homeraria.hencodeuicourse.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.homeraria.hencodeuicourse.app.ipc.MessengerService;

/**
 * 展示IPC中的binder使用
 */
public class a16ipcActivity extends FragmentActivity implements View.OnClickListener {

    private Button mBtnMessenger, mBtnBinder;

    /**
     * 服务端的Messenger
     */
    private Messenger mServerMessenger;
    /**
     * 客户端（当前本地的）Messenger
     */
    private Messenger mClientMessenger = new Messenger(new ClientMessengerHandler());
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            long timeStamp = System.currentTimeMillis();
            Log.v(MessengerService.TAG, "onServiceConnected-->" + timeStamp);
            //通过服务端传回的binder创建Messenger
            mServerMessenger = new Messenger(service);

            mBtnMessenger.setText("Messenger Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.v(MessengerService.TAG, "onServiceDisconnected-->binder die");
            mBtnMessenger.setText("Messenger Disconnect");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);

        mBtnMessenger = findViewById(R.id.button_messenger);
        mBtnMessenger.setOnClickListener(this);
        mBtnMessenger.setText("Messenger Connecting...");

        mBtnBinder = findViewById(R.id.button_binder);
        mBtnBinder.setOnClickListener(this);

        bindMessengerService();
    }

    private void bindMessengerService() {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_messenger:
                if (mServerMessenger != null && mClientMessenger != null) {
                    long timeStamp = System.currentTimeMillis();
                    Log.v(MessengerService.TAG, "msg send-->" + timeStamp
                            + ", threadID=" + Thread.currentThread().getName() + "/" + Thread.currentThread().getId()
                            + ", processID=" + android.os.Process.myPid());

                    //创建消息，通过Messenger传递
                    Message msg = Message.obtain(null, MessengerService.MESSAGE_FROM_CLIENT);
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", "this is a msg from client, timeStamp=" + timeStamp);
                    msg.setData(bundle);
                    //如果需要server哪里能够响应这个msg返回一个msg，需要把本地的Messanger作为replyTo发过去
                    msg.replyTo = mClientMessenger;
                    try {
                        mServerMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.v(MessengerService.TAG, "Msg send error-->Messenger is null");
                }
                break;
            case R.id.button_binder:
                break;
            default:
                break;
        }
    }

    //===========================================================================
    private static class ClientMessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MessengerService.MESSAGE_FROM_SERVER:
                    Log.v(MessengerService.TAG, "receive msg from server: " + msg.getData().getString("msg")
                            + ", threadID=" + Thread.currentThread().getId()
                            + ", processID=" + android.os.Process.myPid());
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }
}
