package com.example;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;

public class MyClass {
    public static void main(String[] args) throws IOException {
        //插入数据的sql语句
        System.out.println("提取xml数据并导入数据库");
        //读取xml文件的数据并写入数据库中
        InsertData();
        //读取数据库中的数据并写入xml文档
//        SelectData();
//        try {
//            TransformerFactory tFactory = TransformerFactory.newInstance();
//            Transformer transformer =
//                    tFactory.newTransformer
//                            (new javax.xml.transform.stream.StreamSource
//                                    ("xslt.xsl"));
//            transformer.transform
//                    (new javax.xml.transform.stream.StreamSource
//                                    ("province_data.xml"),
//                            new javax.xml.transform.stream.StreamResult
//                                    ( new FileOutputStream("aaaaa.html")));
//        }
//        catch (Exception e) {
//            e.printStackTrace( );
//        }

    }

    private static void InsertData() {
        // TODO Auto-generated method stub
        String sql="insert into t_xml(province,city,district,othermsg) "
                + "values (?,?,?,?)";
        Connection conn=null;
        PreparedStatement pstmt=null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydb","root","");

            String sql1 = "create table city(primary key(NO),province char(20),city varchar(20),district varchar(20))";
            Statement stmt = conn.createStatement();
            stmt.execute(sql1);
            //准备执行sql语句
            pstmt=conn.prepareStatement(sql);
            //读取xml文件
            Document doc=new SAXReader().read(new InputStreamReader(
                    new FileInputStream(new File("province_data.xml")),"utf-8"));
            //选择xml文件的节点
            // Element node = doc.getRootElement();
            List itemList=doc.selectNodes("root/province");
            //遍历读出的xml中的节点
            System.out.println("导入数据库的xml数据如下：\n");
            System.out.println("姓名-----电话-----社交账号------手机号---------工作--------地址--------邮箱-------其他信息\n");
            for(Iterator iter=itemList.iterator();iter.hasNext();){
                Element el=(Element)iter.next();
                //读取节点内容
                String name=el.elementText("name");

                List citylist=el.selectNodes("city");
                for(Iterator city=citylist.iterator();iter.hasNext();){
                    Element el1=(Element)city.next();
                    //读取节点内容
                    String cityname=el1.elementText("name");


                    List districtlist=el.selectNodes("district");
                    for(Iterator distric =districtlist.iterator();iter.hasNext();){
                        Element el2=(Element)distric.next();
                        //读取节点内容
                        String districname =el2.elementText("name");
                        //为sql语句赋值
                        pstmt.setString(1, name);
                        pstmt.setString(2, cityname);
                        pstmt.setString(3, districname);
                        pstmt.addBatch();
                    }

                }

            }
            pstmt.executeBatch();
            pstmt.close();
            conn.close();
            System.out.print("将XML文档数据导入数据库成功\n");
        }
        //捕获加载驱动程序异常
        catch ( ClassNotFoundException cnfex ) {
            System.err.println(
                    "装载 JDBC/ODBC 驱动程序失败。" );
            cnfex.printStackTrace();
        }
        //捕获连接数据库异常
        catch ( SQLException sqlex ) {
            System.err.println( "无法连接数据库" );
            sqlex.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{

        }


    }
    private static void SelectData()  {
        // TODO Auto-generated method stub
        String sql = "select * from t_xml";
        Connection conn=null;
        PreparedStatement pstmt=null;
        //声明结果集接受对象
        ResultSet ret = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mydb","root","");
            pstmt=conn.prepareStatement(sql);
            ret=pstmt.executeQuery();
            //创建一个DocumentFactory对象
            System.out.println("将数据库的内容写入xml文档：");
            DocumentFactory factory = new DocumentFactory();
            //通过factory对象创建一个doc文件对象
            Document doc = factory.createDocument();
            doc.addProcessingInstruction("crazyit", "website=\"http://www.crazyit.org\"");
            //加入根元素
            Element root = doc.addElement("txl");
            System.out.println("写入xml文档的数据如下：\n");
            while (ret.next()) {
                String name = ret.getString(1);
                String tel = ret.getString(2);
                String qqmsn = ret.getString(3);
                String mobile = ret.getString(4);
                String work = ret.getString(5);
                String address = ret.getString(6);
                String email = ret.getString(7);
                String othermsg = ret.getString(8);
                Element user =root.addElement("user");
                user.addAttribute("name", name);
                user.addAttribute("tel", tel);
                user.addAttribute("qqmsn", qqmsn);
                user.addAttribute("mobile", mobile);
                user.addAttribute("work", work);
                user.addAttribute("address", address);
                user.addAttribute("email", email);
                user.addAttribute("othermsg", othermsg);

                System.out.println(name + "\t" + tel + "\t" + qqmsn + "\t" +
                        mobile + "\t" + work + "\t" + address + "\t" + email + "\t" +othermsg);

            }//显示数据
//            OutputXml(doc);
            ret.close();
            conn.close();

        }
        catch ( ClassNotFoundException cnfex ) {
            System.err.println(
                    "装载 JDBC/ODBC 驱动程序失败。" );
            cnfex.printStackTrace();
        }
        //捕获连接数据库异常
        catch ( SQLException sqlex ) {
            System.err.println( "无法连接数据库" );
            sqlex.printStackTrace();
        }  catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //准备执行sql语句

    }
    private static void OutputXml(Document doc) {
        // TODO Auto-generated method stub
        XMLWriter writer = null;
        //定义一种输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");// 设置XML文件的编码格式,如果有中文可设置为GBK或UTF-8
        File file = new File("tongxunlu.xml");
        //如果读取的内容中没有中文,可以使用以下的几行代码生成xml
        //
        //  try {
        //   writer = new XMLWriter(new FileWriter(file), format);
        //  } catch (IOException e1) {
        //   e1.printStackTrace();
        //  }

        // 如果上面设置的xml编码类型为GBK，或设为UTF-8但其中有中文则应当用FileWriter来构建xml文件（使用以下代码），否则会出现中文乱码问题
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            writer = new XMLWriter(new OutputStreamWriter(fos,"utf-8"), format);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            writer.write(doc);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
