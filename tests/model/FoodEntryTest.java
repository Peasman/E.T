package model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;
/**
 * Testet FoodEntry
 * @author Friedemann Runte
 *
 */
public class FoodEntryTest {
	/**
	 * Überprüft, ob der Konstruktor alle übergebenen Werte richtig setzt.
	 */
	@Test
	public void test() {
		FoodEntry foodEntry = new FoodEntry(LocalDateTime.of(2017, 4, 1, 9, 0, 0), 5, Unit.GRAMM,"war im restaurant",new Food("Kartoffel",null));
		assertEquals(foodEntry.getTime(), LocalDateTime.of(2017, 4, 1, 9, 0, 0));
		assertEquals(foodEntry.getAmount(),5.0,0);
		assertEquals(foodEntry.getComment(),"war im restaurant");
		assertEquals(foodEntry.getUnit(), Unit.GRAMM);
		assertEquals(foodEntry.getFood(), new Food("Kartoffel",null));
	}

}
