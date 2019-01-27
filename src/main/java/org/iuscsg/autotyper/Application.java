package org.iuscsg.autotyper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;

// changing the package or the name of this class will require updating the isRunningInJar method.
public class Application extends Frame implements ActionListener
{
    private TextField txt = makeTextField();

    public Application() {
        System.out.println(System.console());
        if(shouldRedirectLogging()) // logging for if anyone runs into issues.
            turnOnLogging();
        setIcon();
        add(txt);
        add(makeButton());
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
        btn.setFont(new Font("Helvetica", Font.PLAIN, 18));
        btn.addActionListener(this);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.decode("#263238"));
        return btn;
    }

    private TextField makeTextField() {
        TextField txt = new TextField();
        txt.setForeground(Color.WHITE);
        txt.setFont(new Font("Helvetica", Font.PLAIN, 12));
        txt.setBackground(Color.decode("#263238"));
        return txt;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread t  = new Thread(new Typer(txt.getText()));
        t.start();
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
