package labp;

import labp.models.Station;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void addStations() {
        Reader mock = Mockito.mock(Reader.class);
        mock.read("http://api.gios.gov.pl/pjp-api/rest/station/findAll");
        Mockito.verify(mock).read(Mockito.startsWith("http://"));
        Cache.addStations();
        Station[] stations = Cache.getStations();
        assertFalse(stations == null);
        assertEquals("Wrocław - Bartnicza",stations[0].getStationName());
        assertTrue(new File("stations.txt").exists());
        if(!stations[0].isExpired())
            Mockito.verify(mock,Mockito.never()).getStations();
    }


    @Test
    public void addIndex(){
        Reader mock = Mockito.mock(Reader.class);
        Cache.addStations();
        Cache.addIndex("Nowy Targ, Plac Słowackiego");
        assertFalse(Cache.getStations() == null);
        assertEquals(10254,Cache.findStation("Nowy Targ, Plac Słowackiego").getId());
        assertTrue(Cache.findStation("Nowy Targ, Plac Słowackiego").getIndex() != null);
        Cache.addIndex("Nowy Targ, Plac Słowackiego");
        Mockito.verify(mock,Mockito.atMost(1)).readIndex(Mockito.anyInt());
    }
}