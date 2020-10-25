package com.arun.manipulatingfiles;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author arun on 10/25/20
 */

public class WalkFileTreePattern {

    public static void main(String[] args) {

        Path path = Path.of("src/main/resources/file/media");
        boolean directory = Files.isDirectory(path);
        System.out.println("Directory : " + directory);

        var visitor = new FileVisitor<Path>() {
            private Long countOfFiles = 0L;
            private Long countOfDir = 0L;

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                countOfFiles++;
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                countOfDir++;
                return FileVisitResult.CONTINUE;
            }
        };

        try {
            Files.walkFileTree(path, visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Count of files : " + visitor.countOfFiles);
        System.out.println("Count of Directories : " + visitor.countOfDir);
    }
}
