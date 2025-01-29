package IO_NIO;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IOvsNIO {
    public static void main(String[] args) throws IOException {
        String sourceFilePath = "source.txt";
        String destFilePathIO = "destination_io.txt";
        String destFilePathNIO = "destination_nio.txt";

        long startTimeIO = System.currentTimeMillis();
        copyFileUsingIO(sourceFilePath, destFilePathIO);
        long endTimeIO = System.currentTimeMillis();
        System.out.println("Время выполнения IO: " + (endTimeIO - startTimeIO) + " ms");

        long startTimeNIO = System.currentTimeMillis();
        copyFileUsingNIO(sourceFilePath, destFilePathNIO);
        long endTimeNIO = System.currentTimeMillis();
        System.out.println("Время выполнения NIO: " + (endTimeNIO - startTimeNIO) + " ms");
    }

    public static void copyFileUsingIO(String sourceFilePath, String destFilePath) throws IOException {
        try (InputStream is = new FileInputStream(sourceFilePath);
             OutputStream os = new FileOutputStream(destFilePath)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    public static void copyFileUsingNIO(String sourceFilePath, String destFilePath) throws IOException {
        Path sourcePath = Paths.get(sourceFilePath);
        Path destPath = Paths.get(destFilePath);

        try (FileChannel sourceChannel = FileChannel.open(sourcePath, java.nio.file.StandardOpenOption.READ);
             FileChannel destChannel = FileChannel.open(destPath, java.nio.file.StandardOpenOption.WRITE, java.nio.file.StandardOpenOption.CREATE)) {

            sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
        }
    }
}