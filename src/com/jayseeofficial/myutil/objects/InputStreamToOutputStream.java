package com.jayseeofficial.myutil.objects;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Continually reads an InputStream and writes it to an OutputStream
 *
 * @author jon
 */
public class InputStreamToOutputStream {

    private final InputStream in;
    private final OutputStream out;
    private WriteThread wt = null;
    private boolean running;

    InputStreamToOutputStream(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void start() {
        running = true;
        if (wt == null) {
            wt = new WriteThread();
            wt.start();
        }
    }

    /**
     * @return the InputStream
     */
    public InputStream getInputStream() {
        return in;
    }

    /**
     * @return the OutputStream
     */
    public OutputStream getOutputStream() {
        return out;
    }

    private class WriteThread extends Thread {

        @Override
        public void run() {
            while (running) {
                try {
                    out.write(in.read());
                } catch (IOException ex) {
                }
            }
            wt = null;
        }
    }

}
