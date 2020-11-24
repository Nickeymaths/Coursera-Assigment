import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Utils {
    public static String content = "Hello World\n";

    public static String readAllLines(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        StringBuilder result = new StringBuilder();
        for (String line : lines) {
            result.append(line).append("\n");
        }
        return result.toString();
    }

    public static void writeContentToFile(String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(content);
        fileWriter.close();
    }

    public static void appendContentToFile(String path) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(path));
        writer.append(content);
        writer.close();
    }

    public static File findFileByName(String folderPath, String fileName) {
        File file = new File(folderPath);
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile() && listFiles[i].getName().equals(fileName)) {
                    return listFiles[i];
                }
            }
        }
        return null;
    }
}
