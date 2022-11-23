package finalexam;

import java.awt.*;
import java.io.Serializable;

public class Line extends DrawStruct implements Serializable {

    public Line(Point start, Point end) {
        super(start, end);
    }

    public Line(int start_x, int start_y, int end_x, int end_y) {
        super(new Point(start_x, start_y), new Point(end_x, end_y));
    }

    @Override
    public void drawShape(Graphics g) {
        g.drawLine(start.x, start.y, end.x, end.y);
    }

    @Override
    public void drawSelectPoint(Graphics g) {
        g.fillRect(start.x - 2, start.y - 2, 4, 4);
        g.fillRect(end.x - 2, end.y - 2, 4, 4);
    }

    @Override
    public DrawStruct makeCopiedObj() {
        return (new Line(start.x + 10, start.y, end.x + 10, end.y));
    }

    @Override
    public String toString() {
        return String.format("Line::\t%s", super.toString());
    }
}
