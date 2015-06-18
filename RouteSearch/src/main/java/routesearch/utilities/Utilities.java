package routesearch.utilities;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


/**
 * Az osztály a feladat megoldásához szükséges (főként geometraia és konverziós) 
 * számítási függvényeket tartalmazza.
 * 
 */
public class Utilities {
    
    /**
     * Egy String-ként megadott WKT-beli pont koordinátát konvertál Coordináta típusra
     * @param wktGeometry String-ként leírt WKT geometriai alakzat (POINT)
     * @return Coordináta típus, a WKT-ban megadott pont koordinátája
     */
    public static Coordinate convertPointFromWktGeometry(String wktGeometry){      
        try {                    
            if(wktGeometry == null || "".equals(wktGeometry)) 
                return null;
            
            Geometry point = new WKTReader().read(wktGeometry);
            return new Coordinate(point.getCoordinate().x, point.getCoordinate().y);
            
        } catch (ParseException ex) {
            System.err.println("ParseException while parsing WKTgeometry["+wktGeometry+"] to Point: " + ex.getMessage());
        }
        return null;
    }
 
    /**
     * Egy String-ként megadott WKT-beli MULTILINESTRING koordinátáit konvertálja Road típusra
     * @param wktGeometry String-ként leírt WKT geometriai alakzat (MULTILINESTRING)
     * @return Road az az út, amit megad a szakaszsorozat
     */    
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
    
    /**
     * Egy út (Road) és egy pont közötti távolságot számolja ki
     * @param road az Út 
     * @param point a Pont
     * @return távolság (double)
     */
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
        
        return minDistance;      
    }
    
    /**
     * Két pont közötti távolságot számolja ki
     * @param P egyik pont
     * @param V másik pont
     * @return távolság
     */
    public static double getDistanceBetweenPoints(Coordinate P, Coordinate V){
        return Point2D.distance(P.x, P.y, V.x, V.y);        
    }
    
    /**
     * Két település (Place) közötti távolságot számolja ki
     * @param p1 egyik település
     * @param p2 másik település
     * @return távolság
     */ 
    public static double getDistanceBetweenPlaces(Place p1, Place p2){
        return Utilities.getDistanceBetweenPoints(p1.getCoordinate(), p2.getCoordinate());
    }   
    
    /**
     * Meghatározza, hogy a megadott úton található-e a megadott pont
     * @param road út 
     * @param point pont
     * @return TRUE ha a pont az úton található, különben FALSE
     */
    public static boolean isPointOnRoad(Road road, Coordinate point){     
        return Utilities.getDistanceBetweenRoadAndPoint(road, point) < 0.1;
    }
    
    /**
     * Meghatározza, hogy a két megadott út keresztezi-e egymást
     * @param r1 egyik út
     * @param r2 másik út
     * @return TRUE ha metszi egymást a két út, különben FALSE
     */
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
    
    /**
     * A bejövő utak listáját (List<Road>) leszűkíti azon utak listájára, amik metszenek egy bizonyos utat
     * @param roadList utak listája
     * @param currentRoad az adott út, amivel keressük a metszéspontokat
     * @return leszűkített utak listája
     */
    public static List<Road> getNeighborRoads(List<Road> roadList, Road currentRoad){
        List<Road> returnList = new ArrayList<>();
        
        for(Road r : roadList){
            if(Utilities.doRoadsHaveIntersection(r, currentRoad))
                returnList.add(r);
            
        }
        
        return returnList;
    }
    
    /**
     * Meghatároz egy adott ponthoz lévő legközelebb utat az utak egy listájábáól
     * @param roads utak egy listája
     * @param point pont
     * @return Az az út, amelyik a legközelebb van listából az adott ponthoz
     */
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