package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import exceptions.InvalidDiaryException;
import model.Diary;
import model.DiaryEntry;
import model.Drug;
import model.DrugEntry;
import model.Food;
import model.Ingredient;
import model.Unit;

/**
 * Testklasse für den IOController
 * @author Olga Scheftelowitsch
 *
 */
public class IOControllerTest {
	/**
	 * Tagebuch Bispiel zum initialisieren
	 */
	
	private Diary diary;
	/**
	 * zum speichern der Zeit beim BeispielEintrag
	 */
	
	private LocalDateTime datenow;
	
	/**
	 * kreiert ein Tagebuch mit Einträgen
	 * @throws Exception
	 */
	@Before
    public void setUp() throws Exception {
		diary = new Diary();
		diary.createDrug(new Drug("Ibuprofen"));
		diary.createFood(new Food("Melone", null));
		datenow = LocalDateTime.now();
		diary.addEntry(new DrugEntry(datenow, 200, Unit.GRAMM, "", new Drug("Ibuprofen")));
    }
	

	/**
	 * Hier wird das speichern und Laden des Tagebuchs getestet
	 * @throws IOException 
	 * 					wenn die Datei nicht existiert
	 * @throws InvalidDiaryException 
	 * 					wenn das Tagebuch nicht valide ist
	 */
	@Test
	public void testSaveLoadDiary() throws IOException, InvalidDiaryException {
		File file= new File("save");
		IOController.saveDiary(diary, file);
		Diary diary2 = IOController.loadDiary(file);
		assertTrue(diary2.exists(new Drug("Ibuprofen")));
		assertTrue(diary2.exists(new Food("Melone",null)));
		boolean check = false;
		for(DiaryEntry druge: diary2.getDiaryEntryList()){
			if(((DrugEntry)druge).getAmount()== 200 && druge.getName().equals("Ibuprofen") && druge.getComment().equals("") && druge.getTime().equals(datenow)){
				check = true;
			}
		}
		assertTrue(check);
	}

	/**
	 * Hier wird das speichern und laden der DrugListe getestet indem der Liste Medikamente hinzugefügt werden, gespeichert und dann geladen wird und geprüft ob alles konsistent ist
	 */
	@Test
	public void testSaveDrugList(){
		ArrayList<Drug> drugs = new ArrayList<Drug>();
		drugs.add(new Drug("Ibuprofen"));
		drugs.add(new Drug("Paracetamol"));
		IOController.saveList(drugs, "drug");
		File file= new File("drug");
		List<Drug> drugs2 = IOController.loadDrugList(file);
		boolean check = false;
		for(Drug drug: drugs2){
			if(drug.equals(new Drug("Ibuprofen"))){
				check = true;
			}
		}
		assertTrue(check);
		check = false;
		for(Drug drug: drugs2){
			if(drug.equals(new Drug("Paracetamol"))){
				check = true;
			}
		}
		assertTrue(check);
	}

	/**
	 * Hier wird das speichern und laden der FoodListe getestet indem der Liste Nahrung hinzugefügt werden, gespeichert wird und dann geladen und geprüft ob alles konsistent ist
	 */
	@Test
	public void testSaveFoodList(){
		ArrayList<Food> foods = new ArrayList<Food>();
		foods.add(new Food("Ibuprofen",null));
		foods.add(new Food("Paracetamol",null));
		IOController.saveList(foods, "food");
		File file= new File("food");
		List<Food> foods2 = IOController.loadFoodList(file);
		boolean check = false;
		for(Food food: foods2){
			if(food.equals(new Food("Ibuprofen",null))){
				check = true;
			}
		}
		assertTrue(check);
		check = false;
		for(Food food: foods2){
			if(food.equals(new Food("Paracetamol",null))){
				check = true;
			}
		}
		assertTrue(check);
	}
	
	/**
	 * Hier wird das speichern und laden der IngredientListe getestet indem der Liste Stoffe hinzugefügt werden, gespeichert wird und dann geladen und geprüft ob alles konsistent ist
	 */
	@Test
	public void testSaveIngredientList(){
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Lactose","Durchfall"));
		ingredients.add(new Ingredient("Gluten", "Kopfschmerzen"));
		IOController.saveList(ingredients, "ingerd");
		File file= new File("ingerd");
		List<Ingredient> ingredients2 = IOController.loadIngredientList(file);
		boolean check = false;
		for(Ingredient ingre: ingredients2){
			if(ingre.equals(new Ingredient("Lactose","Durchfall"))){
				check = true;
			}
		}
		assertTrue(check);
		check = false;
		for(Ingredient ingre: ingredients2){
			if(ingre.equals(new Ingredient("Gluten", "Kopfschmerzen"))){
				check = true;
			}
		}
		assertTrue(check);
	}
	
	/**
	 * Hier wird das speichern und laden der configfile getestet
	 */
	@Test
	public void testLoadConfig() {
		IOController.getProperties().setProperty("lastsave", "save");
		IOController.saveProperties();
		System.out.println("!");
		IOController.loadProperties();
		System.out.println("!!");
		assertEquals(IOController.getProperties().getProperty("lastsave"), "save");
	}


}
