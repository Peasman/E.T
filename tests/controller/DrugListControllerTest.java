package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistentException;
import model.Diary;
import model.Drug;
import model.HealthProblem;


/**
 * Test für den DrugListController
 * @author Alexander Herlez
 */
 
public class DrugListControllerTest {
		
/**
 * Beispieltagebuch
 */
private Diary diary;
/**
 * Hauptcontroller
 */
private ETController etc;
/**
 * DrugListController der getestet wird
 */
private DrugListController dlc;
/**
 * Liste von HealthProblems
 */
ArrayList<HealthProblem> healthProblems;
/**
 * Beispielmedikament 2
 */
private Drug drugToTest2;
/**
 * Beispielmedikament 3
 */

private Drug drugToTest3;

	/** 
	 * Konstruiert Testumgebung
	 */
	@Before
	public void setUp() throws Exception {
        diary = new Diary();
		etc = new ETController();
		etc.setCurrentDiary(diary);
        dlc = new DrugListController(etc);
        drugToTest3 = new Drug("Augentropfen");
        healthProblems = new ArrayList<HealthProblem>();
        
        HealthProblem hpr1, hpr2, hpr3;
        hpr1 = new HealthProblem("Kopf", "aua");
        hpr2 = new HealthProblem("Fuß", "kaputt");
        hpr3 = new HealthProblem("Ellenbogen", "dick");
        healthProblems.add(hpr1);
        healthProblems.add(hpr2);
        healthProblems.add(hpr3);
        
        
	}

	/** 
	 * Testet ob der Konstruktor funktioniert
	 */
	@Test (expected = Exception.class)
	public void testDrugListController() {
		DrugListController dlc = new DrugListController(etc);
		DrugListController dlcNull = new DrugListController(null);
		try {
			dlcNull.deleteDrug(drugToTest3);
			dlc.deleteDrug(drugToTest3);
		} catch (ObjectNotExistentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/** 
	 * Testet ob der es möglich ist ein Medikament zur Liste hinzuzufügen.
	 * Randfälle:
	 * 	- Medikament existiert bereits
	 * @throws ObjectAlreadyExistsException Objekt exisiert bereits
	 */
	@Test //(expected = ObjectAlreadyExistsException.class)
	public void testCreateDrug() throws ObjectAlreadyExistsException {
		dlc.createDrug("Hustensaft", "gegen Husten", healthProblems);
		//soll Fehler werfen
		try {dlc.createDrug("Hustensaft", "gegen starken Husten", new ArrayList<HealthProblem>());
		}
		catch (ObjectAlreadyExistsException e) {
			
		}
		
		for(Drug drugToTest : diary.getDrugList()) {
			if ("Hustensaft".equals(drugToTest.getName())) {
				assertTrue("das erste Element sollte nicht überschrieben werden", "gegen Husten".equals(drugToTest.getDescription()));
			}
		}
	}

	/** 
	 * Testet ob es möglich ist ein Medikament zu verändern.
	 * Randfälle:
	 * - Medikament wird auf neuen Namen verändert, welches ein anderes Medikament bereits hat.
	 * @throws ObjectAlreadyExistsException Objekt exisiert bereits
	 * @throws ObjectNotExistentException Medikament exisitert nicht, aber soll verändert werden
	 */
	@Test
	public void testModifyDrug() throws ObjectAlreadyExistsException, ObjectNotExistentException {
		dlc.createDrug("Hustensaft", "gegen Husten", healthProblems);
		Iterator<Drug> iter = diary.getDrugList().iterator();
		Drug drugToTest = (Drug) iter.next();
		
		dlc.modifyDrug(drugToTest, "Hustensaft.", "gegen Husten.", healthProblems);
		for(Drug newDrugToTest : diary.getDrugList()) {
			assertTrue(newDrugToTest.getName().equals("Hustensaft."));
			assertTrue(newDrugToTest.getDescription().equals("gegen Husten."));
		}
		dlc.createDrug("Hustensaft", "gegen Husten", healthProblems);
		for(Drug newDrugToTest : diary.getDrugList()) {
			drugToTest2 = newDrugToTest;
		}

		
		//sollte exception werfen
		try {	
			dlc.modifyDrug(drugToTest2, "Hustensaft.", "gegen Husten..", healthProblems);
			for(Drug newDrugToTest : diary.getDrugList()) {
				assertTrue(!newDrugToTest.getDescription().equals("gegen Husten.."));
			}
		} 
		catch (ObjectAlreadyExistsException e) {
			
		}
	}

	/** 
	 * Testet ob es möglich ist zu ein Medikament zu löschen.
	 * Randfälle:
	 * - zu löschendes Element ist nicht in der Liste
	 * @throws ObjectAlreadyExistsException Objekt exisiert bereits
	 * @throws ObjectNotExistentException Medikament exisitert nicht, aber soll gelöscht werden
	 */
	@Test
	public void testDeleteDrug() throws ObjectNotExistentException, ObjectAlreadyExistsException {
		try {
			dlc.deleteDrug(drugToTest3);
		} catch (ObjectNotExistentException e) {
			
		}
		
		dlc.createDrug("Augentropfen", "gegen Husten", healthProblems);
		Iterator<Drug> iter = diary.getDrugList().iterator();
		Drug drugToTest3 = (Drug) iter.next();
		
		dlc.deleteDrug(drugToTest3);
		assertEquals (diary.getDrugList().size(), 0);
		
	}

}
