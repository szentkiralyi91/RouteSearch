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
public class NonDirectionalCostedGraphTest {
    
    @Test
    public void addPointsToTheGraphTest(){
        NonDirectionalCostedGraph ndcg1 = new NonDirectionalCostedGraph();
        ndcg1.addPoint(1);
        NonDirectionalCostedGraph ndcg2 = new NonDirectionalCostedGraph();
        ndcg2.addPoint(1);
        assertThat(ndcg1, is(ndcg2));
        
        ndcg1.addPoint(2);
        ndcg2.addPoint(2);
        assertThat(ndcg1, is(ndcg2));
         
        ndcg1.addPoint(2);
        assertThat(ndcg1, is(ndcg2));   
        
        ndcg1.addPoint(3);
        ndcg2.addPoint(4);
        assertThat(ndcg1, not(ndcg2));
        
        ndcg1.addPoint(4);
        ndcg1.addPoint(5);
        ndcg1.addPoint(6);
        ndcg1.addPoint(7);
        ndcg1.addPoint(8);
        ndcg1.addPoint(9);
        ndcg1.addPoint(10);

        ndcg2.addPoint(3);
        ndcg2.addPoint(5);
        ndcg2.addPoint(6);
        ndcg2.addPoint(7);
        ndcg2.addPoint(8);
        ndcg2.addPoint(9);
        ndcg2.addPoint(10);
        assertThat(ndcg1, is(ndcg2));
        
    }   
    
    @Test
    public void setEdgeInTheGraphTest(){
        NonDirectionalCostedGraph ndcg1 = new NonDirectionalCostedGraph();
        NonDirectionalCostedGraph ndcg2 = new NonDirectionalCostedGraph();
        
        ndcg1.addPoint(1);
        ndcg1.addPoint(2);
        ndcg2.addPoint(1);
        ndcg2.addPoint(2);
        
        ndcg1.setEdge(1, new Pair(2,2.0));
        ndcg2.setEdge(1, new Pair(2,2.0));
        assertThat(2.0, is(ndcg1.getCost(2, 1)));
        assertThat(ndcg1, is(ndcg2));
        
        ndcg1.setEdge(1, new Pair(1,1.0));
        assertThat(ndcg1, is(ndcg2));
        
        ndcg1.setEdge(1, new Pair(2,3.0));
        assertThat(3.0, is(ndcg1.getCost(2, 1)));
        assertThat(ndcg1, not(ndcg2));
        
    }
    
    @Test
    public void removeEdgeFromTheGraphTest(){
        NonDirectionalCostedGraph ndcg1 = new NonDirectionalCostedGraph();
        NonDirectionalCostedGraph ndcg2 = new NonDirectionalCostedGraph();
        ndcg1.addPoint(1);
        ndcg1.addPoint(2);
        ndcg1.setEdge(1, new Pair(2,2.0));
        ndcg1.addPoint(3);
        
        ndcg2.addPoint(1);
        ndcg2.addPoint(2);
        ndcg2.addPoint(3);
        ndcg2.setEdge(1, new Pair(2,2.0)); 
        ndcg2.setEdge(1, new Pair(3,2.0)); 
        assertThat(ndcg1, not(ndcg2));
        
        ndcg2.removeEdge(1, 3);
        assertThat(ndcg1, is(ndcg2));
        ndcg2.setEdge(1, new Pair(3,2.0));
        assertThat(2.0,is(ndcg2.getCost(3, 1)));
        assertThat(ndcg1, not(ndcg2));
        ndcg2.removeEdge(3, 1);
        assertThat(ndcg1, is(ndcg2));
        
        ndcg2.removeEdge(1, 3);
        assertThat(ndcg1, is(ndcg2));
    } 
    
    @Test
    public void removePointFromTheGraphTest(){
        NonDirectionalCostedGraph ndcg1 = new NonDirectionalCostedGraph();
        NonDirectionalCostedGraph ndcg2 = new NonDirectionalCostedGraph();
        
        ndcg1.addPoint(1);
        ndcg1.addPoint(2);
        ndcg1.setEdge(1, new Pair(2,2.0));
        ndcg1.addPoint(3);
        ndcg1.setEdge(1, new Pair(3,3.0));        
        ndcg2.addPoint(1);
        ndcg2.addPoint(2);
        ndcg2.setEdge(1, new Pair(2,2.0));
        ndcg2.addPoint(4);
        ndcg2.setEdge(1, new Pair(4,2.0)); 
        ndcg2.addPoint(3);
        ndcg2.setEdge(1, new Pair(3,3.0));
        ndcg2.setEdge(4, new Pair(3,5.0));
        
        assertThat(ndcg1, not(ndcg2));
        assertThat(ndcg1.pointsGraphics, not(ndcg2.pointsGraphics));
        
        ndcg2.removePoint(4);
        assertThat(ndcg1, is(ndcg2));  
        assertThat(ndcg1.pointsGraphics, is(ndcg2.pointsGraphics));
        
        ndcg2.removePoint(4);
        assertThat(ndcg1, is(ndcg2));   
    }
    
    @Test
    public void adjOfPointTest(){
        NonDirectionalCostedGraph ndcg = new NonDirectionalCostedGraph();
        
        ndcg.addPoint(1);
        ndcg.addPoint(2);
        ndcg.addPoint(3);
        ndcg.addPoint(4);
        ndcg.setEdge(1, new Pair(2,2.0));
        ndcg.setEdge(1, new Pair(3,4.0));
        ndcg.setEdge(1, new Pair(4,5.0));
        
        List<Pair> list1 = new ArrayList<Pair>();
        list1.add(new Pair(2,2.0));
        list1.add(new Pair(3,4.0));
        list1.add(new Pair(4,5.0));
        List<Pair> ndcgList1 = ndcg.getAdj(1); 
        assertThat(ndcgList1,is(list1));
        
        List<Pair> ndcgList2 = ndcg.getAdj(2);
        assertThat(ndcgList1,not(ndcgList2));
        
    }
    
    @Test
    public void EdgeCostInTheGraphTest(){
        NonDirectionalCostedGraph ndcg = new NonDirectionalCostedGraph();
        ndcg.addPoint(1);
        ndcg.addPoint(2);
        ndcg.setEdge(1, new Pair(2,2.0));
        ndcg.addPoint(3);
        
        double value = ndcg.getCost(1, 2);

        assertThat(2.0, is(value));
        assertThat(ndcg.getCost(2, 1), is(value));
        
        value = ndcg.getCost(1, 3);
        
        assertThat(Double.POSITIVE_INFINITY, is(value));
    }  
    
    @Test
    public void isIncludeNegativCostTest(){
        NonDirectionalCostedGraph ndcg1 = new NonDirectionalCostedGraph();
        ndcg1.addPoint(1);
        ndcg1.addPoint(2);
        ndcg1.addPoint(3);
                
        ndcg1.setEdge(1, new Pair(2,2.0));
        ndcg1.setEdge(1, new Pair(3,-1.0));
        
        assertTrue(ndcg1.isIncludeNegativCost());

        NonDirectionalCostedGraph ndcg2 = new NonDirectionalCostedGraph();
        ndcg2.addPoint(1);
        ndcg2.addPoint(2);
        ndcg2.addPoint(3);
                
        ndcg2.setEdge(1, new Pair(2,2.0));
        ndcg2.setEdge(1, new Pair(3,1.0));  
        
        assertFalse(ndcg2.isIncludeNegativCost());
        
    }
    
    @Test
    public void graphIOTest() throws IOException, ClassNotFoundException{
        NonDirectionalCostedGraph ndcg1 = new NonDirectionalCostedGraph();
        ndcg1.addPoint(1);
        ndcg1.addPoint(2);
        ndcg1.addPoint(3);
                
        
        ndcg1.setEdge(1, new Pair(2,3.0));
        ndcg1.setEdge(1, new Pair(3,4.0));
        
        GraphIO.graphSaveToFile("test3.dat", ndcg1);
        NonDirectionalCostedGraph ndcg2 = (NonDirectionalCostedGraph) GraphIO.graphLoadFromFile("test3.dat");

        assertThat(ndcg1,is(ndcg2));
    }
    
    @Test
    public void graphColorTest(){
        NonDirectionalCostedGraph ndcg1 = new NonDirectionalCostedGraph();
        ndcg1.addPoint(1);
        ndcg1.addPoint(2);
        ndcg1.addPoint(3);        
        ndcg1.setEdge(1, new Pair(2,2.0));
        ndcg1.setEdge(1, new Pair(3,1.0));
        
        assertThat(ndcg1.getPointColor(1),is(Color.BLACK));
        assertThat(ndcg1.getEdgeColor(1, 2),is(Color.BLACK));
        
        assertThat(ndcg1.getPointColor(1),is(ndcg1.getPointColor(2)));
        
        ndcg1.setPointColor(1, Color.RED);
        assertThat(ndcg1.getPointColor(1),not(Color.BLACK));
        assertThat(ndcg1.getPointColor(1),is(Color.RED));
        assertThat(ndcg1.getPointColor(1),not(ndcg1.getPointColor(2)));
        
        assertThat(ndcg1.getEdgeColor(1, 2),is(ndcg1.getEdgeColor(1, 3)));
        
        ndcg1.setEdgeColor(1,2,Color.RED);
        assertThat(ndcg1.getEdgeColor(1,2),not(Color.BLACK));
        assertThat(ndcg1.getEdgeColor(1,2),is(Color.RED));
        assertThat(ndcg1.getEdgeColor(1,2),not(ndcg1.getEdgeColor(1,3)));  
        
        ndcg1.setAllPointColorForBlack();
        assertThat(ndcg1.getPointColor(1),is(Color.BLACK));
        assertThat(ndcg1.getPointColor(2),is(Color.BLACK));
        assertThat(ndcg1.getPointColor(3),is(Color.BLACK));
        
        assertThat(ndcg1.getPointColor(5),is(Color.WHITE));        
        
    }
    
    @Test
    public void getPointCoordinatesTest(){
        NonDirectionalCostedGraph ndcg1 = new NonDirectionalCostedGraph();
        ndcg1.addPoint(1);
        ndcg1.setPointCoordinates(1, new Point(1,2));
        ndcg1.addPoint(2);   
        ndcg1.setPointCoordinates(2, new Point(1,2));
        ndcg1.addPoint(3);
        ndcg1.setPointCoordinates(3, new Point(1,1));
    
        assertThat(ndcg1.getPointCoordinates(1), is(new Point(1,2)));
        assertThat(ndcg1.getPointCoordinates(2), is(ndcg1.getPointCoordinates(1)));
        assertThat(ndcg1.getPointCoordinates(3), not(ndcg1.getPointCoordinates(1)));
        assertThat(ndcg1.getPointCoordinates(3), is(new Point(1,1)));
        
        assertNull(ndcg1.getPointCoordinates(4));
    }
    
    @Test
    public void randomTest() throws IOException, ClassNotFoundException{
        NonDirectionalCostedGraph ndcg1 = new NonDirectionalCostedGraph();
        
        ndcg1.addPoint(1);
        ndcg1.addPoint(2);
        ndcg1.addPoint(3);
        
        ndcg1.randomEdgesGenerateInGraph(60, false);
        
        //System.out.println(ndcg1);
        
        GraphIO.graphSaveToFile("randomdcgtest1.dat", ndcg1);
        
        NonDirectionalCostedGraph ndcg2 = new NonDirectionalCostedGraph();
        ndcg2 = (NonDirectionalCostedGraph)GraphIO.graphLoadFromFile("randomdcgtest1.dat");
        assertThat(ndcg1, is(ndcg2));
        
        //System.out.println(ndcg2);
        
        ndcg1.randomGraphGenerate(40, true, false, 1, 15);
        
        //System.out.println(ndcg1);
        
        GraphIO.graphSaveToFile("randomdcgtest2.dat", ndcg1);
        NonDirectionalCostedGraph ndcg3 = new NonDirectionalCostedGraph();
        ndcg3 = (NonDirectionalCostedGraph)GraphIO.graphLoadFromFile("randomdcgtest2.dat");
        assertThat(ndcg1, is(ndcg3));
        assertThat(ndcg1, not(ndcg2));
        
    }
    
    @Test
    public void removeAllPointTest(){
        NonDirectionalCostedGraph ndcg1 = new NonDirectionalCostedGraph();
        ndcg1.addPoint(1);
        ndcg1.addPoint(2);
        ndcg1.addPoint(3);
        
        ndcg1.setEdge(1, new Pair(3,3.0));
        ndcg1.setEdge(2, new Pair(3,4.0));
        
        NonDirectionalCostedGraph ndcg2 = new NonDirectionalCostedGraph();
        
        assertThat(ndcg1,not(ndcg2));
        
        ndcg1.removeAllPoint();
        
        assertThat(ndcg1,is(ndcg2));
        
    }    
    
}
