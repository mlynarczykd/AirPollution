package labp.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class StationTest {

    @Test
    public void findSensor() {
        Sensor sensor1 = new Sensor();
        sensor1.setParam(new Param());
        sensor1.getParam().setParamCode("test1");
        sensor1.setId(1);
        Sensor sensor2 = new Sensor();
        sensor2.setParam(new Param());
        sensor2.getParam().setParamCode("test2");
        sensor2.setId(2);
        Sensor sensor3 = new Sensor();
        sensor3.setParam(new Param());
        sensor3.getParam().setParamCode("test3");
        sensor3.setId(3);
        Sensor[] sensors = {sensor1,sensor2,sensor3};
        Station station = new Station();
        station.setSensors(sensors);
        assertEquals(1,station.findSensor("test1").getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findSensorException() {
        Sensor sensor1 = new Sensor();
        sensor1.setParam(new Param());
        sensor1.getParam().setParamCode("test1");
        sensor1.setId(1);
        Sensor sensor2 = new Sensor();
        sensor2.setParam(new Param());
        sensor2.getParam().setParamCode("test2");
        sensor2.setId(2);
        Sensor sensor3 = new Sensor();
        sensor3.setParam(new Param());
        sensor3.getParam().setParamCode("test3");
        sensor3.setId(3);
        Sensor[] sensors = {sensor1,sensor2,sensor3};
        Station station = new Station();
        station.setSensors(sensors);
        station.findSensor("test");
    }


    @Test
    public void getMin() {
        Sensor sensor1 = new Sensor();
        sensor1.setParam(new Param());
        sensor1.getParam().setParamCode("test1");
        Values values1 = new Values(100.05);
        Values values2 = new Values(99.99);
        Values values3 = new Values(111.11);
        Values[] values = {values1, values2, values3};
        sensor1.getParam().setValues(values);
        sensor1.setId(1);
        Sensor sensor2 = new Sensor();
        sensor2.setParam(new Param());
        sensor2.getParam().setParamCode("test2");
        sensor2.setId(2);
        Sensor[] sensors = {sensor1,sensor2};
        Station station = new Station();
        station.setSensors(sensors);
        assertEquals(99.99,station.getMin("test1").getValue(),0);
    }

    @Test
    public void getMax() {
        Sensor sensor1 = new Sensor();
        sensor1.setParam(new Param());
        sensor1.getParam().setParamCode("test1");
        Values values1 = new Values(100.05);
        Values values2 = new Values(99.99);
        Values values3 = new Values(111.11);
        Values[] values = {values1, values2, values3};
        sensor1.getParam().setValues(values);
        sensor1.setId(1);
        Sensor sensor2 = new Sensor();
        sensor2.setParam(new Param());
        sensor2.getParam().setParamCode("test2");
        sensor2.setId(2);
        Sensor[] sensors = {sensor1,sensor2};
        Station station = new Station();
        station.setSensors(sensors);
        assertEquals(111.11,station.getMax("test1").getValue(),0);
    }
}