package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistentException;
import model.Diary;
import model.Food;
import model.Ingredient;

/**
 * 	Diese Klasse testet die Funktion des FoodListControllers
 * 
 *  @author Christian Walczuch
 */
public class FoodListContollerTest {
	
	private Diary dir;
	private ETController etc;
	private FoodListController flc;
	private Collection<Food> subFoods;
	private Collection<Ingredient> ingredients;
	private Food testFood;

	/**
	 *  Setzt die für den Test erforderlichen Parameter
	 */
	@Before
	public void setUp(){
		dir = new Diary();
		etc = new ETController();
		etc.setCurrentDiary(dir);
		flc = new FoodListController(etc);
		
		testFood = new Food("Spagetti", null);
		subFoods = new ArrayList<Food>();
		subFoods.add(testFood);
		ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Mehl", null));
	}
	
	/**
	 * Testet den FoodListController auf das Verhalten bei Übergabe eines falschen Parameters
	 */
	@Test
	public void testFoodListContoller() {
		 flc = new FoodListController(null);
	}

	/**
	 * Testet ob ein Food hinzugefügt wird
	 * @throws ObjectAlreadyExistsException
	 */
	@Test
	public void testCreateFood() throws ObjectAlreadyExistsException {
		flc.createFood("TestFood", null, subFoods, ingredients);
		assertTrue(dir.getFoodList().size()==1);
		assertTrue(dir.getFoodList().iterator().next().getName().equals("TestFood"));
	}

	/**
	 * Testet ob ein Food bearbeitet werden kann
	 * @param food Das zu modifizierende Food.
 	 * @param name Der neue Name des zu modifizierenden Food Objekts.
 	 * @param ingredientImage Das neue Bild des zu modifizierenden Food Objekts.
 	 * @param subFoods Die neuen Zutaten des zu modifizierenden Food Objekts.
 	 * @param ingredients Die neuen Inhaltsstoffe des zu modifizierenden Food Objekts.
	 * @throws ObjectAlreadyExistsException Das zum Test zu erstellende Objekt existiert schon
	 */
	@Test
	public void testModifyFood() throws ObjectAlreadyExistsException {
		flc.createFood("TestFood", null, subFoods, ingredients);
		assertTrue(dir.getFoodList().iterator().next().getName().equals("TestFood"));
		flc.modifyFood(dir.getFoodList().iterator().next(), "Test", null, subFoods, ingredients);
		assertTrue(dir.getFoodList().size()==1);
		assertTrue(dir.getFoodList().iterator().next().getName().equals("Test"));
	}

	/**
	 * Testet ob ein Food gelöscht werden kann
	 * @throws ObjectNotExistentException Zu löschendes Objekt existiert nicht
	 * @throws ObjectAlreadyExistsException Das zum Test zu erstellende Objekt existiert schon 
	 */
	@Test(expected = ObjectNotExistentException.class)
	public void testDeleteFood() throws ObjectNotExistentException, ObjectAlreadyExistsException {
		flc.createFood("TestFood", null, subFoods, ingredients);
		assertTrue(dir.getFoodList().size()==1);
		assertTrue(dir.getFoodList().iterator().next().getName().equals("TestFood"));
		flc.deleteFood(testFood);
		assertFalse(dir.exists(testFood));
		assertTrue(dir.getFoodList().size()==0);
	}

}
