package utils;

import android.graphics.ColorMatrix;

/**
 * 一些常用的图像处理矩阵
 * Created by mavin on 2016/12/7.
 */
public class HandleMatrix {


    public static float[] GRAY = { 0.33f,0.59f,0.11f,0,0,
                            0.33f,0.59f,0.11f,0,0,
                            0.33f,0.59f,0.11f,0,0,
                            0,    0,    0,    1,0};

    public static float[] REVERSAL = { -1,0,0,1,1,
                                        0,-1,0,1,1,
                                        0,0,-1,1,1,
                                        0,0,0,1,0,};

    public static ColorMatrix getGrayMatrix(){
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(GRAY);
        return matrix;
    }

    public static ColorMatrix getMatrix(float[] src){
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(src);
        return matrix;
    }
}
