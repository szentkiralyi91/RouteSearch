/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package szentkiralyi91.dataStructure.graph;

import szentkiralyi91.dataStructure.Pair;
import java.awt.Color;
import szentkiralyi91.dataStructure.exceptions.*;
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
public class NonDirectionalCostedGraph extends Graph{

    public NonDirectionalCostedGraph() {
        Map<Integer, List<Pair>> e = new HashMap<Integer, List<Pair>>();
        Map<Integer, Pair> pc = new HashMap<Integer, Pair>();
        this.pointsGraphics = pc;
        this.edgeList = e;
        this.type = "NonDirectionalCostedGraph";
    }

    @Override
    public void setEdge(int u, Pair v) {
        try {
            if (u == v.getV()) {
                throw new GraphIsNotSimpleException();
            }           
            else if (!isInGraph(u)) {
                throw new ImpossibleSetEdgeException();
            }
            else if (!isInGraph(v.getV())) {
                throw new ImpossibleSetEdgeException();
            }  
            else if (!isConnectWithPoint(u, v.getV())) {
                edgeList.get(u).add(v);
                edgesCount++;
                edgeList.get(v.getV()).add(new Pair(u, v.getC()));
                edgesCount++;
            }             
            else {
                for (Pair p : edgeList.get(u)) {
                    if (p.getV() == v.getV()) {
                        p.setC(v.getC());
                        break;
                    }
                }
                for (Pair p : edgeList.get(v.getV())) {
                    if (p.getV() == u) {
                        p.setC(v.getC());
                        break;
                    }
                }
            }
        } catch (GraphIsNotSimpleException ex) {
            //System.err.println("Graph is not simple graph, the program doesn't deal with.");
            JOptionPane.showMessageDialog(new JFrame(), "Hurok él nem megengedett.");
        } catch (ImpossibleSetEdgeException ex){
            //System.err.println("Cannot set edge because the one of given point doesn't exist.");
            JOptionPane.showMessageDialog(new JFrame(), "A két csúcs között nem lehet élt beállítani, mert valamelyik csúcs nem létezik.");
        }   
    }

    @Override
    public double getCost(int u, int v) {
        if (isConnectWithPoint(u, v)) {
            for (Pair pair : edgeList.get(u)) {
                if (pair.getV() == v) {
                    return pair.getC();
                }
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public boolean isIncludeNegativCost() {
        for (Integer points : edgeList.keySet()) {
            for (Pair pair : edgeList.get(points)) {
                if (pair.getC() < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String s = "";
        for (Integer points : edgeList.keySet()) {
            s += points + ": ";
            for (Pair pair : edgeList.get(points)) {
                s += "[" + pair.getV() + "|" + pair.getC() + "] ";
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
        
        Graph g = (Graph) obj;
        
        if(!g.type.equals("NonDirectionalCostedGraph")){
            return false;
        }
        
        NonDirectionalCostedGraph ndcg = (NonDirectionalCostedGraph) obj;
        
        if(this.pointsCount!=ndcg.pointsCount
                ||this.edgesCount!=ndcg.edgesCount
                ||!this.getPoints().equals(ndcg.getPoints())){
            return false;
        }
        
        for (Integer point : ndcg.edgeList.keySet()) {
            for (Pair pair : ndcg.edgeList.get(point)) {
                if(!this.isConnectWithPoint(point, pair.getV())
                        ||this.getCost(point, pair.getV())!=pair.getC()){
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
        if(isConnectWithPoint(u, v)){
            for(Pair pair : edgeList.get(u)){
                if(pair.getV()==v){
                    pair.setColor(c);
                }
            }
            for(Pair pair : edgeList.get(v)){
                if(pair.getV()==u){
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
                //System.err.println("Cannot delete the choosen point because it doesn't exist.");
                JOptionPane.showMessageDialog(new JFrame(), "Nem lehet törölni a megadott csúcsot, mert nem létezik.");
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
        try{
            if(percent < 0 || percent > 100){
                throw new RandomEdgeSettingException();
            } else{
                Set<Integer> ready = new HashSet<Integer>(); 
                for(Integer u : edgeList.keySet()){
                    for(Integer v : edgeList.keySet()){
                        if(u!=v && isProbabilityTrue(percent)&&!ready.contains(v)){
                            setEdge(u, new Pair(v,costGenerate(isIncludeNegativeValue)));
                        }
                    }
                    ready.add(u);
                }
            }
        
        } catch(RandomEdgeSettingException ex){
            //System.err.println("Cannot generate edges because given a bad probability in percent");
        }
    }

    
}

