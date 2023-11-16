package org.example;

import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ищем подстроку в строке.
 */
public class findSubstringIndices {
    /**
     * ищем подстроку в строке.
     */
    public static ArrayList<Integer> find(String filename, String substr) {
        var substring = new String(substr.getBytes(), StandardCharsets.UTF_8);

        var result = new ArrayList<Integer>();
        try {
            FileInputStream fileIS = new FileInputStream(filename);
            InputStreamReader inputSR = new InputStreamReader(fileIS, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputSR);

            char[] buff = new char[substring.length()];
            var a = bufferedReader.read(buff, 0, substring.length());
            char[] temp;
            int index = 0;

            char[] sub = new char[substring.length()];
            for (int i = 0; i < substring.length(); i++) {
                sub[i] = substring.charAt(i);
            }

            while (a != -1) {
                if (Arrays.equals(buff, sub)) {
                    result.add(index);
                }
                a = bufferedReader.read();
                temp = buff.clone();
                System.arraycopy(temp, 1, buff, 0, substring.length() - 1);
                buff[substring.length() - 1] = (char) a;
                index++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ищем подстроку в строке.
     */
    public static void main(String[] args) {
        String filename = "input.txt";
        String substring = "бра";
        System.out.println(find("src/main/resources/" + filename, substring));
    }
}