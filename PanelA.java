package finalexam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelA extends JPanel {
    byte drawMode;
    Point start, end, xy_pointer, wh_size;
    DrawDataHandler dataHandler;

    public PanelA() {
        drawMode = 0;
        start = null;
        end = null;
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

        if (start != null && end != null && start != end) {
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
        }

        public void mouseDragged(MouseEvent e) {
            end = e.getPoint();
            if (dataHandler.moveSelectedShape(e, start, end)) start = e.getPoint();
            repaint();
        }

        public void mouseReleased(MouseEvent e) {
            if (end != null) {
                dataHandler.saveDrawData(start, end, drawMode);
                //dataHandler.clearSelect();
                repaint();
            }
            end = null;
        }

        public void mouseClicked(MouseEvent e) {
            if (dataHandler.selectShape(e)) {
                switch (drawMode) {
                    case 3 -> dataHandler.copyShape();
                    case 4 -> dataHandler.deleteShape();
                    //TODO
                }
            }
            repaint();
        }
    }
}

