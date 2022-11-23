package finalexam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelA extends JPanel {
    int drawMode, prevmode, selectedShape;  // drawMode: -1->도형 이동모드 -2->도형 크기조절, selectedShape: 선택된 도형의 인덱스
    DrawStruct.TRI controlpoint;    // 현재 위치가 어느 쪽 컨트롤포인트인지 저장
    Point start, end, xy_pointer, wh_size;
    DrawDataHandler dataHandler;

    public PanelA() {
        drawMode = 0;
        prevmode = 0;
        selectedShape = -1;
        start = null;
        end = null;
        controlpoint = DrawStruct.TRI.NULL;
        xy_pointer = new Point();
        wh_size = new Point();
        dataHandler = new DrawDataHandler();
        setBackground(Color.YELLOW);
        MyMouseListener listener = new MyMouseListener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        dataHandler.drawSavedData(g);

        if (start != null && end != null && start != end && selectedShape == -1) {
            xy_pointer.x = Math.min(start.x, end.x);
            xy_pointer.y = Math.min(start.y, end.y);
            wh_size.x = Math.abs(start.x - end.x);
            wh_size.y = Math.abs(start.y - end.y);

            switch (drawMode) {
                case 0 -> g.drawRect(xy_pointer.x, xy_pointer.y, wh_size.x, wh_size.y);
                case 1 -> g.drawLine(start.x, start.y, end.x, end.y);
                case 2 -> g.drawOval(xy_pointer.x, xy_pointer.y, wh_size.x, wh_size.y);
                default -> {
                    System.out.println("Unknown Button Input!");
                }
            }
        }
    }

    class MyMouseListener extends MouseAdapter {

        public void mousePressed(MouseEvent e) {
            start = e.getPoint();
            end = start;
            if (dataHandler.isShapeSelected(e.getPoint(), selectedShape)) {
                prevmode = drawMode;
                drawMode = -1;
            }   // 도형이 선택되었다면 현재 모드 저장 후 -1로 변경
            else if (dataHandler.isShapeControlSelected(e.getPoint(), selectedShape)) {
                prevmode = drawMode;
                drawMode = -2;
                controlpoint = dataHandler.getShapeControl(e.getPoint(), selectedShape);
            }   // 모드 변경 후 어느 점을 선택하였는지 저장

            switch (drawMode) {
                case 0, 1, 2 -> selectedShape = -1; // 그리기 모드면 도형 선택 해제
                case 3, 4 -> selectedShape = dataHandler.selectShape(e.getPoint());    // 복사, 삭제 모드라면 도형 선택
            }
            System.out.println("press" + drawMode + ": " + selectedShape);  // TODO 디버그용 콘솔 출력
        }

        public void mouseDragged(MouseEvent e) {
            switch (drawMode) {
                case 0, 1, 2 -> {
                    end = e.getPoint();
                    selectedShape = -1;
                    dataHandler.clearSelect();
                }   // 도형 그리기
                case -1 -> {
                    end = e.getPoint();
                    dataHandler.moveSelectedShape(start, end, selectedShape);
                    start = end;
                }   // 이동
                case -2 -> {
                    dataHandler.resizeSelectedShape(e.getPoint(), selectedShape, controlpoint);
                }   // 크기조정
            }
            repaint();  // 계속 마우스에 의한 변화 저장 후 리페인트
        }

        public void mouseReleased(MouseEvent e) {
            switch (drawMode) {
                case 0, 1, 2 -> {
                    if (Math.abs(start.x - end.x) < 5 && Math.abs(start.y - end.y) < 5) {
                        selectedShape = dataHandler.selectShape(e.getPoint());
                        System.out.println("sel?"); // TODO 디버그용 콘솔 출력
                    }   // 마우스가 거의 움직이지 않았다면 그린 결과물 무시하고 도형 클릭 시도로 간주
                    else dataHandler.saveDrawData(start, end, drawMode);    // 도형 저장
                }
                case 3 -> {
                    if (selectedShape == -1) break; // 선택되지 않았다면 스킵
                    dataHandler.copyShape(selectedShape);
                    selectedShape = -1;
                }   // 도형 복사 후 선택 해제
                case 4 -> {
                    if (selectedShape == -1) break; // 스킵
                    dataHandler.deleteShape(selectedShape);
                    selectedShape = -1;
                }   // 삭제후 선택해제
                case -1 -> {
                    if (!dataHandler.isShapeSelected(e.getPoint(), selectedShape)) {
                        dataHandler.clearSelect();
                        selectedShape = -1;
                        drawMode = prevmode;
                    }   // 선택 도형이 없다면 선택 모드를 해제하고 원래 모드로 되돌림
                }
            }
            end = null;
            repaint();
        }

        public void mouseClicked(MouseEvent e) {
            System.out.println(drawMode);
            if (drawMode == -1 || drawMode == -2) {
                selectedShape = dataHandler.selectShape(e.getPoint());
                System.out.println("sel?"); // TODO 디버그용 콘솔 출력
                drawMode = prevmode;
            }   // 도형 재선택

            if (drawMode == 3 && selectedShape != -1) {
                dataHandler.copyShape(selectedShape);
                selectedShape = -1;
            }
            else if (drawMode == 4 && selectedShape != -1) {
                dataHandler.deleteShape(selectedShape);
                selectedShape = -1;
            }
            repaint();
            System.out.println("clicked" + drawMode + ": " + selectedShape);
        }
    }
}

