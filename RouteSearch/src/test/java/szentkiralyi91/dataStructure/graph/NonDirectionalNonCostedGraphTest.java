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
public class NonDirectionalNonCostedGraphTest {
    
    @Test
    public void addPointsToTheGraphTest(){
        NonDirectionalNonCostedGraph ndncg1 = new NonDirectionalNonCostedGraph();
        ndncg1.addPoint(1);
        NonDirectionalNonCostedGraph ndncg2 = new NonDirectionalNonCostedGraph();
        ndncg2.addPoint(1);
        assertThat(ndncg1, is(ndncg2));
        
        ndncg1.addPoint(2);
        ndncg2.addPoint(2);
        assertThat(ndncg1, is(ndncg2));
         
        ndncg1.addPoint(2);
        assertThat(ndncg1, is(ndncg2));   
        
        ndncg1.addPoint(3);
        ndncg2.addPoint(4);
        assertThat(ndncg1, not(ndncg2));
        
        ndncg1.addPoint(4);
        ndncg1.addPoint(5);
        ndncg1.addPoint(6);
        ndncg1.addPoint(7);
        ndncg1.addPoint(8);
        ndncg1.addPoint(9);
        ndncg1.addPoint(10);

        ndncg2.addPoint(3);
        ndncg2.addPoint(5);
        ndncg2.addPoint(6);
        ndncg2.addPoint(7);
        ndncg2.addPoint(8);
        ndncg2.addPoint(9);
        ndncg2.addPoint(10);
        assertThat(ndncg1, is(ndncg2));
        
    }   
    
    @Test
    public void setEdgeInTheGraphTest(){
        NonDirectionalNonCostedGraph ndncg1 = new NonDirectionalNonCostedGraph();
        NonDirectionalNonCostedGraph ndncg2 = new NonDirectionalNonCostedGraph();
        
        ndncg1.addPoint(1);
        ndncg1.addPoint(2);
        ndncg2.addPoint(1);
        ndncg2.addPoint(2);
        
        ndncg1.setEdge(1, new Pair(2));
        ndncg2.setEdge(1, new Pair(2));
        assertThat(ndncg1, is(ndncg2));
        
        ndncg1.setEdge(1, new Pair(1));
        assertThat(ndncg1, is(ndncg2));
        
        ndncg2.addPoint(3);
        ndncg2.setEdge(1, new Pair(3));
        assertThat(ndncg1, not(ndncg2));
        
    }
    
    @Test
    public void removeEdgeFromTheGraphTest(){
        NonDirectionalNonCostedGraph ndncg1 = new NonDirectionalNonCostedGraph();
        NonDirectionalNonCostedGraph ndncg2 = new NonDirectionalNonCostedGraph();
        ndncg1.addPoint(1);
        ndncg1.addPoint(2);
        ndncg1.setEdge(1, new Pair(2));
        ndncg1.addPoint(3);
        
        ndncg2.addPoint(1);
        ndncg2.addPoint(2);
        ndncg2.addPoint(3);
        ndncg2.setEdge(1, new Pair(2)); 
        ndncg2.setEdge(1, new Pair(3)); 
        assertThat(ndncg1, not(ndncg2));
        
        ndncg2.removeEdge(1, 3);
        assertThat(ndncg1, is(ndncg2));
        ndncg2.setEdge(1, new Pair(3));
        assertThat(ndncg1, not(ndncg2));
        ndncg2.removeEdge(3, 1);
        assertThat(ndncg1, is(ndncg2));
        
        ndncg2.removeEdge(1, 3);
        assertThat(ndncg1, is(ndncg2));
    } 
    
    @Test
    public void removePointFromTheGraphTest(){
        NonDirectionalNonCostedGraph ndncg1 = new NonDirectionalNonCostedGraph();
        NonDirectionalNonCostedGraph ndncg2 = new NonDirectionalNonCostedGraph();
        
        ndncg1.addPoint(1);
        ndncg1.addPoint(2);
        ndncg1.setEdge(1, new Pair(2));
        ndncg1.addPoint(3);
        ndncg1.setEdge(1, new Pair(3));        
        ndncg2.addPoint(1);
        ndncg2.addPoint(2);
        ndncg2.setEdge(1, new Pair(2));
        ndncg2.addPoint(4);
        ndncg2.setEdge(1, new Pair(4)); 
        ndncg2.addPoint(3);
        ndncg2.setEdge(1, new Pair(3));
        ndncg2.setEdge(4, new Pair(3));
        
        assertThat(ndncg1, not(ndncg2));
        assertThat(ndncg1.pointsGraphics, not(ndncg2.pointsGraphics));
        
        ndncg2.removePoint(4);
        assertThat(ndncg1, is(ndncg2));  
        assertThat(ndncg1.pointsGraphics, is(ndncg2.pointsGraphics));
        
        ndncg2.removePoint(4);
        assertThat(ndncg1, is(ndncg2));   
    }
    
    @Test
    public void adjOfPointTest(){
        NonDirectionalNonCostedGraph ndncg = new NonDirectionalNonCostedGraph();
        
        ndncg.addPoint(1);
        ndncg.addPoint(2);
        ndncg.addPoint(3);
        ndncg.addPoint(4);
        ndncg.setEdge(1, new Pair(2));
        ndncg.setEdge(1, new Pair(3));
        ndncg.setEdge(1, new Pair(4));
        
        List<Pair> list1 = new ArrayList<Pair>();
        list1.add(new Pair(2));
        list1.add(new Pair(3));
        list1.add(new Pair(4));
        List<Pair> ndncgList1 = ndncg.getAdj(1); 
        assertThat(ndncgList1,is(list1));
        
        List<Pair> ndncgList2 = ndncg.getAdj(2);
        assertThat(ndncgList1,not(ndncgList2));
        
    }
    
    @Test
    public void EdgeCostInTheGraphTest(){
        NonDirectionalNonCostedGraph ndncg = new NonDirectionalNonCostedGraph();
        ndncg.addPoint(1);
        ndncg.addPoint(2);
        ndncg.setEdge(1, new Pair(2));
        ndncg.addPoint(3);
        
        double value = ndncg.getCost(1, 2);

        assertThat(Double.POSITIVE_INFINITY, is(value));
        assertThat(ndncg.getCost(2, 1), is(value));
        
        value = ndncg.getCost(1, 3);
        
        assertThat(Double.POSITIVE_INFINITY, is(value));
    }  
    
    @Test
    public void isIncludeNegativCostTest(){
        NonDirectionalNonCostedGraph ndncg1 = new NonDirectionalNonCostedGraph();
        ndncg1.addPoint(1);
        ndncg1.addPoint(2);
        ndncg1.addPoint(3);
                
        ndncg1.setEdge(1, new Pair(2));
        ndncg1.setEdge(1, new Pair(3));
        
        assertFalse(ndncg1.isIncludeNegativCost());
        
    }
    
    @Test
    public void graphIOTest() throws IOException, ClassNotFoundException{
        NonDirectionalNonCostedGraph ndncg1 = new NonDirectionalNonCostedGraph();
        ndncg1.addPoint(1);
        ndncg1.addPoint(2);
        ndncg1.addPoint(3);
                
        
        ndncg1.setEdge(1, new Pair(2));
        ndncg1.setEdge(1, new Pair(3));
        
        GraphIO.graphSaveToFile("test4.dat", ndncg1);
        NonDirectionalNonCostedGraph ndncg2 = (NonDirectionalNonCostedGraph) GraphIO.graphLoadFromFile("test4.dat");

        assertThat(ndncg1,is(ndncg2));
    }
    
    @Test
    public void graphColorTest(){
        NonDirectionalNonCostedGraph ndncg1 = new NonDirectionalNonCostedGraph();
        ndncg1.addPoint(1);
        ndncg1.addPoint(2);
        ndncg1.addPoint(3);        
        ndncg1.setEdge(1, new Pair(2));
        ndncg1.setEdge(1, new Pair(3));
        
        assertThat(ndncg1.getPointColor(1),is(Color.BLACK));
        assertThat(ndncg1.getEdgeColor(1, 2),is(Color.BLACK));
        
        assertThat(ndncg1.getPointColor(1),is(ndncg1.getPointColor(2)));
        
        ndncg1.setPointColor(1, Color.RED);
        assertThat(ndncg1.getPointColor(1),not(Color.BLACK));
        assertThat(ndncg1.getPointColor(1),is(Color.RED));
        assertThat(ndncg1.getPointColor(1),not(ndncg1.getPointColor(2)));
        
        assertThat(ndncg1.getEdgeColor(1, 2),is(ndncg1.getEdgeColor(1, 3)));
        
        ndncg1.setEdgeColor(1,2,Color.RED);
        assertThat(ndncg1.getEdgeColor(1,2),not(Color.BLACK));
        assertThat(ndncg1.getEdgeColor(1,2),is(Color.RED));
        assertThat(ndncg1.getEdgeColor(1,2),not(ndncg1.getEdgeColor(1,3)));  
        
        ndncg1.setAllPointColorForBlack();
        assertThat(ndncg1.getPointColor(1),is(Color.BLACK));
        assertThat(ndncg1.getPointColor(2),is(Color.BLACK));
        assertThat(ndncg1.getPointColor(3),is(Color.BLACK));
        
        assertThat(ndncg1.getPointColor(5),is(Color.WHITE));        
        
    }
    
    @Test
    public void getPointCoordinatesTest(){
        NonDirectionalNonCostedGraph ndncg1 = new NonDirectionalNonCostedGraph();
        ndncg1.addPoint(1);
        ndncg1.setPointCoordinates(1, new Point(1,2));
        ndncg1.addPoint(2);   
        ndncg1.setPointCoordinates(2, new Point(1,2));
        ndncg1.addPoint(3);
        ndncg1.setPointCoordinates(3, new Point(1,1));
    
        assertThat(ndncg1.getPointCoordinates(1), is(new Point(1,2)));
        assertThat(ndncg1.getPointCoordinates(2), is(ndncg1.getPointCoordinates(1)));
        assertThat(ndncg1.getPointCoordinates(3), not(ndncg1.getPointCoordinates(1)));
        assertThat(ndncg1.getPointCoordinates(3), is(new Point(1,1)));
        
        assertNull(ndncg1.getPointCoordinates(4));
    }
    
    @Test
    public void randomTest() throws IOException, ClassNotFoundException{
        NonDirectionalNonCostedGraph ndncg1 = new NonDirectionalNonCostedGraph();
        
        ndncg1.addPoint(1);
        ndncg1.addPoint(2);
        ndncg1.addPoint(3);
        
        ndncg1.randomEdgesGenerateInGraph(60, false);
        
        System.out.println(ndncg1);
        
        GraphIO.graphSaveToFile("randomdcgtest1.dat", ndncg1);
        
        NonDirectionalNonCostedGraph ndncg2 = new NonDirectionalNonCostedGraph();
        ndncg2 = (NonDirectionalNonCostedGraph)GraphIO.graphLoadFromFile("randomdcgtest1.dat");
        assertThat(ndncg1, is(ndncg2));
        
        System.out.println(ndncg2);
        
        ndncg1.randomGraphGenerate(40, true, false, 1, 15);
        
        System.out.println(ndncg1);
        
        GraphIO.graphSaveToFile("randomdcgtest2.dat", ndncg1);
        NonDirectionalNonCostedGraph ndncg3 = new NonDirectionalNonCostedGraph();
        ndncg3 = (NonDirectionalNonCostedGraph)GraphIO.graphLoadFromFile("randomdcgtest2.dat");
        
        System.out.println(ndncg3);
        
        assertThat(ndncg1, is(ndncg3));
        assertThat(ndncg1, not(ndncg2));
        
    }
    
    @Test
    public void removeAllPointTest(){
        NonDirectionalNonCostedGraph ndncg1 = new NonDirectionalNonCostedGraph();
        ndncg1.addPoint(1);
        ndncg1.addPoint(2);
        ndncg1.addPoint(3);
        
        ndncg1.setEdge(1, new Pair(3));
        ndncg1.setEdge(2, new Pair(3));
        
        NonDirectionalNonCostedGraph ndncg2 = new NonDirectionalNonCostedGraph();
        
        assertThat(ndncg1,not(ndncg2));
        
        ndncg1.removeAllPoint();
        
        assertThat(ndncg1,is(ndncg2));
        
    }     
    
    
}
