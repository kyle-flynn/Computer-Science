import org.junit.*;

import static edu.gvsu.mipsunit.munit.MUnit.Register.*;
import static edu.gvsu.mipsunit.munit.MUnit.*;

public class nCk {

    @Before
    public void before() {
    set(v0, 1337);
    }

    /******************************************************************
     *
     * Test makes10
     *
     *****************************************************************/

    @Test 
    public void nCk_5_3() {
    run("nCk", 5, 3);
    Assert.assertEquals(10, get(v0));
    }

    @Test 
    public void nCk_100_4() {
    run("nCk", 100, 4);
    Assert.assertEquals(3921225, get(v0));
    }

    /******************************************************************
     *
     * Write many more tests!  * Test Edge Cases *
     *
     *****************************************************************/
    
} // end class

