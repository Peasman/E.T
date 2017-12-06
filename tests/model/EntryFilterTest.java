package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


/**
 * Diese Klasse testet die Einstellungen für den EntryFilter
 * 
 * @author Christian Walczuch
 *
 */
public class EntryFilterTest {

	private List<String> searchFoods;
	private List<String> searchDrugs;
	private List<String> searchIngredients;
	private List<String> searchFactors;
	private List<String> searchHealthProblems;
	private EntryFilter entryfilter;

	/**
	 *  setzt die nötigen Parameter für den Test
	 */
	@Before
	public void setUp(){
		searchFoods = new ArrayList<String>();
		searchFoods.add("Nudeln");
		searchDrugs = new ArrayList<String>();
		searchDrugs.add("Aspirin");
		searchIngredients = new ArrayList<String>();
		searchIngredients.add("Senf");
		searchFactors = new ArrayList<String>();
		searchFactors.add("PMS");
		searchHealthProblems = new ArrayList<String>();
		searchHealthProblems.add("Kopfschmerzen");
	}
	
	/**
	 *  testet den Filter mit gesetzten Filtereinstellungen
	 */
	@Test
	public void testEntryFilter() {
		entryfilter = new EntryFilter(true, true, true, true, true, searchFoods, searchDrugs, searchIngredients, searchFactors, searchHealthProblems);
		
		boolean actuals = entryfilter.isFilterFood();
		assertEquals(true, actuals);
		actuals = entryfilter.isFilterDrugs();
		assertEquals(true, actuals);
		actuals = entryfilter.isFilterIngredients();
		assertEquals(true, actuals);
		actuals = entryfilter.isFilterFactors();
		assertEquals(true, actuals);
		actuals = entryfilter.isFilterHealthProblems();
		assertEquals(true, actuals);
		String actual = searchFoods.iterator().next().toString();
		assertEquals("Nudeln", actual);
		actual = searchDrugs.iterator().next().toString();
		assertEquals("Aspirin", actual);
		actual = searchIngredients.iterator().next().toString();
		assertEquals("Senf", actual);
		actual = searchFactors.iterator().next().toString();
		assertEquals("PMS", actual);
		actual = searchHealthProblems.iterator().next().toString();
		assertEquals("Kopfschmerzen", actual);
		
		entryfilter = new EntryFilter(false, false, false, false, false, searchFoods, searchDrugs, searchIngredients, searchFactors, searchHealthProblems);
		
		actuals = entryfilter.isFilterFood();
		assertEquals(false, actuals);
		actuals = entryfilter.isFilterDrugs();
		assertEquals(false, actuals);
		actuals = entryfilter.isFilterIngredients();
		assertEquals(false, actuals);
		actuals = entryfilter.isFilterFactors();
		assertEquals(false, actuals);
		actuals = entryfilter.isFilterHealthProblems();
		assertEquals(false, actuals);
		
	}
}
