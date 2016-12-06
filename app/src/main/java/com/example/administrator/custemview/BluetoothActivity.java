package com.example.administrator.custemview;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.RunnableFuture;

import allinterface.Constants;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BluetoothActivity extends AppCompatActivity {
    private Button On,Off,Visible,list;
    private BluetoothAdapter BA;
    private HashMap<String, BluetoothDevice> boundList = new HashMap<>();
    private ListView lv, lv2;
    private DeviceAdapter adapter = null;
    private ConnectedThread connectedThread;
    private EditText mEditText;
    private Button mSendButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        On = (Button)findViewById(R.id.button1);
        Off = (Button)findViewById(R.id.button2);
        Visible = (Button)findViewById(R.id.button3);
        list = (Button)findViewById(R.id.button4);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mSendButton = (Button) findViewById(R.id.send);
        mTextView = (TextView) findViewById(R.id.textView1);
        lv = (ListView)findViewById(R.id.listView1);
        lv2 = (ListView)findViewById(R.id.listView2);

        BA = BluetoothAdapter.getDefaultAdapter();

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mEditText.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    sendMessage(s);
                }
            }
        });
    }

    private void sendMessage(String message) {

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            connectedThread.write(send);

            mEditText.setText("");
        }
    }
    public void on(View view){
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(),"Turned on"
                    ,Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Already on",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void list(View view){
        Set<BluetoothDevice> bondedDevices = BA.getBondedDevices();
        if (bondedDevices == null || bondedDevices.isEmpty()) {
            return;
        }

        ArrayList list = new ArrayList();
        for (BluetoothDevice bd:bondedDevices) {
            boundList.put(bd.getName(), bd);
            list.add(bd.getName());
        }

        Toast.makeText(getApplicationContext(),"Showing Paired Devices",
                Toast.LENGTH_SHORT).show();
        final ArrayAdapter adapter = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice bluetoothDevice = boundList.get(adapter.getItem(position));
                conn(bluetoothDevice);
            }
        });
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (adapter != null) {
                    adapter.addDevice(device);
                }
            } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int bondState = device.getBondState();
                switch (bondState) {
                    case BluetoothDevice.BOND_NONE:
                        break;
                    case BluetoothDevice.BOND_BONDING:
                        break;
                    case BluetoothDevice.BOND_BONDED:
                            // 连接
                            conn(device);

                        break;
                }
            }
        }
    };
    public void search(View view) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, filter);
        BA.startDiscovery();

        adapter = new DeviceAdapter(this);
        lv2.setAdapter(adapter);
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BluetoothDevice device = adapter.foundDevice.get(position);
                connetDevice(device);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (BA.isDiscovering()) {
                    BA.cancelDiscovery();
                }

            }
        }, 20000);
    }


    public void off(View view){
        unregisterReceiver(receiver);
        BA.disable();
        Toast.makeText(getApplicationContext(),"Turned off" ,
                Toast.LENGTH_LONG).show();
    }
    public void visible(View view){
        Intent getVisible = new Intent(BluetoothAdapter.
                ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);

    }

    /**
     * 配对或链接设备
     * @param device
     */
    private void connetDevice(BluetoothDevice device){
        int bondState = device.getBondState();
        switch (bondState) {
            case BluetoothDevice.BOND_NONE:
                Method createBond = null;
                try {
                    createBond =BluetoothDevice.class.getMethod("createBond");
                    createBond.invoke(device);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            case BluetoothDevice.BOND_BONDED:
                conn(device);
                break;
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void conn(BluetoothDevice device){
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        BluetoothSocket socket = null;
        try {
            socket = device.createRfcommSocketToServiceRecord(uuid);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectedThread = new ConnectedThread(socket);
        connectedThread.start();
    }

    private final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    if (!TextUtils.isEmpty(readMessage)) {
                        mTextView.setText(readMessage);
                    }
                    break;
            }
        }
    };
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

    private void connectionLost() {
        Toast.makeText(this, "链接中断", Toast.LENGTH_SHORT).show();
    }

    class DeviceAdapter extends BaseAdapter{
        private Context context;
        private LayoutInflater inflater;
        private ArrayList<BluetoothDevice> foundDevice = new ArrayList<>();

        public DeviceAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        public void addDevice(BluetoothDevice device) {
            if (!foundDevice.contains(device)) {
                foundDevice.add(device);
                notifyDataSetChanged();
            }
        }
        @Override
        public int getCount() {
            return foundDevice.size();
        }

        @Override
        public String getItem(int position) {
            if (!TextUtils.isEmpty(foundDevice.get(position).getName())) {
                return foundDevice.get(position).getName();
            } else if (!TextUtils.isEmpty(foundDevice.get(position).getAddress())) {
                return foundDevice.get(position).getAddress();
            }
            return "未知";
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.bluetooth_device_item, parent, false);
                holder = new Holder();
                holder.mTextView = (TextView) convertView.findViewById(R.id.text);
                convertView.setTag(holder);
            }else {
                holder = (Holder) convertView.getTag();
            }

            holder.mTextView.setText(getItem(position));

            return convertView;
        }

        class Holder{
            public TextView mTextView;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
}
