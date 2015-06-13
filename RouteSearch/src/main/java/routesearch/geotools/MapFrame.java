package routesearch.geotools;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JFrame;

public class MapFrame extends JFrame {

    /**
     * Erre a frame-re rajzoljuk ki a térképet, és választó panelt.
     *
     * @param placesFile
     * @param roadsFile
     * @throws Exception
     */
    public MapFrame(File placesFile, File roadsFile) throws Exception {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(new Dimension(1366, 700));

        MapPane mp = new MapPane(placesFile);
        add(mp);
        mp.setBounds(0, 150, 1366, 550);

        QueryPane qp = new QueryPane(placesFile, roadsFile, mp);
        add(qp);
        qp.setBounds(0, 0, 1366, 150);

        setVisible(true);
    }
}
