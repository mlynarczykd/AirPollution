package labp;

import labp.models.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Klasa łącząca wszystkie klasy, wyświetlająca wyniki metod oraz przechowująca wykorzystywane obiekty
 */

public class Facade {
    private Station[] stations;

    /**
     * Metoda ustawiająca tablicę obiektów na wybraną
     * @param stations tablica obiektów
     */

    public void setStations(Station[] stations) {
        this.stations = stations;
    }

    /**
     * Metoda zwracająca przechowywane obiekty
     * @return tablica obiektów
     */
    public Station[] getStations() {
        return stations;
    }

    /**
     * Metoda szukająca stacji o podanej nazwie
     * @param stName nazwa stacji
     * @return stacja o podanej nazwie
     */

    public Station findStation(String stName){
        for (Station station: stations){
            if (station.found(stName))
                return station;
        }
        throw new IllegalArgumentException("Nieprawidłowa nazwa stacji: " + stName);
    }

    /**
     * Metoda znajdująca i wyświetlająca indeks jakości powietrza dla wybranej stacji
     * @param stName nazwa stacji
     */

    public void one (String stName){
        Cache.addIndex(stName);
        stations = Cache.getStations();
        System.out.println(findStation(stName).one());
    }

     private Date createDate(String date){
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date parsedDate = null;
        try {
            parsedDate = format.parse(date);
        } catch (ParseException e) {
            e.getMessage();
        }
        return parsedDate;
    }

    private void printStAndPm(String stName, String pmName){
        System.out.println("Stacja: " + stName + '\n' + "Parametr: " + pmName);
    }

    /**
     * Metoda znajdująca i wyświetlająca wartość zanieszczenia w podanym terminie
     * @param stName nazwa stacji
     * @param pmName nazwa parametru
     * @param date wybrana data
     */

    public void two (String stName, String pmName, String date){
        Cache.addStations();
        Date parsedDate = createDate(date);
        Param param = Cache.getCachedParam(stName,pmName);
        Values values = param.findValues(parsedDate);
        printStAndPm(stName,pmName);
        System.out.println(values);
    }

    /**
     * Metoda obliczająca i wyświetlająca średnią wartość zanieczyszczenia w podanym okresie
     * @param stName nazwa stacji
     * @param pmName nazwa parametru
     * @param begDate data początkowa
     * @param endDate data końcowa
     */

    public void three (String stName, String pmName, String begDate, String endDate){
        Date date1 = createDate(begDate);
        Date date2 = createDate(endDate);
        Cache.addStations();
        Param param = Cache.getCachedParam(stName,pmName);
        double avg = param.three(date1,date2);
        printStAndPm(stName,pmName);
        System.out.println("średnia wartość zanieczyszczenia: " + avg);
    }

    /**
     * Metoda obliczająca największe wahania w podanym terminie
     * @param names tablica nazw wybranych stacji
     * @param date wybrana data
     */

    public void four (String[] names, String date){
        Cache.addStations();
        Date parsedDate = createDate(date);
        Station station;
        String pmName = null;
        String stName = null;
        double diff;
        double max = 0;
        for(String name: names){
            Cache.addAllForStation(name);
            stations = Cache.getStations();
            station = findStation(name);
            diff = station.four(parsedDate);
            if(diff > max) {
                pmName = station.four2(parsedDate);
                max = diff;
                stName = name;
            }
        }
        System.out.println("Stacja: " + stName + " --- największym wahaniom ulegały wartości parametru: " + pmName + " (" + max + ").");
    }

    /**
     * Metoda znajdująca i wyświetlająca najmniejszą wartość parametu w podanym terminie
     * @param date wybrana data
     */
    
    public void five(String date){
        Cache.addAll();
        stations = Cache.getStations();
        Date parsedDate = createDate(date);
        String pmName = null;
        String stName = null;
        double min = Double.MAX_VALUE;
        double tmp;
        for(Station station: stations){
            tmp = station.five(parsedDate);
            if(tmp < min){
                min = tmp;
                pmName = station.five2(parsedDate);
                stName = station.getStationName();
            }
        }
        System.out.println("Data: " + date);
        System.out.println("Najmniejsza wartość: " + min + ", parametr: " + pmName + " (stacja: " + stName + ")");
    }

    /**
     * Metoda znajdująca i wyświetlająca stanowiska, gdzie zanotowano przekroczenie normy zanieczyszczenia
     * @param stName nazwa stacji
     * @param date wybrana data
     * @param n liczba stanowisk do wypisania
     */

    public void six (String stName, String date, int n){
        Date parsedDate = createDate(date);
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Cache.addStations();
        Cache.addAllForStation(stName);
        stations = Cache.getStations();
        Map<String,Double> limits = createLimits();
        Map<Double,String> result;
        Station station = findStation(stName);
        result = station.six(parsedDate,limits);
        if(result.size() == 0)
            System.out.println("Żadne stanowisko nie zanotowało przekroczenia normy.");
        if(result.size() <= n){
            System.out.println("Stacja: " + stName + '\n' + "Data: " + formatter.format(parsedDate));
            for(Map.Entry<Double,String> res: result.entrySet()){
                String tmp1 = "przekroczenie normy o " + res.getKey();
                System.out.println(String.format("%-6s - %s",res.getValue(),tmp1));
            }
        }
        else{
            System.out.println("Stacja: " + stName + ", data: " + parsedDate);
            int count = 0;
            for(Map.Entry<Double,String> res: result.entrySet()) {
                if(count < n) {
                    count++;
                    String tmp1 = "przekroczenie normy o " + res.getKey();
                    System.out.println(String.format("%-6s - %s",res.getValue(),tmp1));
                }
            }
        }
    }

    private Map<String, Double> createLimits() {
        Map<String, Double> limits = new HashMap<>();
        limits.put("C6H6",50.0);
        limits.put("NO2",200.0);
        limits.put("SO2",350.0);
        limits.put("CO",10000.0);
        limits.put("PM10",50.0);
        limits.put("PM2.5",25.0);
        return limits;
    }

    /**
     * Metoda znajdująca i wyświetlająca najmniejszą oraz największą wartość dla podanego parametru.
     * @param pmName nazwa parametru
     */

    public void seven(String pmName){
        Cache.addStations();
        Cache.addAllForSensor(pmName);
        stations = Cache.getStations();
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Values min = new Values(Double.MAX_VALUE);
        Values max = new Values(0);
        Values tmp1, tmp2;
        String nameMin = null;
        String nameMax = null;
        for(Station station: stations){
            tmp1 = station.getMin(pmName);
            tmp2 = station.getMax(pmName);
            if(tmp1 != null && tmp1.getValue() != 0 && tmp1.getValue() < min.getValue()){
                min = tmp1;
                nameMin = station.getStationName();
            }
            if(tmp2 != null && tmp2.getValue() > max.getValue()){
                max = tmp2;
                nameMax = station.getStationName();
            }
        }
        System.out.println("Parametr: " + pmName + '\n' + "Najmniejsza wartość: " + min.getValue() + ", data: " + formatter.format(min.getDate()) + ", stacja: " + nameMin + ".");
        System.out.println("Największa wartość: " + max.getValue() + ", data: " + formatter.format(max.getDate()) + ", stacja: " + nameMax + ".");
    }

    private double getMax(String pmName){
        Cache.addAllForSensor(pmName);
        stations = Cache.getStations();
        Values max = new Values(0);
        Values tmp;
        for(Station station: stations){
            tmp = station.getMin(pmName);
            if(tmp != null && tmp.getValue() > max.getValue()){
                max = tmp;
            }
        }
        return max.getValue();
    }

    /**
     * Metoda wyświetlająca godzinowy wykres wartości zanieczyszczenia podanego parametru w podanym okresie
     * @param names tablica nazw stacji
     * @param pmName nazwa parametru
     * @param begDate data początkowa
     * @param endDate data końcowa
     */

    public void eight(String[] names, String pmName, String begDate, String endDate) {
        Date date1 = createDate(begDate);
        Date date2 = createDate(endDate);
        Map<Values, String> valMap = new TreeMap<>(Collections.reverseOrder());
        double max = getMax(pmName);
        Cache.addStations();
        for (String name : names) {
            Cache.addAllForStation(name);
            stations = Cache.getStations();
            Values[] valArray = findStation(name).findSensor(pmName).getParam().getValues();
            for (Values values : valArray) {
                if (values.found(date1, date2))
                    valMap.put(values, name);
            }
        }
        Calendar calendar = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        Calendar dayB4Yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        dayB4Yesterday.add(Calendar.DATE, -2);
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        DateFormat formatter2 = new SimpleDateFormat("EEEE HH:mm", new Locale("pl"));
        String day;
        System.out.println("Pomiary parametru " + pmName);
        for (Map.Entry<Values, String> entry : valMap.entrySet()) {
            Date date = entry.getKey().getDate();
            calendar.setTime(date);
            if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
                 day = "dzisiaj " + formatter.format(date);
            }
            else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
                 day = "wczoraj " + formatter.format(date);
            } else {
                 day = formatter2.format(date);
            }
            System.out.print(String.format("%-15s", day));
            System.out.print(String.format("%-37s", "(" + entry.getValue() + ")"));
            double val = entry.getKey().getValue();
            for(int i = 0; i < (18*val/max);i++)
                System.out.print("∎");
            System.out.println(" " + val);
        }
    }




}
