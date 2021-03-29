package ru.stqa.fortesters.soap;

import com.lavasoft.GeoIPService;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class GeoIPServiceTests {

    @Test (enabled = true)
    public void testMyIp() {
        String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("178.70.78.44");
        assertEquals(geoIP, "<GeoIP><Country>RU</Country><State>66</State></GeoIP>");
    }
}
