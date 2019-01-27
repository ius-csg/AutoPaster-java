package org.iuscsg.autotyper;

import org.iuscsg.autotyper.hotkey.HotKeyListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

class HistoryManager
{
    private ArrayList<String> history = new ArrayList<>(Collections.singletonList(""));
    private boolean ignoreTextChange = false;
    private int historyIndex = 0;
    private TextField txt;

    void init(TextField txt, Button btn) {
        this.txt = txt;
        HotKeyListener hotkeyListener = initHotKeyListener();
        txt.addTextListener(e -> {
            if(ignoreTextChange) {
                ignoreTextChange = false;
                return;
            }
            historyIndex = -1;
        });

        txt.addKeyListener(hotkeyListener);
        btn.addKeyListener(hotkeyListener);

    }

    void addToHistory(String text) {
        if(history.get(0).equals(text))
            return;
        history.add(0, text);
        historyIndex = 0;
    }

    private HotKeyListener initHotKeyListener() {
        return new HotKeyListener(() -> {
            if(historyIndex > 0){
                historyIndex--;
                ignoreTextChange = true;
                txt.setText(history.get(historyIndex));
            }
        }, () -> {
            if(history.size() > historyIndex + 1) {
                if(historyIndex == -1) {
                    history.add(0, txt.getText());
                    historyIndex++;
                }
                historyIndex++;
                ignoreTextChange = true;
                txt.setText(history.get(historyIndex));
            }
        });
    }
}
