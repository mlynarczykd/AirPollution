package labp;

import labp.models.Index;
import labp.models.IndexLevel;
import labp.models.Station;
import org.junit.Test;

import static org.junit.Assert.*;

public class FacadeTest {

    @Test
    public void findStation() {
        Station station1 = new Station();
        station1.setStationName("stacja 1");
        station1.setId(1);
        Station station2 = new Station();
        station2.setStationName("stacja 2");
        station2.setId(2);
        Station station3 = new Station();
        station3.setStationName("stacja 3");
        station3.setId(3);
        Station[] stations = {station1,station2,station3};
        Facade facade = new Facade();
        facade.setStations(stations);
        assertEquals(2,facade.findStation("stacja 2").getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findStationExceptions() {
        Station station1 = new Station();
        station1.setStationName("stacja 1");
        station1.setId(1);
        Station station2 = new Station();
        station2.setStationName("stacja 2");
        station2.setId(2);
        Station station3 = new Station();
        station3.setStationName("stacja 3");
        station3.setId(3);
        Station[] stations = {station1,station2,station3};
        Facade facade = new Facade();
        facade.setStations(stations);
        facade.findStation("test");
    }

    @Test
    public void one() {
        Station station1 = new Station();
        station1.setStationName("stacja 1");
        station1.setId(1);
        station1.setIndex(new Index());
        station1.getIndex().setStIndexLevel(new IndexLevel());
        station1.setStIndexLevel("test1");
        Station station2 = new Station();
        station2.setStationName("stacja 2");
        station2.setId(2);
        station2.setIndex(new Index());
        station2.getIndex().setStIndexLevel(new IndexLevel());
        station2.setStIndexLevel("test2");
        Station station3 = new Station();
        station3.setStationName("stacja 3");
        station3.setId(3);
        station3.setIndex(new Index());
        station3.getIndex().setStIndexLevel(new IndexLevel());
        station3.setStIndexLevel("test3");
        Station[] stations = {station1,station2,station3};
        Facade facade = new Facade();
        facade.setStations(stations);
        assertEquals("Stacja: stacja 3 --- aktualny indeks jakości powietrza: test3.", facade.findStation("stacja 3").one());
    }
}