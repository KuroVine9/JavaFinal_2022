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
            if (data.isFlag()) {
                data.drawSelectPoint(g);
            }
            data.drawShape(g);
        }
    }

    public void saveDrawData(Point start, Point end, int type) {
        DrawStruct d = null;
        switch (type) {
            case 0 -> d = new Rectangle(start, end);
            case 1 -> d = new Line(start, end);
            case 2 -> d = new Tawon(start, end);
            default -> {
                System.out.println("Cannot Save Not-Dfnd Shape");
                System.exit(1);
            }
        }
        DrawData.add(0, d);
    }

    public void saveDrawData(DrawStruct d) {
        DrawData.add(0, d);
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

    public void copyShape() {
        for (int i = 0; i < DrawData.size(); i++) {
            if (DrawData.get(i).isFlag()) copyShape(i);
        }
    }

    public void copyShape(int i) {
        DrawData.get(i).setFlag(false);
        DrawData.add(0, DrawData.get(i).makeCopiedObj());
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
        if (isShapeSelected(p, index))
            DrawData.get(index).moveShape(new Point(end.x - start.x, end.y - start.y));
    }

    public void resizeSelectedShape(Point p, int index, DrawStruct.TRI mode) {
        DrawData.get(index).resizeShape(p, mode);
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
