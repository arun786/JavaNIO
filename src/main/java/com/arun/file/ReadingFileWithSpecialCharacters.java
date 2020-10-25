package com.arun.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author arun on 10/22/20
 */

public class ReadingFileWithSpecialCharacters {

    //when files have special characters, we use the StandardCharSets.ISO_8859_1
    public static void main(String[] args) {

        Path path = Paths.get("src/main/resources/sonnet-ISO.txt");
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.ISO_8859_1)) {
            String line = reader.readLine();
            while (line != null) {
                System.out.println("line : " + line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
