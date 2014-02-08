package com.jayseeofficial.myutil;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MyFileUtil {

    public static String[] readFlatFile(String file)
            throws FileNotFoundException, IOException {
        Scanner in = new Scanner(new FileReader(new File(file)));
        String[] out = new String[linesInFile(new File(file))];
        int i = 0;
        while (in.hasNext()) {
            MyStringUtil.extendArray(out);
            out[i] = in.nextLine();
            i++;
        }
        return out;
    }

    public static void writeFlatFile(String[] theString, PrintWriter out) {
        for (int i = 0; i < theString.length; i++) {
            out.println(theString[i]);
        }
        out.flush();
    }

    public static void writeCSVLine(String[] line, String delimiter, PrintWriter out) {
        String output = "";
        for (int i = 0; i < line.length; i++) {
            output = output + line[i] + delimiter;
        }
        out.write(output);
        System.out.println(output);
    }

    public static void writeCSVLine(String[] line, PrintWriter out) {
        writeCSVLine(line, ",", out);
    }

    public static String getFriendlyFileName(File theFile) {
        return theFile.getName().substring(0, theFile.getName().indexOf("."));
    }

    public static int linesInFile(File theFile) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(theFile));
        int lines = 0;
        while (reader.readLine() != null) {
            lines++;
        }
        reader.close();
        return lines;
    }

    public static String[][] readCSVFile(File theFIle, String delimiter)
            throws FileNotFoundException, IOException {
        ArrayList list = new ArrayList();

        Scanner line = new Scanner(theFIle);
        line.useDelimiter("\n");

        while (line.hasNext()) {
            ArrayList columns = new ArrayList();

            Scanner in = new Scanner(line.next());
            in.useDelimiter(delimiter + "|\\n");

            while (in.hasNext()) {
                columns.add(in.next());
            }

            list.add(columns.toArray(new String[0]));
        }

        return (String[][]) list.toArray(new String[0][0]);
    }
}