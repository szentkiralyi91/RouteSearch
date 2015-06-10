/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routesearch.main;

import java.io.File;
import java.io.IOException;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.swing.data.JFileDataStoreChooser;
import routesearch.learning.*;

/**
 *
 * @author Jani
 */
public class RouteSearchMain {
    
    public static final String PATH_TO_SHPS = "D:\\github\\RouteSearch\\shapefile\\world\\";
    
    
    public static File currentFile;
    

   /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
        QueryLab.run();
        /*
        currentFile = getFile("cities20");
        if(currentFile != null){
            //SelectionLab.run(currentFile);
            Quickstart.run(currentFile);
            //StyleLab.run();
        } else {
            System.err.println("Nope :(");
        }
        */        
    }
    
    public static File getFile(String shapeFileName) throws IOException{
        File file = null;
        if(shapeFileName != null)
            file = new File(PATH_TO_SHPS + shapeFileName + ".shp");
        
 
        if(file != null && !file.exists() && !file.isDirectory()){
            System.out.println("Given filename ["+shapeFileName+"] not found in path: " + file.toString());
            System.out.println("Browsing file...");
            file = JFileDataStoreChooser.showOpenFile("shp", null);
        }
        
        if (file == null){
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
