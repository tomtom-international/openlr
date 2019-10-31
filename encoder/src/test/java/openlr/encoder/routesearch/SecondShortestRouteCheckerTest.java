package openlr.encoder.routesearch;

import openlr.encoder.OpenLREncoderProcessingException;
import openlr.map.Line;
import openlr.map.teststubs.OpenLRMapDatabaseAdaptor;
import openlr.map.utils.GeometryUtils;
import openlr.map.utils.PQElem;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class SecondShortestRouteCheckerTest {

    private OpenLRMapDatabaseAdaptor map;


   @BeforeTest
   public void loadMapStub(){
       InputStream mapFile = OpenLRMapDatabaseAdaptor.class.getClassLoader().getResourceAsStream("teststubs/TestMapStub.xml");
       this.map = OpenLRMapDatabaseAdaptor.from(mapFile);
   }




    @Test
    public void verifyExistenceOfSecondValidShortestRoute() throws OpenLREncoderProcessingException
    {
        List<Line> location = Arrays.asList(map.getLine(1L),map.getLine(2L),map.getLine(5L),map.getLine(6L));
        SecondShortestRouteChecker checker = SecondShortestRouteChecker.on(location,.20);
        PQElem elem = new PQElem(location.get(0),0,location.get(0).getLineLength(),null);
        assertFalse(checker.exclude(elem,0));


        int actualDistance = location.get(0).getLineLength() + location.get(1).getLineLength();
        int heuristicDistance = actualDistance + (int)GeometryUtils.distance(location.get(1).getEndNode().getGeoCoordinates(),
                map.getLine(6L).getStartNode().getGeoCoordinates());

        PQElem second = new PQElem(location.get(1),heuristicDistance,actualDistance,elem);
        assertTrue(checker.exclude(second,1));
    }




}
