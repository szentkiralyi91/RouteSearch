/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routesearch.main;

import routeserach.utilities.Utilities;
import java.awt.geom.Point2D;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jani
 */
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
        System.out.println("convertPointFromWktGeometry");
        String wktGeometry = "";
        Point2D expResult = null;
        Point2D result = Utilities.convertPointFromWktGeometry(wktGeometry);
        assertEquals(expResult, result);    
    }
    
    @Test
    public void testConvertPointFromWktGeometry2() {
        System.out.println("convertPointFromWktGeometry");
        String wktGeometry = null;
        Point2D expResult = null;
        Point2D result = Utilities.convertPointFromWktGeometry(wktGeometry);
        assertEquals(expResult, result);    
    }   
    
    @Test
    public void testConvertPointFromWktGeometry3() {
        System.out.println("convertPointFromWktGeometry");
        String wktGeometry = "POINT(-38.48760780000001 -12.9710208)";
        Point2D expResult;
        expResult = new Point2D.Double(-38.48760780000001, -12.9710208);
        Point2D result = Utilities.convertPointFromWktGeometry(wktGeometry);
        assertEquals(expResult, result);    
    }       
    
}
