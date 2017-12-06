package controller;
import java.util.ArrayList;

import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistentException;
import gui.ListAUI;
import model.Diary;
import model.Drug;
import model.HealthProblem;

/**
 * Der Controller verwaltet eine Medikamentenliste und stellt alle nötigen Operationen zum Bearbeiten dieser zur Verfügung
 * @author Olga Scheftelowitsch
 *
 */
public class DrugListController {

    /**
 	 * der ETController
 	 */
    private ETController etController;

    /**
 	 * alle List AUIs, die diese Liste beobachen
 	 */
    private ArrayList<ListAUI> listAUI;

    /**
     * Der Konstruktor für den DrugListController
     * @param etController
     * 						der etController um Zugriff auf das Model zu haben
     */
    public DrugListController(ETController etController) {
    	this.etController = etController;
    	this.listAUI = new ArrayList<ListAUI>();
    }

    /**
 	 * erstellt eine Drug und fügt sie, wenn sie noch nicht existiert in die drugList ein
 	 * @param name
 	 * 			Name des neuen Medikaments
 	 * @param description
 	 * 			Beschreibung des neuen Medikaments
 	 * @param healthProblems
 	 * 			Gesundheitsprobleme ausgelöst vom neuen Medikament
     * @throws ObjectAlreadyExistsException 
     * 			Falls das neue Medikament schon existiert
 	 */
    public void createDrug(String name, String description, ArrayList<HealthProblem> healthProblems) throws ObjectAlreadyExistsException {
        Diary diary = etController.getCurrentDiary();
        Drug newDrug = new Drug(name);
        newDrug.setDescription(description);
        newDrug.setHealthProblemList(healthProblems);
        if(diary.exists(newDrug)){
        	throw new ObjectAlreadyExistsException();
        }else{
        	diary.getDrugList().add(newDrug);
        }
        refresh();
    }

    /**
     * Ändert einen Eintrag in der drugList wenn Valide
     * @param drug
     * 				Medikament, das zu ändern ist
     * @param name
     * 				neuer Name des Medikaments
     * @param description
     * 				neue Beschreibung der Droge
     * @param healthProblems
     * 				neue Gesundheitsproblemeliste
     * @throws ObjectAlreadyExistsException
     * 				im Fall das Medikament auf das alles geändert wird existiert schon
     * @throws ObjectNotExistentException 
     * 				falls das zu ändernde Medikament nicht existiert
     * 				
     */
    public void modifyDrug(Drug drug, String name, String description, ArrayList<HealthProblem>  healthProblems) throws ObjectAlreadyExistsException, ObjectNotExistentException {
    	Diary diary = etController.getCurrentDiary();
    	Drug newDrug = new Drug(name);
        newDrug.setDescription(description);
        newDrug.setHealthProblemList(healthProblems);
        if(diary.exists(newDrug) && drug.getName()!=name){
        	throw new ObjectAlreadyExistsException();
        }else{
        	drug.setDescription(description);
        	drug.setName(name);
        	drug.setHealthProblemList(healthProblems);
        }
        refresh();
        
    }

    
    /**
     * entfernt ein Medikament
     * @param drug
     * 				das zu löschende Medikament
     * @throws ObjectNotExistentException
     * 				falls das Medikament nicht existiert
     */
    public void deleteDrug(Drug drug) throws ObjectNotExistentException{
    	Diary diary = etController.getCurrentDiary();
    	diary.removeDrug(drug);
    	refresh();
    }
    
    /**
     * refreshed alle List AUIs
     */
    public void refresh(){
    	if(listAUI!=null){
    		for(ListAUI lAui : listAUI){
    			lAui.refreshList();
    		}
    	}
    }
    public void addAUI(ListAUI list)
    {
    	this.listAUI.add(list);
    }
    public void removeAUI(ListAUI list)
    {
    	this.listAUI.remove(list);
    }
}
