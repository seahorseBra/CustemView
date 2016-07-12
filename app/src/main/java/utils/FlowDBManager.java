package utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javaBean.FlowInfo;

/**
 * 首页信息流数据库管理类
 * Created by zchao on 2016/7/7.
 */
public class FlowDBManager {
    private Context context;
    private SQLiteDatabase db = null;
    private SQLHelper sqlHelper = null;
    private static FlowDBManager instance = null;

    public FlowDBManager(Context context) {
       this.context = context;
        this.sqlHelper = new SQLHelper(context);
    }

    /**
     * @return
     */
    public void openDb() {
        if (db == null || !db.isOpen()) {
            db = sqlHelper.getWritableDatabase();
        }
    }

    /**
     * 插入操作,使用key做主键，使用replace避免重复输入；
     * @param key:对应各条信息流的唯一标识，需由服务器返回；
     * @param seq：对应于信息流展示顺序；
     * @param save_time:入库时间，用于更新数据库数据；
     * @param flag：true表示用户手动取消了此条信息的关注，将不再展示此条信息；
     * @param content：单条信息的具体数据；
     */
	public void insert(String key, int seq, int page, long save_time,
			int flag, String content){
        openDb();
        String sql = "REPLACE INTO " + SQLHelper.TABLE_NAME + " (key,seq,page,save_time,flag,content)"
				+ " VALUES(?,?,?,?,?,?)"
                + " ";
		Object args[]=new Object[]{key, seq, page, save_time, flag, content};
		this.db.execSQL(sql, args);
		this.db.close();
	}


    public void insert(FlowInfo info) {
        if (info == null) {
            return;
        }
        insert(info.key, info.seq, info.page, info.save_time, info.flag, info.content);
    }
    /**
     * 批量插入
     */
    public void insert(List<FlowInfo> list) {
        if (list.isEmpty()) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            insert(list.get(i));
        }
    }

    /**
     * 更新操作，当用户关闭某条消息时候
     * @param key
     * @param flag
     */
    public void update(String key,int flag) {
        openDb();
        String sql = "UPDATE " + SQLHelper.TABLE_NAME + " SET flag=? WHERE key=?";
        Object args[]=new Object[]{flag, key};
        this.db.execSQL(sql, args);
        this.db.close();
    }

    /**
     * 删除某一条数据
     * @param key
     */

    public void delete(int key){
        openDb();
        String sql = "DELETE FROM " + SQLHelper.TABLE_NAME +" WHERE key=?";
        Object args[]=new Object[]{key};
        this.db.execSQL(sql, args);
        this.db.close();
    }

    /**
     * 此删除用于数据过期的删除
     * @param save_time 如果数据库数据入库时间小于此数就删除
     */
    public void delete(long save_time) {
        openDb();
        String sql = "DELETE FROM " + SQLHelper.TABLE_NAME + " WHERE save_time<?";
        Object args[] = new Object[]{save_time};
        this.db.execSQL(sql, args);
        this.db.close();
    }

    /**
     *
     */
    public void clearDate() {
        openDb();
        db.delete(SQLHelper.TABLE_NAME, null, null);
        this.db.close();
    }

    /**
     *查询,查询时候就无需查询被用户忽略的
     * @return
     */
    public List<FlowInfo> query(){
        openDb();
        List<FlowInfo> all = new ArrayList();
        String sql = "SELECT * FROM " + SQLHelper.TABLE_NAME + " WHERE flag=100";
        Cursor result = this.db.rawQuery(sql, null);
        if(result.getCount() == 0)return null;
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            FlowInfo flowInfo = new FlowInfo();
            flowInfo.key = result.getString(0);
            flowInfo.seq = result.getInt(1);
            flowInfo.page = result.getInt(2);
            flowInfo.save_time = result.getLong(3);
            flowInfo.flag = result.getInt(4);
            flowInfo.content = result.getString(5);
            all.add(flowInfo);
        }
        this.db.close();
        return all;
    }

    /**
     * 按页数查询
     * @param page
     * @return
     */
    public List<FlowInfo> query(int page) {
        openDb();
        List<FlowInfo> all = new ArrayList<>();
        String sql = "SELECT * FROM " + SQLHelper.TABLE_NAME + " WHERE flag=100 AND page=?";
        String args[] = new String[]{String.valueOf(page)};
        Cursor result = this.db.rawQuery(sql, args);
        if(result.getCount() == 0)return null;
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            FlowInfo flowInfo = new FlowInfo();
            flowInfo.key = result.getString(0);
            flowInfo.seq = result.getInt(1);
            flowInfo.page = result.getInt(2);
            flowInfo.save_time = result.getLong(3);
            flowInfo.flag = result.getInt(4);
            flowInfo.content = result.getString(5);
            all.add(flowInfo);
        }
        this.db.close();
        return all;
    }

    /**
     * 按页数查询，并将结果排序
     * @param page
     * @param isSort 是否将查询结果按seq直接拍好序列
     * @return
     */
    public List<FlowInfo> query(int page, boolean isSort) {
        openDb();
        List<FlowInfo> all = new ArrayList<>();
        String sql = "SELECT * FROM " + SQLHelper.TABLE_NAME + " WHERE flag=100 AND page=?";
        String sql1 = "SELECT * FROM " + SQLHelper.TABLE_NAME + " WHERE flag=100 AND page=? ORDER BY seq";
        String args[] = new String[]{String.valueOf(page)};
        Cursor result = this.db.rawQuery(isSort ? sql1 : sql, args);
        if(result.getCount() == 0)return null;
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            FlowInfo flowInfo = new FlowInfo();
            flowInfo.key = result.getString(0);
            flowInfo.seq = result.getInt(1);
            flowInfo.page = result.getInt(2);
            flowInfo.save_time = result.getLong(3);
            flowInfo.flag = result.getInt(4);
            flowInfo.content = result.getString(5);
            all.add(flowInfo);
        }
        this.db.close();
        return all;
    }

}
