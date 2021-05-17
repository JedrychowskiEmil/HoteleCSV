import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        String path = new File("").getAbsolutePath();
        path = path.concat("\\src\\main\\resources\\hotele.csv");
        String encoder = "Cp1250";
        List<Hotel> hotels = CSVToListFactory.getList(path, encoder);


        TmpFiles tmpFiles = new TmpFiles();

        //Określić ile hoteli występuje w każdej kategorii
        tmpFiles.createAndAddFile(
                StreamProcessObject.countByField(hotels, Hotel::getKategoriaObiektu, 1)
                        .toString()
                , "LiczbaHoteliWKazdejKategorii"
        );

        //Lista kategorii
        tmpFiles.createAndAddFile(
                StreamProcessObject.uniqueValuesInField(hotels, Hotel::getKategoriaObiektu, 1)
                        .keySet()
                        .toString()
                , "ListaKategorii"
        );

        //Lista miast (bez duplikatów)
        tmpFiles.createAndAddFile(
                StreamProcessObject.
                        uniqueValuesInField(hotels, hotel -> Hotel.getCity(hotel.getAdres()).trim().toUpperCase(), 1)
                        .keySet()
                        .toString()
                        .replaceAll(",", ",\n")
                , "ListaMiastBezDuplikatow"
        );

        //Ile hoteli w poszczególnych miastach
        tmpFiles.createAndAddFile(
                StreamProcessObject
                        .countByField(hotels, hotel -> Hotel.getCity(hotel.getAdres()).trim().toUpperCase(), 1)
                        .toString()
                        .replaceAll(",", ",\n")
                , "LiczbaHoteliWMiastach"
        );

        //Lista hoteli w danym mieście
        String s = "WARSZAWA";
        tmpFiles.createFile(
                StreamProcessObject.fillterByField(hotels, hotel -> hotel.getAdres().toUpperCase().contains(s), 1)
                , "ListaHoteliWarszawa"
                , true
        );

        //Lista obiektów wg charakteru
        Comparator<Hotel> c1 = Comparator.comparing(Hotel::getCharakterUslugi);
        Comparator<Hotel> c2 = Comparator.comparingInt(o -> Integer.parseInt(o.getLp()));
        tmpFiles.createFile(
                StreamProcessObject.sortByTwoFields(hotels, c1, c2, 1)
                , "ListaObiektowWgCharakteru"
                , true
        );


        MailTrap mailTrap = new MailTrap("d31b250e43ecec", "425c75277df389");
        mailTrap.setSender("sender@gmail.com");
        mailTrap.setRecipients("rec1@gmail.com,rec2@gmail.com");
        mailTrap.setSubject("Przetworzone dane");
        mailTrap.addMessage("//Określić ile hoteli występuje w każdej kategorii\n");
        mailTrap.addMessage("//Lista kategorii\n");
        mailTrap.addMessage("//Lista miast (bez duplikatów)\n");
        mailTrap.addMessage("//Ile hoteli w poszczególnych miastach\n");
        mailTrap.addMessage("//Lista hoteli w danym mieście\n");
        mailTrap.addMessage("//Lista obiektów wg charakteru\n");
        for (File f : tmpFiles.getFiles()) {
            mailTrap.addAttachment(f);
        }
        mailTrap.sendMail();

        tmpFiles.clean();

    }


}
