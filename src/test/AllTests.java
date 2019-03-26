package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author anhen
 * This is the test suite for all tests
 */
@RunWith(Suite.class)
@SuiteClasses({ ArmyTest.class, AttackerDefenderValidation.class, CardForArmies1Test.class, CardForArmies2Test.class,
		CardTests.class, ContinentTest.class, CountryTest.class, CountryTest2.class, DiceAllTest.class,
		DiceOneTest.class, DiceThreeTest.class, DiceTwoTest.class, EndOfGameTest.class, FortificaitonTest.class,
		GameEngineTests.class, LoadMapTest.class, MapValidationTest.class, MoveAfterConqueringTest.class,
		PlayerEngineTests.class, PlayerTest.class, SetPlayersTest.class })

public class AllTests {

}
