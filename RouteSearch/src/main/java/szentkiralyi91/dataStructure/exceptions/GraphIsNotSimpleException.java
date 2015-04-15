/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package szentkiralyi91.dataStructure.exceptions;
/**
 *
 * @author Szentkirályi Károly
 */
public class GraphIsNotSimpleException extends Exception{
    public GraphIsNotSimpleException() {
        //System.out.println("Graph is not simple graph, the program doesn't deal with.");
    }

  public GraphIsNotSimpleException(String msg) {
    super(msg);
  }    
}

