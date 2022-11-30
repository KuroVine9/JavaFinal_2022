package finalexam;

import java.awt.*;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

public class DrawDataHandler {
    private LinkedList<DrawStruct> DrawData;
    final private String path = "draw.dat";

    public DrawDataHandler() {
        DrawData = new LinkedList<>();
    }

    public void drawSavedData(Graphics g) {
        for (var data : DrawData) {
            if (data.isFlag()) data.drawSelectPoint(g); // 선택된 도형은 컨트롤 포인트도 같이 그림
            data.drawShape(g);  // 도형 그리기 메소드
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
        DrawData.add(0, d); // 업캐스팅하여 저장
    }

    public void saveDrawData(DrawStruct d) {
        DrawData.add(0, d);
    }

    public int selectShape(Point p) {
        clearSelect();  // 모든 도형 선택해제
        for (var data : DrawData) {
            if (data.isContain(p)) {
                data.setFlag(true);
                return DrawData.indexOf(data);
            }   // 마우스가 도형 안에 있다면 그 도형 인덱스 리턴
        }
        return -1;  // 없다면 -1 리턴
    }

    public void clearSelect() {
        for (var data : DrawData) data.setFlag(false);  // 모든 도형 선택해제
    }

    public boolean isShapeSelected(Point p, int index) {
        if (index == -1) return false;  // 받은 인덱스가 -1이라면 false 리턴
        return DrawData.get(index).isContain(p);    // 인덱스에 해당하는 도형 안에 마우스가 있는지 리턴
    }

    public boolean isShapeControlSelected(Point p, int index) {
        if (index == -1) return false;
        return DrawData.get(index).isControlPoint(p) != DrawStruct.TRI.NULL;
    }

    public DrawStruct.TRI getShapeControl(Point p, int index) {
        if (index == -1) return DrawStruct.TRI.NULL;
        return DrawData.get(index).isControlPoint(p);   // enum으로 마우스가 start인지 end인지 리턴
    }

    public void copyShape() {
        for (int i = 0; i < DrawData.size(); i++) {
            if (DrawData.get(i).isFlag()) copyShape(i);
        }
    }   //모든 선택된 도형 복사

    public void copyShape(int i) {
        DrawData.get(i).setFlag(false); // 선택 해제
        DrawData.add(0, DrawData.get(i).makeCopiedObj());
    }   // 인덱스로 받은 도형만 복사

    public void deleteShape() {
        Iterator<DrawStruct> iter = DrawData.iterator();
        while (iter.hasNext()) if (iter.next().isFlag()) iter.remove();
        // DrawData.removeIf(DrawStruct::isFlag);
    }   // 선택된 도형 모두 삭제

    public void deleteShape(int index) {
        DrawData.remove(index);
    }   // 인덱스로 받은 도형 삭제

    public void moveSelectedShape(Point start, Point end, int index) {
        if (isShapeSelected(end, index))
            DrawData.get(index).moveShape(new Point(end.x - start.x, end.y - start.y));
    }   // 도형 이동

    public void moveSelectedShape(int x, int y, int index) {
        DrawData.get(index).moveShape(new Point(x, y));
    }

    public void resizeSelectedShape(Point p, int index, DrawStruct.TRI mode) {
        DrawData.get(index).resizeShape(p, mode);
    }   // 도형 크기조절

    public void printAllShape() {
        for (var data : DrawData) System.out.println(data);
    }   // 디버그용 콘솔출력

    public void saveObject() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(DrawData); // DrawData 저장
            out.close();
            System.out.println("Saved");
        } catch (FileNotFoundException f) {   //예외처리
            System.out.println("No File");
        } catch (IOException ioe) {   //예외처리
            System.out.println("Error Occur");
        }
    }

    public void loadObject() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(path));
            DrawData = (LinkedList<DrawStruct>) in.readObject(); //Data에 저장
            clearSelect();
            in.close();
        } catch (ClassNotFoundException e) {   //예외처리
            System.out.println("No File");
        } catch (IOException ioe) {   //예외처리
            System.out.println("Error Occur");
        }
    }
}
