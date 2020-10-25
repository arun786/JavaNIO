package com.arun.csvfile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author arun on 10/24/20
 */

public class ReadAFileUsingFunction {
    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/data.csv");
        Function<String, Person> lineToPerson = ReadAFileUsingFunction::lineToPerson;

        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(l -> !l.startsWith("#")).map(lineToPerson).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Person lineToPerson(String line) {
        String[] split = line.split(";");
        String name = split[0];
        int age = Integer.parseInt(split[1]);
        String city = split[2];
        return new Person()
                .setName(name)
                .setCity(city)
                .setAge(age);
    }
}
