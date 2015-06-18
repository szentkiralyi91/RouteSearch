package routesearch.geotools;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.geometry.jts.WKTReader2;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleFactory;
import org.geotools.swing.JMapPane;
import org.geotools.swing.action.InfoAction;
import org.geotools.swing.action.NoToolAction;
import org.geotools.swing.action.PanAction;
import org.geotools.swing.action.ResetAction;
import org.geotools.swing.action.ZoomInAction;
import org.geotools.swing.action.ZoomOutAction;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory;
import routesearch.utilities.Place;
import routesearch.utilities.Road;
import routesearch.utilities.Utilities;

public class MapPane extends JMapPane {

    private final MapContent map;
    private final StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();
    private final FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();
    private SimpleFeatureCollection placesFeature;

    /**
     * Betölti a városokat (places) a térképre.
     *
     * @param placesFile
     * @throws Exception
     */
    public MapPane(File placesFile) throws Exception {
        map = new MapContent();
        map.setTitle("Hungary");

        Layer placesLayer = loadLayer(placesFile, "places", null);
        addLayer(placesLayer);

        loadTools();
    }

    /**
     * A térképhez hozzáadjuk a layert, majd megjelenítjük.
     *
     * @param layer
     */
    protected final void addLayer(Layer layer) {
        /*Ha kirajzoltunk egy útvonalat, akkor 3 layer van a térképen. 
         Ha egy másik útvonalat akarunk kirajzolni, akkor viszont az előzőt el 
         kell tűntetni, azaz az utolsó két layert.*/
        if (map.layers().size() == 3) {
            map.removeLayer(map.layers().get(map.layers().size()-1));
            map.removeLayer(map.layers().get(map.layers().size()-1));
        }
        
        map.addLayer(layer);
        setMapContent(map);
        
        System.out.println("Map layer size: " + map.layers().size());
    }

    /**
     * Layer betöltése fájlból: Attól függően, hogy milyen fájlt adunk meg,
     * különböző események fognak végrehajtódni.
     *
     * @param file
     * @param type
     * @param data
     * @return
     * @throws java.lang.Exception
     */
    protected final Layer loadLayer(File file, String type, ArrayList<String> data) throws Exception {
        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        SimpleFeatureSource featureSource = store.getFeatureSource();
        
        Layer layer = null;
        switch (type) {
            case "places": {
                
                //A filter segítségével leszűri az adatokat
                if(placesFeature == null)
                    placesFeature = getFilteredFeatures(featureSource, "type='town' or type = 'village' or type ='city'");
                //Stílus beállítás
                Style style = SLD.createSimpleStyle(placesFeature.getSchema());
                //Layer létrehozása az adatok, és a stílus alapján
                layer = new FeatureLayer(placesFeature, style);
                break;
            }
            case "roads": {
                SimpleFeatureCollection features; 
                features = getFilteredFeatures(featureSource, "type='primary'");  
                boolean letsTryThis = true;
                if(letsTryThis){
                    WKTReader2 wktReader = new WKTReader2();

                    SimpleFeatureIterator it = features.features();
                    List<Road> allImportantRoads = new ArrayList<>();
                    List<Road> drawingRoads = new ArrayList<>();
                    while (it.hasNext()) {
                        SimpleFeature currentFeature = it.next();
                        Road road = new Road(currentFeature.getAttribute("osm_id").toString(),
                                             wktReader.read(currentFeature.getAttribute("the_geom").toString()).getCoordinates());
                        allImportantRoads.add(road);
                        //System.out.println(road);
                    }    

                    System.out.println("allRoads size    : " + allImportantRoads.size());



                    Place fromPlace = null;
                    Place toPlace = null;
                    boolean routesFound = false;

                    if(placesFeature == null)
                        placesFeature = getFilteredFeatures(featureSource, "type='town' or type = 'village' or type ='city'");                
                    SimpleFeatureIterator placesIterator = placesFeature.features();

                    while(placesIterator.hasNext()){
                        SimpleFeature currentPlace = placesIterator.next();
                        String placeName = currentPlace.getAttribute("name").toString();
                        if(placeName.equalsIgnoreCase(data.get(0))){
                            fromPlace = new Place(placeName,
                                                  currentPlace.getAttribute("name").toString(),
                                                  currentPlace.getAttribute("type").toString(),
                                                  Utilities.convertPointFromWktGeometry(getGetWktGeometryStringFromFeature(currentPlace)));
                        } else if(placeName.equalsIgnoreCase(data.get(1))){
                             toPlace = new Place(placeName,
                                                  currentPlace.getAttribute("name").toString(),
                                                  currentPlace.getAttribute("type").toString(),
                                                  Utilities.convertPointFromWktGeometry(getGetWktGeometryStringFromFeature(currentPlace)));                       
                        }

                        if(fromPlace != null && toPlace != null){
                            routesFound = true;
                            System.out.println("From: " + fromPlace.toString());
                            System.out.println("To  : " + toPlace.toString());
                            break;
                        }
                    }

                    if(routesFound){
                        for(Road r : allImportantRoads){
                            if(Utilities.isPointOnRoad(r, fromPlace.getCoordinate()))
                                drawingRoads.add(r);
                        }

                        boolean b = false;
                        double distance, mindistance = 999999;
                        int counter = 1, drawingListSize = -1;
                        while(b == false){

                            for(Road r : allImportantRoads){
                                if(!drawingRoads.contains(r)){
                                    for( Road d : drawingRoads){
                                        if(Utilities.doRoadsHaveIntersection(r, d)){
                                            distance = Utilities.getDistanceBetweenRoadAndPoint(r, toPlace.getCoordinate());
                                            if(distance < mindistance){
                                                mindistance = distance;
                                                drawingRoads.add(r);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }

                            for(Road r : drawingRoads){
                                if(Utilities.isPointOnRoad(r, toPlace.getCoordinate())){
                                    b = true;
                                    break;
                                }
                            }

                            if(counter == 30 || drawingListSize == drawingRoads.size())
                                break;

                            System.out.println("counter: " + (counter++) + ". drawingRoads size: " + drawingRoads.size());
                            drawingListSize = drawingRoads.size();
                        }



                        System.out.println("drawingRoads size: " + drawingRoads.size());
                        String filterText = "osm_id=" + drawingRoads.get(0).getOsmId();
                        for(int i=1; i<drawingRoads.size(); i++){
                            filterText += " OR osm_id=" + drawingRoads.get(i).getOsmId();
                        }

                        System.out.println("FilterText: " + filterText);                                
                        features = getFilteredFeatures(featureSource, filterText);


                        Style style = createStyle(featureSource, Color.BLUE);
                        layer = new FeatureLayer(features, style);             
                        break;
                    }  else {
                        System.err.println("Can not find one of thiw places in the shp file: [" + data.get(0) + "], [" + data.get(1) + "].");
                    } 
                } else {
                    Style style = createStyle(featureSource, Color.BLUE);
                    layer = new FeatureLayer(features, style);             
                    break;                    
                }
            }
            case "selectedPlaces": {
                SimpleFeatureCollection features = getFilteredFeatures(featureSource, getSelectedPlacesFilter(data.get(0), data.get(1)));
                Style style = createStyle(featureSource, Color.RED);
                layer = new FeatureLayer(features, style);
                break;
            }
        }

        return layer;
    }
    
    /*
    CQL lekérdezésből létrehoz egy filtert és a filter segítségével leszűri az adatokat  
    */
    public static SimpleFeatureCollection getFilteredFeatures(File file, String filterString) throws IOException, CQLException{
        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        SimpleFeatureSource featureSource = store.getFeatureSource();         
        return getFilteredFeatures(featureSource, filterString);  
    }
 
    public static SimpleFeatureCollection getFilteredFeatures(SimpleFeatureSource featureSource, String filterString) throws IOException, CQLException{
        Filter filter = CQL.toFilter(filterString);
        return featureSource.getFeatures(filter);        
    }
    
    public String getSelectedPlacesFilter(String fromWhere, String toWhere){
        return "name = '" + fromWhere + "' or name ='" + toWhere + "' and (type='town' or type = 'village' or type ='city')";
    }
 
  
    public String getGetWktGeometryStringFromFeature(SimpleFeature feature) throws ParseException{
        WKTReader2 wktReader = new WKTReader2();
        String s = wktReader.read(feature.getAttribute("the_geom").toString()).toString();
        return s;
    }
    
    public void createTranzitTable(File placesFile, File roadsFile) throws ParseException, IOException, CQLException{
        SimpleFeatureCollection placesFeatures = getFilteredFeatures(placesFile, "type='city'");
        SimpleFeatureCollection roadsFeatures = getFilteredFeatures(placesFile, "type='primary'");
        
        SimpleFeatureIterator placesFeaturesIterator = placesFeatures.features();
        List<Place> placeList = new ArrayList<>();
        while (placesFeaturesIterator.hasNext()) {
            SimpleFeature currentFeature = placesFeaturesIterator.next();
            placeList.add( new Place(currentFeature.getAttribute("osm_id").toString(),
                                     currentFeature.getAttribute("name").toString(),
                                     currentFeature.getAttribute("type").toString(),
                                      Utilities.convertPointFromWktGeometry(getGetWktGeometryStringFromFeature(currentFeature))));        
        }
            
        System.out.println("Transit table");
        PrintStream printStream = new PrintStream(System.out, true, "ISO-8859-2");
        for (int i=0; i<placeList.size(); i++) {
            for(int j=0; j<placeList.size(); j++) {
                printStream.format("%20s%20s%40s\n", placeList.get(i).getName(), placeList.get(j).getName(), 
                                                        Utilities.getDistanceBetweenPlaces(placeList.get(i),  placeList.get(j)));
                //System.out.format("%20s%20s%40s\n", placeList.get(i).getName(), placeList.get(j).getName(), 
                //                                        Utilities.getDistanceBetweenPlaces(placeList.get(i),  placeList.get(j)));
            }
        }       
    }
       

    /**
     * Eszközök betöltése.
     */
    private void loadTools() {
        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);

        JButton noToolButton = new JButton(new NoToolAction(this));
        toolBar.add(noToolButton);

        JButton infoButton = new JButton(new InfoAction(this));
        toolBar.add(infoButton);

        JButton panButton = new JButton(new PanAction(this));
        toolBar.add(panButton);

        JButton zoomInBtn = new JButton(new ZoomInAction(this));
        toolBar.add(zoomInBtn);

        JButton zoomOutBtn = new JButton(new ZoomOutAction(this));
        toolBar.add(zoomOutBtn);

        JButton resetButton = new JButton(new ResetAction(this));
        toolBar.add(resetButton);

        add(toolBar);
    }

    /**
     * Egy stílust hoz létre, attól függően, hogy multipoligon, egyenes, vagy
     * pont a poligonunk.
     */
    private Style createStyle(FeatureSource featureSource, Color color) {
        SimpleFeatureType schema = (SimpleFeatureType) featureSource.getSchema();
        Class geomType = schema.getGeometryDescriptor().getType().getBinding();

        if (Polygon.class.isAssignableFrom(geomType)
                || MultiPolygon.class.isAssignableFrom(geomType)) {
            return createPolygonStyle(color);

        } else if (LineString.class.isAssignableFrom(geomType)
                || MultiLineString.class.isAssignableFrom(geomType)) {
            return createLineStyle(color);

        } else {
            return createPointStyle(color);
        }
    }

    /**
     * Multipoligon stílusa: piros
     */
    private Style createPolygonStyle(Color color) {

        // piros vonal
        Stroke stroke = styleFactory.createStroke(
                filterFactory.literal(color),
                filterFactory.literal(1),
                filterFactory.literal(0.5));

        // piros kitöltés, kicsit átlátszó
        Fill fill = styleFactory.createFill(
                filterFactory.literal(color),
                filterFactory.literal(0.5));

        PolygonSymbolizer sym = styleFactory.createPolygonSymbolizer(stroke, fill, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[]{rule});
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }

    /**
     * A vonal stílusa: piros
     */
    private Style createLineStyle(Color color) {
        Stroke stroke = styleFactory.createStroke(
                filterFactory.literal(color),
                filterFactory.literal(1));

        LineSymbolizer sym = styleFactory.createLineSymbolizer(stroke, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[]{rule});
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }

    /**
     * A pont stílusa: piros
     */
    private Style createPointStyle(Color color) {
        Graphic gr = styleFactory.createDefaultGraphic();

        Mark mark = styleFactory.getCircleMark();

        mark.setStroke(styleFactory.createStroke(
                filterFactory.literal(color), filterFactory.literal(1)));

        mark.setFill(styleFactory.createFill(filterFactory.literal(color)));

        gr.graphicalSymbols().clear();
        gr.graphicalSymbols().add(mark);
        gr.setSize(filterFactory.literal(5));

        PointSymbolizer sym = styleFactory.createPointSymbolizer(gr, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[]{rule});
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }
}
