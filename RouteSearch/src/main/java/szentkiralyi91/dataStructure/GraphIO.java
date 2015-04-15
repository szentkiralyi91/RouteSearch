/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package szentkiralyi91.dataStructure;

import szentkiralyi91.dataStructure.graph.*;
import java.io.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Szentkirályi Károly
 */
public class GraphIO {

    public static Graph graphLoadFromFile(String fileName) throws IOException, ClassNotFoundException {
        Graph g = null;
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        try {
            fileIn = new FileInputStream(fileName);
            in = new ObjectInputStream(fileIn);
            String type = (String) in.readObject();
            if (type.equals("DirectionalCostedGraph")) {
                g = new DirectionalCostedGraph();
            } else if (type.equals("DirectionalNonCostedGraph")) {
                g = new DirectionalNonCostedGraph();
            } else if (type.equals("NonDirectionalCostedGraph")) {
                g = new NonDirectionalCostedGraph();
            } else {
                g = new NonDirectionalNonCostedGraph();
            }
            g.setEdgeList((Map<Integer, List<Pair>>) in.readObject());
            g.setPointsCount((Integer) in.readObject());
            g.setEdgesCount((Integer) in.readObject());
            g.setPointsGraphics((Map<Integer, Pair>) in.readObject());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Fájlkezelési hiba");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "A beolvasott gráf hibás");
        } 
            finally {
            if (in != null) {
                in.close();
            }
            if (fileIn != null) {
                fileIn.close();
            }
        }
        g.setAllPointColorForBlack();
        g.setAllEdgeColorForBlack();

        return g;
    }
    
    public static void graphSaveToFile(String fileName, Graph g) throws IOException {
        FileOutputStream fileOut = null;
        ObjectOutputStream out = null;
        try {
            fileOut =
                    new FileOutputStream(fileName);
            out =
                    new ObjectOutputStream(fileOut);
            out.writeObject(g.getType());
            out.writeObject(g.getEdgeList());
            out.writeObject(g.getPointsCount());
            out.writeObject(g.getEdgesCount());
            out.writeObject(g.getPointsGraphics());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Fájlkezelési hiba");
        } finally {
            if (out != null) {
                out.close();
                //System.out.println("OK");
            }
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

}

