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
        Coordinate expResult;
        expResult = new Coordinate(-38.48760780000001, -12.9710208);
        Coordinate result = Utilities.convertPointFromWktGeometry(wktGeometry);
        assertEquals(expResult, result);    
    }  
    
    /*
    @Test(expected=ParseException.class)
    public void testConvertPointFromWktGeometry4() {
        String wktGeometry = "POINT(-38.48760780000001 -12.9710208)";
        Coordinate result = Utilities.convertPointFromWktGeometry(wktGeometry);
    }     
    */
}
