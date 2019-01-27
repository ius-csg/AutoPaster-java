package org.iuscsg.autotyper.hotkey;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HotKeyListener implements KeyListener
{
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
            case KeyEvent.VK_UP: backDown = true; break;
            case KeyEvent.VK_DOWN: forwardDown = true; break;
        }
        executeListenersIfHotKeysPressed();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP: backDown = false; break;
            case KeyEvent.VK_DOWN: forwardDown = false; break;
        }
    }


    private void executeListenersIfHotKeysPressed() {
        if (backDown) {
            onBackListener.onChange();
        } else if(forwardDown) {
            onForwardListener.onChange();
        }
    }
}
