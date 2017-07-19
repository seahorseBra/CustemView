package com.example;

import com.spreada.utils.chinese.ZHConverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简体转繁体转换工具
 */
public class MyClass1 {
    public static void main(String[] args) throws IOException {
        System.out.print("开始繁简转换");
        File file1 = null;
        File file = new File("F:\\CustemView\\javatest\\src\\main\\java\\com\\example\\strings.xml");
        if (!file.exists()) {
            System.out.print("文件不存在");
        } else {
            String absolutePath = file.getParent();
            String name = file.getName();
            file1 = new File(absolutePath + "\\data\\");
            if (file1.exists()) {
                file1.delete();
            }
            file1.mkdirs();
            file1 = new File(file1.getAbsoluteFile() + "\\"+name);
            file1.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new FileWriter(file1));

        String bfS = null;
        while ((bfS = br.readLine()) != null){
            System.out.println(bfS);
            String chinese = getChinese(bfS);
            System.out.println(chinese);
            bw.write(chinese);
            bw.newLine();
        }

        br.close();
        bw.close();
    }

    /**
     * 正则表达式匹配汉字。并直接转换简体为繁体
     * @param paramValue
     * @return
     */
    public static String getChinese(String paramValue) {
        String regex = "([\u4e00-\u9fa5]+)";
        String va = paramValue;
        Matcher matcher = Pattern.compile(regex).matcher(paramValue);
        while (matcher.find()) {
            String group = matcher.group(0);
            String s = convertWord(group);
            va = va.replace(group, s);
        }
        return va;
    }


    public static String convertWord(String word){
        ZHConverter converter2 = ZHConverter.getInstance(ZHConverter.TRADITIONAL);
        return converter2.convert(word);
    }

}
