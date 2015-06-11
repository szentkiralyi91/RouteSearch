package routesearch.geotools;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JFrame;

public class MapFrame extends JFrame {

    public MapFrame(File file) throws Exception {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(new Dimension(1366, 700));
        
        
        QueryPane qp = new QueryPane();
        add(qp);
        qp.setBounds(0, 0, 1366, 100);
        
        MapPane mp = new MapPane(file);
        add(mp);
        mp.setBounds(0, 100, 1366, 600);
        
        setVisible(true);

    }

}
