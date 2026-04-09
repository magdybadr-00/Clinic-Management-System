package clinic.utils;
import java.io.*;
import java.util.*;

public class FileManager {
    public static <T> void saveData(List<T> data, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) { System.out.println("Error saving: " + e.getMessage()); }
    }
    @SuppressWarnings("unchecked")
    public static <T> List<T> loadData(String filename) {
        File file = new File(filename);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (Exception e) { return new ArrayList<>(); }
    }
}