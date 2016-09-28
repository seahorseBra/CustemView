package com.example.administrator.custemview;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

import allinterface.Constants;

/**
 * Created by zchao on 2016/9/28.
 */

public class BluetoothChatService {
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    private Handler mHandler;
    private Context mContext;
    private BluetoothAdapter adapter;

    private ConnectThread mConnectThread;

    public BluetoothChatService(Handler mHandler, Context mContext) {
        this.mHandler = mHandler;
        this.mContext = mContext;
    }

    /**
     * 新链接时候先，关闭之前所有的链接再新建一个链接
     */
    private void conn(BluetoothSocket socket, BluetoothDevice device) {

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

    }

    private void connectionFailed() {

    }


    /**
     * 链接设备线程
     */
    class ConnectThread extends Thread{
        private final BluetoothDevice mDevice;
        private BluetoothSocket mSocket;

        public ConnectThread(BluetoothDevice device) {
            this.mDevice  =device;
            try {
                mSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID_SECURE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            setName("ConnectThread");


            try {
                mSocket.connect();
            } catch (IOException e) {
                try {
                    mSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                connectionFailed();
                return;
            }

            synchronized (BluetoothChatService.this) {
                mConnectThread = null;
            }

            conn(mSocket, mDevice);
        }

        public void cancel() {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设备链接中
     */
    class ConnectedThread extends Thread {
        private BluetoothSocket socket;
        private final InputStream mInputStream;
        private OutputStream mOutputStream;

        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mInputStream = inputStream;
            mOutputStream = outputStream;
        }


        @Override
        public void run() {
            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    int count = mInputStream.read(buffer);
                    mHandler.obtainMessage(Constants.MESSAGE_READ, count, -1, buffer).sendToTarget();

                } catch (IOException e) {
                    e.printStackTrace();
                    connectionLost();
                    BluetoothChatService.this.start();
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                mOutputStream.write(buffer);
                mHandler.obtainMessage(Constants.MESSAGE_WRITE, -1, -1, buffer).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class AcceptThread extends Thread{
        private BluetoothSocket socket;

        public AcceptThread(BluetoothSocket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {


        }
    }

    /**
     * 链接丢失处理
     */
    private void connectionLost() {

    }


    public void start() {

    }
}
