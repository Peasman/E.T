package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Testet die Klasse Ingredient
 * 
 * @author Moritz Ludolph
 *
 */
public class IngredientTest {

	/**
	 * Testet das erzeugen eines neuen Ingredient-Objektes
	 */
	@Test
	public void testIngredient() {
		Ingredient ingredient = new Ingredient("Laktose", "ist geil");

		assertEquals(ingredient.getName(), "Laktose");
		assertEquals(ingredient.getInformation(), "ist geil");
	}

}
