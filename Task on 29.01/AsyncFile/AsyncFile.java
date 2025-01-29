package AsyncFile;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsyncFile {
    public static void main(String[] args) {
        String filePath = "source.txt";

        Path path = Paths.get(filePath);

        try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            long position = 0;

            Future<Integer> result = fileChannel.read(buffer, position);

            while (!result.isDone()) {
                // Можно делать что-то другое пока файл читается асинхронно
            }

            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            System.out.println("Чтение файла через Future: " + new String(data));

            fileChannel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    attachment.flip();
                    byte[] data = new byte[attachment.remaining()];
                    attachment.get(data);
                    System.out.println("Чтение файла через CompletionHandler: " + new String(data));
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.err.println("Ошибка при чтении файла: " + exc.getMessage());
                }
            });

        } catch (IOException e) {
            System.err.println("Ошибка при открытии файла: " + e.getMessage());
        }
    }
}
