package controller;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import exceptions.ObjectNotExistentException;
import gui.EntryListAUI;
import model.*;

/**
 * Controller, welcher Tagebucheinträge verwalten kann und die GUI aktualisiert. Er greift über den ETController auf das Model zu.
 * Besitzt zudem eine Filterfunktion für die Entries.
 * @author Alexander Herlez
 */

public class EntryListController extends SuperEntryListController{

    
    
    /**
     * Konstruktor benötigt als Parameter den ETController.
     * @param etController der Hauptcontroller um auf die Modelschicht zuzugreifen 
     */
    public EntryListController(ETController etController) {
    	if (etController == null) {
    		throw new IllegalArgumentException("EntryListController muss mit seinem ETContoller intialisiert werden");
    	}
    		this.etController = etController;
    		foodEntryNumMap = new HashMap<LocalDate, Integer>();
    		drugEntryNumMap = new HashMap<LocalDate, Integer>();
    		problemEntryNumMap = new HashMap<LocalDate, Integer>();
    		lastUsedFilter = new EntryFilter(true, true, true, true, true, new ArrayList<String>(), 
    				new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
    		filterController = new FilterController();
    		entryListAUI = new ArrayList<EntryListAUI>();
    }

    
   
    
    /**
     * Gibt alle Einträge von einem Tag aus
     * @param date
     * 				das Datum nach dem Gefiltert wird
     * @return
     * 				alle Einträge von dem Tag
     */
    public ArrayList<DiaryEntry> getEntrysOfDay(ArrayList<DiaryEntry> entries, LocalDate date){
    	ArrayList<DiaryEntry> diaryEntryOfDay = new ArrayList<DiaryEntry>();
    	for(DiaryEntry diaryEntry : entries){
    		if(diaryEntry.getTime().toLocalDate().equals(date)){
    			diaryEntryOfDay.add(diaryEntry);
    		}
    	}
    	return diaryEntryOfDay;
    }
    

    /**
 	 * Verändert eine Nahrungsmitteleinnahme.
 	 * @param time neuer Zeitpunkt der Einnahme
 	 * @param amount neue Menge der Einnahme
 	 * @param unit neue Einheit der Einnahme
 	 * @param comment neuer Kommentar zur Einnahme
 	 * @param meal neue Mahlzeit
 	 * @param entry zu bearbeitender Eintrag
 	 */
    public FoodEntry modifyFoodEntry(LocalDateTime time, double amount, Unit unit, String comment, Food meal, FoodEntry entry) {
    	addFoodMap(entry.getTime(), -1);
        addFoodMap(time, 1);
    	entry.setFood(meal);
    	entry.setTime(time);
        entry.setAmount(amount);
        entry.setUnit(unit);
        entry.setComment(comment);
        
        refresh();
        return entry;
    }

    /**
 	 * Verändert eine Medikamenteneinnahme.
 	 * @param time neuer Zeitpunkt der Einnahme
 	 * @param amount neue Menge der Einnahme
 	 * @param unit neue Einheit der Einnahme
 	 * @param comment neuer Kommentar zur Einnahme
 	 * @param drug neues Medikament 
 	 * @param entry zu bearbeitender Eintrag
 	 */
    public DrugEntry modifyDrugEntry(LocalDateTime time, double amount, Unit unit, String comment, Drug drug, DrugEntry entry) {
    	addDrugMap(entry.getTime(), -1);
        addDrugMap(time, 1);
    	entry.setDrug(drug);
    	entry.setTime(time);
        entry.setAmount(amount);
        entry.setUnit(unit);
        entry.setComment(comment);
        addDrugMap(entry.getTime(), -1);
        addDrugMap(time, 1);
        refresh();
        return entry;
    }

    /**
 	 *
 	 * Verändert einen Beschwerdeeintrag.
 	 * @param time neuer Zeitpunkt der Beschwerde
 	 * @param duration neue Dauer der Beschwerde
 	 * @param comment neuer Kommentar der Beschwerde
 	 * @param problem neuer Name der Beschwerde
 	 * @param description neue Beschreibung der Beschwerde
 	 * @param entry zu bearbeitender Beschwerdeeintrag
 	 */
    public HealthProblemEntry modifyHealthProblemEntry(LocalDateTime time, double duration, String comment, String problem, String description, HealthProblemEntry entry) {
    	addProblemMap(entry.getTime(), -1);
        addProblemMap(time, 1);
    	HealthProblem newHealthProblem = new HealthProblem(problem, description);
    	entry.setHealthProblem(newHealthProblem);
    	entry.setTime(time);
        entry.setAmount(duration);
        entry.setComment(comment);
        
        refresh();
        return entry;
    }
 
    /**
 	 * Verändert einen 'sonstigen Faktor' - Eintrag aus dem Tagebuch
 	 * @param name Name des Faktors 
 	 * @param time neuer Zeitpunkt des sonstigen Faktors
 	 * @param description neue Beschreibung des sonstigen Faktors
 	 * @param comment Kommentar zum sonstigen Faktor
 	 * @param entry zu verändernder Eintrag
 	 */
    public FactorEntry modifyFactorEntry(LocalDateTime time, String name, String description, String comment, FactorEntry entry) {
    	entry.setName(name);
        entry.setDescription(description);
        entry.setTime(time);
        entry.setComment(comment);
        refresh();
        return entry;
    }

    /**
 	 * Löscht einen Eintrag aus dem Tagebuch
 	 * @param entry Eintrag welcher gelöscht werden soll 
     * @throws ObjectNotExistentException sollte nicht passieren: Ein Element wird ausgewählt und soll gelöscht werden, aber exisitert nicht.
 	 */
    public void deleteEntry(DiaryEntry entry) throws ObjectNotExistentException {
        try {
        	etController.getCurrentDiary().removeEntry(entry);
        	if (entry instanceof FoodEntry) {
        		addFoodMap(entry.getTime(), -1);
        	} else if (entry instanceof DrugEntry) {
        		addDrugMap(entry.getTime(), -1);
        	} else if (entry instanceof HealthProblemEntry) {
        		addProblemMap(entry.getTime(), -1);
        	}
    	} catch (ObjectNotExistentException e) {
        	e.printStackTrace();
        }
        refresh();
    }
    /**
 	 * Gibt zurück ob an dem Tag FoodEntries existieren.
 	 * @param localdate der Tag an dem gesucht wird
 	 * @return der Wahrheitswert, ob es Einträge gibt
 	 */
    public boolean existsFoodEntry(LocalDate localdate) {
    	
    	if (foodEntryNumMap.containsKey(localdate)) {
    		if(foodEntryNumMap.get(localdate) > 0) {
    			return true;
    		}
    	}
    	return false;
    }
    /**
 	 * Gibt zurück ob an dem Tag DrugEntries existieren.
 	 * @param localdate der Tag an dem gesucht wird
 	 * @return der Wahrheitswert, ob es Einträge gibt
 	 */
    public boolean existsDrugEntry(LocalDate localdate) {
    	if (drugEntryNumMap.containsKey(localdate)) {
    		if(drugEntryNumMap.get(localdate) > 0) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
 	 * Gibt zurück ob an dem Tag HealthProblemEntries existieren.
 	 * @param localdate der Tag an dem gesucht wird
 	 * @return der Wahrheitswert, ob es Einträge gibt
 	 */
    public boolean existsHealthProblemEntry(LocalDate localdate) {
    	if (problemEntryNumMap.containsKey(localdate)) {
    		if(problemEntryNumMap.get(localdate) > 0) {
    			return true;
    		}
    	}
    	return false;
    }

    /**
 	 * Filtert die aktuelle Liste der Einträge und gibt diese zurück. 
 	 * @param filter der Filter der angewandt wird
 	 * @return  gefilterte Liste
 	 */
    public ArrayList<DiaryEntry> filterEntryList(EntryFilter filter, boolean useAltDiary) {
        lastUsedFilter = filter;
        Diary diary = null;
        if (useAltDiary)
        	diary = etController.getAltDiary();
        else 
        	diary = etController.getCurrentDiary();
        
        ArrayList<DiaryEntry> unfilteredList = diary.getDiaryEntryList();
        ArrayList<DiaryEntry> filteredList = new ArrayList<DiaryEntry>();
    	for (DiaryEntry candidate : unfilteredList) {
    		if (filterController.filterEntry(candidate, filter)) {
    			filteredList.add(candidate);
    		}
    	}
    	return filteredList;
    }

    
    /**
 	 * verändert den Eintrag in der Hashmap um i. Wird genutzt um zu inkrementieren oder dekrementieren.
 	 * Falls vorher kein hash gesetzt war wird er auf 1 gesetzt, da dies der erste Eingtag an diesem Datum ist.
 	 * @param time Zeit zu der ein neuer Eintrag hinzugefügt wurde
 	 * @param searchString Suchbegriffe nach denen gesucht wird
 	 */


    
    public void createEntryMaps(){
    	foodEntryNumMap.clear();
    	drugEntryNumMap.clear();
    	problemEntryNumMap.clear();
    	for (DiaryEntry e : etController.getCurrentDiary().getDiaryEntryList()){
    		if (e instanceof DrugEntry){
    			addDrugMap(e.getTime(), 1);
    		}
    		else if (e instanceof FoodEntry){
    			addFoodMap(e.getTime(), 1);
    		}
    		else if (e instanceof HealthProblemEntry){
    			addProblemMap(e.getTime(), 1);
    		}
    	}
    }
    
    
 

	/**
 	 * gibt den ETController zurück
 	 * @return der ETController
 	 */
	public ETController getEtController() {
		return etController;
	}


	/**
 	 * gibt die GUIs welche bei Änderungen aktualisiert werden müssen zurück.
 	 * @return die GUIs
 	 */
	public Collection<EntryListAUI> getEntryListAUI() {
		return entryListAUI;
	}

	/**
 	 * setzt die GUIs welche bei Änderungen aktualisiert werden müssen
 	 * @param entryListAUI die GUIs
 	 */
	public void setEntryListAUI(Collection<EntryListAUI> entryListAUI) {
		this.entryListAUI = entryListAUI;
	}

	/**
 	 * gibt den zuletzt benutzten Filter zurück
 	 * @return der zuletzt genutzte Filter
 	 */
	public EntryFilter getLastUsedFilter() {
		return lastUsedFilter;
	}
	
	/**
	 * Fügt eine UI hinzu, welche refreshed wird
	 * @param elAUI UI, die refreshed werden muss
	 */
	public void addAUI (EntryListAUI elAUI) {
		entryListAUI.add(elAUI);
	}

	/**
	 * Entfernt UI, welche nicht mehr refreshed werden muss
	 * @param elAUI UI, die nicht mehr refreshed werden muss
	 */
	public void removeAUI (EntryListAUI elAUI) {
		if (elAUI != null) {
		entryListAUI.remove(elAUI);
		}
	}
}
