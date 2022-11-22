package finalexam;

import java.awt.*;
import java.io.Serializable;

public class DrawStruct implements Serializable {
    Point start, end;
    int type;
    boolean flag;

    enum TRI {START, END, NULL}

    public DrawStruct(Point start, Point end, int type) {
        this(start, end, type, false);
    }

    public DrawStruct(Point start, Point end, int type, boolean flag) {
        this.start = start;
        this.end = end;
        this.type = type;
        this.flag = flag;
    }

    public boolean isContain(Point point) {
//        return (start.x < point.x && end.x > point.x && start.y < point.y && end.y > point.y);

        //TODO start가 end보다 높은 경우 있어 제대로 비교되지 않음
    }

    public TRI isControlPoint(Point point) {
        if (start.x - 10 < point.x && start.x + 10 > point.x && start.y - 10 < point.y && start.y + 10 > point.y) {
            return TRI.START;
        }
        else if (end.x - 10 < point.x && end.x + 10 > point.x && end.y - 10 < point.y && end.y + 10 > point.y) {
            return TRI.END;
        }
        else {
            return TRI.NULL;
        }
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

    public String toString() {
        return String.format("S: (%d, %d) E: (%d, %d), T: %d", start.x, start.y, end.x, end.y, type);
    }
}
