package routesearch.geotools;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFactorySpi;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.Query;
import org.geotools.data.postgis.PostgisNGDataStoreFactory;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.swing.action.SafeAction;
import org.geotools.swing.data.JDataStoreWizard;
import org.geotools.swing.table.FeatureCollectionTableModel;
import org.geotools.swing.wizard.JWizard;
import org.opengis.feature.type.FeatureType;
import org.opengis.filter.Filter;

/**
 * The Query Lab is an excuse to try out Filters and Expressions on your own
 * data with a table to show the results.
 * <p>
 * Remember when programming that you have other options then the CQL parser,
 * you can directly make a Filter using CommonFactoryFinder.getFilterFactory2().
 */
public class QueryPane extends JPanel {

    private DataStore dataStore;
    private JComboBox featureTypeCBox;
    private JTable table;
    private JTextField text;
        
    public QueryPane() {
        setPreferredSize(new Dimension(1366,100));
        text = new JTextField(80);
        text.setText("include"); // include selects everything!
        add(text, BorderLayout.NORTH);

        table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setModel(new DefaultTableModel(5, 5));
        table.setPreferredScrollableViewportSize(new Dimension(500, 200));
        
        JButton features = new JButton("Features");
        features.addActionListener(new SafeAction("Get features") {
            @Override
            public void action(ActionEvent e) throws Throwable {
                filterFeatures();
            }
        });
        add(features);
//        dataMenu.add(new SafeAction("Count") {
//            public void action(ActionEvent e) throws Throwable {
//                countFeatures();
//            }
//        });
//        dataMenu.add(new SafeAction("Geometry") {
//            public void action(ActionEvent e) throws Throwable {
//                queryFeatures();
//            }
//        });
    }

    private void connect(DataStoreFactorySpi format) throws Exception {
        JDataStoreWizard wizard = new JDataStoreWizard(format);
        int result = wizard.showModalDialog();
        if (result == JWizard.FINISH) {
            Map<String, Object> connectionParameters = wizard.getConnectionParameters();
            dataStore = DataStoreFinder.getDataStore(connectionParameters);
            if (dataStore == null) {
                JOptionPane.showMessageDialog(null, "Could not connect - check parameters");
            }
            updateUI();
        }
    }

//    private void updateUI() throws Exception {
//        ComboBoxModel cbm = new DefaultComboBoxModel(dataStore.getTypeNames());
//        featureTypeCBox.setModel(cbm);
//
//        table.setModel(new DefaultTableModel(5, 5));
//    }

    private void filterFeatures() throws Exception {
        String typeName = (String) featureTypeCBox.getSelectedItem();
        SimpleFeatureSource source = dataStore.getFeatureSource(typeName);

        Filter filter = CQL.toFilter(text.getText());
        //Filter filter = CQL.toFilter("type='town'");
        SimpleFeatureCollection features = source.getFeatures(filter);
        FeatureCollectionTableModel model = new FeatureCollectionTableModel(features);
        table.setModel(model);
    }

    private void countFeatures() throws Exception {
        String typeName = (String) featureTypeCBox.getSelectedItem();
        SimpleFeatureSource source = dataStore.getFeatureSource(typeName);

        Filter filter = CQL.toFilter(text.getText());
        SimpleFeatureCollection features = source.getFeatures(filter);

        int count = features.size();
        JOptionPane.showMessageDialog(text, "Number of selected features:" + count);
    }

    private void queryFeatures() throws Exception {
        String typeName = (String) featureTypeCBox.getSelectedItem();
        SimpleFeatureSource source = dataStore.getFeatureSource(typeName);

        FeatureType schema = source.getSchema();
        String name = schema.getGeometryDescriptor().getLocalName();

        Filter filter = CQL.toFilter(text.getText());

        Query query = new Query(typeName, filter, new String[]{name});

        SimpleFeatureCollection features = source.getFeatures(query);

        FeatureCollectionTableModel model = new FeatureCollectionTableModel(features);
        table.setModel(model);
    }
}