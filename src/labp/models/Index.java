package labp.models;

import labp.Cachable;
import java.util.Calendar;
import java.util.Date;

/**
 * Klasa reprezentująca indeks stacji
 */

public class Index implements Cachable {
    private IndexLevel stIndexLevel;
    private Date expireDate;

    /**
     * Bezargumentowy konstruktor obliczający datę wygaśniecia ważności przechowywanych danych
     */

    public Index() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(cal.MINUTE, 60);
        this.expireDate = cal.getTime();
    }

    /**
     * @return data wygaśnięcia ważności danych
     */

    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * Metoda znajdująca indeks jakości powietrza
     * @return indeks jakości powietrza
     */

    public String one() {
        return this.stIndexLevel.getIndexLevelName();
    }

    /**
     * Metoda, która określa czy dany obiekt jest jeszcze aktualny
     * @return true jeśli obiekt nie jest już aktualny
     */

    @Override
    public boolean isExpired() {
        return this.expireDate.before(new Date());
    }

    /**
     * @return (@link IndexLevel)
     */

    public IndexLevel getStIndexLevel() {
        return stIndexLevel;
    }

    private String getStIndexLevelName() {
        return stIndexLevel.getIndexLevelName();
    }

    /**
     * Metoda ustawiająca nazwę poziomu indeksu na wybraną
     * @param level nazwa
     */

    public void setStIndexLevelName(String level){
        this.stIndexLevel.setIndexLevelName(level);
    }

    /**
     * Metoda ustawiająca poziom indeksu na wybrany
     * @param indexLevel (@link IndexLevel)
     */

    public void setStIndexLevel(IndexLevel indexLevel){
        this.stIndexLevel = indexLevel;
    }
}
