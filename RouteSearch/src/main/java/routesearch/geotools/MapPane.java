package routesearch.geotools;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.filter.text.cql2.CQL;
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
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory;

public class MapPane extends JMapPane {

    private final MapContent map;
    private final StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();
    private final FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();

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
        
        System.out.println(map.layers().size());
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
                //CQL lekérdezésből létrehoz egy filtert
                Filter filter = CQL.toFilter("type='town' or type = 'village' or type ='city'");
                //A filter segítségével leszűri az adatokat
                SimpleFeatureCollection features = featureSource.getFeatures(filter);
                //Stílus beállítás
                Style style = SLD.createSimpleStyle(features.getSchema());
                //Layer létrehozása az adatok, és a stílus alapján
                layer = new FeatureLayer(features, style);
                break;
            }
            case "roads": {
                Filter filter = CQL.toFilter("ref='M5'");
                SimpleFeatureCollection features = featureSource.getFeatures(filter);
                Style style = createStyle(featureSource);
                layer = new FeatureLayer(features, style);
                break;
            }
            case "selectedPlaces": {
                Filter filter = CQL.toFilter("name = '" + data.get(0) + 
                        "' or name ='" + data.get(1) + 
                        "' and (type='town' or type = 'village' or type ='city')");
                SimpleFeatureCollection features = featureSource.getFeatures(filter);
                Style style = createStyle(featureSource);
                layer = new FeatureLayer(features, style);
                break;
            }
        }

        return layer;
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
    private Style createStyle(FeatureSource featureSource) {
        SimpleFeatureType schema = (SimpleFeatureType) featureSource.getSchema();
        Class geomType = schema.getGeometryDescriptor().getType().getBinding();

        if (Polygon.class.isAssignableFrom(geomType)
                || MultiPolygon.class.isAssignableFrom(geomType)) {
            return createPolygonStyle();

        } else if (LineString.class.isAssignableFrom(geomType)
                || MultiLineString.class.isAssignableFrom(geomType)) {
            return createLineStyle();

        } else {
            return createPointStyle();
        }
    }

    /**
     * Multipoligon stílusa: piros
     */
    private Style createPolygonStyle() {

        // piros vonal
        Stroke stroke = styleFactory.createStroke(
                filterFactory.literal(Color.RED),
                filterFactory.literal(1),
                filterFactory.literal(0.5));

        // piros kitöltés, kicsit átlátszó
        Fill fill = styleFactory.createFill(
                filterFactory.literal(Color.RED),
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
    private Style createLineStyle() {
        Stroke stroke = styleFactory.createStroke(
                filterFactory.literal(Color.RED),
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
    private Style createPointStyle() {
        Graphic gr = styleFactory.createDefaultGraphic();

        Mark mark = styleFactory.getCircleMark();

        mark.setStroke(styleFactory.createStroke(
                filterFactory.literal(Color.RED), filterFactory.literal(1)));

        mark.setFill(styleFactory.createFill(filterFactory.literal(Color.RED)));

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
