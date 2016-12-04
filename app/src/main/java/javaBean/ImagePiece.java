package javaBean;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片实体类
 * Created by zchao on 2016/3/30.
 */
public class ImagePiece implements Parcelable{
    public int indexX;
    public int indexY;
    public Bitmap bitmap = null;

    public ImagePiece(Parcel in) {
        indexX = in.readInt();
        indexY = in.readInt();
        bitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<ImagePiece> CREATOR = new Creator<ImagePiece>() {
        @Override
        public ImagePiece createFromParcel(Parcel in) {
            return new ImagePiece(in);
        }

        @Override
        public ImagePiece[] newArray(int size) {
            return new ImagePiece[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(indexX);
        dest.writeInt(indexY);
        dest.writeParcelable(bitmap, flags);
    }
}
