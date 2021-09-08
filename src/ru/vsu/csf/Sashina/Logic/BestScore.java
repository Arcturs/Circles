package ru.vsu.csf.Sashina.Logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class BestScore {

    public static int readScore () {
        try {
            BufferedReader br = new BufferedReader(new FileReader("bestScore.txt"));
            String str = br.readLine();
            int score = Integer.parseInt(str);
            br.close();
            return score;
        } catch (Exception exp) {
            return 0;
        }
    }

    public static void writeScore (int score) {
        try {
            PrintWriter pw = new PrintWriter("bestScore.txt");
            pw.println(score);
            pw.close();
        } catch (Exception exp) {
            return;
        }
    }
}
