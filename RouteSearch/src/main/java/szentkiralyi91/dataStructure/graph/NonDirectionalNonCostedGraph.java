/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package szentkiralyi91.dataStructure.graph;

import szentkiralyi91.dataStructure.Pair;
import szentkiralyi91.dataStructure.exceptions.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Szentkirályi Károly
 */
public class NonDirectionalNonCostedGraph extends Graph {

    public NonDirectionalNonCostedGraph() {
        Map<Integer, List<Pair>> e = new HashMap<Integer, List<Pair>>();
        Map<Integer, Pair> pc = new HashMap<Integer, Pair>();
        this.pointsGraphics = pc;
        this.edgeList = e;
        this.type = "NonDirectionalNonCostedGraph";
    }

    @Override
    public void setEdge(int u, Pair v) {
        try {
            if (u == v.getV()) {
                throw new GraphIsNotSimpleException();
            } else if (!isInGraph(u)) {
                throw new ImpossibleSetEdgeException();
            } else if (!isInGraph(v.getV())) {
                throw new ImpossibleSetEdgeException();
            } else if (!isConnectWithPoint(u, v.getV())) {
                edgeList.get(u).add(v);
                edgesCount++;
                edgeList.get(v.getV()).add(new Pair(u));
                edgesCount++;
            }
        } catch (GraphIsNotSimpleException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "Hurokél nem megengedett.");
            //System.err.println("Graph is not simple graph, the program doesn't deal with.");
        } catch (ImpossibleSetEdgeException ex) {
            JOptionPane.showMessageDialog(new JFrame(), "A két csúcs között nem lehet élt beállítani, mert valamelyik csúcs nem létezik.");
            //System.err.println("Cannot set edge because the one of given point doesn't exist.");
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (Integer points : edgeList.keySet()) {
            s += points + ": ";
            for (Pair pair : edgeList.get(points)) {
                s += "[" + pair.getV() + "] ";
            }
            s += "\n";
        }
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }

        NonDirectionalNonCostedGraph ndncg = (NonDirectionalNonCostedGraph) obj;

        if (!ndncg.type.equals("NonDirectionalNonCostedGraph")
                || this.pointsCount != ndncg.pointsCount
                || this.edgesCount != ndncg.edgesCount
                || !this.getPoints().equals(ndncg.getPoints())) {
            return false;
        }

        for (Integer point : ndncg.edgeList.keySet()) {
            for (Pair pair : ndncg.edgeList.get(point)) {
                if (!this.isConnectWithPoint(point, pair.getV())) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void removeEdge(int u, int v) {
        if (isInGraph(u) && isInGraph(v) && isConnectWithPoint(u, v)) {
            for (Pair pair : edgeList.get(u)) {
                if (pair.getV() == v) {
                    edgeList.get(u).remove(pair);
                    edgesCount--;
                    break;
                }
            }
            for (Pair pair : edgeList.get(v)) {
                if (pair.getV() == u) {
                    edgeList.get(v).remove(pair);
                    edgesCount--;
                    break;
                }
            }
        } else {
            try {
                throw new ImpossibleDeleteEdgeException();
            } catch (ImpossibleDeleteEdgeException ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Nem lehet törölni a megadott élt, mert nem létezik.");
                //System.err.println("Cannot delete the choosen edge because it doesn't exist.");
            }
        }
    }

    @Override
    public void setEdgeColor(int u, int v, Color c) {
        if (isConnectWithPoint(u, v)) {
            for (Pair pair : edgeList.get(u)) {
                if (pair.getV() == v) {
                    pair.setColor(c);
                }
            }
            for (Pair pair : edgeList.get(v)) {
                if (pair.getV() == u) {
                    pair.setColor(c);
                }
            }
        }
    }

    @Override
    public void removePoint(int u) {
        if (isInGraph(u)) {
            edgesCount -= getAdj(u).size();
            edgeList.remove(u);
            pointsGraphics.remove(u);
            for (List<Pair> element : edgeList.values()) {
                for (Pair pair : element) {
                    if (pair.getV() == u) {
                        element.remove(pair);
                        edgesCount--;
                        break;
                    }
                }
            }
            pointsCount--;
        } else {
            try {
                throw new ImpossibleDeletePointException();
            } catch (ImpossibleDeletePointException ex) {
                JOptionPane.showMessageDialog(new JFrame(), "Nem lehet törölni a megadott csúcsot, mert nem létezik.");
                //System.err.println("Cannot delete the choosen point because it doesn't exist.");
            }
        }
    }

    @Override
    public void randomGraphGenerate(int percent, boolean isIncludeNegativeValue, boolean isFix, int x, int y) {
        randomPointsGenerate(isFix,x,y);
        randomEdgesGenerateInGraph(percent, isIncludeNegativeValue);
    }

    @Override
    public void randomEdgesGenerateInGraph(int percent, boolean isIncludeNegativeValue) {
        try {
            if (percent < 0 || percent > 100) {
                throw new RandomEdgeSettingException();
            } else {
                Set<Integer> ready = new HashSet<Integer>();
                for (Integer u : edgeList.keySet()) {
                    for (Integer v : edgeList.keySet()) {
                        if (u!=v && isProbabilityTrue(percent) && !ready.contains(v)) {
                            setEdge(u, new Pair(v));
                        }
                    }
                    ready.add(u);
                }
            }

        } catch (RandomEdgeSettingException ex) {
            //System.err.println("Cannot generate edges because given a bad probability in percent");
        }
    }

    @Override
    public double getCost(int u, int v) {
        try {
            throw new NonCostedGraphException();
        } catch (NonCostedGraphException ex) {
            //System.err.println("The graph is non-Costed.");
        }
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public boolean isIncludeNegativCost() {
        try {
            throw new NonCostedGraphException();
        } catch (NonCostedGraphException ex) {
            //System.err.println("The graph is non-Costed.");
        }
        return false;
    }
}

