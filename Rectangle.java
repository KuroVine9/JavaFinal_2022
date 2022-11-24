package finalexam;

import java.awt.*;
import java.io.Serializable;

public class Rectangle extends DrawStruct implements Serializable {
    private int rect_x, rect_y, rect_w, rect_h;
    // 계산을 줄이기 위해 데이터 저장

    public Rectangle(Point start, Point end) {
        super(start, end);
        refresh();
    }

    public Rectangle(int start_x, int start_y, int end_x, int end_y) {
        this(new Point(start_x, start_y), new Point(end_x, end_y));
    }

    @Override
    public void drawShape(Graphics g) {
        g.drawRect(rect_x, rect_y, rect_w, rect_h);
    }

    @Override
    public void drawSelectPoint(Graphics g) {
        g.fillRect(rect_x - 2, rect_y - 2, 4, 4);
        g.fillRect(rect_x + rect_w - 2, rect_y + rect_h - 2, 4, 4);
    }

    @Override
    public DrawStruct makeCopiedObj() {
        return (new Rectangle(start.x + 10, start.y + 10, end.x + 10, end.y + 10));
    }

    @Override
    public void moveShape(Point p) {
        super.moveShape(p);
        refresh();
    }

    @Override
    public void resizeShape(Point p, TRI mode) {
        super.resizeShape(p, mode);
        refresh();
    }

    @Override
    public String toString() {
        return String.format("Rect::\t%s", super.toString());
    }

    private void refresh() {
        rect_x = Math.min(start.x, end.x);
        rect_y = Math.min(start.y, end.y);
        rect_w = Math.abs(start.x - end.x);
        rect_h = Math.abs(start.y - end.y);
    }   // 도형의 위치나 크기에 변동이 있을 경우 호출
}
