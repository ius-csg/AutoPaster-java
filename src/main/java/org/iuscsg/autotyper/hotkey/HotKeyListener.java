package org.iuscsg.autotyper.hotkey;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HotKeyListener implements KeyListener
{

    private KeyState selectAllKeyState;
    private KeyState onModifierKeyState;
    private KeyState onBackKeyState;
    private KeyState onForwardKeyState;

    public HotKeyListener(OnKeyChange onForwardListener,
                          OnKeyChange onBackListener,
                          OnKeyChange onModifierListener,
                          OnKeyChange onSelectAllListener) {
        this.selectAllKeyState = new KeyState(down -> {
            if(this.onModifierKeyState.isKeyDown() && down) onSelectAllListener.onChange(true);
        }, KeyEvent.VK_A);
        this.onModifierKeyState = new KeyState(onModifierListener, KeyEvent.VK_CONTROL);
        this.onBackKeyState = new KeyState(onBackListener, KeyEvent.VK_UP);
        this.onForwardKeyState = new KeyState(onForwardListener, KeyEvent.VK_DOWN);
    }

    public KeyState getModifierKeyState() {
        return this.onModifierKeyState;
    }

    @Override
    public void keyTyped(KeyEvent e) { /* NOT NEEDED */}

    @Override
    public void keyPressed(KeyEvent e)
    {
        selectAllKeyState.handleKeyPressed(e);
        onModifierKeyState.handleKeyPressed(e);
        onBackKeyState.handleKeyPressed(e);
        onForwardKeyState.handleKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        selectAllKeyState.handleKeyReleased(e);
        onModifierKeyState.handleKeyReleased(e);
        onBackKeyState.handleKeyReleased(e);
        onForwardKeyState.handleKeyReleased(e);
    }

    public void onFrameDeactivated() {
        selectAllKeyState.handleFrameDeactivation();
        onModifierKeyState.handleFrameDeactivation();
        onBackKeyState.handleFrameDeactivation();
        onForwardKeyState.handleFrameDeactivation();
    }

}
