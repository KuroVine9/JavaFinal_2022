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
        for (var data : DrawData) {
            int x = Math.min(data.start.x, data.end.x);
            int y = Math.min(data.start.y, data.end.y);
            int width = Math.abs(data.start.x - data.end.x);
            int height = Math.abs(data.start.y - data.end.y);

            if (data.isFlag()) {
                switch (data.type) {
                    case 0, 2 -> {
                        g.drawRect(x - 2, y - 2, 4, 4);
                        g.drawRect(x + width - 2, y + height - 2, 4, 4);
                    }
                    case 1 -> {
                        g.drawRect(data.start.x - 2, data.start.y - 2, 4, 4);
                        g.drawRect(data.end.x - 2, data.end.y - 2, 4, 4);
                    }
                }
            }
            switch (data.type) {
                case 0 -> g.drawRect(x, y, width, height);
                case 1 -> g.drawLine(data.start.x, data.start.y, data.end.x, data.end.y);
                case 2 -> g.drawOval(x, y, width, height);
                default -> {
                    System.out.println("Unknown Type!");
                }
            }
        }
    }

    public void saveDrawData(Point start, Point end, int type) {
        DrawData.add(0, new DrawStruct(start, end, type));
    }

    public int selectShape(MouseEvent e) {
        clearSelect();
        for (var data : DrawData) {
            if (data.isContain(e.getPoint())) {
                data.setFlag(true);
                System.out.println("tagged");
                return DrawData.indexOf(data);
            }
        }
        return -1;
    }

    public void clearSelect() {
        for (var data : DrawData) {
            data.setFlag(false);
        }
    }

    public boolean isShapeSelected(Point p, int index) {
        if (index == -1) return false;
        return DrawData.get(index).isContain(p);
    }

    public boolean isShapeControlSelected(Point p, int index) {
        if (index == -1) return false;
        return DrawData.get(index).isControlPoint(p) != DrawStruct.TRI.NULL;
    }

    public DrawStruct.TRI getShapeControl(Point p, int index) {
        if (index == -1) return DrawStruct.TRI.NULL;
        return DrawData.get(index).isControlPoint(p);
    }

    public void copyShape(int i) {
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
            DrawData.add(0, new DrawStruct(tempS, tempE, DrawData.get(i).type));
            return;
        }
    }

    public void deleteShape() {
        Iterator<DrawStruct> iter = DrawData.iterator();
        while (iter.hasNext()) if (iter.next().isFlag()) iter.remove();
        // DrawData.removeIf(DrawStruct::isFlag);
    }

    public void deleteShape(int index) {
        DrawData.remove(index);
    }

    public void moveSelectedShape(Point p, Point start, Point end, int index) {
        if (end == null) return;
        else if (isShapeSelected(p, index))
            DrawData.get(index).moveShape(new Point(end.x - start.x, end.y - start.y));
    }

    public void resizeSelectedShape(Point p, int index) {
        switch (DrawData.get(index).isControlPoint(p)) {
            case START -> {
                DrawData.get(index).start = p;
            }
            case END -> {
                DrawData.get(index).end = p;
            }
        }
    }

    public void resizeSelectedShape(Point p, int index, DrawStruct.TRI mode) {
        switch (mode) {
            case START -> {
                DrawData.get(index).start = p;
            }
            case END -> {
                DrawData.get(index).end = p;
            }
        }
    }

    public void printAllShape() {   //디버그용 콘솔출력
        for (var data : DrawData) System.out.println(data);
    }

    public void saveObject() {
        // TODO 도형 저장 메소드
    }

    public void loadObject() {
        // TODO 도형 불러오기 메소드
    }
}
