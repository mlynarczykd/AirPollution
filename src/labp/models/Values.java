package labp.models;

import labp.Cachable;
import labp.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Klasa reprezentująca wartości mierzone dla parametrów
 */

public class Values implements Cachable, Component, Comparable<Values> {
    private Date date;
    private double value;
    private Date expireDate;

    /**
     * Metoda ustawiająca wartość pomiaru na wybraną
     * @param value wybrana wartość pomiaru
     */
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String one() {
        return null;
    }


    @Override
    public double three(Date begDate, Date endDate) {
        if ((this.date.equals(begDate) || this.date.after(begDate) && (this.date.equals(endDate) || this.date.before(endDate)))){
            if(this.value != 0)
                return this.value;
            else
                throw new IllegalArgumentException("Brak danych dla daty " + this.date);
        }
        else
            return 0;
    }


    /**
     * @param date wybrana data
     * @return wartość pomiaru jeśli pomiar mieści się w przedziale czasowym, 0 w przeciwnym przypadku
     */
    @Override
    public double four(Date date) {
        if(this.date.equals(date) || this.date.after(date))
            return this.value;
        else
            return 0;
    }

    /**
     * Bezargumentowy konstruktor obliczający datę wygaśniecia ważności przechowywanych danych
     */
    public Values(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(cal.MINUTE, 60);
        this.expireDate = cal.getTime();
    }

    /**
     * Konstruktor
     * @param value wartośćpomiaru
     */
    public Values(double value){
        this.value = value;
    }

    /**
     * Konstruktor
     * @param date data pomiaru
     * @param value wartośćpomiaru
     */
    public Values(Date date, double value) {
        this.date = date;
        this.value = value;
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
     * Metoda zamieniająca Values na String
     */
    @Override
    public String toString(){
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        if(value != 0)
            return "data: " + formatter.format(this.date) + " --- " + "wartość: " + this.value + '\n';
        else
            return "data: " + formatter.format(this.date) + " --- " + "wartość: brak danych" + '\n';
    }

    /**
     * @param date wybrana data
     * @return true jeśli data pomiaru jest taka sama lub wcześniejsza niż przekazana jako argument
     */
    public boolean found(Date date){
        if(this.date.equals(date) || date.after(this.date))
            return true;
        else
            return false;
    }

    /**
     * @param date wybrana data
     * @return true jeśli data pomiaru jest taka sama co przekazana jako argument
     */
    public boolean found2(Date date){
        if(this.date.equals(date))
            return true;
        else
            return false;
    }

    /**
     * @return wartość pomiaru
     */
    public double getValue() {
        return this.value;
    }

    /**
     * @return data pomiaru
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * @param date wybrana data
     * @return wartość pomiaru jeśli data pomiaru jest taka sama co przekazana jako argument, 0 w przeciwnym wypadku
     */
    public double five(Date date) {
        if(this.date.equals(date))
            return this.value;
        else
            return 0;
    }

    /**
     * @param begDate data początkowa
     * @param endDate data końcowa
     * @return true jeśli data pomiaru znajduje się w podanym przedziale czasowym
     */

    public boolean found(Date begDate, Date endDate) {
        if ((this.date.equals(begDate) || this.date.after(begDate) && (this.date.equals(endDate) || this.date.before(endDate)))){
            if(this.value != 0)
                return true;
            else
                throw new IllegalArgumentException("Brak danych dla daty " + this.date);
        }
        else
            return false;
    }

    /**
     * Metoda służąca do porównywania wartości
     * @param values wartość do porównania
     * @return -1 jeśli wartość jest mniejsza od porównywanej, 0 jeśli są takie same, 1 jeśli jest większa
     */
    @Override
    public int compareTo(Values values) {
        if(this.getDate().compareTo(values.getDate()) == 0)
            return 1;
        return this.getDate().compareTo(values.getDate());
    }

    /**
     * @param o obiekt do porównania
     * @return true jeśli obiekty są takie same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Values)) return false;
        Values values = (Values) o;
        return Double.compare(values.getValue(), getValue()) == 0 &&
                Objects.equals(getDate(), values.getDate()) &&
                Objects.equals(getExpireDate(), values.getExpireDate());
    }

}

