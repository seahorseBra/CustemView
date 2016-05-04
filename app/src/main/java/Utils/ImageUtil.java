package Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javaBean.ImagePiece;

/**
 * 图片处理类
 * Created by zchao on 2016/3/30.
 */
public class ImageUtil {
    private Bitmap bitmap = null;
    public ImageUtil() {
    }

    /**
     * 图片分割为多块
     * @param bitmap 需要分割的图片
     * @param xSplitSize x方向分割数量
     * @param ySplitSize y方向分割数量
     * @return 分割后图片集合 {@link ImagePiece}
     */
    public static List<ImagePiece> SplitImage(Bitmap bitmap, int xSplitSize, int ySplitSize){
        if (bitmap == null)return null;
        List<ImagePiece> pieces = new ArrayList<>();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int weightForPiece = width / xSplitSize;
        int heightForPiece = height / ySplitSize;
        for (int i = 0; i < ySplitSize; i++) {
            for (int j = 0; j < xSplitSize; j++) {
                ImagePiece imagePiece = new ImagePiece();
                imagePiece.indexX = j;
                imagePiece.indexY = i;
                imagePiece.bitmap = Bitmap.createBitmap(bitmap, j * weightForPiece, i * heightForPiece, weightForPiece, heightForPiece);
                pieces.add(imagePiece);
            }
        }
        return pieces;
    }


    /**
     * 图片压缩，按最小
     * @param bitmap 待压缩图片
     * @param size 要压缩的大小
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap, float size){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , bos);
        int quality = 100;
        while (bos.size() / 1024 > size) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality , bos);
            quality -= 10;
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        Bitmap bitmap1 = BitmapFactory.decodeStream(bis);
        return bitmap1;
    }

    /**
     * 图片压缩,按百分比压缩
     * @param bitmap 待压缩图片
     * @param percent 要压缩的大小
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap, int percent){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, percent, bos);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        return BitmapFactory.decodeStream(bis);
    }


    /**
     * 图片压缩
     * @param pathName 压缩图片文件
     * @param size 要压缩的大小
     * @return
     */
    public static Bitmap compressImage(String pathName, float size){
        Bitmap bitmap = BitmapFactory.decodeFile(pathName);
        return compressImage(bitmap, size);
    }
}
