package controller;

import java.util.Collection;
import java.util.List;

import model.DiaryEntry;
import model.DrugEntry;
import model.EntryFilter;
import model.FactorEntry;
import model.Food;
import model.FoodEntry;
import model.HealthProblem;
import model.HealthProblemEntry;
import model.Ingredient;

public class SuperFilterController {
	protected EntryFilter filter;

	/**
	 * Überprüft ob Entry durch den Filter geht
	 * 
	 * @param toCheck
	 *            Entry welcher überprüft wird
	 * @return boolean gibt zurück ob der Entry durch den Filter geht
	 */
	// gibt an, ob der Entry den Filter erfüllt, also angezeigt werden soll
	protected boolean filterFood(FoodEntry toCheck) {
		if (filter.isFilterFood()) {
			// die Strings nach denen im Comment gesucht werden soll
			List<String> keywords = filter.getSearchFoods();

			if (keywords.isEmpty()) {
				return true;
			}

			// Beginnt zu Filtern
			// sucht im Kommentar, falls dort der String drin ist, return true
			if (filterComment(toCheck, keywords)) {
				return true;
			}
		}

		return filterFood(toCheck.getFood());
	}

	/**
	 * Überprüft ob Entry durch den Filter geht
	 * 
	 * @param toCheck
	 *            Entry welcher überprüft wird
	 * @return boolean gibt zurück ob der Entry durch den Filter geht
	 */
	protected boolean filterDrug(DrugEntry toCheck) {
		// Beginnt zu Filtern
		if (filter.isFilterDrugs()) {
			// die Strings nach denen gesucht werden soll
			List<String> keywords = filter.getSearchDrugs();

			if (keywords.isEmpty()) {
				return true;
			}

			// sucht im Kommentar, falls dort der String drin ist, return true
			if (filterComment(toCheck, keywords)) {
				return true;
			}

			// sucht im Namen und in der Beschreiung
			if (listContainsString(keywords, toCheck.getDrug().getName())
					|| listContainsString(keywords, toCheck.getDrug().getDescription())) {
				return true;
			}
		}

		// sucht in den Beschwerden
		if (filter.isFilterHealthProblems()) {
			for (HealthProblem healthProblem : toCheck.getDrug().getHealthProblemList()) {
				if (checkHealthProblem(healthProblem)) {
					return true;
				}
			}
		}

		// ansonsten false
		return false;
	}

	/**
	 * Überprüft ob Entry durch den Filter geht
	 * 
	 * @param toCheck
	 *            Entry welcher überprüft wird
	 * @return boolean gibt zurück ob der Entry durch den Filter geht
	 */
	protected boolean filterHealthProblem(HealthProblemEntry toCheck) {
		// die Strings nach denen gesucht werden soll
		List<String> keywords = filter.getSearchFoods();

		// Beginnt zu Filtern
		// sucht im Kommentar, falls dort der String drin ist, return true
		if (filterComment(toCheck, keywords)) {
			return true;
		}

		// sucht im Namen
		return checkHealthProblem(toCheck.getHealthProblem());
	}

	/**
	 * Überprüft ob Entry durch den Filter geht
	 * 
	 * @param toCheck
	 *            Entry welcher überprüft wird
	 * @return boolean gibt zurück ob der Entry durch den Filter geht
	 */
	protected boolean filterFactor(FactorEntry toCheck) {
		// die Strings nach denen gesucht werden soll
		List<String> keywords = filter.getSearchFactors();

		// Beginnt zu Filtern
		// sucht im Kommentar, falls dort der String drin ist, return true
		if (filterComment(toCheck, keywords)) {
			return true;
		}

		// sucht im Namen
		if (listContainsString(keywords, toCheck.getDescription())) {
			return true;
		}

		// ansonsten false
		return false;
	}

	protected boolean filterFood(Food food) {
		if (filter.isFilterFood()) {
			// die Strings nach denen im Comment gesucht werden soll
			List<String> keywords = filter.getSearchFoods();

			if (keywords.isEmpty()) {
				return true;
			}

			// sucht im Namen
			if (listContainsString(keywords, food.getName()) && filter.isFilterFood()) {
				return true;
			}
		}

		// sucht in ingredientsOfFoodList
		for (Food subfood : food.getIngredientsOfFoodList()) {
			if (filterFood(subfood)) {
				return true;
			}
		}

		if (filter.isFilterIngredients() || filter.isFilterHealthProblems()) {
			// sucht in ingredients
			Collection<Ingredient> ingredients = food.getIngredients();

			for (Ingredient ingredient : ingredients) {
				if (checkIngredient(ingredient)) {
					return true;
				}
			}
		}

		// ansonsten false
		return false;
	}
	/**
	 * Ueberprueft, ob das uebergebene Objekt vom Typ Ingredient einen der Suchbegriffe enthaelt.
	 * 
	 * @param ingredient Das zu untersuchende Objekt vom Typ Ingredient.
	 * @return Gibt einen boolschen Wert zurueck, der angibt, ob das uebergebene Objekt vom Typ Ingredient einen der Suchbegriffe enthaelt.
	 */
	protected boolean checkIngredient(Ingredient ingredient) {
		if (filter.isFilterIngredients()) {
			List<String> keywords = filter.getSearchIngredients();
			
			if(keywords.isEmpty()) {
				return true;
			}
			
			if ((listContainsString(keywords, ingredient.getName()) || listContainsString(keywords, ingredient.getInformation()))) {
				return true;
			}
		}
		
		if (filter.isFilterHealthProblems()) {
			for (HealthProblem healthProblem : ingredient.getHealthProblemList()) {
				if (checkHealthProblem(healthProblem)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Uberprueft, ob das uebergebene Objekt vom Typ HealthProblem einen der Suchbegriffe enthaelt.
	 * 
	 * @param healthProblem Das zu untersuchende Objekt vom Typ HealthProblem.
	 * @return Gibt einen boolschen Wert zurueck, der angibt, ob das uebergebene Objekt vom Typ HealthProblem einen der Suchbegriffe enthaelt.
	 */
	protected boolean checkHealthProblem(HealthProblem healthProblem) {
		List<String> keywords = filter.getSearchHealthProblems();
		
		if (listContainsString(keywords, healthProblem.getName()) || listContainsString(keywords, healthProblem.getDescription())) {
			return true;
		}
		
		return false;
	}
    
	/**
 	 * Überprüft ob der Kommentar Suchbegriffe enthält
 	 * @param entryToCheck Entry welcher überprüft wird
 	 * @param searchString Suchbegriffe nach denen gesucht wird
 	 * @return boolean gibt zurück ob der Kommentar durch den Filter geht
 	 */
	protected boolean filterComment(DiaryEntry entryToCheck, List<String> keywords) {
    	for (String keyword : keywords) {
	    	if (entryToCheck.getComment().contains(keyword)) {
	    		return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Ueberprueft, ob die uebergebene Liste mit generischem Typ 'String' den uebergebenen String 'contained' enthaelt.
     * 
     * @param keywords Eine Liste mit generischem Typ 'String'.
     * @param contained Der String, der in der uebergebenen Liste gesucht werden soll.
     * @return Gibt einen boolschen Wert zurueck, der angibt, ob der String 'contained' in der Stringliste 'keywords' enthalten ist.
     */
	protected boolean listContainsString(List<String> keywords, String contained) {
    	for (String keyword : keywords) {
    		if (keyword.contains(contained)) {
    			return true;
    		}
    	}
    	
    	return false;
    }
}
