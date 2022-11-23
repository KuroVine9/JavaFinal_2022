package finalexam;

import javax.swing.*;
import java.awt.*;

public class FigureEditor extends JFrame {
    public FigureEditor() {
        PanelA pa;
        setTitle("FigureEditor.java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        pa = new PanelA();
        add(pa, BorderLayout.CENTER);
        add(new PanelC(pa), BorderLayout.LINE_START);

        setVisible(true);
    }

    public static void main(String[] args) {
        new FigureEditor();
    }
}
