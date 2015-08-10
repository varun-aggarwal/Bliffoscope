package com.varun.bliffoscope;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        
        String path = "D:\\qafe\\git\\Bliffoscope\\Bliffoscope\\BliffJaxRS\\WebContent\\samples\\Starship.blf";
       try {
           char[][] sample = (new Main()).readSample(path);
           print2DArray(sample);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    }
    
    private static void print2DArray(char[][] sample) {
        
        for (char[] x : sample) {
            for (char y : x) {
                System.out.print(y);
            }
            System.out.print("\n");
        }
                
            
    }

    private char[][] constructMatrix(List<String> rows) {
        int row = rows.size();
        int column = rows.get(0).length();
        char[][] matrix = new char[row][column];

        for (int i = 0; i < row; i++) {
            String r = rows.get(i);
            for (int j = 0; j < column; j++) {
                matrix[i][j] = r.charAt(j);
            }
        }
        return matrix;
    }

    public char[][] readSample(String filepath) throws IOException {
        List<String> rows = this.readFile(filepath);
        for (int i = 0; i < rows.size(); i++) {
            StringBuilder sb = new StringBuilder(rows.get(i));
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(0);
            rows.set(i, sb.toString());
        }

        return this.constructMatrix(rows);
    }

    private List<String> readFile(String filepath) throws IOException {
        File sampleFile = new File(filepath);
        InputStream is = new FileInputStream(sampleFile);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;
        List<String> rows = new ArrayList<String>();
        while ((line = br.readLine()) != null) {
            rows.add(line);
        }
        br.close();
        return rows;
    }
}
