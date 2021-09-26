package labp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Klasa pobierająca informacje
 */

public class Reader {

    /**
     * Metoda wysyłająca zapytanie na podany adres i pobierająca informacje
     * @param urlStr adres strony z potrzebnymi informacjami
     * @return obiekt typu String z pobranymi informacjami
     */

    public String read (String urlStr){
        String json = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            json = readStream(in);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("Problemy z siecią.");
            e.printStackTrace();
        }
        finally{
            if(urlConnection != null)
                urlConnection.disconnect();
        }
        return json;
    }

    private String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }

    /**
     * Metoda pobierająca informacje o stacjach
     * @return obiekt typu String z pobranymi informacjami
     */
    public String getStations(){
        return read("http://api.gios.gov.pl/pjp-api/rest/station/findAll");
    }

    /**
     * Metoda pobierająca informacje o indeksie dla wybranej stacji
     * @param stId numer id stacji
     * @return obiekt typu String z pobranymi informacjami
     */

    public String readIndex(int stId){
        String url = new StringBuilder().append("http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/").append(stId).toString();
        return read(url);
    }

    /**
     * Metoda pobierająca informacje o czujnikach dla wybranej stacji
     * @param stId numer id stacji
     * @return obiekt typu String z pobranymi informacjami
     */

    public String readSensors(int stId){
        String url = new StringBuilder().append("http://api.gios.gov.pl/pjp-api/rest/station/sensors/").append(stId).toString();
        return read(url);
    }

    /**
     * Metoda pobierająca informacje o indeksie dla wybranego czujnika
     * @param senId numer id czujnika
     * @return obiekt typu String z pobranymi informacjami
     */

    public String readValues(int senId){
        String url = new StringBuilder().append("http://api.gios.gov.pl/pjp-api/rest/data/getData/").append(senId).toString();
        return read(url);
    }


    /**
     * Metoda wczytująca informacje z pliku
     * @param filePath ścieżka do pliku
     * @return obiekt typu String z zawartością pliku
     */
    public String readFile(String filePath) {
        StringBuilder linesB = new StringBuilder();
        try (BufferedReader file = new BufferedReader(new FileReader(filePath))) {
            String line = file.readLine();
            while (line != null) {
                linesB.append(line);
                line = file.readLine();
            }
        } catch (FileNotFoundException e1) {
            e1.getMessage();
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return linesB.toString();
    }
}
