package javaBean;

/**
 * Created by zchao on 2016/3/10.
 */
public class Point3D extends Print {
    private Point3D(int x, int y) {
        super(x, y);
    }

    public static Point3D createPoint3D(int x, int y) {
        return new Point3D(x, y);
    }
}
