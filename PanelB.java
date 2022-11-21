package finalexam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class PanelB extends JPanel {
    private JButton[] buttonArr;
    private PanelA pa;
    final private HashMap<String, Integer> drawModeMap = new HashMap<>() {{
        put("사각", 0);
        put("직선", 1);
        put("타원", 2);
        put("복사", 3);
        put("삭제", 4);
        put("저장", 5);
        put("불러오기", 6);
        put("출력", 7);  //TODO 디버그 완료 후 삭제할것
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
