package org.iuscsg.autotyper.hotkey;

import java.awt.event.KeyEvent;

public class KeyState
{
    private boolean keyDown = false;
    private OnKeyChange onKeyChangeListener;
    private int keycode;

    public KeyState(OnKeyChange onKeyChangeListener, int keycode) {
        this.onKeyChangeListener = onKeyChangeListener;
        this.keycode = keycode;
    }

    public void handleKeyPressed(KeyEvent e) {
        if(e.getKeyCode() == keycode && !keyDown) {
            keyDown = true;
            onKeyChangeListener.onChange(true);
        }
    }
    public void handleKeyReleased(KeyEvent e) {
        if(e.getKeyCode() == keycode && keyDown) {
            keyDown = false;
            onKeyChangeListener.onChange(false);
        }
    }
    public boolean isKeyDown() {
        return this.keyDown;
    }
    public void handleFrameDeactivation() {
        keyDown = false;
    }
}
