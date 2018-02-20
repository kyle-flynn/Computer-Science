
import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;

public class AssemblyIntroSampleTest {

    /******************************************************************
     *
     * Test double
     *
     *****************************************************************/


    @Test 
    public void double_positive_number() {
	run("double", 2);
	Assert.assertEquals(4, get(v0));
    }


    @Test 
    public void doubles_zero() {
	run("double", 0);
	Assert.assertEquals(0, get(v0));
    }


    @Test 
    public void doubles_negative_numbers() {
	run("double", -6);
	Assert.assertEquals(-12, get(v0));
    }

  
    /******************************************************************
     *
     * Test triple
     *
     *****************************************************************/

    @Test 
    public void triples_positive_number() {
	run("triple", 2);
	Assert.assertEquals(6, get(v0));
    }

    @Test 
    public void triples_zero() {
	run("triple", 0);
	Assert.assertEquals(0, get(v0));
    }


    @Test 
    public void triples_negative_numbers() {
	run("triple", -6);
	Assert.assertEquals(-18, get(v0));
    }
}

