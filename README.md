# File NIO

## To read a file with UTF8, no special characters

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


## To read a file with special characters

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

## Writing to a file

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


## Read a CSV file with old java way

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


## Read a csv file java 8 way

    package com.arun.csvfile;
    
    import java.io.BufferedReader;
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

## Read a buggy file in csv file

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

# check files and directories

## use of Files.walkFileTree

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


## use Files.walk


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
