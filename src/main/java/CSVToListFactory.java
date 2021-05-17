import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class CSVToListFactory {

    //Get first line from file and pass it to factory
    public static <E extends ICSVEntity> List<E> getList(File file, String encoder) {
        try (InputStream inputStream = new FileInputStream(file)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoder));
            return checkFirstLine(file, encoder, bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Overloads
    public static <E extends ICSVEntity> List<E> getList(File file) {
        return getList(file, "UTF-8");
    }

    public static <E extends ICSVEntity> List<E> getList(String path, String encoder) {
        File file = new File(path);
        return getList(file, encoder);
    }

    public static <E extends ICSVEntity> List<E> getList(String path) {
        File file = new File(path);
        return getList(file, "UTF-8");
    }

    //Factory
    private static <E extends ICSVEntity> List<E> checkFirstLine(File file, String encoder, String firstLine) {
        try (InputStream inputStream = new FileInputStream(file)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoder));
            switch (firstLine) {
                case "Lp.;Nazwa własna;Telefon;Email;Charakter usług;Kategoria obiektu;Rodzaj obiektu;Adres":
                    return (List<E>) bufferedReader.lines().map(Hotel::stringToHotel).collect(Collectors.toList());
                default:
                    throw new FileNotFoundException("File content not supported");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
