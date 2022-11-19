package finalexam;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawDataHandler {
    private ArrayList<DrawStruct> DrawData;

    public DrawDataHandler() {
        DrawData = new ArrayList<>();
    }

    public void drawSavedData(Graphics g) {
        for (var drawdata : DrawData) {
            int x = Math.min(drawdata.start.x, drawdata.end.x);
            int y = Math.min(drawdata.start.y, drawdata.end.y);
            int width = Math.abs(drawdata.start.x - drawdata.end.x);
            int height = Math.abs(drawdata.start.y - drawdata.end.y);

            if (drawdata.isFlag()) {
                switch (drawdata.type) {
                    case 0, 2 -> {
                        g.drawRect(x - 2, y - 2, 4, 4);
                        g.drawRect(x + width - 2, y + height - 2, 4, 4);
                    }
                    case 1 -> {
                        g.drawRect(drawdata.start.x - 2, drawdata.start.y - 2, 4, 4);
                        g.drawRect(drawdata.end.x - 2, drawdata.end.y - 2, 4, 4);
                    }
                }
            }
            switch (drawdata.type) {
                case 0 -> g.drawRect(x, y, width, height);
                case 1 -> g.drawLine(drawdata.start.x, drawdata.start.y, drawdata.end.x, drawdata.end.y);
                case 2 -> g.drawOval(x, y, width, height);
                default -> {
                    System.out.println("Unknown Type!");
                }
            }
        }
    }

    public void saveDrawData(Point start, Point end, byte type) {
        DrawData.add(0, new DrawStruct(start, end, type));
    }

    public boolean selectShape(MouseEvent e) {
        clearSelect();
        for (var data : DrawData) {
            if (data.isContain(e.getPoint())) {
                data.setFlag(true);
                System.out.println("tagged");
                return true;
            }
        }
        return false;
    }

    public void clearSelect() {
        for (var data : DrawData) {
            data.setFlag(false);
        }
    }

    public void copyShape() {
        for (int i = DrawData.size() - 1; i >= 0; i--) {
            if (DrawData.get(i).flag) {
                Point tempS, tempE;
                if (DrawData.get(i).type == 1) {
                    tempS = new Point(DrawData.get(i).start.x + 10, DrawData.get(i).start.y);
                    tempE = new Point(DrawData.get(i).end.x + 10, DrawData.get(i).end.y);
                }
                else {
                    tempS = new Point(DrawData.get(i).start.x + 10, DrawData.get(i).start.y + 10);
                    tempE = new Point(DrawData.get(i).end.x + 10, DrawData.get(i).end.y + 10);
                }
                DrawData.get(i).setFlag(false);
                DrawData.add(0, new DrawStruct(tempS, tempE, DrawData.get(i).type, true));
                return;
            }
        }
    }

    public void deleteShape() {
        Iterator<DrawStruct> iter = DrawData.iterator();
        while (iter.hasNext()) if (iter.next().isFlag()) iter.remove();
    }

    public boolean moveSelectedShape(MouseEvent e, Point start, Point end) {
        Point vec = new Point(end.x - start.x, end.y - start.y);
        for (var data : DrawData) {
            if (data.isContain(e.getPoint()) && data.isFlag()) {
                data.moveShape(vec);
                return true;
            }
        }
        return false;
    }
}
