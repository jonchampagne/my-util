/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jayseeofficial.myutil.Objects;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author jon
 */
public class AudioCapturer {

    boolean capturing = false;
    ByteArrayOutputStream byteArrayOutputStream;
    AudioFormat audioFormat;
    TargetDataLine targetDataLine;
    AudioInputStream audioInputStream=null;
    SourceDataLine sourceDataLine;

    public AudioCapturer() {

    }

    public void startCapture() throws LineUnavailableException {
        capturing = true;

        //Get everything set up for
        // capture
        audioFormat = getAudioFormat();
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
        targetDataLine.open(audioFormat);
        targetDataLine.start();

        Thread t=new Thread(new CaptureThread());
        t.start();
        
    }

    public void stopCapture() {
        capturing = false;
    }

    public boolean isCapturing() {
        return capturing;
    }

    public AudioFormat getAudioFormat() {
        float sampleRate = 8000.0F;
        //8000,11025,16000,22050,44100
        int sampleSizeInBits = 16;
        //8,16
        int channels = 1;
        //1,2
        boolean signed = true;
        //true,false
        boolean bigEndian = false;
        //true,false
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }
    
    public AudioInputStream getAudioInputStream(){
        //Get everything set up for
            // playback.
            //Get the previously-saved data
            // into a byte array object.
            byte audioData[]
                    = byteArrayOutputStream.
                    toByteArray();
            //Get an input stream on the
            // byte array containing the data
            InputStream byteArrayInputStream
                    = new ByteArrayInputStream(
                            audioData);
            audioFormat
                    = getAudioFormat();
            audioInputStream
                    = new AudioInputStream(
                            byteArrayInputStream,
                            audioFormat,
                            audioData.length / audioFormat.
                            getFrameSize());
            return audioInputStream;
    }
    
    private class CaptureThread extends Thread {

        //An arbitrary-size temporary holding
        // buffer
        byte tempBuffer[] = new byte[10000];

        public void run() {
            byteArrayOutputStream
                    = new ByteArrayOutputStream();
            try {//Loop until stopCapture is set
                // by another thread that
                // services the Stop button.
                while (capturing) {
                    //Read data from the internal
                    // buffer of the data line.
                    int cnt = targetDataLine.read(
                            tempBuffer,
                            0,
                            tempBuffer.length);
                    if (cnt > 0) {
                        //Save data in output stream
                        // object.
                        byteArrayOutputStream.write(
                                tempBuffer, 0, cnt);
                    }//end if
                }//end while
                byteArrayOutputStream.close();
            } catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }//end catch
        }//end run
    }

}
