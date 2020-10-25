package com.arun.csvfile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author arun on 10/24/20
 */

public class ReadAFileWithValidAndInvalidData {

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/dataWithInvalidData.csv");
        try (Stream<String> lines = Files.lines(path)) {
            lines.filter(line -> !line.startsWith("#"))
                    .flatMap(ReadAFileWithValidAndInvalidData::convertLineToPerson)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stream<Person> convertLineToPerson(String line) {
        try {
            String[] split = line.split(";");
            String name = split[0];
            int age = Integer.parseInt(split[1]);
            String city = split[2];
            Person person = new Person().setAge(age).setCity(city).setName(name);
            return Stream.of(person);
        } catch (Exception e) {
            System.out.println("Buggy line " + e.getMessage());
        }
        return Stream.empty();
    }
}
