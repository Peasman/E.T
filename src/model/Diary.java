package model;

import java.util.ArrayList;

import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistentException;

/**
 * Die Haupt-Model-Klasse die alle Listen beinhaltet.
 * Das sind die diaryEntryList, foodList, ingredientList und drugList.
 * @author Friedemann Runte
 *
 */
public class Diary {

	/**
	 * Die List die alle DiaryEntry enthält
	 */
	private ArrayList<DiaryEntry> diaryEntryList;

	/**
	 * Die Liste die alle Foods enthält
	 */
	private ArrayList<Food> foodList;

	/**
	 * Die Liste die alle Ingredients enthält
	 */
	private ArrayList<Ingredient> ingredientList;

	/**
	 * Die Liste die alle Medikamente enthält
	 */
	private ArrayList<Drug> drugList;

	/**
	 * Initialisiert alle Listen die das Diary benötigt
	 */
	public Diary() {
		diaryEntryList = new ArrayList<DiaryEntry>();
		foodList = new ArrayList<Food>();
		ingredientList = new ArrayList<Ingredient>();
		drugList = new ArrayList<Drug>();
	}

	/**
	 *
	 * Fügt ein Food in die foodList ein.
	 * 
	 * @param food
	 *            Das Food welches in die Liste eingefügt werden soll.
	 * @precondition Die foodList muss initialisiert sein.
	 * @throws UnsupportedOperation
	 *             Exception Diese Exception wird geworfen, fallsdie Methode
	 *             noch nicht implementiert ist.
	 */
	public void createFood(Food food) throws ObjectAlreadyExistsException {
		if (food == null)
			throw new NullPointerException();
		if (!exists(food)) {
			foodList.add(food);
		} else {
			throw new ObjectAlreadyExistsException();
		}
	}

	/**
	 *
	 * Fügt ein Medikament in die drugList ein.
	 * 
	 * @param drug
	 *            Das Medikament was eingefügt werden soll.
	 * @precondition Die drugList muss initialisiert sein.
	 * @throws UnsupportedOperation
	 *             Exception Diese Exception wird geworfen, fallsdie Methode
	 *             noch nicht implementiert ist.
	 */
	public void createDrug(Drug drug) throws ObjectAlreadyExistsException {
		if (drug == null)
			throw new NullPointerException();
		if (!exists(drug)) {
			drugList.add(drug);
		} else {
			throw new ObjectAlreadyExistsException();
		}
	}

	/**
	 *
	 * Fügt einen ingredient in die ingredientList ein.
	 * 
	 * @param ingredient
	 *            Der ingredient der eingefügt werden soll.
	 * @precondition Die ingredientList muss initialisiert worden sein.
	 * @throws UnsupportedOperation
	 *             Exception Diese Exception wird geworfen, fallsdie Methode
	 *             noch nicht implementiert ist.
	 */
	public void createIngredient(Ingredient ingredient) throws ObjectAlreadyExistsException {
		if (ingredient == null)
			throw new NullPointerException();
		if (!exists(ingredient)) {
			ingredientList.add(ingredient);
		} else {
			throw new ObjectAlreadyExistsException();
		}
	}

	/**
	 * Löscht einen Eintrag aus der DiaryEntryList
	 * 
	 * @param entry
	 *            der zu löschende Eintrag
	 * @throws ObjectNotExistentException
	 *             Falls das zu löschende Object nicht exisitiert
	 */
	public void removeEntry(DiaryEntry entry) throws ObjectNotExistentException {
		if (entry == null)
			throw new NullPointerException();
		for (DiaryEntry e : diaryEntryList) {
			if (e.equals(entry)) {
				diaryEntryList.remove(e);
				return;
			}
		}
		throw new ObjectNotExistentException();
	}

	/**
	 *
	 * Löscht ein Food aus der foodList.
	 * 
	 * @param food
	 *            Das Food was gelöscht werden soll.
	 * @precondition Die foodList muss initialisiert worden sein und das Food
	 *               muss in dieser Liste vorhanden sein.
	 * @throws ObjectNotExistentException
	 *             Falls das zu löschende Object nicht exisitiert
	 */
	public void removeFood(Food food) throws ObjectNotExistentException {
		if (food == null)
			throw new NullPointerException();
		if (exists(food)) {
			foodList.remove(food);
		} else {
			throw new ObjectNotExistentException();

		}
	}

	/**
	 *
	 * Löscht ein drug aus der drugList.
	 * 
	 * @param drug
	 *            Die drug die gelöscht werden soll.
	 * @precondition Die drugList muss initialisiert worden sein und die drug
	 *               muss in dieser Liste vorhanden sein.
	 * @throws ObjectNotExistentException
	 *             Falls das zu löschende Object nicht exisitiert
	 */
	public void removeDrug(Drug drug) throws ObjectNotExistentException {
		if (drug == null)
			throw new NullPointerException();
		
		if (exists(drug)) {
			drugList.remove(drug);
		} else {
			throw new ObjectNotExistentException();
		}
	}

	/**
	 *
	 * Löscht ein ingredient aus der ingredientList.
	 * 
	 * @param ingredient
	 *            Der ingredient der gelöscht werden soll.
	 * @precondition Die ingredientList muss initialisiert worden sein und das
	 *               Ingredient enthalten
	 * @throws ObjectNotExistentException
	 *             Falls das zu löschende Object nicht exisitiert
	 */
	public void removeIngredient(Ingredient ingredient) throws ObjectNotExistentException {
		if (ingredient == null)
			throw new NullPointerException();
		
		if (exists(ingredient)) {
			ingredientList.remove(ingredient);
		} else {
			throw new ObjectNotExistentException();
		}
	}

	/**
	 *
	 * Fügt einen DiaryEntry in die diaryEntryList ein.
	 * 
	 * @param entry
	 *            Der Eintrag der in die Liste eingefügt werden soll
	 * @precondition Die diaryEntryList muss initialisiert worden sein.
	 * @throws UnsupportedOperation
	 *             Exception Diese Exception wird geworfen, fallsdie Methode
	 *             noch nicht implementiert ist.
	 */
	public void addEntry(DiaryEntry entry) {
		if (entry == null)
			throw new NullPointerException();
		
		diaryEntryList.add(entry);
	}

	/**
	 *
	 * Gibt zurück ob das Food bereits in der Liste vorhanden ist.
	 * 
	 * @param food
	 *            Das Food wonach gesucht wird.
	 * @return boolean Ja falls es vorhanden ist, Nein wenn nicht
	 */
	public boolean exists(Food food) {
		if (food == null)
			throw new NullPointerException();
		
		if (foodList.contains(food)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 *
	 * Gibt zurück ob das Medikament bereits in der Liste vorhanden ist.
	 * 
	 * @param drug
	 *            Die Drug wonach gesucht wird.
	 * @return boolean Ja falls es vorhanden ist, Nein wenn nicht
	 */
	public boolean exists(Drug drug) {
		if (drug == null)
			throw new NullPointerException();
		if (drugList.contains(drug)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 *
	 * Gibt zurück ob das ingredient bereits in der Liste vorhanden ist.
	 * 
	 * @param food
	 *            Das ingredient wonach gesucht wird.
	 * @return boolean Ja falls es vorhanden ist, Nein wenn nicht
	 * 
	 */
	public boolean exists(Ingredient ingredient) {
		if (ingredient == null)
			throw new NullPointerException();
		if (ingredientList.contains(ingredient)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * gibt alle Einträge als Liste zurück
	 * 
	 * @return List<DiaryEntry>
	 */
	public ArrayList<DiaryEntry> getDiaryEntryList() {
		return diaryEntryList;
	}

	/**
	 * gibt die NahrungsmittelListe zurück
	 * 
	 * @return List<Food>
	 */
	public ArrayList<Food> getFoodList() {
		return foodList;
	}

	/**
	 * Gibt alle Inhaltsstoffe zurück
	 * 
	 * @return List<Ingredient>
	 */
	public ArrayList<Ingredient> getIngredientList() {
		return ingredientList;
	}

	/**
	 * Gibt alle Medikamente zurück
	 * 
	 * @return List<Drug>
	 */
	public ArrayList<Drug> getDrugList() {
		return drugList;
	}
}
