public class Hotel implements ICSVEntity {
    private String lp, nazwaWlasna, telefon, email, charakterUslugi, kategoriaObiektu, rodzajObiektu, adres;

    public static Hotel stringToHotel(String s) {
        String[] arr = s.split(";");
        return new Hotel(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7]);
    }
    public String getLp() {
        return lp;
    }

    public void setLp(String lp) {
        this.lp = lp;
    }

    public String getNazwaWlasna() {
        return nazwaWlasna;
    }

    public void setNazwaWlasna(String nazwaWlasna) {
        this.nazwaWlasna = nazwaWlasna;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCharakterUslugi() {
        return charakterUslugi;
    }

    public void setCharakterUslugi(String charakterUslugi) {
        this.charakterUslugi = charakterUslugi;
    }

    public String getKategoriaObiektu() {
        return kategoriaObiektu;
    }

    public void setKategoriaObiektu(String kategoriaObiektu) {
        this.kategoriaObiektu = kategoriaObiektu;
    }

    public String getRodzajObiektu() {
        return rodzajObiektu;
    }

    public void setRodzajObiektu(String rodzajObiektu) {
        this.rodzajObiektu = rodzajObiektu;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public Hotel(String lp, String nazwaWlasna, String telefon, String email, String charakterUslugi, String kategoriaObiektu, String rodzajObiektu, String adres) {
        this.lp = lp;
        this.nazwaWlasna = nazwaWlasna;
        this.telefon = telefon;
        this.email = email;
        this.charakterUslugi = charakterUslugi;
        this.kategoriaObiektu = kategoriaObiektu;
        this.rodzajObiektu = rodzajObiektu;
        this.adres = adres;
    }

    public static String getCity(String s) {
        s = s.replaceAll("\\d{2}-\\d{3}", "");
        if (s.indexOf(',') > -1) {
            s = s.substring(0, s.indexOf(','));
        }
        if (s.isBlank() || s.isEmpty()) {
            s = "";
        }
        return s;
    }

    @Override
    public String toString() {
        return getLp() + ", "
                + getNazwaWlasna() + ", "
                + getTelefon() + ", "
                + getEmail() + ", "
                + getCharakterUslugi() + ", "
                + getKategoriaObiektu() + ", "
                + getLp() + ", "
                + getRodzajObiektu() + ", "
                + getAdres();
    }
}
