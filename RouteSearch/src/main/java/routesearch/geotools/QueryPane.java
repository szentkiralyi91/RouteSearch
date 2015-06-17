package routesearch.geotools;

import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.map.Layer;
import org.geotools.swing.action.SafeAction;
import org.geotools.swing.table.FeatureCollectionTableModel;
import org.opengis.filter.Filter;

public class QueryPane extends JPanel {

    private FileDataStore dataStore;
    private final JTable table;
    private final JScrollPane scrollPaneForTable;
    private final JTextField textFrom;
    private final JTextField textTo;
    private final MapPane mapPane;

    /**
     * Egy panelra rárakunk két texfieldet, amiben meg lehet adni a helyeket,
     * hogy honnan hová szeretnénk menni. Plusz egy gombot, amivel útvonalat
     * rajzoljuk ki.
     *
     * @param placesFile
     * @param roadsFile
     * @param mapPane
     * @throws Exception
     */
    public QueryPane(final File placesFile, final File roadsFile, final MapPane mapPane) throws Exception {
        this.mapPane = mapPane;
        setLayout(null);

        textFrom = new JTextField(20);
        textFrom.setText("Debrecen");
        add(textFrom);
        textFrom.setBounds(200, 30, 200, 25);

        textTo = new JTextField(20);
        textTo.setText("Tiszaigar");
        add(textTo);
        textTo.setBounds(200, 90, 200, 25);

        //A tábla, amiben megjelenítjük a kiválasztott városok adatait.
        table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setModel(new DefaultTableModel(6, 5));
        table.setVisible(false);

        scrollPaneForTable = new JScrollPane(table);
        add(scrollPaneForTable);
        scrollPaneForTable.setBounds(800, 30, 500, 100);
        scrollPaneForTable.setVisible(false);

        connect(placesFile);
        JButton features = new JButton("Útvonal");
        features.addActionListener(new SafeAction("Útvonal") {
            @Override
            public void action(ActionEvent e) throws Throwable {
                System.out.println("Begining time: " + new Timestamp(new Date().getTime()));
                addLayerFromQueryPane(roadsFile, "roads");
                addLayerFromQueryPane(placesFile, "selectedPlaces");
                //mapPane.createTranzitTable(placesFile, roadsFile);
                filterFeatures();
                
                table.setVisible(true);
                scrollPaneForTable.setVisible(true);
                System.out.println("Ending time: " + new Timestamp(new Date().getTime()));
               
            } 
        });
        add(features);
        features.setBounds(500, 60, 100, 25);
    }

    private void addLayerFromQueryPane(File file, String type) throws Exception {
        ArrayList<String> data = new ArrayList<>();
        switch (type) {
            case "selectedPlaces":
                data.add(textFrom.getText());
                data.add(textTo.getText());
                break;
            case "roads":
                data.add(textFrom.getText());
                data.add(textTo.getText());
                break;
        }
        Layer layer = mapPane.loadLayer(file, type, data);
        mapPane.addLayer(layer);
    }

    private void connect(File file) throws Exception {
        dataStore = FileDataStoreFinder.getDataStore(file);
        if (dataStore == null) {
            JOptionPane.showMessageDialog(null, "Could not connect - check parameters");
        }
        updateQueryUI();
    }

    private void filterFeatures() throws Exception {
        //Kiszedi az adatokat egy shp fileból.
        SimpleFeatureSource source = dataStore.getFeatureSource("places");
        //CQL lekérdezésből létrehoz egy filtert
        Filter filter = CQL.toFilter("name = '" + textFrom.getText() + 
                        "' or name ='" + textTo.getText() + 
                        "' and (type='town' or type = 'village' or type ='city')");
        //A filter segítségével leszűri az adatokat
        SimpleFeatureCollection features = source.getFeatures(filter);
        //Egy tábla modellt hoz létre, amit majd oda lehet adni JTable-nek, hogy megjelenítse.
        FeatureCollectionTableModel model = new FeatureCollectionTableModel(features);
        table.setModel(model);
    }

    private void updateQueryUI() throws Exception {
        table.setModel(new DefaultTableModel(5, 5));
    }
}
