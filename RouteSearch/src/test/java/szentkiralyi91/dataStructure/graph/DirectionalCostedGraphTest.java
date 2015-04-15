/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package szentkiralyi91.dataStructure.graph;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import szentkiralyi91.dataStructure.Pair;
import szentkiralyi91.dataStructure.GraphIO;
import java.awt.Point;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author szentkiralyi
 */
public class DirectionalCostedGraphTest {
    
    @Test
    public void addPointsToTheGraphTest(){
        DirectionalCostedGraph dcg1 = new DirectionalCostedGraph();
        dcg1.addPoint(1);
        DirectionalCostedGraph dcg2 = new DirectionalCostedGraph();
        dcg2.addPoint(1);
        assertThat(dcg1, is(dcg2));
        
        dcg1.addPoint(2);
        dcg2.addPoint(2);
        assertThat(dcg1, is(dcg2));
         
        dcg1.addPoint(2);
        assertThat(dcg1, is(dcg2));   
        
        dcg1.addPoint(3);
        dcg2.addPoint(4);
        assertThat(dcg1, not(dcg2));
        
        dcg1.addPoint(4);
        dcg1.addPoint(5);
        dcg1.addPoint(6);
        dcg1.addPoint(7);
        dcg1.addPoint(8);
        dcg1.addPoint(9);
        dcg1.addPoint(10);

        dcg2.addPoint(3);
        dcg2.addPoint(5);
        dcg2.addPoint(6);
        dcg2.addPoint(7);
        dcg2.addPoint(8);
        dcg2.addPoint(9);
        dcg2.addPoint(10);
        dcg2.addPoint(11);
        assertThat(dcg1, is(dcg2));
        
    }   
    
    @Test
    public void setEdgeInTheGraphTest(){
        DirectionalCostedGraph dcg1 = new DirectionalCostedGraph();
        DirectionalCostedGraph dcg2 = new DirectionalCostedGraph();
        
        dcg1.addPoint(1);
        dcg1.addPoint(2);
        dcg2.addPoint(1);
        dcg2.addPoint(2);
        
        dcg1.setEdge(1, new Pair(2,2.0));
        dcg2.setEdge(1, new Pair(2,2.0));
        assertThat(dcg1, is(dcg2));
        
        dcg1.setEdge(1, new Pair(1,1.0));
        assertThat(dcg1, is(dcg2));
        
        dcg1.setEdge(1, new Pair(2,3.0));
        assertThat(dcg1, not(dcg2));
        
    }
    
    @Test
    public void removeEdgeFromTheGraphTest(){
        DirectionalCostedGraph dcg1 = new DirectionalCostedGraph();
        DirectionalCostedGraph dcg2 = new DirectionalCostedGraph();
        dcg1.addPoint(1);
        dcg1.addPoint(2);
        dcg1.setEdge(1, new Pair(2,2.0));
        dcg1.addPoint(3);
        
        dcg2.addPoint(1);
        dcg2.addPoint(2);
        dcg2.addPoint(3);
        dcg2.setEdge(1, new Pair(2,2.0)); 
        dcg2.setEdge(1, new Pair(3,2.0)); 
        assertThat(dcg1, not(dcg2));
        
        dcg2.removeEdge(1, 3);
        assertThat(dcg1, is(dcg2));
        
        dcg2.removeEdge(1, 3);
        assertThat(dcg1, is(dcg2));
    } 
    
    @Test
    public void removePointFromTheGraphTest(){
        DirectionalCostedGraph dcg1 = new DirectionalCostedGraph();
        DirectionalCostedGraph dcg2 = new DirectionalCostedGraph();
        
        dcg1.addPoint(1);
        dcg1.addPoint(2);
        dcg1.setEdge(1, new Pair(2,2.0));
        dcg2.addPoint(1);
        dcg2.addPoint(2);
        dcg2.setEdge(1, new Pair(2,2.0));
        dcg2.addPoint(3);
        dcg2.setEdge(1, new Pair(3,2.0)); 
        dcg2.setEdge(3, new Pair(2,2.0)); 
        assertThat(dcg1, not(dcg2));
        assertThat(dcg1.pointsGraphics, not(dcg2.pointsGraphics));
        
        dcg2.removePoint(3);
        assertThat(dcg1, is(dcg2));  
        assertThat(dcg1.pointsGraphics, is(dcg2.pointsGraphics));
        
        dcg2.removePoint(3);
        assertThat(dcg1, is(dcg2));         
    }
    
    @Test
    public void adjOfPointTest(){
        DirectionalCostedGraph dcg = new DirectionalCostedGraph();
        
        dcg.addPoint(1);
        dcg.addPoint(2);
        dcg.addPoint(3);
        dcg.addPoint(4);
        dcg.setEdge(1, new Pair(2,2.0));
        dcg.setEdge(1, new Pair(3,4.0));
        dcg.setEdge(1, new Pair(4,5.0));
        
        List<Pair> list1 = new ArrayList<Pair>();
        list1.add(new Pair(2,2.0));
        list1.add(new Pair(3,4.0));
        list1.add(new Pair(4,5.0));
        List<Pair> dcgList1 = dcg.getAdj(1); 
        assertThat(dcgList1,is(list1));
        
        List<Pair> dcgList2 = dcg.getAdj(2);
        assertThat(dcgList1,not(dcgList2));
        
    }
    
    
    @Test
    public void EdgeCostInTheGraphTest(){
        DirectionalCostedGraph dcg = new DirectionalCostedGraph();
        dcg.addPoint(1);
        dcg.addPoint(2);
        dcg.setEdge(1, new Pair(2,2.0));
        dcg.addPoint(3);
        
        double value = dcg.getCost(1, 2);

        assertThat(2.0, is(value));
        
        value = dcg.getCost(1, 3);
        
        assertThat(Double.POSITIVE_INFINITY, is(value));
    }  
    
    @Test
    public void isIncludeNegativCostTest(){
        DirectionalCostedGraph dcg1 = new DirectionalCostedGraph();
        dcg1.addPoint(1);
        dcg1.addPoint(2);
        dcg1.addPoint(3);
                
        dcg1.setEdge(1, new Pair(2,2.0));
        dcg1.setEdge(1, new Pair(3,-1.0));
        
        assertTrue(dcg1.isIncludeNegativCost());

        DirectionalCostedGraph dcg2 = new DirectionalCostedGraph();
        dcg2.addPoint(1);
        dcg2.addPoint(2);
        dcg2.addPoint(3);
                
        dcg2.setEdge(1, new Pair(2,2.0));
        dcg2.setEdge(1, new Pair(3,1.0));  
        
        assertFalse(dcg2.isIncludeNegativCost());
        
    }
    
    @Test
    public void graphIOTest() throws IOException, ClassNotFoundException{
        DirectionalCostedGraph dcg1 = new DirectionalCostedGraph();
        dcg1.addPoint(1);
        dcg1.addPoint(2);
        dcg1.addPoint(3);
                
        
        dcg1.setEdge(1, new Pair(2,3.0));
        dcg1.setEdge(1, new Pair(3,4.0));
        
        GraphIO.graphSaveToFile("test1.dat", dcg1);
        DirectionalCostedGraph dcg2 = (DirectionalCostedGraph) GraphIO.graphLoadFromFile("test1.dat");

        assertThat(dcg1,is(dcg2));
    }
    
    @Test
    public void graphColorTest(){
        DirectionalCostedGraph dcg1 = new DirectionalCostedGraph();
        dcg1.addPoint(1);
        dcg1.addPoint(2);
        dcg1.addPoint(3);        
        dcg1.setEdge(1, new Pair(2,2.0));
        dcg1.setEdge(1, new Pair(3,1.0));
        
        assertThat(dcg1.getPointColor(1),is(Color.BLACK));
        assertThat(dcg1.getEdgeColor(1, 2),is(Color.BLACK));
        
        assertThat(dcg1.getPointColor(1),is(dcg1.getPointColor(2)));
        
        dcg1.setPointColor(1, Color.RED);
        assertThat(dcg1.getPointColor(1),not(Color.BLACK));
        assertThat(dcg1.getPointColor(1),is(Color.RED));
        assertThat(dcg1.getPointColor(1),not(dcg1.getPointColor(2)));
        
        assertThat(dcg1.getEdgeColor(1, 2),is(dcg1.getEdgeColor(1, 3)));
        
        dcg1.setEdgeColor(1,2,Color.RED);
        assertThat(dcg1.getEdgeColor(1,2),not(Color.BLACK));
        assertThat(dcg1.getEdgeColor(1,2),is(Color.RED));
        assertThat(dcg1.getEdgeColor(1,2),not(dcg1.getEdgeColor(1,3)));  
        
        dcg1.setAllPointColorForBlack();
        assertThat(dcg1.getPointColor(1),is(Color.BLACK));
        assertThat(dcg1.getPointColor(2),is(Color.BLACK));
        assertThat(dcg1.getPointColor(3),is(Color.BLACK));
        
        assertThat(dcg1.getPointColor(5),is(Color.WHITE));        
        
    }
    
    @Test
    public void getPointCoordinatesTest(){
        DirectionalCostedGraph dcg1 = new DirectionalCostedGraph();
        dcg1.addPoint(1);
        dcg1.setPointCoordinates(1, new Point(1,2));
        dcg1.addPoint(2);
        dcg1.setPointCoordinates(2, new Point(1,2));
        dcg1.addPoint(3);
        dcg1.setPointCoordinates(3, new Point(1,1));
        dcg1.addPoint(4);
    
        assertThat(dcg1.getPointCoordinates(1), is(new Point(1,2)));
        assertThat(dcg1.getPointCoordinates(2), is(dcg1.getPointCoordinates(1)));
        assertThat(dcg1.getPointCoordinates(3), not(dcg1.getPointCoordinates(1)));
        assertThat(dcg1.getPointCoordinates(3), is(new Point(1,1)));
        assertThat(dcg1.getPointCoordinates(4), is(new Point(500,100)));
        
        assertNull(dcg1.getPointCoordinates(5));
    }
    
    @Test
    public void randomTest() throws IOException, ClassNotFoundException{
        DirectionalCostedGraph dcg1 = new DirectionalCostedGraph();
        
        dcg1.addPoint(1);
        dcg1.addPoint(2);
        dcg1.addPoint(3);
        
        dcg1.randomEdgesGenerateInGraph(60, false);
        
        GraphIO.graphSaveToFile("randomdcgtest1.dat", dcg1);
        
        DirectionalCostedGraph dcg2 = new DirectionalCostedGraph();
        dcg2 = (DirectionalCostedGraph)GraphIO.graphLoadFromFile("randomdcgtest1.dat");
        assertThat(dcg1, is(dcg2));
        
        dcg1.randomGraphGenerate(40, true, false, 1, 15);
        GraphIO.graphSaveToFile("randomdcgtest2.dat", dcg1);
        DirectionalCostedGraph dcg3 = new DirectionalCostedGraph();
        dcg3 = (DirectionalCostedGraph)GraphIO.graphLoadFromFile("randomdcgtest2.dat");
        assertThat(dcg1, is(dcg3));
        assertThat(dcg1, not(dcg2));
        
    }
    
    @Test
    public void removeAllPointTest(){
        DirectionalCostedGraph dcg1 = new DirectionalCostedGraph();
        dcg1.addPoint(1);
        dcg1.addPoint(2);
        dcg1.addPoint(3);
        
        dcg1.setEdge(1, new Pair(3,3.0));
        dcg1.setEdge(2, new Pair(3,4.0));
        
        DirectionalCostedGraph dcg2 = new DirectionalCostedGraph();
        
        assertThat(dcg1,not(dcg2));
        
        dcg1.removeAllPoint();
        
        assertThat(dcg1,is(dcg2));
        
    }
    
    @Test
    public void test(){
        DirectionalCostedGraph g = new DirectionalCostedGraph();
        g.addPoint(1);
        g.addPoint(2);
        g.addPoint(3);
        g.addPoint(4);
        g.addPoint(5);
        g.addPoint(6);
        g.addPoint(7);
        g.addPoint(8);    
        
        g.setEdge(1, new Pair(2,2.0));
        g.setEdge(1, new Pair(3,1.0));
        g.setEdge(1, new Pair(4,1.0));
        g.setEdge(2, new Pair(5,1.0));
        g.setEdge(2, new Pair(6,1.0));
        g.setEdge(3, new Pair(2,1.0));
        g.setEdge(3, new Pair(4,1.0));
        g.setEdge(3, new Pair(6,1.0));
        g.setEdge(4, new Pair(6,1.0));
        g.setEdge(4, new Pair(7,1.0));
        g.setEdge(5, new Pair(8,1.0));
        g.setEdge(6, new Pair(7,1.0));
        g.setEdge(6, new Pair(8,1.0));
        
        
    }
    
    
}
