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
public class DirectionalNonCostedGraphTest {
    
    @Test
    public void addPointsToTheGraphTest(){
        DirectionalNonCostedGraph dncg1 = new DirectionalNonCostedGraph();
        dncg1.addPoint(1);
        DirectionalNonCostedGraph dncg2 = new DirectionalNonCostedGraph();
        dncg2.addPoint(1);
        assertThat(dncg1, is(dncg2));
        
        dncg1.addPoint(2);
        dncg2.addPoint(2);
        assertThat(dncg1, is(dncg2));
         
        dncg1.addPoint(2);
        assertThat(dncg1, is(dncg2));   
        
        dncg1.addPoint(3);
        dncg2.addPoint(4);
        assertThat(dncg1, not(dncg2));
         
        dncg1.addPoint(4);
        dncg1.addPoint(5);
        dncg1.addPoint(6);
        dncg1.addPoint(7);
        dncg1.addPoint(8);
        dncg1.addPoint(9);
        dncg1.addPoint(10);

        dncg2.addPoint(3);
        dncg2.addPoint(5);
        dncg2.addPoint(6);
        dncg2.addPoint(7);
        dncg2.addPoint(8);
        dncg2.addPoint(9);
        dncg2.addPoint(10);
        dncg2.addPoint(11);
        assertThat(dncg1, is(dncg2));
        
    }   
    
    @Test
    public void setEdgeInTheGraphTest(){
        DirectionalNonCostedGraph dncg1 = new DirectionalNonCostedGraph();
        DirectionalNonCostedGraph dncg2 = new DirectionalNonCostedGraph();
        
        dncg1.addPoint(1);
        dncg1.addPoint(2);
        dncg2.addPoint(1);
        dncg2.addPoint(2);
        
        dncg1.setEdge(1, new Pair(2));
        dncg2.setEdge(1, new Pair(2));
        assertThat(dncg1, is(dncg2));

        dncg1.setEdge(1, new Pair(3));
        assertThat(dncg1, is(dncg2));
 
        dncg1.setEdge(1, new Pair(1));
        assertThat(dncg1, is(dncg2));        
        
        dncg1.addPoint(3);
        dncg1.setEdge(1, new Pair(3));
        assertThat(dncg1, not(dncg2));
        
    }
    
    @Test
    public void removeEdgeFromTheGraphTest(){
        DirectionalNonCostedGraph dncg1 = new DirectionalNonCostedGraph();
        DirectionalNonCostedGraph dncg2 = new DirectionalNonCostedGraph();
        dncg1.addPoint(1);
        dncg1.addPoint(2);
        dncg1.setEdge(1, new Pair(2));
        dncg1.addPoint(3);
        
        dncg2.addPoint(1);
        dncg2.addPoint(2);
        dncg2.addPoint(3);
        dncg2.setEdge(1, new Pair(2)); 
        dncg2.setEdge(1, new Pair(3)); 
        
        assertThat(dncg1, not(dncg2));
        
        dncg2.removeEdge(1, 3);
        assertThat(dncg1, is(dncg2));
        
        dncg2.removeEdge(1, 3);
        assertThat(dncg1, is(dncg2));
    } 
    
    @Test
    public void removePointFromTheGraphTest(){
        DirectionalNonCostedGraph dncg1 = new DirectionalNonCostedGraph();
        DirectionalNonCostedGraph dncg2 = new DirectionalNonCostedGraph();
        
        dncg1.addPoint(1);
        dncg1.addPoint(2);
        dncg1.setEdge(1, new Pair(2));
        dncg2.addPoint(1);
        dncg2.addPoint(2);
        dncg2.setEdge(1, new Pair(2));
        dncg2.addPoint(3);
        dncg2.setEdge(1, new Pair(3)); 
        dncg2.setEdge(3, new Pair(2));
        assertThat(dncg1, not(dncg2));
        assertThat(dncg1.pointsGraphics, not(dncg2.pointsGraphics));
        
        dncg2.removePoint(3);
        assertThat(dncg1, is(dncg2));  
        assertThat(dncg1.pointsGraphics, is(dncg2.pointsGraphics));
        
        dncg2.removePoint(3);
        assertThat(dncg1, is(dncg2));         
    }
    
    @Test
    public void adjOfPointTest(){
        DirectionalNonCostedGraph dncg = new DirectionalNonCostedGraph();
        
        dncg.addPoint(1);
        dncg.addPoint(2);
        dncg.addPoint(3);
        dncg.addPoint(4);
        dncg.setEdge(1, new Pair(2));
        dncg.setEdge(1, new Pair(3));
        dncg.setEdge(1, new Pair(4));
        
        List<Pair> list1 = new ArrayList<Pair>();
        list1.add(new Pair(2));
        list1.add(new Pair(3));
        list1.add(new Pair(4));
        List<Pair> dncgList1 = dncg.getAdj(1); 
        assertThat(dncgList1,is(list1));
        
        List<Pair> dncgList2 = dncg.getAdj(2);
        assertThat(dncgList1,not(dncgList2));
        
    }  
    
    @Test
    public void EdgeCostInTheGraphTest(){
        DirectionalNonCostedGraph dncg = new DirectionalNonCostedGraph();
        dncg.addPoint(1);
        dncg.addPoint(2);
        dncg.setEdge(1, new Pair(2));
        dncg.addPoint(3);
        
        double value = dncg.getCost(1, 2);

        assertThat(Double.POSITIVE_INFINITY, is(value));
        
        value = dncg.getCost(1, 3);
        
        assertThat(Double.POSITIVE_INFINITY, is(value));
    }  
    
    @Test
    public void isIncludeNegativCostTest(){
        DirectionalNonCostedGraph dncg1 = new DirectionalNonCostedGraph();
        dncg1.addPoint(1);
        dncg1.addPoint(2);
        dncg1.addPoint(3);
                
        dncg1.setEdge(1, new Pair(2));
        dncg1.setEdge(1, new Pair(3));
        
        assertFalse(dncg1.isIncludeNegativCost());
        
    }    
    
    @Test
    public void graphIOTest() throws IOException, ClassNotFoundException{
        DirectionalNonCostedGraph dncg1 = new DirectionalNonCostedGraph();
        dncg1.addPoint(1);
        dncg1.addPoint(2);
        dncg1.addPoint(3);
                
        
        dncg1.setEdge(1, new Pair(2));
        dncg1.setEdge(1, new Pair(3));
        
        GraphIO.graphSaveToFile("test2.dat", dncg1);
        DirectionalNonCostedGraph dncg2 = (DirectionalNonCostedGraph) GraphIO.graphLoadFromFile("test2.dat");

        assertThat(dncg1,is(dncg2));
    }
    
    @Test
    public void graphColorTest(){
        DirectionalNonCostedGraph dncg1 = new DirectionalNonCostedGraph();
        dncg1.addPoint(1);
        dncg1.addPoint(2);
        dncg1.addPoint(3);        
        dncg1.setEdge(1, new Pair(2));
        dncg1.setEdge(1, new Pair(3));
        
        assertThat(dncg1.getPointColor(1),is(Color.BLACK));
        assertThat(dncg1.getEdgeColor(1, 2),is(Color.BLACK));
        
        assertThat(dncg1.getPointColor(1),is(dncg1.getPointColor(2)));
        
        dncg1.setPointColor(1, Color.RED);
        assertThat(dncg1.getPointColor(1),not(Color.BLACK));
        assertThat(dncg1.getPointColor(1),is(Color.RED));
        assertThat(dncg1.getPointColor(1),not(dncg1.getPointColor(2)));
        
        assertThat(dncg1.getEdgeColor(1, 2),is(dncg1.getEdgeColor(1, 3)));
        
        dncg1.setEdgeColor(1,2,Color.RED);
        assertThat(dncg1.getEdgeColor(1,2),not(Color.BLACK));
        assertThat(dncg1.getEdgeColor(1,2),is(Color.RED));
        assertThat(dncg1.getEdgeColor(1,2),not(dncg1.getEdgeColor(1,3)));  
        
        dncg1.setAllPointColorForBlack();
        assertThat(dncg1.getPointColor(1),is(Color.BLACK));
        assertThat(dncg1.getPointColor(2),is(Color.BLACK));
        assertThat(dncg1.getPointColor(3),is(Color.BLACK));
        
        assertThat(dncg1.getPointColor(5),is(Color.WHITE));        
        
    }
    
    @Test
    public void getPointCoordinatesTest(){
        DirectionalNonCostedGraph dncg1 = new DirectionalNonCostedGraph();
        dncg1.addPoint(1);
        dncg1.addPoint(2);   
        dncg1.addPoint(3);
    
        assertThat(dncg1.getPointCoordinates(1), is(new Point(1,2)));
        assertThat(dncg1.getPointCoordinates(2), is(dncg1.getPointCoordinates(1)));
        assertThat(dncg1.getPointCoordinates(3), not(dncg1.getPointCoordinates(1)));
        assertThat(dncg1.getPointCoordinates(3), is(new Point(1,1)));
        
        assertNull(dncg1.getPointCoordinates(4));
    }
    
    @Test
    public void randomTest() throws IOException, ClassNotFoundException{
        DirectionalNonCostedGraph dncg1 = new DirectionalNonCostedGraph();
        
        dncg1.addPoint(1);
        dncg1.addPoint(2);
        dncg1.addPoint(3);
        
        dncg1.randomEdgesGenerateInGraph(60, false);
        
        System.out.println(dncg1);
        
        GraphIO.graphSaveToFile("randomdcgtest1.dat", dncg1);
        
        DirectionalNonCostedGraph dncg2 = new DirectionalNonCostedGraph();
        dncg2 = (DirectionalNonCostedGraph)GraphIO.graphLoadFromFile("randomdcgtest1.dat");
        assertThat(dncg1, is(dncg2));
        
        //System.out.println(dncg2);
        
        dncg1.randomGraphGenerate(40, true, false, 1, 15);
        
        //System.out.println(dncg1);
        
        GraphIO.graphSaveToFile("randomdcgtest2.dat", dncg1);
        DirectionalNonCostedGraph dncg3 = new DirectionalNonCostedGraph();
        dncg3 = (DirectionalNonCostedGraph)GraphIO.graphLoadFromFile("randomdcgtest2.dat");
        
        //System.out.println(dncg3);
        
        assertThat(dncg1, is(dncg3));
        assertThat(dncg1, not(dncg2));
        
    }
    
    @Test
    public void removeAllPointTest(){
        DirectionalNonCostedGraph dncg1 = new DirectionalNonCostedGraph();
        dncg1.addPoint(1);
        dncg1.addPoint(2);
        dncg1.addPoint(3);
        
        dncg1.setEdge(1, new Pair(3));
        dncg1.setEdge(2, new Pair(3));
        
        DirectionalNonCostedGraph dncg2 = new DirectionalNonCostedGraph();
        
        assertThat(dncg1,not(dncg2));
        
        dncg1.removeAllPoint();
        
        assertThat(dncg1,is(dncg2));
        
    }    
      
    
}
