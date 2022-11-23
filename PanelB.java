package finalexam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;

public class PanelB extends JPanel {
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
    }}; // 버튼 목록

    public PanelB(PanelA pa) {
        this.pa = pa;
        setBackground(Color.BLUE);
        setLayout(new GridLayout(drawModeMap.size(), 1, 5, 5)); // 버튼 목록 사이즈에 따라 가변 조절

        JButton[] buttonArr = new JButton[drawModeMap.size()];
        ActionListener listener = new MyActionListener();

        for (var keymap : drawModeMap.entrySet()) {
            buttonArr[keymap.getValue()] = new JButton(keymap.getKey());
            add(buttonArr[keymap.getValue()]);
            buttonArr[keymap.getValue()].addActionListener(listener);
        }   // 목록 read 후 리스너 추가
    }

    class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (drawModeMap.get(e.getActionCommand())) {
                case 5:
                    pa.dataHandler.saveObject();    // 저장 메소드 호출
                    return;
                case 6:
                    pa.dataHandler.loadObject();    // 불러오기 메소드 호출
                    return;
                case 7:
                    pa.dataHandler.printAllShape(); // TODO 디버그용 case문
                    return;
            }
            pa.drawMode = drawModeMap.get(e.getActionCommand());    // PanelA에 버튼 입력값 전송
        }
    }
}
