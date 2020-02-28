/*
 * Copyright (c) 2019 Guangdong oppo Mobile Communication(Shanghai)
 * Corp.,Ltd. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *     * Neither the name of The Linux Foundation nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * File: MessengerService.java
 * Description:
 *
 * ---------------------------- Revision History: ------------------------
 * <author>             <date>          <version>           <desc>
 * sean.zhou@oppo.com   2020/2/27         1.0                 create this module
 * -----------------------------------------------------------------------
 */
package com.homeraria.hencodeuicourse.app.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * 位于remote进程的service，用于检测message的跨进程通信功能
 *
 * 1. 使用Messenger来传递Message，Message中能使用的字段只有what、arg1、arg2、Bundle和replyTo,自定义的Parcelable对象无法通过object字段来传输
 * 2. Message中的Bundle支持多种数据类型，replyTo字段用于传输Messenger对象，以便进程间相互通信
 * 3. Messenger以串行的方式处理客户端发来的消息，不适合有大量并发的请求
 * 4. Messenger方法只能传递消息，不能跨进程调用方法
 */
public class MessengerService extends Service {
    public static final String TAG = "Messenger";
    public static final int MESSAGE_FROM_CLIENT = 1;
    public static final int MESSAGE_FROM_SERVER = 2;

    /**
     * 可执行跨进程通信的Messenger对象
     */
    private final Messenger mMessenger = new Messenger(new ServerMessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //将Messenger对象中的binder返回给客户端
        return mMessenger.getBinder();
    }

    //===================================================================================

    /**
     * 处理来自client的消息，是Messenger的构建handler
     */
    private static class ServerMessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_FROM_CLIENT:
                    Log.v(TAG, "receive msg from client: " + msg.getData().getString("msg")
                            + ", threadID=" + Thread.currentThread().getId()
                            + ", processID=" + android.os.Process.myPid());

                    try {
                        //不能在此线程sleep,会报ANR_LOG: Blocked msg，表示处理该msg时间过长
                        //但是不会出现逻辑失败（无法sendBack），只是报出这个error
                        Thread.sleep(2000);

                        //接收到消息后，延迟一段时间模拟耗时操作后，给client端返回一个msg
                        sendBackMsg(msg.replyTo);
                    } catch (InterruptedException | RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

        /**
         * 需要从client发来的msg中获取Messenger，从而reply一些信息
         * @param clientMessenger Messenger对象已经包含在msg中
         */
        private void sendBackMsg(Messenger clientMessenger) throws RemoteException {
            Message msg = Message.obtain(null, MESSAGE_FROM_SERVER);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hi client, this is a msg from server" + System.currentTimeMillis());
            msg.setData(bundle);

            clientMessenger.send(msg);
        }
    }
}
