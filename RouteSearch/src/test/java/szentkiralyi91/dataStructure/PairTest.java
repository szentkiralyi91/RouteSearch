/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package szentkiralyi91.dataStructure;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PairTest {

    @Test
    public void test1() {
        Pair pair = new Pair(1, 2.0);

        assertThat(1, is(pair.getV()));
        assertThat(2.0, is(pair.getC()));
    }

    @Test
    public void test2() {
        Pair pair1 = new Pair(1);
        Pair pair2 = new Pair(1);

        assertThat(pair1, is(pair2));
    }

    @Test
    public void test3() {
        Pair pair1 = new Pair(1);
        Pair pair2 = new Pair(2);

        assertThat(pair1, not(pair2));
    }

    @Test
    public void test4() {
        Pair pair1 = new Pair(1, 2.0);
        Pair pair2 = new Pair(1, 2.0);

        assertThat(pair1, is(pair2));
    }

    @Test
    public void test5() {
        Pair pair1 = new Pair(1, 2.0);
        Pair pair2 = new Pair(1, 3.0);

        assertThat(pair1, not(pair2));
    }

}
