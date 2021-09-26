package labp.models;

import labp.Cachable;

import java.util.Calendar;
import java.util.Date;

/**
 * Klasa reprezentująca parametr
 */

public class Param2 implements Cachable {
   private String key;
   private Values[] values;
   private Date expireDate;


   /**
    * Bezargumentowy konstruktor obliczający datę wygaśniecia ważności przechowywanych danych
    */

   public Param2(){
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      cal.add(cal.MINUTE, 60);
      this.expireDate = cal.getTime();
   }

   /**
    * @return data wygaśnięcia ważności przechowywanych danych
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
    * @return tablica wartości danego parametru
    */

   public Values[] getValues() {
      return values;
   }
}
