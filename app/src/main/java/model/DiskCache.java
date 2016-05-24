package model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by zchao on 2016/5/24.
 */
public class DiskCache {

    private static final String TAG = "DiskCache";
    private File baseFile;
    private String appVer;
    private Executor mDiskExecutor;

    private static DiskCache instance;
    private final Gson gson;

    private static DiskCache mInstance;

    public static DiskCache getInstance(Context context) {
        if (mInstance == null) {
            File file = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                file = new File(Environment.getExternalStorageDirectory(), "Android/data/" + context.getPackageName() + "/cache");
            } else {
                context.getCacheDir();
            }

            String appVer = "";
            PackageInfo info = null;

            try {
                info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_GIDS);
                if (info != null) {
                    appVer = info.versionName;
                }
            } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
            }
            mInstance = new DiskCache(file, appVer);
        }
        Log.d(TAG, "getInstance() called with: " + "context = [" + "" + "]");
        return mInstance;

    }

    public DiskCache(File baseFile, String appVer) {
        mDiskExecutor = Executors.newSingleThreadExecutor();
        this.baseFile = baseFile;
        this.appVer = appVer;
        gson = new Gson();
    }

    public File getCacheFile(String key) {
        return getCacheFile("json", key);

    }

    public File getCacheFile(String key, String pre) {
        File file = new File(baseFile, pre + "/" + strToMD5(key));
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }


    /**
     * 获取MD5加密
     * @param str
     * @return
     */
    public String strToMD5(String str) {
        byte[] md5s;
        try {
            md5s =  MessageDigest.getInstance("MD5").digest(str.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder SB = new StringBuilder();
        for (byte b: md5s) {
            SB.append(b);
        }
        return SB.toString();
    }

    public void storyCacheObj(final String key, final Object object){
        mDiskExecutor.execute(new Runnable() {

            @Override
            public void run() {
                CacheObj<Object> cacheObj = new CacheObj<>(object, System.currentTimeMillis(), appVer);
                String jsonStr = gson.toJson(cacheObj);
                File mCacheFile = getCacheFile(key);
                BufferedOutputStream bos = null;
                FileOutputStream fos = null;
                if (mCacheFile != null) {
                    try {
                        fos = new FileOutputStream(mCacheFile);
                        byte[] bytes = new byte[1024];
                        bos = new BufferedOutputStream(fos);
                        bos.write(bytes, 0, jsonStr.length());
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            if (bos != null) {
                                bos.close();
                            }
                            if (fos != null) {
                                fos.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


        });
    }
    public synchronized <T>CacheObj<T> getCacheObj(String key, Type type){
        File file = getCacheFile(key);
        if (file == null || !file.exists()) {
            return null;
        }

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        String s = "";
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            byte[] temp = new byte[1024];
            int count = 0;
            if ((count = bis.read(temp)) != -1) {
                 s = new String(temp, 0, count);
            }
            return gson.fromJson(s, type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
