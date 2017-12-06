package controller;

import java.util.ArrayList;
import java.util.Collection;

import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistentException;
import gui.ListAUI;
import model.HealthProblem;
import model.Ingredient;

/**
 * 
 * Schnittstelle/Vermittler zwischen View und Model(hier: Inhaltsstoffe)
 * 
 * @author Simon Kurz, Christian Walczuch
 */
public class IngredientListController {

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
     * Der Konstruktor erwartet als Argument ein Objekt vom Typ ETController ueber den auf die weiteren Controller im Programm zugegriffen werden kann.
     * 
     * @param etController Der zentrale Controller des Programms. Wird genutzt, um an weiteren Conroller heranzukommen.
     */
    public IngredientListController(ETController etController) {
    	this.etController = etController;
		this.listAUI = new ArrayList<ListAUI>();
    }

	/**
	 * Legt ein neues Objekt vom Typ Ingredient an.
	 *
	 * @param name
	 *            Der Name des zu erstellenden Inhaltsstoffs.
	 * @param information
	 *            Weitere Informationen zum Inhaltsstoff.
	 * @param healthProblems
	 *            Liste der Beschwerden, die durch den Inhaltsstoff
	 *            hervorgerufen werden können.
	 * @throws ObjectAlreadyExistsException
	 *             Das zu erstellende Objekt ist bereits vorhanden.
	 * 
	 * @precondition Der ETController muss über ein Objekt vom Typ Diary
	 *               verfuegen.
	 */
	public void createIngredient(String name, String information, Collection<HealthProblem> healthProblems)
			throws ObjectAlreadyExistsException {
		Ingredient ingredient = new Ingredient(name, information);
		ingredient.setHealthProblemList(healthProblems);
		etController.getCurrentDiary().createIngredient(ingredient);
		refresh();
	}

	/**
	 * Modifiziert einen Inhaltsstoff. Die als Parameter übergebenen Attribute
	 * überschreiben die alten Attribute des Inhaltsstoffes.
	 *
	 * @param name
	 *            Der neue Name des übergebenen Inhaltsstoffes.
	 * @param information
	 *            Neue Informationen des übergebenen Inhaltsstoffes.
	 * @param healthProblems
	 *            Neue Beschwerden, die durch den Inhaltsstoff hervorgerufen
	 *            werden können.
	 * @param ingredient
	 *            Der zu modifizierende Inhaltsstoff.
	 * 
	 */
	public void modifyIngredient(String name, String information, Collection<HealthProblem> healthProblem,
			Ingredient ingredient) {
		if (name != null)
			ingredient.setName(name);
		if (information != null)
			ingredient.setInformation(information);
		if (healthProblem != null)
			ingredient.setHealthProblemList(healthProblem);
		refresh();
	}


	public void addAUI(ListAUI list) {
		this.listAUI.add(list);
	}

	public void removeAUI(ListAUI list) {
		this.listAUI.remove(list);
	}

	
	public void refresh(){
		
		for(ListAUI lAUI : listAUI)
    	{
    		lAUI.refreshList();
    	}
	}
	
    /**
 	 * Löscht den als Parameter uebergebenen Inhaltsstoff aus der Inhaltsstoffliste, falls dieser vorhanden ist.
 	 *
 	 * @param ingredient Der zu löschende Inhaltsstoff.
     * @throws ObjectAlreadyExistsException Das zu loeschende Objekt ist nicht im Programm vorhanden und kann darum nicht geloescht werden.
 	 * 
 	 * @precondition Der ETController muss über ein Objekt vom Typ Diary verfügen.
 	 */
    public void deleteIngredient(Ingredient ingredient) throws ObjectNotExistentException {
    	etController.getCurrentDiary().removeIngredient(ingredient);
    	refresh();
    }
    
}
