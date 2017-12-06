package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistentException;
import model.Diary;
import model.HealthProblem;
import model.Ingredient;


/**
 * 
 * JUnit Testklasse fuer die Klasse IngredientListController.
 * 
 * @author Simon Kurz
 */
public class IngredientListControllerTest {

	private ETController etController;
	private Diary diary;
	private IngredientListController controller;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	/**
	 * Erstellt jeweils ein Objekt vom Typ ETController, Diary und IngredientListConroller. 
	 * Das Diary wird dem ETController ueber die Methode ETController.setCurrentDiary(Diary diary) uebergeben.
	 */
	@Before
	public void setUp() {
		etController = new ETController();
		diary = new Diary();
		etController.setCurrentDiary(diary);
		controller = new IngredientListController(etController);
	}
	
	/**
	 * Testet, den Konstuktor der Klasse IngredientListController auf funktionale Korrektheit.
	 */
	@Test
	public void testIngredientListController() {
		IngredientListController controller = new IngredientListController(etController);
		assertNotNull("Es konnte kein Objekt vom Typ IngredientListController erzeugt werden.", controller);
	}

	/**
	 * Testet, ob ein Inhaltsstoff, der nicht bereits vorhanden ist, im Programm korrekt hinzugefuegt wird.
	 */
	@Test
	public void testCreateIngredient() {
		assertEquals(0, diary.getIngredientList().size());
		
		ArrayList<HealthProblem> healthProblems = new ArrayList<>();
		healthProblems.add(new HealthProblem("problem_1", "description_1"));
		
		try {
			controller.createIngredient("name_1", "information_1", healthProblems);
		} catch (ObjectAlreadyExistsException e) {
			e.printStackTrace();
		}
		
		ArrayList<Ingredient> ingredientList = (ArrayList <Ingredient>) diary.getIngredientList();
		
		assertEquals(1, ingredientList.size());
		assertEquals("name_1", ingredientList.get(0).getName());
		assertEquals("information_1", ingredientList.get(0).getInformation());
		assertEquals(healthProblems, ingredientList.get(0).getHealthProblemList());
	}
	
	/**
	 * Testet, ob das Programm erkennt, wenn ein Inhaltsstoff hinzugefuegt werden soll, der bereits vorhanden ist.
	 */
	@Test
	public void testCreateIngredientForException() throws ObjectAlreadyExistsException {
		ArrayList<HealthProblem> healthProblems = new ArrayList<>();
		healthProblems.add(new HealthProblem("problem_1", "description_1"));
		
		thrown.expect(ObjectAlreadyExistsException.class);
		
		controller.createIngredient("name_1", "information_1", healthProblems);
		controller.createIngredient("name_1", "information_1", healthProblems);
	}

	/**
	 * Testet, ob ein Inhaltsstoff korrekt modifiziert wird, 
	 * d.h. Eigenschaften werden nur erstetzt, wenn der zugehoerige Parameter nicht 'null' ist.
	 */
	@Test
	public void testModifyIngredient() {
		ArrayList<HealthProblem> healthProblems1 = new ArrayList<>();
		healthProblems1.add(new HealthProblem("problem_1", "description_1"));
		ArrayList<HealthProblem> healthProblems2 = new ArrayList<>();
		healthProblems2.add(new HealthProblem("problem_2", "description_2"));
		Ingredient ingredient = new Ingredient("name_1", "information_1", healthProblems1);
		
		controller.modifyIngredient("name_2", "information_2", healthProblems2, ingredient);
		
		assertEquals("name_2", ingredient.getName());
		assertEquals("information_2", ingredient.getInformation());
		assertEquals(healthProblems2, ingredient.getHealthProblemList());
		
		controller.modifyIngredient(null, null, null, ingredient);
		
		assertEquals("name_2", ingredient.getName());
		assertEquals("information_2", ingredient.getInformation());
		assertEquals(healthProblems2, ingredient.getHealthProblemList());
		
		controller.modifyIngredient("name_3", null, null, ingredient);
		
		assertEquals("name_3", ingredient.getName());
		assertEquals("information_2", ingredient.getInformation());
		assertEquals(healthProblems2, ingredient.getHealthProblemList());
		
		controller.modifyIngredient(null, "information_3", null, ingredient);
		
		assertEquals("name_3", ingredient.getName());
		assertEquals("information_3", ingredient.getInformation());
		assertEquals(healthProblems2, ingredient.getHealthProblemList());
		
		ArrayList<HealthProblem> healthProblems3 = new ArrayList<>();
		healthProblems3.add(new HealthProblem("problem_3", "description_3"));
		
		controller.modifyIngredient(null, null, healthProblems3, ingredient);
		
		assertEquals("name_3", ingredient.getName());
		assertEquals("information_3", ingredient.getInformation());
		assertEquals(healthProblems3, ingredient.getHealthProblemList());
	}

	/**
	 * Testet, ob das Loeschen eines vorhandenen Inhaltsstoffes funktioniert.
	 */
	@Test
	public void testDeleteIngredientIngredient() {
		ArrayList<HealthProblem> healthProblems1 = new ArrayList<>();
		healthProblems1.add(new HealthProblem("problem_1", "description_1"));
		
		Ingredient ingredient1 = new Ingredient("name_1", "information_1", healthProblems1);
		
		ArrayList<HealthProblem> healthProblems2 = new ArrayList<>();
		healthProblems2.add(new HealthProblem("problem_2", "description_2"));
		
		Ingredient ingredient2 = new Ingredient("name_2", "information_2", healthProblems2);
		
		Collection<Ingredient> ingredientList = diary.getIngredientList();
		ingredientList.add(ingredient1);
		ingredientList.add(ingredient2);
		
		assertEquals(2, diary.getIngredientList().size());
		
		try {
			controller.deleteIngredient(ingredient1);
		} catch (ObjectNotExistentException e) {
			e.printStackTrace();
		}
		
		assertEquals(1, diary.getIngredientList().size());
		
		Ingredient remainingIngredient = ingredientList.iterator().next();
		
		assertEquals("name_2", remainingIngredient.getName());
		assertEquals("information_2", remainingIngredient.getInformation());
		assertEquals(healthProblems2, remainingIngredient.getHealthProblemList());
	}
	
	/**
	 * Testet, ob das Programm erkennt, wenn ein Inhaltsstoff geloescht werden soll, der gar nicht vorhanden ist.
	 * @throws ObjectNotExistentException 
	 */
	@Test
	public void testDeleteIngredientForException() throws ObjectNotExistentException {
		ArrayList<HealthProblem> healthProblems1 = new ArrayList<>();
		healthProblems1.add(new HealthProblem("problem_1", "description_1"));
		
		Ingredient ingredient1 = new Ingredient("name_1", "information_1", healthProblems1);
		
		ArrayList<HealthProblem> healthProblems2 = new ArrayList<>();
		healthProblems2.add(new HealthProblem("problem_2", "description_2"));
		
		Ingredient ingredient2 = new Ingredient("name_2", "information_2", healthProblems2);
		
		
		thrown.expect(ObjectNotExistentException.class);
		
		Collection<Ingredient> ingredientList = diary.getIngredientList();
		ingredientList.add(ingredient2);
		
		controller.deleteIngredient(ingredient1);
	}

}
