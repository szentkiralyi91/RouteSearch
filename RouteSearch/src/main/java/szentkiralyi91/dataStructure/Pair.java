/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package szentkiralyi91.dataStructure;

import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Szentkirályi Károly
 */
public class Pair implements Serializable{

    private int v;
    private double c;
    private Color color = Color.BLACK;
    private boolean isCosted;
    private Point p;

    public Pair() {

    }
    
    public Pair(Point p){
        this.p = p;
    }
    
    public Pair(int v, double c) {
        this.v = v;
        this.c = c;
        this.isCosted = true;
    }

    public Pair(int v) {
        this.v = v;
        this.isCosted = false;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }    
    
    public void setC(double c) {
        if (isCosted) {
            this.c = c;
        }
    }

    public void setV(int v) {
        this.v = v;
    }

    public double getC() {
        if (isCosted) {
            return c;
        }
        return Double.POSITIVE_INFINITY;
    }

    public int getV() {
        return v;
    }

    public Point getP() {
        return p;
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
        Pair p = (Pair) obj;
        if ((this.isCosted && this.v == p.getV() && this.c == p.getC())
                || (!this.isCosted && this.v == p.getV() 
                && this.getC()==Double.POSITIVE_INFINITY && p.getC()==Double.POSITIVE_INFINITY)) 
                {
            return true;
        } 

        return false;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.v;
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.c) ^ (Double.doubleToLongBits(this.c) >>> 32));
        return hash;
    }
}
