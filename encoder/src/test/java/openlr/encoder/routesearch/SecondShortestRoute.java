package openlr.encoder.routesearch;

import openlr.OpenLRProcessingException;
import openlr.map.Line;
import openlr.map.simplemockdb.OpenLRMapDatabaseAdaptor;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SecondShortestRoute {

    @Test
    public void alternateRouteToDestination() throws OpenLRProcessingException {
        InputStream mapFile = OpenLRMapDatabaseAdaptor.class.getClassLoader().getResourceAsStream("simplemockedmaps/SimpleMockedTestMap.xml");
        OpenLRMapDatabaseAdaptor map = OpenLRMapDatabaseAdaptor.from(mapFile);
        List<Line> location = new ArrayList<>();
        location.add(map.getLine(1));
        location.add(map.getLine(2));
        location.add(map.getLine(5));
        location.add(map.getLine(6));
        RouteSearch routeSearch = new RouteSearch(location);
        RouteSearchResult result = routeSearch.calculateRoute();
        assertTrue(result.hasSecondIntermediate());
    }


}
