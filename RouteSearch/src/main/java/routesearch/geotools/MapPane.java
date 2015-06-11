package routesearch.geotools;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToolBar;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.filter.text.cql2.CQLException;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapPane;
import org.geotools.swing.action.InfoAction;
import org.geotools.swing.action.NoToolAction;
import org.geotools.swing.action.PanAction;
import org.geotools.swing.action.ResetAction;
import org.geotools.swing.action.ZoomInAction;
import org.geotools.swing.action.ZoomOutAction;
import org.opengis.filter.Filter;

public class MapPane extends JMapPane {

    public MapPane(File file) throws Exception {

        // Térkép létrehozása, amihez később hozzáadjuk a layer-eket
        MapContent map = new MapContent();
        map.setTitle("Hungary");

        //Layer betöltése
        Layer layer = LoadLayer(file);

        LoadTools();

        //Térképhez hozzáadjuk a layert.
        map.addLayer(layer);
        setSize(new Dimension(1366,500));
        setMapContent(map);
    }

    /**
     * Eszközök betöltése.
     */
    private void LoadTools() {
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
     * Layer betöltése
     * @param file
     * @return
     * @throws CQLException
     * @throws IOException 
     */
    private Layer LoadLayer(File file) throws CQLException, IOException {
        FileDataStore store = FileDataStoreFinder.getDataStore(file);
        SimpleFeatureSource featureSource = store.getFeatureSource();
        Filter filter = CQL.toFilter("type='town' or type = 'village' or type ='city'");
        SimpleFeatureCollection features = featureSource.getFeatures(filter);
        //stílus beállítás
        Style style = SLD.createSimpleStyle(features.getSchema());
        Layer layer = new FeatureLayer(features, style);
        return layer;
    }

}
