package com.youloft.mysmall.bluetoothsample;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mList;
    private Button mOpenBT;
    private Button mSearchBT;
    private Button mSendMsg;
    private TextView mState;
    private BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniView();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void iniView() {
        mList = (RecyclerView) findViewById(R.id.list);
        mOpenBT = (Button) findViewById(R.id.button1);
        mSearchBT = (Button) findViewById(R.id.button2);
        mSendMsg = (Button) findViewById(R.id.button3);
        mState = (TextView) findViewById(R.id.text_state);

        mOpenBT.setOnClickListener(this);
        mSearchBT.setOnClickListener(this);
        mSendMsg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                openBt();
                break;
            case R.id.button2:
                searchBT();
                break;
            case R.id.button3:
                sendMsg();
                break;

        }
    }

    /**
     * 发送数据
     */
    private void sendMsg() {

    }

    /**
     * 搜索蓝牙
     */
    private void searchBT() {
        if (mBluetoothAdapter != null) {


        }
    }


    private static int OPEN_CODE = 111;

    /**
     * 打开蓝牙
     */
    private void openBt() {
        if (mBluetoothAdapter != null) {
//            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(intent, OPEN_CODE);
            boolean enable = mBluetoothAdapter.enable();
            if (enable) {
                mState.setText("蓝牙设备就绪");
                return;
            }
        }
        mState.setText("设备可能不支持蓝牙功能");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OPEN_CODE) {
            if (resultCode == RESULT_OK) {
                mState.setText("蓝牙设备就绪");
            } else if (resultCode == RESULT_CANCELED) {
                mState.setText("如需使用请打开蓝牙开关");
            }
        }
    }
}
