package org.iuscsg.autotyper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends Frame implements ActionListener
{
    private TextField txt1 = new TextField();

    public Application() {
        GridLayout layout = new GridLayout(2,1);
        Button btn1 = new Button();
        btn1.setLabel("Paste");
        btn1.addActionListener(this);
        add(txt1);
        add(btn1);
        setTitle("Auto Paster");
        setSize(300,300);//frame size 300 width and 300 height
        setLayout(layout);//no layout manager
        setVisible(true);//now frame will be visible, by default not visible
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we)
            {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
       new  Application();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Thread t  = new Thread(new Paster(txt1.getText()));
        t.start();
    }
}
