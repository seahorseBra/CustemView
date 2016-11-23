package utils;

import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.BuildConfig;
import android.support.v4.app.NavUtils;
import android.view.ScaleGestureDetector;

/**
 * Created by zchao on 2016/11/23.
 */

public class ScreenShotDetector {

    private static final String EXTERAL_CONTENT_URI_MATCHER = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString();
    private static final String[] PRIJECTION = new String[]{MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DATE_ADDED};
    private static final String SORT_ORDER = MediaStore.Images.Media.DATE_ADDED + " DESC";
    private static final long DEFAULT_DETECT_WINDOW_SECONDS = 30;

    private Context context;
    private ScreenShotListener listener;
    private ContentResolver contentResolver;
    private Class tagActivity;

    public interface ScreenShotListener {
        void onScreenShot(String path);
    }

    public ScreenShotDetector(Context context) {
        this(context, null, null);
    }

    public ScreenShotDetector(Context context, Class tagActivity) {
        this(context, null, tagActivity);

    }

    public ScreenShotDetector(Context context, ScreenShotListener listener) {
        this(context, listener, null);
    }

    public ScreenShotDetector(Context context, ScreenShotListener listener, Class tagActivity){

        this.context = context;
        this.listener = listener;
        this.tagActivity = tagActivity;
        contentResolver = context.getContentResolver();
    }

    final ContentObserver contentObserver = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            if (uri.toString().matches(EXTERAL_CONTENT_URI_MATCHER)) {
                Cursor cursor = null;
                try {
                    cursor = contentResolver.query(uri, PRIJECTION, null, null, SORT_ORDER);
                    if (cursor != null && cursor.moveToFirst()) {
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        long date = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                        long currDate = System.currentTimeMillis() / 1000;
                        if (matchPath(path) && mathcTime(currDate, date)) {
                            onScreenShotEvent(path);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }
    };

    public void start(){
        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, contentObserver);
    }

    public void stop(){
        contentResolver.unregisterContentObserver(contentObserver);
    }


    private void onScreenShotEvent(String path) {
        if (tagActivity != null) {
            Intent intent = new Intent(context, tagActivity);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("PATH", path);
            context.startActivity(intent);
        }
        Intent intent = new Intent();
        intent.setAction("com.custemView.action.SCREEN_SHOT");
        intent.putExtra("PATH", path);
        context.sendBroadcast(intent);
    }

    private static boolean matchPath(String path){
        return path.toLowerCase().contains("screenshot")
                ||path.contains("截图")
                ||path.contains("截屏")
                ||path.toLowerCase().contains("screenshots");
    }

    private static boolean mathcTime(long currentTime, long dataAdded){
        return Math.abs(currentTime - dataAdded) <= DEFAULT_DETECT_WINDOW_SECONDS;
    }
}
