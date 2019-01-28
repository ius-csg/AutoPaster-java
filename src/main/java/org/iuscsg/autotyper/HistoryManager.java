package org.iuscsg.autotyper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

class HistoryManager
{
    private ArrayList<String> history = new ArrayList<>(Collections.singletonList(""));
    private boolean ignoreTextChange = false;
    private int historyIndex = 0;
    private TextArea txt;

    void init(TextArea txt) {
        this.txt = txt;

        txt.addTextListener(e -> {
            if(ignoreTextChange) {
                ignoreTextChange = false;
                return;
            }
            historyIndex = -1;
        });
    }

    void addToHistory(String text) {
        if(history.get(0).equals(text))
            return;
        history.add(0, text);
        historyIndex = 0;
    }

    public void goBack() {
        if(history.size() > historyIndex + 1) {
            if(historyIndex == -1) {
                history.add(0, txt.getText());
                historyIndex++;
            }
            historyIndex++;
            ignoreTextChange = true;
            txt.setText(history.get(historyIndex));
        }
    }

    public void goForward()
    {
        if (historyIndex > 0) {
            historyIndex--;
            ignoreTextChange = true;
            txt.setText(history.get(historyIndex));
        }
    }


}
