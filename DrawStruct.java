package finalexam;

import java.awt.*;
import java.io.Serializable;

abstract public class DrawStruct implements Serializable {  // 도형의 슈퍼 클래스(추상)
    Point start, end;
    boolean flag;   // 도형이 선택되었는지 여부 저장

    enum TRI {START, END, NULL} // 도형 크기조절을 위한 리턴에 사용

    public DrawStruct(Point start, Point end) {
        this(start, end, false);
    }

    public DrawStruct(Point start, Point end, boolean flag) {
        this.start = start;
        this.end = end;
        this.flag = flag;
    }

    abstract public void drawShape(Graphics g);

    // 도형 그리기
    abstract public void drawSelectPoint(Graphics g);

    // 도형의 컨트롤포인트 그리기
    abstract public DrawStruct makeCopiedObj();

    // 도형의 이동된 복사본 리턴
    public void moveShape(Point p) {
        start.x += p.x;
        start.y += p.y;
        end.x += p.x;
        end.y += p.y;
    }   // p만큼 도형 이동

    public void resizeShape(Point p, TRI mode) {
        switch (mode) {
            case START -> start = p;
            case END -> end = p;
        }
    }   // 도형 크기조절

    public boolean isContain(Point point) {
        return (start.x < point.x && end.x > point.x && start.y < point.y && end.y > point.y);

        //TODO start가 end보다 높은 경우 있어 제대로 비교되지 않음
    }   // point가 도형의 내부인지 리턴

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
    }   // point가 어느 컨트롤 포인트에 있는지 enum으로 리턴

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return String.format("S: (%d, %d)\tE: (%d, %d)\tF: %B", start.x, start.y, end.x, end.y, flag);
    }
}
