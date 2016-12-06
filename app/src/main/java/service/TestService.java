package service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;


public class TestService extends Service {

    private static final String TAG = "TestService";
    public static ArrayList<File> musicList = new ArrayList<>();

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() called");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() called with: intent = [" + intent + "], flags = [" + flags + "], startId = [" + startId + "]");

        new Thread(new Runnable() {
            @Override
            public void run() {
                searchMusic();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 搜索所有的音乐文件
     */
    private void searchMusic() {
        File externalCacheDir = Environment.getExternalStorageDirectory();
        File kgmusic = new File(externalCacheDir, "kgmusic");
        search(kgmusic);
    }

    /**
     * 搜索MP3文件
     * @param file
     */
    private void search(File file){
        if (file == null) {
            return;
        }
        if (file.canRead() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                search(files[i]);
            }
        }
        if (file.canRead() && file.isFile() && file.getName().endsWith(".mp3")) {
            musicList.add(file);
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind() called with: intent = [" + intent + "]");
        return new SimpleBinder();
    }

    public class SimpleBinder extends Binder {

        public int add(int a, int b) {
            return (a+b);
        }

        public ArrayList<File> getAllMusic() {
            if (!musicList.isEmpty()) {
                return musicList;
            } else {
                return null;
            }
        }
    }

}
