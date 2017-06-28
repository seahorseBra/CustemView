package com.example.administrator.custemview;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

public class SQLcitysActivity extends AppCompatActivity {

    private SPLHelper splHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splHelper = new SPLHelper(this);
        db = splHelper.getWritableDatabase();
        InsertData();
    }

    public void insert(SQLiteDatabase db, String province, String city, String district) {

        String sql = "REPLACE INTO " + SPLHelper.TABLE_NAME + " (province,city,district)"
                + " VALUES(?,?,?)"
                + " ";
        Object args[] = new Object[]{province, city, district};
        db.execSQL(sql, args);
        db.close();
    }

    private void InsertData() {
        try {

            Document doc = new SAXReader().read(new InputStreamReader(
                    getResources().getAssets().open("province_data.xml"), "utf-8"));

            //选择xml文件的节点
            // Element node = doc.getRootElement();
            List itemList = doc.selectNodes("root/province");
            //遍历读出的xml中的节点
            System.out.println("导入数据库的xml数据如下：\n");
            System.out.println("姓名-----电话-----社交账号------手机号---------工作--------地址--------邮箱-------其他信息\n");
            for (Iterator iter = itemList.iterator(); iter.hasNext(); ) {
                Element el = (Element) iter.next();
                //读取节点内容
                String name = el.attributeValue("name");

                List citylist = el.selectNodes("city");
                for (Iterator city = citylist.iterator(); city.hasNext(); ) {
                    Element el1 = (Element) city.next();
                    //读取节点内容
                    String cityname = el1.attributeValue("name");


                    List districtlist = el1.selectNodes("district");
                    for (Iterator distric = districtlist.iterator(); distric.hasNext(); ) {
                        Element el2 = (Element) distric.next();
                        //读取节点内容
                        String districname = el2.attributeValue("name");
                        //为sql语句赋值
                        if (!db.isOpen()) {
                            db = splHelper.getWritableDatabase();
                        }
                        insert(db, name, cityname, districname);
                        System.out.print("将XML" + name + "|" + cityname + "|" + districname);
                    }

                }

            }
            System.out.print("将XML文档数据导入数据库成功\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }


    }
}
