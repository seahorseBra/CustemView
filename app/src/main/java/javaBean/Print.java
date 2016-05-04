package javaBean;

/**
 * Created by zchao on 2016/3/9.
 */
public class Print {
    private int x;
    private int y;
    private int z;

    public Print() {
    }

    public Print(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Print{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
