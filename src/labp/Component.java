package labp;

import java.util.Date;

/**
 * Inerfejs reprezentujący pojedyńcze obiekty jak i ich kontenery
 */
public interface Component {

    /**
     * Metoda znajdująca indeks jakości powietrza dla wybranej stacji
     * @return nazwa stacji i indeks jakości powietrza
     */
    String one();

    /**
     * Metoda obliczająca średnią wartość zanieczyszczenia w podanym okresie
     * @param begDate data początkowa
     * @param endDate data końcowa
     * @return średnią wartość zanieczyszczenia w podanym okresie
     */
    double three(Date begDate, Date endDate);

    /**
     * Metoda obliczająca największe wahania w podanym terminie
     * @param parsedDate wybrana data
     * @return wartość największych wahań wartości zanieczyszczeń
     */
    double four(Date parsedDate);
}
