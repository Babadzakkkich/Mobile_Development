package FileCopyNIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileCopyNIO {
    public static void copyFile(String source, String dest) throws IOException {
        try (FileChannel srcChannel = new FileInputStream(source).getChannel();
             FileChannel destChannel = new FileOutputStream(dest).getChannel()) {

            srcChannel.transferTo(0, srcChannel.size(), destChannel);
        }
    }

    public static void main(String[] args) {
        String sourceFilePath = "source.txt";
        String destFilePath = "destination.txt";

        try {
            copyFile(sourceFilePath, destFilePath);
            System.out.println("Файл успешно скопирован.");
        } catch (IOException e) {
            System.err.println("Ошибка при копировании файла: " + e.getMessage());
        }
    }
}
