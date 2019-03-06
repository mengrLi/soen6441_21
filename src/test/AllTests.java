package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ArmyTest.class, ContinentTest.class, CountryTest.class, CountryTest2.class, PlayerTest.class,
CardTests.class, GameEngineTests.class, LoadMapTest.class, MapValidationTest.class, SetPlayersTest.class})
public class AllTests {

}
