package org.iuscsg.autotyper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;

// changing the package or the name of this class will require updating the isRunningInJar method in this class.
public class Application extends Frame
{
    private TextField txt = makeTextField();
    private HistoryManager historyManager = new HistoryManager();

    private Application() {
        if(shouldRedirectLogging()) // logging for if anyone runs into issues.
            turnOnLogging();
        setIcon();
        Button btn = makeButton();
        historyManager.init(txt, btn);
        add(txt);
        add(btn);
        setTitle("Auto Typer");
        setSize(300,300);
        setLayout(new GridLayout(2,1));
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
       new  Application();
    }

    private Button makeButton() {
        Button btn = new Button();
        btn.setLabel("Type it!");
        btn.addActionListener(e ->typeWithDelay(txt.getText()));
        return themeControl(btn, 18);
    }

    private <T extends Component> T themeControl(T component, int fontSize) {
        component.setForeground(Color.WHITE);
        component.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
        component.setBackground(Color.decode("#263238"));
        return component;
    }

    private TextField makeTextField() {
        TextField txt = new TextField();
        return themeControl(txt, 12);
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
            PrintStream out = new PrintStream(new FileOutputStream("AutoTyper.log"));
            System.setOut(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
