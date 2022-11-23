package finalexam;

import java.awt.*;
import java.io.Serializable;

public class Tawon extends DrawStruct implements Serializable {
    int tawon_x = Math.min(start.x, end.x);
    int tawon_y = Math.min(start.y, end.y);
    int tawon_w = Math.abs(start.x - end.x);
    int tawon_h = Math.abs(start.y - end.y);

    public Tawon(Point start, Point end) {
        super(start, end);
    }

    public Tawon(int start_x, int start_y, int end_x, int end_y) {
        super(new Point(start_x, start_y), new Point(end_x, end_y));
    }

    @Override
    public void drawShape(Graphics g) {
        g.drawOval(tawon_x, tawon_y, tawon_w, tawon_h);
    }

    @Override
    public void drawSelectPoint(Graphics g) {
        g.fillRect(tawon_x - 2, tawon_y - 2, 4, 4);
        g.fillRect(tawon_x + tawon_w - 2, tawon_y + tawon_h - 2, 4, 4);
    }

    @Override
    public DrawStruct makeCopiedObj() {
        return (new Tawon(start.x + 10, start.y + 10, end.x + 10, end.y + 10));
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
        return String.format("Tawon::\t%s", super.toString());
    }

    private void refresh() {
        tawon_x = Math.min(start.x, end.x);
        tawon_y = Math.min(start.y, end.y);
        tawon_w = Math.abs(start.x - end.x);
        tawon_h = Math.abs(start.y - end.y);
    }
}
