package routesearch.main;

import java.io.File;
import java.io.IOException;
import org.geotools.swing.data.JFileDataStoreChooser;
import routesearch.geotools.MapFrame;

public class RouteSearchMain {

    public static final String PATH_TO_SHPS = "..\\shapefile\\hungary\\";

    public static File placesFile;
    public static File roadsFile;
    public static MapFrame mainMapFrame;

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
              
        placesFile = getFile("places");
        roadsFile = getFile("roads");
        if (placesFile != null && roadsFile != null) {
            mainMapFrame = new MapFrame(placesFile, roadsFile);
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
        return placesFile;
    }

    public static void setCurrentFile(File currentFile) {
        RouteSearchMain.placesFile = currentFile;
    }
    // </editor-fold>
}
