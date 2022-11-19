package finalexam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class PanelB extends JPanel {
    private JButton[] buttonArr;
    private PanelA pa;
    final private HashMap<String, Byte> drawModeMap = new HashMap<>() {{
        put("사각", (byte) 0);
        put("직선", (byte) 1);
        put("타원", (byte) 2);
        put("복사", (byte) 3);
        put("삭제", (byte) 4);
        put("저장", (byte) 5);
        put("불러오기", (byte) 6);
    }};

    public PanelB(PanelA pa) {
        this.pa = pa;
        setBackground(Color.BLUE);
        setLayout(new GridLayout(drawModeMap.size(), 1, 5, 5));

        buttonArr = new JButton[drawModeMap.size()];
        ActionListener listener = new MyActionListener();
        for (int i = 0; i < drawModeMap.size(); i++) {
            for (var keymap : drawModeMap.entrySet()) {
                if (keymap.getValue() == i) {
                    buttonArr[i] = new JButton(keymap.getKey());
                    break;
                }
            }
            add(buttonArr[i]);
            buttonArr[i].addActionListener(listener);
        }
    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            pa.drawMode = drawModeMap.get(e.getActionCommand());
        }
    }
}
