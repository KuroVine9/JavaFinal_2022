package finalexam;

import java.awt.*;

public class DrawStruct {
    Point start, end;
    byte type;
    boolean flag;

    public DrawStruct(Point start, Point end, byte type) {
        this(start, end, type, false);
    }

    public DrawStruct(Point start, Point end, byte type, boolean flag) {
        this.start = start;
        this.end = end;
        this.type = type;
        this.flag = flag;
    }

    public boolean isContain(Point point) {
        return (start.x < point.x && end.x > point.x && start.y < point.y && end.y > point.y);
    }

    public void moveShape(Point p) {
        start.x += p.x;
        start.y += p.y;
        end.x += p.x;
        end.y += p.y;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
