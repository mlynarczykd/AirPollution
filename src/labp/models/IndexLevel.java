package labp.models;

/**
 * Klasa reprezentująca poziom indeksu
 */
public class IndexLevel{
    private String indexLevelName;

    /**
     * @return nazwa poziomu indeksu
     */

    public String getIndexLevelName() {
        return indexLevelName;
    }

    /**
     * Metoda ustawiająca nazwę poziomu indeksu na wybraną
     * @param level nazwa
     */

    public void setIndexLevelName(String level) {
        this.indexLevelName= level;
    }
}
