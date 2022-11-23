package finalexam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

public class PanelB extends JPanel {
    private JButton[] buttonArr;
    private PanelA pa;

    final private LinkedHashMap<String, Integer> drawModeMap = new LinkedHashMap<>() {{
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

        for (var keymap : drawModeMap.entrySet()) {
            buttonArr[keymap.getValue()] = new JButton(keymap.getKey());
            add(buttonArr[keymap.getValue()]);
            buttonArr[keymap.getValue()].addActionListener(listener);
        }
    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (drawModeMap.get(e.getActionCommand())) {
                case 5:
                    pa.dataHandler.saveObject();
                    return;
                case 6:
                    pa.dataHandler.loadObject();
                    return;
                case 7:
                    pa.dataHandler.printAllShape(); // TODO 디버그용 case문
                    return;
            }
            pa.drawMode = drawModeMap.get(e.getActionCommand());
        }
    }
}
