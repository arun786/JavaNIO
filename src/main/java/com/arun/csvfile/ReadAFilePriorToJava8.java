package com.arun.csvfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author arun on 10/22/20
 */

public class ReadAFilePriorToJava8 {

    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/data.csv");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String lines = reader.readLine();
            while (lines != null) {
                if (!lines.startsWith("#")) {
                    String[] split = lines.split(";");
                    String name = split[0];
                    int age = Integer.parseInt(split[1]);
                    String city = split[2];

                    Person person = new Person().setName(name).setAge(age).setCity(city);
                    System.out.println(person);
                }
                lines = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
