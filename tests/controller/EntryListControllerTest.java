package controller;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import exceptions.ObjectNotExistentException;
import model.*;

/**
 * Kontrolliert alle Testfälle der Klasse EntryListController
 * 
 * @author Miran Shefketi
 * 
 */
public class EntryListControllerTest {

	private final String LOREM_IPSUM = "Lorem Ipsum und so";

	private Diary testingDiary;
	private ETController testingEtC;
	private EntryListController testingELC;

	private LocalDateTime testingDate = LocalDateTime.now();
	private Food testFood;
	private Drug testDrug;
	private HealthProblem testProblem;
	private String testFactorDesc;

	private FoodEntry testFoodEntry;
	private DrugEntry testDrugEntry;
	private HealthProblemEntry testProblemEntry;
	private FactorEntry testFactorEntry;

	/**
	 * Bereitet eine Umgebung für alle Testfälle vor und stellt gültige Objekte
	 * der Modelschicht bereit
	 */
	@Before
	public void setUp() {
		testingDiary = new Diary();
		testingEtC = new ETController();
		testingEtC.setCurrentDiary(testingDiary);
		testingELC = new EntryListController(testingEtC);

		testFood = new Food("Test Food", null);
		testDrug = new Drug("JUnit!");
		testProblem = new HealthProblem("Depression", "Woche 2 des Sopras");
		testFactorDesc = "Test Factor Description";

		testFoodEntry = new FoodEntry(testingDate, Double.POSITIVE_INFINITY, Unit.VOID, LOREM_IPSUM, testFood);
		testDrugEntry = new DrugEntry(testingDate, Double.POSITIVE_INFINITY, Unit.VOID, LOREM_IPSUM, testDrug);
		testProblemEntry = new HealthProblemEntry(testingDate, Double.POSITIVE_INFINITY, LOREM_IPSUM, testProblem);
		testFactorEntry = new FactorEntry(testingDate, LOREM_IPSUM, LOREM_IPSUM, testFactorDesc);
	}

	/**
	 * Testet den Konstruktor auf NullPointerExceptions
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructor() {
		new EntryListController(null);
	}

	/**
	 * Testet, ob ein Lebensmitteleintrag erstellt werden kann indem die Methode
	 * aufgerufen wird, und anschließend kontrolliert wird, ob der Eintrag im
	 * Tagebuch existiert und die Inhalte des Eintrags mit den Eingaben
	 * übereinstimmen
	 */
	@Test
	public void testCreateFoodEntry() {
		FoodEntry drugEntry = testingELC.createFoodEntry(testingDate, 1.0, Unit.LITRE, LOREM_IPSUM, testFood);
		assertTrue(testingDiary.getDiaryEntryList().contains(drugEntry));
	}

	/**
	 * Testet, ob ein Medizineintrag erstellt werden kann indem die Methode
	 * aufgerufen wird, und anschließend kontrolliert wird, ob der Eintrag im
	 * Tagebuch existiert und die Inhalte des Eintrags mit den Eingaben
	 * übereinstimmen
	 */
	@Test
	public void testCreateDrugEntry() {
		DrugEntry drugEntry = testingELC.createDrugEntry(testingDate, Double.POSITIVE_INFINITY, Unit.MINUTES, LOREM_IPSUM,
				testDrug);
		assertTrue(testingDiary.getDiaryEntryList().contains(drugEntry));
	}

	/**
	 * Testet, ob ein Beschwerdeneintrag erstellt werden kann indem die Methode
	 * aufgerufen wird, und anschließend kontrolliert wird, ob der Eintrag im
	 * Tagebuch existiert und die Inhalte des Eintrags mit den Eingaben
	 * übereinstimmen
	 */
	@Test
	public void testCreateHealthProblemEntry() {
		HealthProblemEntry drugEntry = testingELC.createHealthProblemEntry(testingDate, 0, LOREM_IPSUM, testProblem.getName(),
				testProblem.getDescription());
		assertTrue(testingDiary.getDiaryEntryList().contains(drugEntry));
	}

	/**
	 * Testet, ob ein Eintrag zu sonstigen Faktoren erstellt werden kann indem
	 * die Methode aufgerufen wird, und anschließend kontrolliert wird, ob der
	 * Eintrag Objekt im Tagebuch existiert und die Inhalte des Eintrags mit den
	 * Eingaben übereinstimmen
	 */
	@Test
	public void testCreateFactorEntry() {
		FactorEntry factorEntry = testingELC.createFactorEntry(testingDate, LOREM_IPSUM, LOREM_IPSUM, testFactorDesc);
		assertTrue(testingDiary.getDiaryEntryList().contains(factorEntry));
	}

	/**
	 * Testet, ob ein Lebensmitteleintrag bearbeitet werden kann indem die
	 * Methode aufgerufen wird, und anschließend kontrolliert wird, ob der
	 * Eintrag noch im Tagebuch existiert und die Inhalte des Eintrags mit den
	 * Veränderungen übereinstimmen
	 */
	@Test
	public void testModifyFoodEntry() {
		FoodEntry foodEntry= testingELC.createFoodEntry(testFoodEntry.getTime(), testFoodEntry.getAmount(), testFoodEntry.getUnit(),
				testFoodEntry.getComment(), testFoodEntry.getFood());
		Food newFood = new Food("New Name", null);
		FoodEntry newFoodEntry = testingELC.modifyFoodEntry(LocalDateTime.MAX, 0, Unit.PIECES, LOREM_IPSUM.toUpperCase(), newFood, foodEntry);
		assertTrue(testingDiary.getDiaryEntryList().contains(newFoodEntry));
	}

	/**
	 * Testet, ob ein Medizineintrag bearbeitet werden kann indem die Methode
	 * aufgerufen wird, und anschließend kontrolliert wird, ob der Eintrag noch
	 * im Tagebuch existiert und die Inhalte des Eintrags mit den Veränderungen
	 * übereinstimmen
	 */
	@Test
	public void testModifyDrugEntry() {
		DrugEntry drugEntry =testingELC.createDrugEntry(testDrugEntry.getTime(), testDrugEntry.getAmount(), testDrugEntry.getUnit(),
				testDrugEntry.getComment(), testDrugEntry.getDrug());
		Drug newDrug = new Drug("New Name");
		DrugEntry newDrugEntry =testingELC.modifyDrugEntry(LocalDateTime.MAX, 0,  Unit.PIECES, LOREM_IPSUM.toUpperCase(), newDrug, drugEntry);
		assertTrue(testingDiary.getDiaryEntryList().contains(newDrugEntry));
	}

	/**
	 * Testet, ob ein Beschwerdeneintrag bearbeitet werden kann indem die
	 * Methode aufgerufen wird, und anschließend kontrolliert wird, ob der
	 * Eintrag noch im Tagebuch existiert und die Inhalte des Eintrags mit den
	 * Veränderungen übereinstimmen
	 */
	@Test
	public void testModifyHealthProblemEntry() {
		HealthProblemEntry problemEntry = testingELC.createHealthProblemEntry(testProblemEntry.getTime(), testProblemEntry.getAmount(),
				testProblemEntry.getComment(), testProblemEntry.getHealthProblem().getName(),
				testProblemEntry.getHealthProblem().getDescription());

		HealthProblemEntry newProblemEntry = testingELC.modifyHealthProblemEntry(LocalDateTime.MAX, 0.0, LOREM_IPSUM.toUpperCase(),
				"NEW PROBLEM","NEW DESCRIPTION", problemEntry);
		assertTrue(testingDiary.getDiaryEntryList().contains(newProblemEntry));
	}

	/**
	 * Testet, ob ein Eintrag zu sonstigen Faktoren bearbeitet werden kann indem
	 * die Methode aufgerufen wird, und anschließend kontrolliert wird, ob der
	 * Eintrag noch im Tagebuch existiert und die Inhalte des Eintrags mit den
	 * Veränderungen übereinstimmen
	 */
	@Test
	public void testModifyFactorEntry() {
		testingELC.createFactorEntry(testFactorEntry.getTime(), testFactorEntry.getName(), testFactorEntry.getComment(), testFactorEntry.getDescription());

		String newFactorDesc = "New Description";
		FactorEntry newFactorEntry = new FactorEntry(LocalDateTime.MAX, LOREM_IPSUM.toUpperCase(),
				LOREM_IPSUM.toUpperCase(), newFactorDesc);

		assertTrue(testingDiary.getDiaryEntryList().iterator().hasNext());
		DiaryEntry drugEntry = testingDiary.getDiaryEntryList().iterator().next();

		testingELC.modifyFactorEntry(LocalDateTime.MAX, LOREM_IPSUM.toUpperCase(), newFactorDesc, "neueBeschreibung",
				((FactorEntry) drugEntry));

		assertTrue(((FactorEntry) drugEntry).equals(newFactorEntry));
	}

	/**
	 * Testet, ob ein Eintrag aus dem Tagebuch gelöscht werden kann indem die
	 * Methode aufgerufen wird, und anschließend kontrolliert wird, ob der
	 * Eintrag noch im Tagebuch existiert
	 */
	@Test
	public void testDeleteEntry() {
		testingELC.createFoodEntry(testingDate, 0, Unit.GRAMM, LOREM_IPSUM, testFood);
		testingELC.createDrugEntry(testingDate, 0, Unit.GRAMM, LOREM_IPSUM, testDrug);
		testingELC.createHealthProblemEntry(testingDate, 0, LOREM_IPSUM, testProblem.getName(),
				testProblem.getDescription());
		testingELC.createFactorEntry(testingDate, LOREM_IPSUM, LOREM_IPSUM, testFactorDesc);
		assertTrue(testingDiary.getDiaryEntryList().size() == 4);
		for (int i = 0; i < 4; i++) {
			try {
				testingELC.deleteEntry(testingDiary.getDiaryEntryList().get(0));
			} catch (ObjectNotExistentException onee) {
				// ¯\_(ツ)_/¯
			}
		}
		assertTrue(testingDiary.getDiaryEntryList().size() == 0);
	}

	/**
	 * Testet, ob Einträge aus dem Tagebuch nach Stichwörtern gefiltert werden
	 * können, indem ein(ige) Beispieleintrag(e) angelt wird/werden und Nach
	 * diesem/n mithilfe von Stichwörtern gesucht wird Anschließend wird
	 * kontrolliert ob die Zurückgegebene Liste alle erwarteten Referenzen an
	 * die gesuchten Objekte enthält.
	 */
	@Test
	public void testFilterEntryList() {
		Food ingrFood = new Food("test", null);
		Ingredient ingr = new Ingredient("foo", "bar");
		ingrFood.getIngredients().add(ingr);
		testFood.getIngredientsOfFoodList().add(ingrFood);
		testingELC.createFoodEntry(testingDate, 0, Unit.GRAMM, LOREM_IPSUM, testFood);
		ArrayList<String> filter = new ArrayList<String>(1);
		ArrayList<String> filter2 = new ArrayList<String>(1);
		filter.add("bar");
		filter2.add("");
		EntryFilter testingFilter = new EntryFilter(true, false, true, false, false, filter2, new ArrayList<String>(),
				filter, new ArrayList<String>(), new ArrayList<String>());
		ArrayList<DiaryEntry> entries = (ArrayList<DiaryEntry>) testingELC.filterEntryList(testingFilter, false);
		assertTrue(entries.size() == 1);
		assertTrue(((FoodEntry) entries.get(0)).getFood().getName().equals("Test Food"));
	}

}
