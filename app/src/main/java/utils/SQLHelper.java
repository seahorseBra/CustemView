package utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zchao on 2016/7/7.
 */
public class SQLHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "hl.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "flow_table";//主界面信息流表

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 说明：目前暂定的数据库参数为以下几项,各自说明

         *      key:定义为主键。对应各条信息流的唯一标识，需由服务器返回；
         *      seq：对应于信息流展示顺序；
         *      page：对应于信息流所在页数；
         *      save_time:入库时间，用于更新数据库数据；
         *      flag：true表示用户手动取消了此条信息的关注，将不再展示此条信息；
         *      content：单条信息的具体数据；
         *
         */
        String sql = "CREATE TABLE " + TABLE_NAME + "("
                +" key TINYTEXT PRIMARY KEY,"
                +" seq INTEGER,"
                +" page INTEGER,"
                +" save_time LONG,"
                +" flag INTEGER,"
                +" content TEXT"
                + ")";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql  = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        this.onCreate(db);
    }
}
