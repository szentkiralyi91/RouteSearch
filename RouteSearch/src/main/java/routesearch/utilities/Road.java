package routesearch.utilities;

import com.vividsolutions.jts.geom.Coordinate;
import java.util.Arrays;
import java.util.Objects;

/**
 * Az osztály egy adott út reprezentálása. A shape fájlban egy út WKT formátumban, 
 * szakaszsorozatként (MULTILINESTRING) van megadva, így az osztály lényegében egy
 * koordináta tömböt tartalmaz, valamint az shp-ban található ID-jét (ha az kinyerhető)
 *
 */
public class Road {
    
    private final String osmId;
    private final Coordinate[] coordinates;

    public Road(String osmId, Coordinate[] coordinates) {
        this.osmId = osmId;
        this.coordinates = coordinates;
    }

    public Road(Coordinate[] coordinates) {
        this.osmId = null;
        this.coordinates = coordinates;
    }
      

    public String getOsmId() {
        return osmId;
    }
    
    public Coordinate[] getCoordinates() {
        return coordinates;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.osmId);
        hash = 47 * hash + Arrays.deepHashCode(this.coordinates);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Road other = (Road) obj;       
        return Arrays.deepEquals(this.coordinates, other.coordinates);
    }

    public String getCoordinateString(){
        String returnString = "";
        for(int i=0; i<coordinates.length; i++){
            returnString += coordinates[i].x + " " + coordinates[i].y;
            if(i != coordinates.length-1)
                returnString += ", ";
        }
        return returnString;        
    }

    @Override
    public String toString() {
        return getCoordinateString();
    }
    
    /**
     * Az út realtív hosszát (első és utolsó pontja közötti egyenes hosszát) határozza meg
     */
    public double getRelativeLength(){
        Coordinate first = new Coordinate(this.getCoordinates()[0].x, this.getCoordinates()[0].y);
        Coordinate last = new Coordinate(this.getCoordinates()[this.getCoordinates().length-1].x, 
                                         this.getCoordinates()[this.getCoordinates().length-1].y);
        
        return Math.abs(Math.sqrt(Math.pow(first.x - last.x, 2) + Math.pow(first.y - last.y, 2)));
    }
}
