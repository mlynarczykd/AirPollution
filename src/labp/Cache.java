package labp;

import com.google.gson.Gson;
import labp.models.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Klasa przechowująca cachowane obiekty i zarządzająca ich pobieraniem
 */

public class Cache {
    private static Station[] cachedStations;

    /**
     * Metoda zwracająca przechowywane obiekty
     * @return tablica przechowywanych obiektów
     */

    public static Station[] getStations(){
        return cachedStations;
    }

    /**
     * Metoda dodająca stacje do pamięci cache
     */

    public static void addStations() {
        File file = new File("stations.txt");
        Reader reader = new Reader();
        String json;
        Gson gson = new Gson();
        if (cachedStations != null) {
            if (cachedStations[0].isExpired()) {
                json = reader.getStations();
                cachedStations = gson.fromJson(json, Station[].class);
                serialize();
            }
        } else if (file.exists()) {
            json = reader.readFile("stations.txt");
            cachedStations = gson.fromJson(json, Station[].class);
            if (cachedStations[0].isExpired()) {
                json = reader.getStations();
                cachedStations = gson.fromJson(json, Station[].class);
                serialize();
            }
        } else {
            json = reader.getStations();
            cachedStations = gson.fromJson(json, Station[].class);
            serialize();
        }
    }

    /**
     * Metoda przetwarzająca obiekty zapisane w tablicy do jsona i zapisująca je do pliku
     */

    private static String serialize(){
        String json;
        Gson gson = new Gson();
        json = gson.toJson(cachedStations);
        try (PrintWriter out = new PrintWriter("stations.txt")) {
            out.println(json);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Metoda szukająca stacji o podanej nazwie
     * @param stName nazwa stacji
     * @return stacja o podanej nazwie
     */

    public static Station findStation(String stName){
        for (Station station: cachedStations){
            if (station.found(stName))
                return station;
        }
        throw new IllegalArgumentException("Nieprawidłowa nazwa stacji");
    }

    /**
     * Metoda dodająca indeks dla danej stacji do pamięci cache
     * @param stName nazwa stacji
     */

    public static void addIndex(String stName){
        addStations();
        Index index = null;
        for (Station station: cachedStations) {
            if (station.found(stName)) {
                int stId = station.getId();
                index = station.getIndex();
                String json;
                Gson gson = new Gson();
                Reader reader = new Reader();
                if (index != null) {
                    if (index.isExpired()) {
                        json = reader.readIndex(stId);
                        index = gson.fromJson(json, Index.class);
                        station.setIndex(index);
                    }
                } else {
                    json = reader.readIndex(stId);
                    index = gson.fromJson(json, Index.class);
                    station.setIndex(index);
                }
            }
        }
        if(index == null)
            throw new IllegalArgumentException("Nieprawidłowa nazwa stacji: " + stName);
        serialize();
    }

    /**
     *  Metoda dodająca czujniki dla danej stacji do pamięci cache
     * @param stName nazwa stacji
     */

    public static void addSensors(String stName){
        Sensor[] sensors  = null;
        for (Station station: cachedStations) {
            if (station.found(stName)) {
                addSensorsForStation(station);
                sensors = station.getSensors();
            }
        }
        if(sensors == null)
            throw new IllegalArgumentException("Nieprawidłowa nazwa stacji: " + stName);
        serialize();
    }


    private static void addSensorsForStation(Station station){
        int stId = station.getId();
        Sensor[] sensors = station.getSensors();
        String json;
        Gson gson = new Gson();
        Reader reader = new Reader();
        if (sensors != null) {
            if (sensors.length >= 1 && sensors[0].isExpired()) {
                json = reader.readSensors(stId);
                sensors = gson.fromJson(json, Sensor[].class);
                station.setSensors(sensors);
            }
        } else {
            json = reader.readSensors(stId);
            sensors = gson.fromJson(json, Sensor[].class);
            station.setSensors(sensors);
        }
    }


    private static Values[] getValues(int senId){
        Reader reader = new Reader();
        String json = reader.readValues(senId);
        Gson gson = new Gson();
        Param2 param2  = gson.fromJson(json, Param2.class);
        if(param2 == null)
            throw new IllegalArgumentException("Nieprawidłowa nazwa parametru");
        return param2.getValues();
    }

    /**
     * Metoda dodająca wartości dla danego parametru i stacji do pamięci cache
     * @param stName nazwa stacji
     * @param pmName nazwa parametru
     */
    public static void addValues(String stName, String pmName) {
        addSensors(stName);
        Values[] values = null;
        for (Station station : cachedStations) {
            if (station.found(stName)) {
                for (Sensor sensor : station.getSensors()) {
                    if (sensor.getParam().found(pmName)) {
                        addValuesForSensor(sensor);
                        values = sensor.getParam().getValues();
                    }
                }
                if (values == null)
                    throw new IllegalArgumentException("Nieprawidłowa nazwa parametru: " + pmName);
            }
        }
        if (values == null)
            throw new IllegalArgumentException("Nieprawidłowa nazwa stacji: " + stName);
        serialize();
    }

    /**
     * Metoda dodająca wartości dla danego parametru i stacji do pamięci cache
     * @param stName nazwa stacji
     * @param pmName nazwa parametru
     * @return parametr z uzupełnionymi wartościami
     */

    public static Param getCachedParam(String stName, String pmName){
        addValues(stName, pmName);
        return findStation(stName).findSensor(pmName).getParam();
    }

    /**
     * Metoda dodająca wartości dla danego parametru i dla wszystkich stacji do pamięci cache
     * @param pmName nazwa parametru
     */

    public static void addAllForSensor(String pmName){
        addStations();
        for(Station station: cachedStations) {
            addSensorsForStation(station);
            for(Sensor sensor: station.getSensors()){
                if(sensor.getParam().found(pmName))
                    addValuesForSensor(sensor);
            }
        }
        serialize();
    }

    /**
     * Metoda dodająca wszystkie czujniki i wartości dla danej stacji do pamięci cache
     * @param stName nazwa stacji
     */

    public static void addAllForStation(String stName){
        addStations();
        Station station = findStation(stName);
        addSensorsForStation(station);
        for(Sensor sensor: station.getSensors()){
            addValuesForSensor(sensor);
        }
        serialize();
    }

    /**
     * Metoda dodająca wszystkie stacje, czujniki i wartości parametrów
     */

    public static void addAll(){
        addStations();
        for(Station station: cachedStations){
            addSensorsForStation(station);
            for(Sensor sensor: station.getSensors()){
                addValuesForSensor(sensor);
            }
        }
        serialize();
    }

    private static void addValuesForSensor(Sensor sensor) {
        int id = sensor.getId();
        Param param = sensor.getParam();
        Values[] values;
        if (param.getValues() != null) {
            values = param.getValues();
            if (values.length >= 1 && values[0].isExpired()) {
                values = getValues(id);
                param.setValues(values);
                sensor.setParam(param);
            }
        } else {
            values = getValues(id);
            param.setValues(values);
            sensor.setParam(param);
        }
    }

}
