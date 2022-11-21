package finalexam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelA extends JPanel {
    int drawMode, prevmode, selectedShape;
    DrawStruct.TRI controlpoint;
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
            }
            else if (dataHandler.isShapeControlSelected(e.getPoint(), selectedShape)) {
                prevmode = drawMode;
                drawMode = -2;
                controlpoint = dataHandler.getShapeControl(e.getPoint(), selectedShape);
            }

            switch (drawMode) {
                case 0, 1, 2 -> selectedShape = -1;
                case 3, 4 -> selectedShape = dataHandler.selectShape(e);//복사, 삭제
            }
            System.out.println("press" + drawMode + ": " + selectedShape);
        }

        public void mouseDragged(MouseEvent e) {
            switch (drawMode) {
                case 0, 1, 2 -> {
                    end = e.getPoint();
                    selectedShape = -1;
                    dataHandler.clearSelect();
                }
                case 3, 4 -> {
                }
                case -1 -> {
                    end = e.getPoint();
                    dataHandler.moveSelectedShape(e.getPoint(), start, end, selectedShape);
                    start = end;
                }//이동
                case -2 -> {
                    dataHandler.resizeSelectedShape(e.getPoint(), selectedShape, controlpoint);
                }//크기조정
            }
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            switch (drawMode) {
                case 0, 1, 2 -> {
                    if (Math.abs(start.x - end.x) < 5 && Math.abs(start.y - end.y) < 5) {
                        selectedShape = dataHandler.selectShape(e);
                        System.out.println("sel?");
                    }
                    else dataHandler.saveDrawData(start, end, drawMode);
                }
                case 3 -> {
                    if (selectedShape == -1) break;
                    dataHandler.copyShape(selectedShape);
                    selectedShape = -1;
                }
                case 4 -> {
                    if (selectedShape == -1) break;
                    dataHandler.deleteShape(selectedShape);
                    selectedShape = -1;
                }
                case -1 -> {
                    if (!dataHandler.isShapeSelected(e.getPoint(), selectedShape)) {
                        dataHandler.clearSelect();
                        selectedShape = -1;
                        drawMode = prevmode;
                    }
                }
            }
            end = null;
            repaint();
        }

        public void mouseClicked(MouseEvent e) {
            switch (drawMode) {
                case -1, -2 -> {
                    selectedShape = dataHandler.selectShape(e);
                    System.out.println("sel?");
                    drawMode = prevmode;
                }
                case 7 -> dataHandler.printAllShape(); //TODO 디버그 완료 후 삭제할것
            }
            repaint();
            System.out.println("clicked" + drawMode + ": " + selectedShape);
        }
    }
}

