/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayseeofficial.myutil.Objects;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author jon
 */
public class AudioInputStreamPlayer {

    private final AudioInputStream in;
    private final AudioFormat format;
    SourceDataLine sourceDataLine;

    public AudioInputStreamPlayer(AudioInputStream in, AudioFormat format) {
        this.in = in;
        this.format = format;
    }

    public void play() throws LineUnavailableException {
        DataLine.Info dataLineInfo
                = new DataLine.Info(
                        SourceDataLine.class,
                        format);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(
                dataLineInfo);
        sourceDataLine.open(format);
        sourceDataLine.start();

        new PlayThread(in).start();
    }

    private class PlayThread extends Thread {

        byte tempBuffer[] = new byte[10000];

        AudioInputStream audioInputStream;

        public PlayThread(AudioInputStream audioInputStream) {
            this.audioInputStream = audioInputStream;
        }

        @Override
        public void run() {
            try {
                int cnt;
                //Keep looping until the input
                // read method returns -1 for
                // empty stream.
                while ((cnt = audioInputStream.
                        read(tempBuffer, 0,
                                tempBuffer.length)) != -1) {
                    if (cnt > 0) {
                        //Write data to the internal
                        // buffer of the data line
                        // where it will be delivered
                        // to the speaker.
                        sourceDataLine.write(
                                tempBuffer, 0, cnt);
                    }//end if
                }//end while
                //Block and wait for internal
                // buffer of the data line to
                // empty.
                sourceDataLine.drain();
                sourceDataLine.close();
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            }//end catch
        }//end run
    }//end inner class PlayThread

}
