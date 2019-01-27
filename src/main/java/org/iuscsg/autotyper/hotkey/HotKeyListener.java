package org.iuscsg.autotyper.hotkey;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HotKeyListener implements KeyListener
{
    private boolean backKeyDown = false;
    private boolean forwardKeyDown = false;
    private boolean modifierKeyDown = false;
    private OnKeyChange onForwardListener;
    private OnKeyChange onBackListener;
    private OnKeyChange onModifierListener;

    public HotKeyListener(OnKeyChange onForwardListener, OnKeyChange onBackListener, OnKeyChange onModifierListener) {
        this.onForwardListener = onForwardListener;
        this.onBackListener = onBackListener;
        this.onModifierListener = onModifierListener;
    }

    public boolean IsModifierKeDown() {
        return this.modifierKeyDown;
    }

    @Override
    public void keyTyped(KeyEvent e) { /* NOT NEEDED */}

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP: backKeyDown = true; break;
            case KeyEvent.VK_DOWN: forwardKeyDown = true; break;
            case KeyEvent.VK_CONTROL: modifierKeyDown = true; break;
        }
        executeListenersOnDown();
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP: backKeyDown = false; break;
            case KeyEvent.VK_DOWN: forwardKeyDown = false; break;
            case KeyEvent.VK_CONTROL: modifierKeyDown = false; break;
        }
        executeListenersOnUp();
    }


    private void executeListenersOnDown() {
        if (backKeyDown) {
            onBackListener.onChange(true);
        } else if(forwardKeyDown) {
            onForwardListener.onChange(true);
        }
        else if(modifierKeyDown){
            onModifierListener.onChange(true);
        }
    }


    private void executeListenersOnUp() {
        if (!backKeyDown)
            onBackListener.onChange(false);
        if(!forwardKeyDown)
            onForwardListener.onChange(false);
        if(!modifierKeyDown)
            onModifierListener.onChange(false);
    }
}
