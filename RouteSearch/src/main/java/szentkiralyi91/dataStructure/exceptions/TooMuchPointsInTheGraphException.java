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
public class TooMuchPointsInTheGraphException extends Exception {
  
    public TooMuchPointsInTheGraphException() {
        //System.out.println("The choosen algorithms need non-directional or directional costed graph.");
    }

  public TooMuchPointsInTheGraphException(String msg) {
    super(msg);
  }
}     
