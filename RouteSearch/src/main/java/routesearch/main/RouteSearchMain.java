package routesearch.main;

import java.io.File;
import java.io.IOException;
import org.geotools.swing.data.JFileDataStoreChooser;
import routesearch.geotools.MapFrame;
import routesearch.geotools.MapPane;
import routesearch.geotools.QueryPane;
import routesearch.geotools.SelectionLab;

public class RouteSearchMain {

    public static final String PATH_TO_SHPS = "..\\shapefile\\hungary\\";

    public static File currentFile;

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        currentFile = getFile("places");
        if (currentFile != null) {
            //SelectionLab.run(currentFile);
            new MapFrame(currentFile);
//            QueryPane.run();
        } else {
            System.err.println("Nope :(");
        }
    }

    public static File getFile(String shapeFileName) throws IOException {
        File file = null;
        if (shapeFileName != null) {
            file = new File(PATH_TO_SHPS + shapeFileName + ".shp");
        }

        if (file != null && !file.exists() && !file.isDirectory()) {
            System.out.println("Given filename [" + shapeFileName + "] not found in path: " + file.toString());
            System.out.println("Browsing file...");
            file = JFileDataStoreChooser.showOpenFile("shp", null);
        }

        if (file == null) {
            System.err.println("Could not read file!");
            return null;
        }

        System.out.println("Returning file: " + file.toString());
        return file;

    }

    //<editor-fold defaultstate="collapsed" desc="Setters, Getters">
    public static File getCurrentFile() {
        return currentFile;
    }

    public static void setCurrentFile(File currentFile) {
        RouteSearchMain.currentFile = currentFile;
    }
    // </editor-fold>
}
