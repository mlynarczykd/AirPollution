package labp.models;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class ParamTest {

    @Test
    public void three() throws ParseException {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date1 = format.parse("21.01.2019 08:00");
        Date date2 = format.parse("21.01.2019 09:00");
        Date date3 = format.parse("21.01.2019 10:00");
        Date date4 = format.parse("21.01.2019 11:00");
        Values values1 = new Values(date1,5.0);
        Values values2 = new Values(date2,3.0);
        Values values3 = new Values(date3, 7.0);
        Values values4 = new Values(date4, 1.0);
        Values[] values = {values4, values3, values2, values1};
        Param param = new Param();
        param.setValues(values);
        assertEquals(5.0,param.three(date1,date3),0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void threeException() throws ParseException {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date1 = format.parse("21.01.2019 08:00");
        Date date2 = format.parse("21.01.2019 09:00");
        Date date3 = format.parse("21.01.2019 10:00");
        Date date4 = format.parse("21.01.2019 11:00");
        Date date5 = format.parse("20.01.2019 00:00");
        Values values1 = new Values(date1,5.0);
        Values values2 = new Values(date2,3.0);
        Values values3 = new Values(date3, 7.0);
        Values values4 = new Values(date4, 1.0);
        Values[] values = {values4, values3, values2, values1};
        Param param = new Param();
        param.setValues(values);
        param.three(date5,date4);
    }

    @Test
    public void four() throws ParseException {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date1 = format.parse("21.01.2019 08:00");
        Date date2 = format.parse("21.01.2019 09:00");
        Date date3 = format.parse("21.01.2019 10:00");
        Date date4 = format.parse("21.01.2019 11:00");
        Values values1 = new Values(date1,5.0);
        Values values2 = new Values(date2,3.0);
        Values values3 = new Values(date3, 7.0);
        Values values4 = new Values(date4, 1.0);
        Values[] values = {values4, values3, values2, values1};
        Param param = new Param();
        param.setValues(values);
        assertEquals(6.0,param.four(date2),0);
    }
}