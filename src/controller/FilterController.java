package controller;

import model.DiaryEntry;
import model.DrugEntry;
import model.EntryFilter;
import model.FactorEntry;
import model.FoodEntry;
import model.HealthProblemEntry;

public class FilterController extends SuperFilterController{

	
	/**
 	 * Entscheidet was für ein Eintrag EntryToCheck ist und benutzt die passende Filtermethode.
 	 * @param entryToCheck Entry welche überprüft wird
 	 * @return boolean gibt zurück ob der Entry durch den Filter geht
 	 */
    public boolean filterEntry(DiaryEntry entryToCheck, EntryFilter filter) {
    	this.filter = filter;
    	
    	if (entryToCheck instanceof FoodEntry && (filter.isFilterFood() || filter.isFilterIngredients() || filter.isFilterHealthProblems())) {
    		if (filter.getSearchFoods().isEmpty()) {
    			return true;
    		}
    		
    		return filterFood((FoodEntry) entryToCheck);
    	}
    	if (entryToCheck instanceof DrugEntry && (filter.isFilterDrugs() || filter.isFilterHealthProblems())) {
    		if (filter.getSearchDrugs().isEmpty()) {
    			return true;
    		}
    		
    		return filterDrug((DrugEntry) entryToCheck);
    	}
    	if (entryToCheck instanceof HealthProblemEntry && filter.isFilterHealthProblems()) {
    		if (filter.getSearchHealthProblems().isEmpty()) {
    			return true;
    		}
    		
    		return filterHealthProblem((HealthProblemEntry) entryToCheck);
    	}
    	if (entryToCheck instanceof FactorEntry && filter.isFilterFactors()) {
    		if (filter.getSearchFactors().isEmpty()) {
    			return true;
    		}
    		
    		return filterFactor((FactorEntry) entryToCheck);
    	}
		
    	return false;
	}
    

   
	
	
}
