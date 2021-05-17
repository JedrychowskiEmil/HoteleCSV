import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TmpFiles {
    private ArrayList<File> files;
    private int index;

    public TmpFiles() {
        this.files = new ArrayList<>();
        this.index = 0;
    }

    public TmpFiles(ArrayList<File> files) {
        this.files = files;
        this.index = 0;
    }

    public void addFile(File file) {
        files.add(file);
        index++;
    }

    public void createAndAddFile(String content, String filename) {
        try {
            String path = new File("").getAbsolutePath();
            path = path.concat("\\src\\main\\resources\\" + filename + ".txt");
            File newFile = new File(path);

            if (newFile.createNewFile()) {
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(content);
                fileWriter.close();
            }

            files.add(new File(path));
            index++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAndAddFile(String content) {
        createAndAddFile(content, "tmp" + index);
    }

    public void clean() {
        for (File f : files) {
            String s = f.getName();
            if (f.delete()) {
                System.out.println("File:" + s + " has been deleted ");
            } else {
                System.out.println("error -> File:" + s + " HASN'T been deleted ");
            }
        }
        files.clear();
    }

    public ArrayList<File> getFiles() {
        return files;
    }

    public File createFile(List<?> list, String fileName, boolean add) {
        try {
            String path = new File("").getAbsolutePath();
            path = path.concat("\\src\\main\\resources\\" + fileName + ".txt");
            File newFile = new File(path);

            if (newFile.createNewFile()) {
                FileWriter fileWriter = new FileWriter(path);
                for (Object o : list) {
                    fileWriter.write(o.toString() + "\n");
                }
                fileWriter.close();
            }
            if (add) {
                addFile(newFile);
            }
            return newFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File createFile(List<?> list, String fileName){
        return createFile(list, fileName, false);
    }

    public File createFile(List<?> list){
        return createFile(list, "tmp" + index);
    }

}


