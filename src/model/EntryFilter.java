package model;

import java.util.List;

/**
 * Klasse zum Speichern von Filteroptionen, bestehend aus den zu durchsuchenden Kategorien und den dazugehoerigen Suchbegriffen.
 * 
 * @author Simon Kurz
 */
public class EntryFilter {

	/**
	 * gibt an, ob nach Lebensmitteln gefiltert werden soll
	 */
	private boolean filterFood;

	/**
	 * gibt an, ob nach Medikamenten gefiltert werden soll
	 */
	private boolean filterDrugs;

	/**
	 * gibt an, ob nach Inhaltsstoffen gefiltert werden soll
	 */
	private boolean filterIngredients;

	/**
	 * gibt an, ob nach sonstigen Faktoren gefiltert werden soll
	 */
	private boolean filterFactors;

	/**
	 * gibt an, ob nach Beschwerden gefiltert werden soll
	 */
	private boolean filterHealthProblems;

	/**
	 * //////////////////////// SUCHBEGRIFFE ////////////////////////
	 */

	/**
	 * Suchbegriffe fuer Suchkatergorie Lebensmittel.
	 */
	private List<String> searchFoods;

	/**
	 * Suchbegriffe fuer Suchkatergorie Medikamente.
	 */
	private List<String> searchDrugs;

	/**
	 * Suchbegriffe fuer Suchkatergorie Inhaltsstoffe.
	 */
	private List<String> searchIngredients;

	/**
	 * Suchbegriffe fuer Suchkatergorie sonstige Faktoren.
	 * Kommata
	 */
	private List<String> searchFactors;

	/**
	 * Suchbegriffe fuer Suchkatergorie Beschwerden.
	 */
	private List<String> searchHealthProblems;

	/**
	 * 
	 * @param filterByFood Gibt an, ob nach Lebensmitteln gefiltert werden soll.
	 * @param filterByDrugs Gibt an, ob nach Medikamenten gefiltert werden soll.
	 * @param filterByIngredients Gibt an, ob nach Inhaltsstoffen gefiltert werden soll.
	 * @param filterByFactors Gibt an, ob nach weiteren Faktoren gefiltert werden soll.
	 * @param filterByHealthProblems Gibt an, ob nach Beschwerden gefiltert werden soll.
	 * 
	 * @param searchFoods Suchbegriffe fuer Suchkatergorie 'Lebensmittel'.
	 * @param searchDrugs Suchbegriffe fuer Suchkatergorie 'Medikamente'.
	 * @param searchIngredients Suchbegriffe fuer Suchkatergorie 'Inhaltsstoffe'.
	 * @param searchFactors Suchbegriffe fuer Suchkatergorie 'weitere Faktoren'.
	 * @param searchHealthProblems Suchbegriffe fuer Suchkatergorie 'Beschwerden'.
	 */
	public EntryFilter(boolean filterByFood, 
				boolean filterByDrugs, 
				boolean filterByIngredients, 
				boolean filterByFactors, 
				boolean filterByHealthProblems, 
				List<String> searchFoods, 
				List<String> searchDrugs, 
				List<String> searchIngredients, 
				List<String> searchFactors, 
				List<String> searchHealthProblems) {
		this.filterFood = filterByFood;
		this.filterDrugs = filterByDrugs;
		this.filterIngredients = filterByIngredients;
		this.filterFactors = filterByFactors;
		this.filterHealthProblems = filterByHealthProblems;
		
		/*this.searchFoods = searchFoods;
		this.searchDrugs = searchDrugs;
		this.searchIngredients = searchIngredients;
		this.searchFactors = searchFactors;
		this.searchHealthProblems = searchHealthProblems;*/
		
		this.searchFoods = searchFoods;
		this.searchDrugs = searchDrugs;
		this.searchIngredients = searchIngredients;
		this.searchFactors = searchFactors;
		this.searchHealthProblems = searchHealthProblems;
		
		for(String s : this.searchFoods) {
			s.trim();
		}
		for(String s : this.searchDrugs) {
			s.trim();
		}
		for(String s : this.searchIngredients) {
			s.trim();
		}
		for(String s : this.searchFactors) {
			s.trim();
		}
		for(String s : this.searchHealthProblems) {
			s.trim();
		}
	}

	/**
	 * Gibt an, ob nach Lebensmitteln gefiltert werden soll.
	 * 
	 * @return Soll nach Lebensmitteln gefiltert werden?
	 */
	public boolean isFilterFood() {
		return filterFood;
	}

	/**
	 * Gibt an, ob nach Medikamenten gefiltert werden soll.
	 * 
	 * @return Soll nach Medikamenten gefiltert werden?
	 */
	public boolean isFilterDrugs() {
		return filterDrugs;
	}

	/**
	 * Gibt an, ob nach Inhaltsstoffen gefiltert werden soll.
	 * 
	 * @return Soll nach Inhaltsstoffen gefiltert werden?
	 */
	public boolean isFilterIngredients() {
		return filterIngredients;
	}

	/**
	 * Gibt an, ob nach sonstigen Faktoren gefiltert werden soll.
	 * 
	 * @return Soll nach sonstigen Faktoren gefiltert werden?
	 */
	public boolean isFilterFactors() {
		return filterFactors;
	}

	/**
	 * Gibt an, ob nach Beschwerden gefiltert werden soll.
	 * 
	 * @return Soll nach Beschwerden gefiltert werden?
	 */
	public boolean isFilterHealthProblems() {
		return filterHealthProblems;
	}

	/**
	 * Gibt Suchbegriffe fuer Suchkatergorie 'Lebensmittel' zurueck.
	 * 
	 * @return Suchbegriffe fuer Suchkatergorie 'Lebensmittel'.
	 */
	public List<String> getSearchFoods() {
		return searchFoods;
	}

	/**
	 * Gibt Suchbegriffe fuer Suchkatergorie 'Medikamente' zurueck.
	 * 
	 * @return Suchbegriffe fuer Suchkatergorie 'Medikamente'.
	 */
	public List<String> getSearchDrugs() {
		return searchDrugs;
	}

	/**
	 * Gibt Suchbegriffe fuer Suchkatergorie 'Inhalststoffe' zurueck.
	 * 
	 * @return Suchbegriffe fuer Suchkatergorie 'Inhalststoffe'.
	 */
	public List<String> getSearchIngredients() {
		return searchIngredients;
	}

	/**
	 * Gibt Suchbegriffe fuer Suchkatergorie 'sonstige Faktoren' zurueck.
	 * 
	 * @return Suchbegriffe fuer Suchkatergorie 'sonstige Faktoren'.
	 */
	public List<String> getSearchFactors() {
		return searchFactors;
	}

	/**
	 * Gibt Suchbegriffe fuer Suchkatergorie 'Beschwerden' zurueck.
	 * 
	 * @return Suchbegriffe fuer Suchkatergorie 'Beschwerden'.
	 */
	public List<String> getSearchHealthProblems() {
		return searchHealthProblems;
	}

	
}
