package labp.models;

import labp.Component;
import java.util.Date;
import static java.lang.Math.abs;


/**
 * Klasa reprezentująca parametr
 */
public class Param implements Component{
    private String paramCode;
    private Values[] values;

    /**
     * @return krótka nazwa parametru
     */

    public String getParamCode() {
        return paramCode;
    }

    /**
     * Metoda ustawiająca krótką nazwę parametru
     * @param paramCode wybrana nazwa
     */

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    @Override
    public String one() {
        return null;
    }


    /**
     * Metoda obliczająca średnią wartość zanieczyszczenia w podanym okresie
     * @param begDate data początkowa
     * @param endDate data końcowa
     * @return średnią wartość zanieczyszczenia w podanym okresie
     */

    public double three(Date begDate, Date endDate){
        if(this.values.length >= 1 && begDate.before(this.values[values.length - 1].getDate()) && endDate.after(this.values[0].getDate()))
            if(this.values[0].getValue() != 0)
                throw new IllegalArgumentException("Podano nieprawidłowe daty. Data najstarszego pomiaru: " + this.values[values.length - 1].getDate() + '\n' +"Data najnowszego pomiaru: " + this.values[0].getDate());
            else
                throw new IllegalArgumentException("Podano nieprawidłowe daty. Data najstarszego pomiaru: " + this.values[values.length - 1].getDate() + "," + '\n' +"data najnowszego pomiaru: " + this.values[1].getDate());
        if(this.values.length >= 1 && endDate.after(this.values[0].getDate()))
            if(this.values[0].getValue() != 0)
                throw new IllegalArgumentException("Podano nieprawidłową datę końcową. Data najnowszego pomiaru: " + this.values[0].getDate());
            else
                throw new IllegalArgumentException("Podano nieprawidłową datę końcową. Data najnowszego pomiaru: " + this.values[1].getDate());
        else if(begDate.before(this.values[values.length - 1].getDate()))
            throw new IllegalArgumentException("Podano nieprawidłową datę początkową. Data najstarszego pomiaru: " + this.values[values.length - 1].getDate());
        double sum = 0;
        int count = 0;
        for(Values values: this.values) {
            double tmp = values.three(begDate, endDate);
            if (tmp != 0) {
                sum += tmp;
                count++;
            }
        }
        if (sum != 0 && count != 0)
            return sum/count;
        else
            throw new IllegalArgumentException("Podano nieprawidłowe daty.");
    }


    /**
     * Metoda obliczająca największe wahania w podanym terminie
     * @param date wybrana data
     * @return wartość największych wahań wartości zanieczyszczeń
     */

    @Override
    public double four(Date date) {
        if(this.values.length >= 1 && date.after(this.values[0].getDate()))
            if(this.values[0].getValue() != 0)
                throw new IllegalArgumentException("Podano nieprawidłową datę. Data najnowszego pomiaru: " + this.values[0].getDate());
            else
                throw new IllegalArgumentException("Podano nieprawidłową datę. Data najnowszego pomiaru: " + this.values[1].getDate());
        double min = Double.MAX_VALUE;
        double max = 0;
        double tmp;
        for(Values values: this.values){
            tmp = values.four(date);
            if(tmp > 0) {
                if (tmp > max)
                    max = tmp;
                if (tmp < min)
                    min = tmp;
            }
        }
        return abs(max-min);
    }

    /**
     * @param pmName nazwa parametru
     * @return true jeśli nazwa przekazana w metodzie jest taka sama jak nazwa obiektu wywołującego metodę
     */

    public boolean found (String pmName){
        return this.paramCode.equals(pmName);
    }

    /**
     * @return tablica wartości dla parametru
     */
    public Values[] getValues() {
        return values;
    }

    /**
     * Metoda ustawiająca tablicę wartości dla danego parametru
     * @param values tablica wartości
     */
    public void setValues(Values[] values) {
        this.values = values;
    }

    /**
     * Metoda szukająca wartości zmierzonych w podanym terminie lub po nim
     * @param date wybrana data
     * @return wartość dla podanej daty (jeśli zostałą znaleziona)
     */

    public Values findValues(Date date){
        for(Values values: this.values){
            if (values.found(date))
                return values;
        }
        throw new IllegalArgumentException("Podano nieprawidłową datę. Data najstarszego pomiaru: " + this.values[values.length - 1].getDate() + ". Data najnowszego pomiaru: " + this.values[0].getDate());
    }

    /**
     * Metoda szukająca wartości zmierzonych w podanym terminie
     * @param date wybrana data
     * @return wartość dla podanej daty (jeśli zostałą znaleziona)
     */

    public Values findValues2(Date date){
        for(Values values: this.values){
            if (values.found2(date))
                return values;
        }
        throw new IllegalArgumentException("Podano nieprawidłową datę. Data najstarszego pomiaru: " + this.values[values.length - 1].getDate() + ". Data najnowszego pomiaru: " + this.values[0].getDate());
    }


    /**
     * Metoda zwraca najmniejszą znalezioną wartość w podanym terminie
     * @param date wybrana data
     * @return najmniejsza znaleziona wartość
     */

    public double five(Date date) {
        if(this.values.length >= 1 && date.after(this.values[0].getDate()))
            if(this.values[0].getValue() != 0)
                throw new IllegalArgumentException("Podano nieprawidłową datę. Data najnowszego pomiaru: " + this.values[0].getDate());
            else
                throw new IllegalArgumentException("Podano nieprawidłową datę. Data najnowszego pomiaru: " + this.values[1].getDate());
        double min = Double.MAX_VALUE;
        double tmp;
        for(Values values: this.values){
            tmp = values.five(date);
            if(tmp > 0) {
                if (tmp < min)
                    min = tmp;
            }
        }
        return min;
    }

    /**
     * Metoda zwraca najmniejszą znalezioną wartość w ogóle
     * @return najmniejsza znaleziona wartość
     */

    public Values getMin() {
        Values min = new Values(Double.MAX_VALUE);
        for(Values values: this.values)
            if(values.getValue() > 0 && values.getValue() < min.getValue()){
                min = values;
            }
        if(min.getValue() != Double.MAX_VALUE)
            return min;
        else
            return null;
    }

    /**
     * Metoda zwraca największą znalezioną wartość w ogóle
     * @return największa znaleziona wartość
     */

    public Values getMax() {
        Values max = new Values(0);
        for(Values values: this.values)
            if(values.getValue() > max.getValue()){
                max = values;
            }
        if(max.getValue() != 0)
            return max;
        else
            return null;
    }

    /**
     * Metoda obliczająca przekroczenie normy zanieczyszczenia w podanym terminie
     * @param date wybrana data
     * @param limit norma dla danego parametru
     * @return różnica między wartością a normą
     */

    public double six(Date date, Double limit) {
        Values values = findValues2(date);
        double diff = values.getValue() - limit;
        return diff;
    }
}
