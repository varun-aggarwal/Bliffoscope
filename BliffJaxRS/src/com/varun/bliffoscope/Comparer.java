package com.varun.bliffoscope;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
 * Compares data with sample
 * it compares the pattern for sample file for each pixel and log the percentage of pattern matching to MatchCoorinates
 */
public class Comparer {

    public List<MatchCoorindates> generateSimilarity(String sample_name, char[][] data, char[][] sample) {
        List<MatchCoorindates> results = new ArrayList<MatchCoorindates>();
        int data_row = data.length;
        int data_column = data[0].length;
        int sample_row = sample.length;
        int sample_column = sample[0].length;

        if (data_row < sample_row)
            return null;
        else if (data_column < sample_column)
            return null;

        int y_length = data_row - sample_row;
        int x_length = data_column - sample_column;

        for (int i = 0; i < y_length; i++) {
            for (int j = 0; j < x_length; j++) {
                results.add(calculateConfidence(sample_name, i, j, data, sample));
            }
        }
        return results;
    }

    // compare the specified area with sample matrix and add it to results
    private MatchCoorindates calculateConfidence(String sample_name, int i, int j, char[][] data,
            char[][] sample) {
        int sample_row = sample.length;
        int sample_column = sample[0].length;

        int total_pixels = sample_row * sample_column;
        int matched_pixels = 0;
        DecimalFormat df = new DecimalFormat("#,###.00");

        df.setRoundingMode(RoundingMode.FLOOR);
        DecimalFormatSymbols newSymbols = new DecimalFormatSymbols(Locale.US);
        df.setDecimalFormatSymbols(newSymbols); 
        for (int m = 0; m < sample_row; m++) {
            for (int n = 0; n < sample_column; n++) {
                if (data[m + i][n + j] == sample[m][n])
                    matched_pixels++;
            }
        }
        
        double conf = (double) matched_pixels / (double) total_pixels;
        if(conf>0.0){
            conf= new Double(df.format(conf));
        }
            
        return new MatchCoorindates(sample_name, j, i, conf);

    }

}
