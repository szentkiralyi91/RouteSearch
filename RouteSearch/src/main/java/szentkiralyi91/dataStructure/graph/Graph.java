/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package szentkiralyi91.dataStructure.graph;

import szentkiralyi91.dataStructure.Pair;
import szentkiralyi91.dataStructure.exceptions.GraphIncludePointException;
import szentkiralyi91.dataStructure.exceptions.GraphNotIncludePointException;
import szentkiralyi91.dataStructure.exceptions.TooMuchPointsInTheGraphException;
import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Szentkirályi Károly
 */
public abstract class Graph implements Serializable {

    protected final static int MAXPOINTS = 15;
    protected Map<Integer, List<Pair>> edgeList;
    protected int pointsCount = 0;
    protected int edgesCount = 0;
    protected String type;
    protected Map<Integer, Pair> pointsGraphics;

    public static final Point[] DEFAULTPOINTCOORDINATESARRAY = {
        new Point(300,60), new Point(200,120), new Point(400,120),
        new Point(150,180), new Point(450,180), new Point(180,240),
        new Point(400,240), new Point(100,300), new Point(470,300),
        new Point(150,360), new Point(420,360), new Point(120,420),
        new Point(400,420), new Point(250,480), new Point(350,500),
    };

    public static int getMAXPOINTS() {
        return MAXPOINTS;
    }
    
    public void setPointCoordinates(int u, Point p){
        try{
        if(isInGraph(u)){
            pointsGraphics.put(u, new Pair(p));
        } else {

                throw new GraphNotIncludePointException();

            }
        } catch (GraphNotIncludePointException ex) {
            //System.err.println("Cannot set this point coordinate because the graph is not include this point.");
    }
    }
    
    public Set<Integer> getPoints() {
        Set<Integer> pointsSet = new HashSet();
        for (Integer element : edgeList.keySet()) {
            pointsSet.add(element);
        }
        return pointsSet;
    }

    public void setEdgeList(Map<Integer, List<Pair>> edgeList) {
        this.edgeList = edgeList;
    }

    public void setPointsGraphics(Map<Integer, Pair> pointsGraphics) {
        this.pointsGraphics = pointsGraphics;
    }

    public Map<Integer, Pair> getPointsGraphics() {
        return pointsGraphics;
    }

    public int getEdgesCount() {
        return edgesCount;
    }

    public void setPointsCount(int pointsCount) {
        this.pointsCount = pointsCount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract void setEdge(int u, Pair v);

    public Map<Integer, List<Pair>> getEdgeList() {
        return edgeList;
    }

    public String getType() {
        return type;
    }

    public boolean isInGraph(int u) {
        for (Integer points : edgeList.keySet()) {
            if (points.equals(u)) {
                return true;
            }
        }
        return false;
    }

    public boolean isConnectWithPoint(int u, int v) {
        for (Pair p : edgeList.get(u)) {
            if (p.getV() == v) {
                return true;
            }
        }
        return false;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public void setPointColor(int u, Color c) {
        if (isInGraph(u)) {
            Pair p = pointsGraphics.get(u);
            p.setColor(c);
            pointsGraphics.put(u, p);
        }
    }

    public void setAllPointColorForBlack() {
        for (Integer element : pointsGraphics.keySet()) {
            Pair p = pointsGraphics.get(element);
            p.setColor(Color.BLACK);
            pointsGraphics.put(element, p);
        }
    }

    public void setGreenPointForBlack() {
        for (Integer element : pointsGraphics.keySet()) {
            Pair p = pointsGraphics.get(element);
            if (p.getColor().equals(Color.GREEN)) {
                p.setColor(Color.BLACK);
                pointsGraphics.put(element, p);
            }
        }
    }

    public abstract void setEdgeColor(int u, int v, Color c);

    public void setAllEdgeColorForBlack() {
        for (Integer element : edgeList.keySet()) {
            for (Pair pair : edgeList.get(element)) {
                pair.setColor(Color.BLACK);

            }
        }
    }

    public void setGreenEdgeForBlack() {
        for (Integer element : edgeList.keySet()) {
            for (Pair pair : edgeList.get(element)) {
                if (pair.getColor().equals(Color.GREEN)) {
                    pair.setColor(Color.BLACK);
                }
            }
        }
    }

    public Color getEdgeColor(int u, int v) {
        Color c = Color.WHITE;

        if (isConnectWithPoint(u, v)) {
            for (Pair pair : edgeList.get(u)) {
                if (pair.getV() == v) {
                    return pair.getColor();
                }
            }
        }

        return c;
    }

    public Color getPointColor(int u) {
        if (isInGraph(u)) {
            return pointsGraphics.get(u).getColor();
        } else {
            return Color.WHITE;
        }
    }

    public Point getPointCoordinates(int u) {
        if (isInGraph(u)) {
            return pointsGraphics.get(u).getP();
        } else {
            return null;
        }
    }

    public void addPoint(int u) {
        try {
            if (pointsCount < MAXPOINTS) {
            if (!isInGraph(u)) {
                    edgeList.put(new Integer(u), new ArrayList<Pair>());
                    pointsGraphics.put(new Integer(u), new Pair(DEFAULTPOINTCOORDINATESARRAY[pointsCount]));
                    pointsCount++;
                } else {
                    throw new GraphIncludePointException();
                }
            } else {
                throw new TooMuchPointsInTheGraphException();
            }
        } catch (GraphIncludePointException ex) {
            //System.err.println("Cannot add this point to the graph because the graph include this point.");
        } catch (TooMuchPointsInTheGraphException ex) {
            //System.err.println("Cannot be add this point to the graph because"
            //      + "too much points in the graph. Big graph cannot be displayed.");
            JOptionPane.showMessageDialog(new JFrame(), "Nem adhatsz több pontot a gráfhoz.");
        }
    }

    public abstract void removeEdge(int u, int v);

    public abstract void removePoint(int u);

    public void removeAllPoint() {
        edgeList.clear();
        pointsGraphics.clear();
        pointsCount = 0;
        edgesCount = 0;
    }

    public List<Pair> getAdj(int u) {
        List<Pair> adj = new ArrayList<Pair>();
        if (isInGraph(u)) {
            for (Pair pair : edgeList.get(u)) {
                adj.add(pair);
            }
        }
        return adj;
    }

    public void randomPointsGenerate(boolean isFix, int x, int y) {
        removeAllPoint();
        Random r = new Random();

        if (isFix) {
            for (int i = 1; i <= y; i++) {
                addPoint(i);
            }
        } else {
            if (x < y) {
                for (int i = 1; i <= r.nextInt(y-x+1) + x; i++) {
                    addPoint(i);
                }
            } else {
                for (int i = 1; i <= r.nextInt(x-y+1) + y; i++) {
                    addPoint(i);
                }
            }
        }
    }

    public boolean isProbabilityTrue(int percent) {
        if (percent == 0) {
            return false;
        } else if (percent == 100) {
            return true;
        }

        Random r = new Random();

        int v = r.nextInt(100) + 1;

        if (v > 0 && v <= percent) {
            return true;
        } else {
            return false;
        }

    }

    public void setEdgesCount(int edges) {
        edgesCount = edges;
    }

    public abstract double getCost(int u, int v);

    public abstract boolean isIncludeNegativCost();

    public abstract void randomGraphGenerate(int percent, boolean isIncludeNegativeValue, boolean isFix, int x, int y);

    public abstract void randomEdgesGenerateInGraph(int percent, boolean isIncludeNegativeValue);

    public double costGenerate(boolean isIncludeNegativeValue) {
        Random r = new Random();
        if (isIncludeNegativeValue) {
            return ((double) r.nextInt(20) - 5);
        } else {
            return ((double) r.nextInt(15) + 1);
        }
    }

    public void pointsGenerate(int count) {
        for(int i=1;i<=count;i++){
            addPoint(i);
        }
    }
}

