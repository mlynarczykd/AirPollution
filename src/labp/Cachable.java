package labp;

import java.util.Date;

/**
 * Interfejs, który musi zostać zaimplementowany przez wszystkie obiekty przecowywane w pamięci cache.
 */
public interface Cachable {

    /**
     * Metoda, która określa czy dany obiekt jest jeszcze aktualny
     * @return true jeśli obiekt nie jest już aktualny
     */
    boolean isExpired();

    /**
     * @return data wygaśnięcia ważności danych
     */

    Date getExpireDate();

}
