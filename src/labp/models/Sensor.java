package labp.models;

import labp.Cachable;

import java.util.*;

/**
 * Klasa reprezentująca stanowisko pomiarowe
 */

public class Sensor implements Cachable {
    private int id;
    private int stationId;
    private Param param;
    private Date expireDate;

    /**
     * Metoda ustawiająca id
     * @param id id stanowiska
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Bezargumentowy konstruktor obliczający datę wygaśniecia ważności przechowywanych danych
     */

    public Sensor(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(cal.YEAR, 1);
        this.expireDate = cal.getTime();
    }

    /**
     * @return data wygaśnięcia ważności danych
     */
    @Override
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
     * @return id stanowiska pomiarowego
     */
    public int getId() {
        return id;
    }


    /**
     * @return (@link Param)
     */
    public Param getParam(){
        return this.param;
    }

    /**
     * Metoda ustawiajaca parametr na wybrany
     * @param param wybrany parametr
     */
    public void setParam(Param param) {
        this.param = param;
    }


}
