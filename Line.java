package finalexam;

import java.awt.*;

public class Line extends DrawStruct {

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

    @Override
    public boolean isContain(Point h) {
        double xx, yy;
        if (!super.isContain(h)) return false;
        if (end.y == start.y) {
            xx = h.x;
            yy = start.y;
        }
        else if (end.x == start.x) {
            xx = start.x;
            yy = h.y;
        }
        else {
            double giulgi1 = -1 * (double) (end.x - start.x) / (double) (end.y - start.y);
            double giulgi2 = (double) (end.y - start.y) / (double) (end.x - start.x);
            double aa = (double) (start.y) - (giulgi2 * start.x);
            double bb = (double) (h.y) - (giulgi1 * h.x);
            xx = (bb - aa) / (giulgi2 - giulgi1);
            yy = (giulgi2 * xx) + aa;
        }

        double distance = Math.pow((h.x - xx), 2) + Math.pow((h.y - yy), 2);
        return (distance < 1000.0);

    }
}
