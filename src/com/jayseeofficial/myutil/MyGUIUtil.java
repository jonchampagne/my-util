package com.jayseeofficial.myutil;


import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MyGUIUtil {

    public static void setNativeLookAndFeel()
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        String os = System.getProperty("os.name");
        if (os.startsWith("Linux")) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } else {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
    }
}
