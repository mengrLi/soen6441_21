package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AggressiveTest.class, AggressiveTest2.class, AggressiveTest3.class, ArmyTest.class,
		AttackerDefenderValidation.class, BenevolentTest.class, BenevolentTest2.class, BenevolentTest3.class,
		BenevolentTest4.class, CardForArmies1Test.class, CardForArmies2Test.class, CardTests.class,
		CheaterReinforceTest.class, ContinentTest.class, CountryTest.class, CountryTest2.class, DiceAllTest.class,
		DiceOneTest.class, DiceThreeTest.class, DiceTwoTest.class, EndOfGameTest.class, FortificaitonTest.class,
		GameEngineTests.class, LoadGameFormatTest.class, LoadGameTest.class, LoadMapTest.class, MapValidationTest.class,
		MoveAfterConqueringTest.class, PlayerEngineTests.class, PlayerTest.class, SaveGameFormatTest.class,
		SaveGameTest.class, SetPlayersTest.class, TournamentTest.class })
public class AllTests {

}
