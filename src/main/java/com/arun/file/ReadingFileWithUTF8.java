package com.arun.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author arun on 10/19/20
 */

public class ReadingFileWithUTF8 {

    //reading a file with UTF8, where we dont have special character
    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/sonnet-UTF8.txt");
        boolean exists = Files.exists(path);

        if (exists) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {

                String line = reader.readLine();
                while (line != null) {
                    System.out.println("Line : " + line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File does not exist");
        }
    }
}
