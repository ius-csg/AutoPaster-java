package org.iuscsg.autotyper;

import org.iuscsg.autotyper.hotkey.HotKeyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;

// changing the package or the name of this class will require updating the isRunningInJar method in this class.
public class Application extends Frame
{
    private TextArea txt = themeControl(new TextArea("", 0, 0, TextArea.SCROLLBARS_NONE), 12);
    private HistoryManager historyManager = new HistoryManager();
    private Button btn = makeButton();
    private HotKeyListener keyListener = makeHotKeyListener();
    private Application() {
        if(shouldRedirectLogging()) // logging for if anyone runs into issues.
            turnOnLogging();
        setAlwaysOnTop(true);
        setIcon();
        Checkbox checkbox = themeControl(new Checkbox(), 12);
        checkbox.setLabel("Always on Top");
        historyManager.init(txt);
        btn.addKeyListener(keyListener);
        txt.addKeyListener(keyListener);


        setTitle("Auto Typer");
        setSize(300,300);
        GridLayout grid = new GridLayout(1,1);
        grid.setHgap(20);
        Panel panel = themeControl(new Panel(), 12);
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);
        panel.add(checkbox);
        panel.add(txt);
        panel.add(btn);

        setBackground(Color.decode("#263238"));
        setLayout(grid);
        add(panel);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                keyListener.onFrameDeactivated();
            }
        });
    }

    public static void main(String[] args) {
       new  Application();
    }

    private Button makeButton() {
        Button btn = new Button();
        btn.setLabel("Type it!");
        btn.addActionListener(e -> onBtn_Click());
        return themeControl(btn, 18);
    }

    private <T extends Component> T themeControl(T component, int fontSize) {
        component.setForeground(Color.WHITE);
        component.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        component.setBackground(Color.decode("#263238"));
        return component;
    }

    private void setIcon() {
        try {
            InputStream letter = this.getClass().getClassLoader().getResourceAsStream("resources/letter.png");
            if(letter == null) {
                System.out.println("Icon loaded as null");
                return;
            }
            BufferedImage img = ImageIO.read(letter);
            setIconImage(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onBtn_Click() {
        if(keyListener.getModifierKeyState().isKeyDown()) {
            String clipboard = getClipboard();
            if(clipboard != null && clipboard.length() > 0) {
                typeWithDelay(clipboard);
                return;
            }
        }
        typeWithDelay(txt.getText());
    }

    private void typeWithDelay(String text) {
        (new Thread(new Typer(text))).start();
        historyManager.addToHistory(text);
    }

    private boolean shouldRedirectLogging() {
        if(System.console() != null) // if ran from the commandline, then output will go to stdout.
            return true;
       return isRunningInJar();
    }

    private boolean isRunningInJar() {
        return Application.class.getResource("Application.class").toString().contains(".jar");
    }

    private void turnOnLogging() {
        try {
            PrintStream out = new PrintStream(new FileOutputStream("AutoTyper.log", true));
            System.setOut(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getClipboard() {
        try {
            return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HotKeyListener makeHotKeyListener() {
        return new HotKeyListener(
                (boolean down) -> { if(down) historyManager.goForward(); },
                (boolean down) -> { if(down) historyManager.goBack(); },
                (boolean down) -> {
                    if(down) btn.setLabel("Type from clipboard!");
                    else btn.setLabel("Type It!");
                },
                (down) -> { if(txt.hasFocus()) {
                    txt.selectAll();
                } }
                );
    }

}
