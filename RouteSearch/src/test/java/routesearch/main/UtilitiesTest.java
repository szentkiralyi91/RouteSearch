/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routesearch.main;

import com.vividsolutions.jts.geom.Coordinate;
import routesearch.utilities.Utilities;
import java.awt.geom.Point2D;
import java.text.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import routesearch.utilities.Place;
import routesearch.utilities.Road;

public class UtilitiesTest {
    
    public UtilitiesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of convertPointFromWktGeometry method, of class Utilities.
     */
    @Test
    public void testConvertPointFromWktGeometry1() {
        String wktGeometry = "";
        Coordinate expResult = null;
        Coordinate result = Utilities.convertPointFromWktGeometry(wktGeometry);
        assertEquals(expResult, result);    
    }
    
    @Test
    public void testConvertPointFromWktGeometry2() {
        String wktGeometry = null;
        Coordinate expResult = null;
        Coordinate result = Utilities.convertPointFromWktGeometry(wktGeometry);
        assertEquals(expResult, result);    
    }   
    
    @Test
    public void testConvertPointFromWktGeometry3() {
        String wktGeometry = "POINT(-38.48760780000001 -12.9710208)";
        Coordinate expResult = new Coordinate(-38.48760780000001, -12.9710208);
        Coordinate result = Utilities.convertPointFromWktGeometry(wktGeometry);
        assertEquals(expResult, result);    
    }       
    
    @Test
    public void convertRoadFromWktGeometry1() {
        String wktGeometry = "";
        Coordinate expResult = null;
        Road result = Utilities.convertRoadFromWktGeometry(wktGeometry);
        assertEquals(expResult, result);    
    }
    
    @Test
    public void convertRoadFromWktGeometry2() {
        String wktGeometry = null;
        Road expResult = null;
        Road result = Utilities.convertRoadFromWktGeometry(wktGeometry);
        assertEquals(expResult, result);    
    }      
    
    @Test
    public void convertRoadFromWktGeometry3() {
        String wktGeometry = "MULTILINESTRING ((10 10, 20 20, 10 40),(40 40, 30 30, 40 20, 30 10))";
        Road expResult = new Road(new Coordinate[]{new Coordinate(10, 10), new Coordinate(20, 20), new Coordinate(10, 40),
                                                   new Coordinate(40, 40), new Coordinate(30, 30), new Coordinate(40, 20), new Coordinate(30, 10)});
        Road result = Utilities.convertRoadFromWktGeometry(wktGeometry);
        assertEquals(expResult, result);    
    }  
    
    @Test(expected = java.lang.NullPointerException.class)
    public void getDistanceBetweenPlaces1(){
        Place p1 = null;
        Place p2 = new Place(null, null, null, new Coordinate(-8,7));
        double expectedDistance = Math.sqrt(257);
        
        assertEquals(expectedDistance, Utilities.getDistanceBetweenPlaces(p1, p2), 0);

    }  
    
    @Test
    public void getDistanceBetweenPlaces2(){
        Place p1 = new Place("asf", "city2", "city", new Coordinate(8,8));
        Place p2 = new Place("afs", "town4", "town", new Coordinate(-8,7));
        double expectedDistance = Math.sqrt(257);
        
        assertEquals(expectedDistance, Utilities.getDistanceBetweenPlaces(p1, p2), 0);

    }      
      
    @Test
    public void isPointOnRoad1(){    
        Road road = new Road(new Coordinate[]{new Coordinate(5, 10), new Coordinate(10, 10)});
        Coordinate coord = new Coordinate(5, 10);
        boolean expected = true;
        assertEquals(expected, Utilities.isPointOnRoad(road, coord));
    }
    
    @Test
    public void isPointOnRoad2(){    
        Road road = new Road(new Coordinate[]{new Coordinate(5, 10), new Coordinate(10, 10)});
        Coordinate coord = new Coordinate(4, 10);
        boolean expected = false;
        assertEquals(expected, Utilities.isPointOnRoad(road, coord));
    }  
    
    @Test
    public void isPointOnRoad3(){    
        Road road = new Road(new Coordinate[]{new Coordinate(5, 10), new Coordinate(10, 10)});
        Coordinate coord = new Coordinate(7, 10);
        boolean expected = true;
        assertEquals(expected, Utilities.isPointOnRoad(road, coord));
    }  
    
    @Test
    public void isPointOnRoad4(){    
        Road road = new Road(new Coordinate[]{new Coordinate(5, 10), new Coordinate(10, 10)});
        Coordinate coord = new Coordinate(7, 9.8);
        boolean expected = false;
        assertEquals(expected, Utilities.isPointOnRoad(road, coord));
    }        
}
