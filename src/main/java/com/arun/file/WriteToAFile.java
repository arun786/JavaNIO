package com.arun.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author arun on 10/22/20
 */

public class WriteToAFile {

    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/debug.log");
        try (
                BufferedWriter writer1 = Files.newBufferedWriter(path);
                BufferedWriter writer2 = new BufferedWriter(writer1);
                PrintWriter printWriter = new PrintWriter(writer2);
        ) {
            writer1.write("Hello World");
            printWriter.write("\nThis is new World");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
