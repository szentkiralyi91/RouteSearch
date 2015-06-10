/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routesearch.main;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jani
 */
public class Utilities {
    
    public static Point2D convertPointFromWktGeometry(String wktGeometry){
        
        try {         
            
            if(wktGeometry == null || "".equals(wktGeometry)) 
                return null;
            
            Geometry point = new WKTReader().read(wktGeometry);
            return new Point2D.Double(point.getInteriorPoint().getX(), point.getInteriorPoint().getY());
            
        } catch (ParseException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
