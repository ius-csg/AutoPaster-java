package org.iuscsg.autotyper.hotkey;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HotKeyListener implements KeyListener
{
    private boolean modifierKey = false;
    private boolean backDown = false;
    private boolean forwardDown = false;

    private OnHistoryChangeListener onForwardListener;
    private OnHistoryChangeListener onBackListener;

    public HotKeyListener(OnHistoryChangeListener onForwardListener, OnHistoryChangeListener onBackListener) {
        this.onForwardListener = onForwardListener;
        this.onBackListener = onBackListener;
    }

    @Override
    public void keyTyped(KeyEvent e) { /* NOT NEEDED */}

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_CONTROL: modifierKey = true; break;
            case KeyEvent.VK_ALT: modifierKey = true; break;
            case KeyEvent.VK_LEFT: backDown = true; break;
            case KeyEvent.VK_RIGHT: forwardDown = true; break;
        }
        executeListenersIfHotKeysPressed();
    }

    private void executeListenersIfHotKeysPressed() {
        if (backDown && modifierKey) {
            onBackListener.onChange();
        } else if(forwardDown && modifierKey) {
            onForwardListener.onChange();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_CONTROL: modifierKey = false; break;
            case KeyEvent.VK_ALT: modifierKey = false; break;
            case KeyEvent.VK_LEFT: backDown = false; break;
            case KeyEvent.VK_RIGHT: forwardDown = false; break;
        }
    }
}
