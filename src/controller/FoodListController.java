package controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistentException;
import gui.ListAUI;
import model.Food;
import model.HealthProblem;
import model.Ingredient;

/**
 * Schnittstelle/Vermittler zwischen View und Model(hier: Nahrungsmittel)
 * 
 * @author Simon Kurz
 *
 */
public class FoodListController {

	/**
	 * Zentraler Controller des Programms. Wird genutzt, um an weiteren
	 * Conroller heranzukommen.
	 */
	private ETController etController;

	/**
	 * Liste der mit Inhaltsstoffen assoziierten GUIs.
	 */
	private Collection<ListAUI> listAUI;

	/**
	 * Der Konstruktor erwartet als Argument ein Objekt vom Typ ETController
	 * ueber den auf die weiteren Controller im Programm zugegriffen werden
	 * kann.
	 * 
	 * @param etController
	 *            Der zentrale Controller des Programms. Wird genutzt, um an
	 *            weiteren Conroller heranzukommen.
	 */
	public FoodListController(ETController etController) {
		this.etController = etController;
		listAUI = new ArrayList<ListAUI>();
	}

	/**
	 * Erstellt ein neues Objekt vom Typ Food und fuegt es, wenn nicht bereits
	 * vorhanden, in die Liste aller 'Food' ein.
	 *
	 * @param name
	 *            Der Name des zu erstellenden Nahrungsmittels.
	 * @param ingredientImage
	 *            Ein optionales Bild der Inhaltsstoffe/Zutaten des
	 *            Lebensmittels.
	 * @param subFoods
	 *            Eine Liste der Zutaten des Lebensmittels.
	 * @param ingredients
	 *            Eine Liste der Inhaltsstoffe des Lebensmittels.
	 * @throws ObjectAlreadyExistsException
	 *             Das zu erstellende Objekt ist bereits vorhanden.
	 * 
	 * @precondition Der ETController muss ueber ein Objekt vom Typ Diary
	 *               verfuegen.
	 */
	public void createFood(String name, String ingredientPicturePath, Collection<Food> subFoods,
			Collection<Ingredient> ingredients) throws ObjectAlreadyExistsException {
		Food food = new Food(name, ingredientPicturePath);
		food.setIngredientPicturePath(ingredientPicturePath);
		food.setIngredients(ingredients);
		food.setIngredientsOfFoodList(subFoods);
		etController.getCurrentDiary().createFood(food);
		refresh();
	}

	/**
	 * Ueberschreibt die Attribute des uebergebenen Food Objekts, falls der
	 * zugehoerige Parameter (name, ingredientImage, subFoods, ingredients) !=
	 * null.
	 *
	 * @param food
	 *            Das zu modifizierende Food.
	 * @param name
	 *            Der neue Name des zu modifizierenden Food Objekts.
	 * @param ingredientImage
	 *            Das neue Bild der Inhaltsstoffe/Zutaten des zu modifizierenden
	 *            Food Objekts.
	 * @param subFoods
	 *            Die neuen Zutaten des zu modifizierenden Food Objekts.
	 * @param ingredients
	 *            Die neuen Inhaltsstoffe des zu modifizierenden Food Objekts.
	 * 
	 * @precondition Der ETController muss ueber ein Objekt vom Typ Diary
	 *               verfuegen.
	 */
	public void modifyFood(Food food, String name, String ingredientPicturePath, Collection<Food> subFoods,
			Collection<Ingredient> ingredients) {
		if (food != null)
			food.setName(name);
		food.setIngredientPicturePath(ingredientPicturePath);
		if (ingredients != null)
			food.setIngredients(ingredients);
		if (subFoods != null)
			food.setIngredientsOfFoodList(subFoods);
		refresh();
	}

	/**
	 * Loescht das uebergebene Food Objekt aus dem Diary.
	 *
	 * @param food
	 *            Das zu loeschende Food Objekt.
	 * @throws ObjectNotExistentException
	 *             Das zu loeschende Objekt ist nicht im Programm vorhanden und
	 *             kann darum nicht geloescht werden.
	 * 
	 * @precondition Der ETController muss ueber ein Objekt vom Typ Diary
	 *               verfuegen.
	 */
	public void deleteFood(Food food) throws ObjectNotExistentException {
		etController.getCurrentDiary().removeFood(food);
		refresh();
	}

	/**
	 * Lädt alle Inhaltsstoffe von Zutaten einer Mahlzeit
	 * 
	 * @param food
	 *            Das Nahrungsmittel dessen Inhaltsstoffe extrahiert werden
	 *            sollen.
	 * @return Ein HashSet was alle Ingredients beinhaltet
	 */
	public static HashSet<Ingredient> extractIngredients(int level, Food food) {
		if (food.getIngredientsOfFoodList() == null || food.getIngredientsOfFoodList().isEmpty()) {
			return new HashSet<Ingredient>(food.getIngredients());
		}

		HashSet<Ingredient> ingredients = new HashSet<Ingredient>();
		if (level <= 10) {
			for (Food fs : food.getIngredientsOfFoodList()) {
				level++;
				ingredients.addAll(extractIngredients(level, fs));
				ingredients.addAll(fs.getIngredients());
			}
		}
		ingredients.addAll(food.getIngredients());
		return ingredients;
	}

	/**
	 * Lädt alle HealthProblems von Ingredients
	 * 
	 * @param ingredients
	 *            als Hashmap die bereits extrahiert wurden
	 * @return Ein HashSet was alle HealthProblems beinhaltet
	 */
	public static HashSet<HealthProblem> extractHealthProblems(HashSet<Ingredient> ingredients) {

		if (ingredients == null || ingredients.isEmpty())
			return new HashSet<HealthProblem>();

		HashSet<HealthProblem> healthProblems = new HashSet<HealthProblem>();
		for (Ingredient i : ingredients) {
			healthProblems.addAll(i.getHealthProblemList());
		}
		return healthProblems;
	}

	public void addAUI(ListAUI listAUI) {
		this.listAUI.add(listAUI);
	}

	public void deleteAUI(ListAUI listAUI) {
		this.listAUI.remove(listAUI);
	}

	public void refresh() {
		for (ListAUI lAUI : listAUI) {
			lAUI.refreshList();
		}
	}

}
