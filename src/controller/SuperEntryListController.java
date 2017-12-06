package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;

import gui.EntryListAUI;
import model.Drug;
import model.DrugEntry;
import model.EntryFilter;
import model.FactorEntry;
import model.Food;
import model.FoodEntry;
import model.HealthProblem;
import model.HealthProblemEntry;
import model.Unit;

/**
 * Class to part the entryListControllers apart
 * @author Friedemann Runte
 *
 */

public class SuperEntryListController {
	/**
 	 * Die Hashmap ordnet jedem Datum die Anzahl der Einträge an diesem Datum zu.
 	 */
    protected HashMap<LocalDate, Integer> foodEntryNumMap;
    protected HashMap<LocalDate, Integer> drugEntryNumMap;
    protected HashMap<LocalDate, Integer> problemEntryNumMap;
    
    /**
 	 * der ETController, mit dem auf die Modelschicht zugegriffen wird.
 	 */
    protected ETController etController;
    
    /**
 	 * die GUIs, welche aktualisiert werden müssen.
 	 */
    protected Collection<EntryListAUI> entryListAUI;
    
    /**
 	 * der zuletzt angewandte Filter. Wird beispielsweise genutzt um die letzte Suche einzutragen.
 	 */
    protected EntryFilter lastUsedFilter;
    
    /**
     * der Filter welcher verwendet wird um Tagebucheinträge zu filtern.
     */
    protected FilterController filterController;
    
    /**
 	 * Fuegt einen Nahrungsmitteleinnahme zum Tagebuch hinzu. 
 	 * @param time Zeitpunkt der Einnahme
 	 * @param amount Menge der Einnahme
 	 * @param unit Einheit der Menge
 	 * @param comment zusätzlicher Kommentar
 	 * @param meal das Food-Objekt welches eingefügt werden soll
 	 * @return der neue Entry
 	 */
    
    public FoodEntry createFoodEntry(LocalDateTime time, double amount, Unit unit, String comment, Food meal) {
        FoodEntry newFoodEntry = new FoodEntry (time, amount, unit, comment, meal);
    	etController.getCurrentDiary().addEntry(newFoodEntry);
    	addFoodMap(time, 1);
    	refresh();
    	return newFoodEntry;
    }

    /**
 	 * Fuegt eine Medikamenteneinnahme zum Tagebuch hinzu.
 	 * @param time Zeitpunkt der Einnahme
 	 * @param amount Menge der Einnahme
 	 * @param unit Einheit der Menge
 	 * @param comment zusätzlicher Kommentar
 	 * @param drug das Drug-Objekt welches eingefügt werden soll
 	 * @return Der erzeugte Entry
 	 */
    public DrugEntry createDrugEntry(LocalDateTime time, double amount, Unit unit, String comment, Drug drug) {
    	DrugEntry newDrugEntry = new DrugEntry (time, amount, unit, comment, drug);
    	etController.getCurrentDiary().addEntry(newDrugEntry);
    	addDrugMap(time, 1);
    	refresh();
    	return newDrugEntry;
    }
    
    /**
 	 * zählt die Anzahl der ProblemEntries
 	 * @param time Tag an dem die Anzahl der Einträge verändert wird
 	 * @param intToAdd Zahl um die sich die Anzahl der Einträge verändern wird
 	 */
    protected void addProblemMap(LocalDateTime time, int intToAdd) { 
    	LocalDate locald = time.toLocalDate();
    	if (problemEntryNumMap.containsKey(locald)) {
	    	problemEntryNumMap.put(locald, problemEntryNumMap.get(locald) + intToAdd);
		} else {
			problemEntryNumMap.put(locald, 1);
		}
    }
    /**
   	 * zählt die Anzahl der FoodEntries
   	 * @param time Tag an dem die Anzahl der Einträge verändert wird
   	 * @param intToAdd Zahl um die sich die Anzahl der Einträge verändern wird
   	 */
    protected void addFoodMap(LocalDateTime time, int intToAdd) { 
	    LocalDate locald = time.toLocalDate();
    	if (foodEntryNumMap.containsKey(locald)) {
			foodEntryNumMap.put(locald, foodEntryNumMap.get(locald) + intToAdd);
		} else {
			foodEntryNumMap.put(locald, 1);
		}
    }
    /**
   	 * zählt die Anzahl der DrugEntries
   	 * @param time Tag an dem die Anzahl der Einträge verändert wird
   	 * @param intToAdd Zahl um die sich die Anzahl der Einträge verändern wird
   	 */
    protected void addDrugMap(LocalDateTime time, int intToAdd) { 
    	LocalDate locald = time.toLocalDate();
	    if (drugEntryNumMap.containsKey(locald)) {
	    	drugEntryNumMap.put(locald, drugEntryNumMap.get(locald) + intToAdd);
		} else {
			drugEntryNumMap.put(locald, 1);
		}
    }

    /**
 	 * Fuegt einen Beschwerdeeintrag zum Tagebuch hinzu.
 	 * @param time Zeitpunkt der Beschwerde
 	 * @param duration Dauer der Beschwerde
 	 * @param comment zusätzlicher Kommentar
 	 * @param problem Name der Beschwerde
 	 * @param description Beschreibung der Beschwerde 
 	 * @return der neue Entry
 	 */
    public HealthProblemEntry createHealthProblemEntry(LocalDateTime time, double duration, String comment, String problem, String description) {
    	HealthProblem newHealthproblem = new HealthProblem(problem, description);
    	HealthProblemEntry newHealthProblemEntry = new HealthProblemEntry (time, duration, comment, newHealthproblem);
    	etController.getCurrentDiary().addEntry(newHealthProblemEntry);
    	addProblemMap(time, 1);
    	refresh();
    	return newHealthProblemEntry;
    }

    /**
 	 * Fuegt einen sonstige Faktor zum Tagebuch hinzu.
 	 * @param time Zeitpunkt des sonstigen Faktors
 	 * @param name Der Name des Faktoreintrags
 	 * @param description Beschreibung des sonstigen Faktors
 	 * @param comment zusätzlicher Kommentar
 	 * @return der neue Entry
 	 */
    public FactorEntry createFactorEntry(LocalDateTime time, String name, String comment, String description) {
    	FactorEntry newFactorEntry = new FactorEntry (time, name, comment, description);
    	etController.getCurrentDiary().addEntry(newFactorEntry);
    	refresh();
    	return newFactorEntry;
    }
    /**
     * refreshed alle GUI-Elemente aus dem Liste entryListAUI
     */
    public void refresh () {
		for (EntryListAUI elaui : entryListAUI) {
			elaui.refreshDiary();
		}
    }
}
