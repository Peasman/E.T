package controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test zur Klasse ETController
 * @author Moritz Ludolph
 *
 */
public class ETControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Testet das instanziieren eines neuen ETController Objektes
	 */
	@Test
	public void test() {
		ETController controller = new ETController();
		assertNotNull(controller.getDrugListController());
		assertNotNull(controller.getEntryListController());
		assertNotNull(controller.getFoodListController());
		assertNotNull(controller.getIngredientListController());
	}

}
