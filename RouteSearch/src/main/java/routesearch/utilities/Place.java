/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routesearch.utilities;

import com.vividsolutions.jts.geom.Coordinate;
import java.util.Objects;

public class Place {
    private final String osmId;
    private final String name;
    private final String type;
    private final Coordinate coordinate;

    public Place(String osmId, String name, String type, Coordinate coordinate) {
        this.osmId = osmId;
        this.name = name;
        this.type = type;
        this.coordinate = coordinate;
    }

    public String getOsmId() {
        return osmId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.type);
        hash = 23 * hash + Objects.hashCode(this.coordinate);
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
        final Place other = (Place) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.coordinate, other.coordinate);
    }     

    @Override
    public String toString() {
        return "Place{" + "osmId=" + osmId + ", name=" + name + ", type=" + type + ", coordinate=" + coordinate + '}';
    }
    
    
}
