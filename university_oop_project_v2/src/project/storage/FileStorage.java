package project.storage;
import java.io.*;
import java.nio.file.*;
public class FileStorage {
    private final Path path;
    public FileStorage(String fileName){ this.path = Paths.get(fileName); }
    public void save(AppData data) throws IOException {
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path.toFile()))){ out.writeObject(data); }
    }
    public AppData load() throws IOException, ClassNotFoundException {
        if(!Files.exists(path)) return null;
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(path.toFile()))){ return (AppData) in.readObject(); }
    }
}
