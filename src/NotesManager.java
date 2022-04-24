import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NotesManager {

    private ArrayList<File> fileList = new ArrayList<>();
    private final Path directoryPath;
    private final String directoryName = "data";

    public NotesManager() throws IOException {
        directoryPath = Paths.get(new File("").getAbsolutePath() + "\\" + directoryName); //Getting a string for the directory where all the notes will be
        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath); //Creating the directory itself if it does not exist
        }
        File directory = new File(directoryPath.toString());
        for (File file : directory.listFiles()) { // Initializing fileList
            fileList.add(file);
        }
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void addNote(String name) throws IOException {
        FileWriter fileWriter = new FileWriter(directoryPath + "\\" + name); // Creating a new file with a given name
        fileWriter.write("Write your note here"); // Adding in filler text
        fileWriter.close();
        fileList.add(new File(directoryPath + "\\" + name)); // Adding the same file to the array
    }

    public void removeNote(String name) {
        for (File file : fileList) {
            if (file.getName().equals(name)) {
                fileList.remove(file); // Removing the file from the array first
                file.delete(); // And then deleting it proper
                return;
            }
        }
    }

    public void editNoteText(String name, String text) {
        for (File file : fileList) {
            if (file.getName().equals(name)) {
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(text); // Rewriting the content of the file
                    fileWriter.close();
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public String getNoteText(String name) throws IOException {
        for (File file : fileList) {
            if (file.getName().equals(name)) {
                Scanner scanner = new Scanner(file);
                String content = "";
                while (scanner.hasNextLine()) {
                    content = content + scanner.nextLine(); // Getting all the
                }
                return content;
            }
        }
        return null;
    }
}