package routesearch.utilities;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

public class Utilities {
    
    public static Coordinate convertPointFromWktGeometry(String wktGeometry){      
        try {                    
            if(wktGeometry == null || "".equals(wktGeometry)) 
                return null;
            
            Geometry point = new WKTReader().read(wktGeometry);
            return new Coordinate(point.getInteriorPoint().getX(), point.getInteriorPoint().getY());
            
        } catch (ParseException ex) {
            System.err.println("ParseException while parsing WKTgeometry["+wktGeometry+"] to Point: " + ex.getMessage());
        }
        return null;
    }
    
    public static Road convertRoadFromWktGeometry(String wktGeometry){
         try {                    
            if(wktGeometry == null || "".equals(wktGeometry)) 
                return null;
            
            Geometry geom = new WKTReader().read(wktGeometry);
            return new Road(geom.getCoordinates());
            
        } catch (ParseException ex) {
            System.err.println("ParseException while parsing WKTgeometry["+wktGeometry+"] to Road: " + ex.getMessage());
        }
        return null;
    }   
    
    public static double getDistanceBetweenRoadAndPoint(Road road, Coordinate point){
        
        double distance;
        double minDistance;
        
        minDistance = Line2D.ptSegDist(road.getCoordinates()[0].x, road.getCoordinates()[0].y, 
                                       road.getCoordinates()[1].x, road.getCoordinates()[1].y,
                                       point.x, point.y);
        
        for(int i=0; i<road.getCoordinates().length - 1; i++){
            Coordinate curr = road.getCoordinates()[i];
            Coordinate next = road.getCoordinates()[i+1];
            distance = Line2D.ptSegDist(curr.x, curr.y, next.x, next.y, point.x, point.y);
            
            if(distance < minDistance)
               minDistance = distance;           
        }
        
//        System.out.println("DISTANCRE: " + minDistance);
        return minDistance;      
    }
    
    public static double getDistanceBetweenPoints(Coordinate P, Coordinate V){
        return Point2D.distance(P.x, P.y, V.x, V.y);        
    }
    
    public static boolean isPointOnRoad(Road road, Coordinate point){     
        return Utilities.getDistanceBetweenRoadAndPoint(road, point) < 0.0;
    }
    
    public static boolean doRoadsHaveIntersection(Road r1, Road r2) {
        
        Line2D line1;
        Line2D line2;
        
        if(r1.equals(r2))
            return false;
        
        for(int i=0; i<r1.getCoordinates().length - 1; i++){
            double r1CurrentX = r1.getCoordinates()[i].x;
            double r1CrrentY = r1.getCoordinates()[i].y;           
            double r1NextX = r1.getCoordinates()[i+1].x;
            double r1NextY = r1.getCoordinates()[i+1].y;               
            line1 = new Line2D.Double(r1CurrentX, r1CrrentY, r1NextX, r1NextY);
            
            for(int j=0; j<r2.getCoordinates().length - 1; j++){
                double r2CurrentX = r2.getCoordinates()[j].x;
                double r2CrrentY = r2.getCoordinates()[j].y;           
                double r2NextX = r2.getCoordinates()[j+1].x;
                double r2NextY = r2.getCoordinates()[j+1].y;      
                line2 = new Line2D.Double(r2CurrentX, r2CrrentY, r2NextX, r2NextY);
                
                if(line1.intersectsLine(line2))
                    return true;
                
            }
        }
        
        return false;
    }
    
    public static Road nearestRoadToPoint(List<Road> roads, Coordinate point){       
        double distance;
        
        Road minDistancedRoad = roads.get(0);
        double minDistance = getDistanceBetweenRoadAndPoint(roads.get(0), point);;
        
        
        for(Road road : roads){
            distance = getDistanceBetweenRoadAndPoint(road, point);
            if(distance < minDistance){
                minDistance = distance;
                minDistancedRoad = road;
            }
        }
        
        return minDistancedRoad;
    }
    
    
   
}