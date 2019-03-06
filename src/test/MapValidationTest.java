package test;

import model.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Assert.*;


/**
 * This test case is used to test the function of map validation
 * @author QingLi
 *
 */
public class MapValidationTest {
	Map gameMap=Map.getMapInstance();
	
    /**
     * To test the error case1: empty map
     */
    @Test
    public void checkMapValidationTestCase1() {
   
    	gameMap.loadMap("empty.map");
    	String message = "Error1: Empty Map";
    	assertEquals(message,gameMap.checkMapValidation("save"));
    	assertEquals(message,gameMap.checkMapValidation("load"));
    }
    
    /**
     * To test error case2: Only containing one country in the whole map
     */
    @Test
    public void checkMapValidationTestCase2() {
    	
    	gameMap.loadMap("onlyone.map");
    	String message = "Error2: Containing only one country is invalid, please add more countries!";
    	assertEquals(message,gameMap.checkMapValidation("save"));
    	assertEquals(message,gameMap.checkMapValidation("load"));
    }
    
    
    /**
     * To test error case3: Continents are internally connected but the whole map is not connected
     */
    @Test
    public void checkMapValidationTestCase3() {
    	
    	gameMap.loadMap("TheWholeMapNot Connected.map");
    	String message = "Error3: The whole map is not connected";
    	assertEquals(message,gameMap.checkMapValidation("save"));
    	assertEquals(message,gameMap.checkMapValidation("load"));
    }
    
    /**
     * To test error case4: Continents are not internally connected
     */
    @Test
    public void checkMapValidationTestCase4() {
    	
    	gameMap.loadMap("continentNot Connected.map");
    	String message = "Error4: Africa continent is not connected";
    	assertEquals(message,gameMap.checkMapValidation("save"));
    	assertEquals(message,gameMap.checkMapValidation("load"));
    }
    
    /**
     * To test valid map: map is successfully connected
     */
    @Test
    public void checkMapValidationTestCase5() {
    	
    	gameMap.loadMap("correct.map");
    	String message = "Map is successfully connected!";
    	assertEquals(message,gameMap.checkMapValidation("save"));
    	assertEquals(message,gameMap.checkMapValidation("load"));
    }
    
    
    
}
