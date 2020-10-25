package com.arun.manipulatingfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author arun on 10/25/20
 */

public class WalkPattern {

    public static void main(String[] args) {
        Path path = Path.of("src/main/resources/file/media");
        boolean directory = Files.isDirectory(path);
        System.out.println("Directory : " + directory);


        try (
                Stream<Path> walkDirectory = Files.walk(path);
                Stream<Path> walkFiles = Files.walk(path);
        ) {

            long count = walkDirectory.filter(Files::isDirectory).count();
            System.out.println("Count of total Directories : " + count);

            long fileCount = walkFiles.filter(Files::isRegularFile).count();
            System.out.println("Count of total Files : " + fileCount);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
