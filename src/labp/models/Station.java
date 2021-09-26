package labp.models;

import labp.Cachable;
import labp.Component;

import java.util.*;

/**
 * Klasa reprezentująca stację
 */

public class Station implements Cachable, Component {
    private int id;
    private String stationName;
    private Sensor[] sensors;
    private Index index;
    private Date expireDate;

    /**
     * @return nazwa stacji
     */
    public String getStationName() {
        return stationName;
    }


    /**
     * Metoda ustawiająca id
     * @param id id stanowiska
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metoda ustawiająca nazwę stacji na wybraną
     * @param stationName nazwa stacji
     */

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    @Override
    public double three(Date begDate, Date endDate) {
        return 0;
    }

    /**
     * Bezargumentowy konstruktor obliczający datę wygaśniecia ważności przechowywanych danych
     */
    public Station(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(cal.YEAR, 1);
        this.expireDate = cal.getTime();
    }

    /**
     * @return data wygaśnięcia ważności danych
     */

    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * Metoda, która określa czy dany obiekt jest jeszcze aktualny
     * @return true jeśli obiekt nie jest już aktualny
     */
    @Override
    public boolean isExpired(){
        return this.expireDate.before(new Date());
    }

    /**
     * Metoda znajdująca indeks jakości powietrza dla wybranej stacji
     * @return nazwa stacji i indeks jakości powietrza
     */

    public String one(){
        return "Stacja: " + this.stationName + " --- aktualny indeks jakości powietrza: " + this.index.one() + ".";
    }

    /**
     * @return id stacji
     */
    public int getId() {
        return id;
    }

    /**
     * @return (@link Index)
     */
    public Index getIndex() {
        return index;
    }

    /**
     * Metoda ustawiająca indeks na wybrany
     * @param index wybrany indeks
     */
    public void setIndex(Index index) {
        this.index = index;
    }

    /**
     * Metoda ustawiająca nazwę poziomu indeksu na wybrany
     * @param level wybrana nazwa poziomu indesu
     */
    public void setStIndexLevel(String level){
        this.index.setStIndexLevelName(level);
    }

    /**
     * @return tablica stanowisk pomiarowych danej stacji
     */
    public Sensor[] getSensors() {
        return sensors;
    }

    /**
     * Metoda ustawiająca tablicę stanowisk na wybraną
     * @param sensors tablica stanowisk pomiarowych
     */
    public void setSensors(Sensor[] sensors) {
        this.sensors = sensors;
    }

    /**
     * @param stationName nazwa parametru
     * @return true jeśli nazwa przekazana w metodzie jest taka sama jak nazwa obiektu wywołującego metodę
     */
    public boolean found (String stationName){
        return this.stationName.equals(stationName);
    }


    /**
     * Metoda szukająca stanowiska o podanej nazwie parametru
     * @param pmName nazwa parametru
     * @return stanowisko mierzące wartości parametru o podanej nazwie
     */
    public Sensor findSensor(String pmName){
        for(Sensor sensor: this.sensors){
            if (sensor.getParam().found(pmName)){
                return sensor;
            }
        }
        throw new IllegalArgumentException("Nieprawidłowa nazwa parametru: " + pmName);
    }

    /**
     * Metoda szukająca stanowiska o podanej nazwie parametru
     * @param pmName nazwa parametru
     * @return stanowisko mierzące wartości parametru o podanej nazwie
     */

    public Sensor findSensorExp(String pmName){
        for(Sensor sensor: this.sensors){
            if (sensor.getParam().found(pmName)){
                return sensor;
            }
        }
        return null;
    }

    /**
     * Metoda obliczająca największe wahania w podanym terminie
     * @param parsedDate wybrana data
     * @return wartość największych wahań wartości zanieczyszczeń
     */
    @Override
    public double four(Date parsedDate) {
        double diff = 0;
        double tmp;
        for(Sensor sensor: this.sensors){
            tmp = sensor.getParam().four(parsedDate);
            if(tmp > diff) {
                diff = tmp;
            }
        }
        return diff;
    }

    /**
     * Metoda obliczająca największe wahania w podanym terminie
     * @param parsedDate wybrana data
     * @return nazwa parametru, dla którego występują największe wahania wartości zanieczyszczeń
     */
    public String four2(Date parsedDate) {
        double diff = 0;
        double tmp;
        String name = null;
        for(Sensor sensor: this.sensors){
            tmp = sensor.getParam().four(parsedDate);
            if(tmp > diff) {
                diff = tmp;
                name = sensor.getParam().getParamCode();
            }
        }
        return name;
    }

    /**
     * Metoda zwraca najmniejszą znalezioną wartość w podanym terminie
     * @param parsedDate wybrana data
     * @return najmniejsza znaleziona wartość
     */

    public double five(Date parsedDate) {
        double min = Double.MAX_VALUE;
        double tmp;
        for(Sensor sensor: this.sensors){
            tmp = sensor.getParam().five(parsedDate);
            if(tmp < min) {
                min = tmp;
            }
        }
        return min;
    }

    /**
     * Metoda zwraca najmniejszą znalezioną wartość w podanym terminie
     * @param parsedDate wybrana data
     * @return nazwa parametru, dla którego znaleziono najmniejszą wartość
     */

    public String five2(Date parsedDate) {
        double min = Double.MAX_VALUE;
        double tmp;
        String name = null;
        for(Sensor sensor: this.sensors){
            tmp = sensor.getParam().five(parsedDate);
            if(tmp < min) {
                name = sensor.getParam().getParamCode();
            }
        }
        return name;
    }

    /**
     * Metoda zwraca najmniejszą znalezioną wartość dla podanego parametru
     * @param pmName nazwa parametru
     * @return najmniejsza znaleziona wartość
     */

    public Values getMin(String pmName) {
        Sensor sensor = findSensorExp(pmName);
        if(sensor != null)
            return sensor.getParam().getMin();
        else
            return null;
    }

    /**
     * Metoda zwraca największą znalezioną wartość dla podanego parametru
     * @param pmName nazwa parametru
     * @return największa znaleziona wartość
     */
    public Values getMax(String pmName) {
        Sensor sensor = findSensorExp(pmName);
        if(sensor != null)
            return sensor.getParam().getMax();
        else
            return null;
    }

    /**
     * Metoda obliczająca przekroczenie normy zanieczyszczenia w podanym terminie
     * @param date wybrana data
     * @param limits mapa zwartościami norm dla wszystkich parametrów
     * @return mapa różnic między wartościami a normami dla parametrów
     */

    public Map<Double, String> six(Date date, Map<String, Double> limits) {
        Map<Double,String> result = new TreeMap<>();
        for(Sensor sensor: this.sensors){
            String name = sensor.getParam().getParamCode();
            double diff = sensor.getParam().six(date,limits.get(name));
            if(diff > 0)
                result.put(diff,name);
        }
        return result;
    }
}
