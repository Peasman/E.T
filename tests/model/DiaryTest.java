package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistentException;

/**
 * Testet die Klasse Diary
 * 
 * @author Moritz Ludolph
 * 
 */
public class DiaryTest {

	Diary diary;

	/**
	 * Erzeugt ein Diary-Objekt für die Tests
	 */
	@Before
	public void setUp() {
		diary = new Diary();
	}

	/**
	 * Testet, ob beim Konstruktor von Diary alle Listen initialisiert werden
	 */
	@Test
	public void testDiary() {
		assertNotNull(diary.getDiaryEntryList());
		assertNotNull(diary.getDrugList());
		assertNotNull(diary.getFoodList());
		assertNotNull(diary.getIngredientList());
	}

	/**
	 * Testet, ob das einfügen eines neuen Food-Objektes korrekt funktioniert
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateFood() {
		Food food = new Food("Spaghetti", null);
		try {
			diary.createFood(food);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
		assertTrue(diary.exists(food));

		try {
			diary.createFood(null);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Testet, ob doppeltes einfügen korrekt behandelt wird
	 * 
	 * @throws ObjectAlreadyExistsException
	 */
	@Test(expected = ObjectAlreadyExistsException.class)
	public void testCreateFoodOnDoubleEntry() throws ObjectAlreadyExistsException {
		Food food = new Food("Spaghetti", null);

		diary.createFood(food);
		diary.createFood(food);
	}

	/**
	 * Testet, ob das einfügen eines neuen Drug-Objektes korrekt funktioniert
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateDrug() {
		Drug drug = new Drug("Paracetamol");
		try {
			diary.createDrug(drug);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
		assertTrue(diary.exists(drug));

		try {
			diary.createDrug(null);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Testet, ob doppeltes einfügen korrekt behandelt wird
	 * 
	 * @throws ObjectAlreadyExistsException
	 */
	@Test(expected = ObjectAlreadyExistsException.class)
	public void testCreateDrugOnDoubleEntry() throws ObjectAlreadyExistsException {
		Drug drug = new Drug("Paracetamol");

		diary.createDrug(drug);
		diary.createDrug(drug);
	}

	/**
	 * Testet, ob das einfügen eines neuen Ingredient-Objektes korrekt
	 * funktioniert
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateIngredient() {
		Ingredient ingredient = new Ingredient("Laktose", "Geil");
		try {
			diary.createIngredient(ingredient);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
		assertTrue(diary.exists(ingredient));

		try {
			diary.createIngredient(null);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Testet, ob doppeltes einfügen korrekt behandelt wird
	 * 
	 * @throws ObjectAlreadyExistsException
	 */
	@Test(expected = ObjectAlreadyExistsException.class)
	public void testCreateIngredientOnDoubleEntry() throws ObjectAlreadyExistsException {
		Ingredient ingredient = new Ingredient("Laktose", "Geil");
		diary.createIngredient(ingredient);
		diary.createIngredient(ingredient);
	}

	/**
	 * Testet, ob das löschen eines Food-Objektes korrekt funktioniert
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveFood() {
		Food food = new Food("Pizza", null);
		try {
			diary.createFood(food);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
		try {
			diary.removeFood(food);
		} catch (ObjectNotExistentException e) {
			e.printStackTrace();
		}
		assertFalse(diary.exists(food));

		try {
			diary.removeFood(null);
		} catch (ObjectNotExistentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Testet, ob das löschen eines nicht vorhandenen Food-Objektes korrekt
	 * funktioniert
	 * 
	 * @throws ObjectNotExistentException
	 */
	@Test(expected = ObjectNotExistentException.class)
	public void testRemoveFoodNotExistent() throws ObjectNotExistentException {
		Food food = new Food("Pizza", null);
		diary.removeFood(food);
	}

	/**
	 * Testet, ob das löschen eines Drug-Objektes korrekt funktioniert
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveDrug() {
		Drug drug = new Drug("Paracetamol");
		try {
			diary.createDrug(drug);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
		try {
			diary.removeDrug(drug);
		} catch (ObjectNotExistentException e) {
			e.printStackTrace();
		}
		assertFalse(diary.exists(drug));

		try {
			diary.removeDrug(null);
		} catch (ObjectNotExistentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Testet, ob das löschen eines nicht vorhandenen Drug-Objektes korrekt
	 * funktioniert
	 * 
	 * @throws ObjectNotExistentException
	 */
	@Test(expected = ObjectNotExistentException.class)
	public void testRemoveDrugNotExistent() throws ObjectNotExistentException {
		Drug drug = new Drug("Paracetamol");
		diary.removeDrug(drug);
	}

	/**
	 * Testet, ob das löschen eines Ingredient-Objektes korrekt funktioniert
	 */
	@Test(expected = NullPointerException.class)
	public void testRemoveIngredient() {
		Ingredient ingredient = new Ingredient("GEIL", null);
		try {
			diary.createIngredient(ingredient);
		} catch (ObjectAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			diary.removeIngredient(ingredient);
		} catch (ObjectNotExistentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(diary.exists(ingredient));

		try {
			diary.removeIngredient(null);
		} catch (ObjectNotExistentException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Testet, ob das löschen eines nicht vorhandenen Ingredient-Objektes
	 * korrekt funktioniert
	 * 
	 * @throws ObjectNotExistentException
	 */
	@Test(expected = ObjectNotExistentException.class)
	public void testRemoveIngredientNotExistent() throws ObjectNotExistentException {
		Ingredient ingredient = new Ingredient("GEIL", null);
		diary.removeIngredient(ingredient);
	}

	/**
	 * Testet das hinzufügen eines neuen Tagebucheintrags
	 */
	@Test
	public void testAddEntry() {
		Food food = new Food("Lul", null);
		FoodEntry entry = new FoodEntry(LocalDateTime.MIN, 3.0, Unit.MINUTES, "Hi", food);
		diary.addEntry(entry);

		assertTrue(diary.getDiaryEntryList().contains(entry));
	}

	/**
	 * Testet, ob exists Food bei einem existierenden Objekt true zurück gibt
	 * und bei einem nicht existierenden false
	 */
	@Test
	public void testExistsFood() {
		Food foodrug1 = new Food("Pizza", null);
		Food foodrug2 = new Food("Nudeln", null);
		try {
			diary.createFood(foodrug1);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
		assertTrue(diary.exists(foodrug1));
		assertFalse(diary.exists(foodrug2));
	}

	/**
	 * Testet, ob exists Drug bei einem existierenden Objekt true zurück gibt
	 * und bei einem nicht existierenden false
	 */
	@Test
	public void testExistsDrug() {
		Drug drug1 = new Drug("Paracetamol");
		Drug drug2 = new Drug("Aspirin");
		try {
			diary.createDrug(drug1);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
		assertTrue(diary.exists(drug1));
		assertFalse(diary.exists(drug2));

	}

	/**
	 * Testet, ob exists Ingredient bei einem existierenden Objekt true zurück
	 * gibt und bei einem nicht existierenden false
	 */
	@Test
	public void testExistsIngredient() {
		Ingredient ingerdient1 = new Ingredient("Laktose", null);
		Ingredient ingerdient2 = new Ingredient("Gluten", null);
		try {
			diary.createIngredient(ingerdient1);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
		assertTrue(diary.exists(ingerdient1));
		assertFalse(diary.exists(ingerdient2));
	}
}
