package routesearch.utilities;

import com.vividsolutions.jts.geom.Coordinate;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import routesearch.geotools.MapPane;
import static routesearch.geotools.MapPane.getFilteredFeatures;

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
        if (!Objects.equals(this.osmId, other.osmId)) {
            return false;
        }
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
}
