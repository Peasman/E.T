package controller;

import java.io.File;
import java.io.FileNotFoundException;

import exceptions.InvalidDiaryException;
import model.Diary;

/**
 * Die Controller Klasse die alle anderen Controller beinhaltet und zwischen diesen Kommunikation eröffnet.
 * @author Friedemann Runte
 *
 */
public class ETController {

	/**
	 *  
	 */
	private DrugListController drugListController;

	/**
	 * 
	 */
	private FoodListController foodListController;

	/**
	 * 
	 */
	private IngredientListController ingredientListController;

	/**
	 * 
	 */
	private EntryListController entryListController;

	/**
	 * 
	 */
	private Diary currentDiary;

	/**
	 * 
	 */
	private Diary altDiary;

	/**
	 * Konstruktor des ETControllers intiialisiert alle Controller des Programms
	 */
	public ETController() {
		drugListController = new DrugListController(this);
		foodListController = new FoodListController(this);
		ingredientListController = new IngredientListController(this);
		entryListController = new EntryListController(this);
	}

	/**
	 *
	 * Initialisiert das standardDiary (currentDiary) mit allen Daten die er vom
	 * IOController aus lädt
	 * 
	 * @throws InvalidDiaryException
	 *            Ein Diary was nicht lesbar ist wirft diese Exception
	 */
	public void initDiary() {
		//currentDiary = IOController.loadDiary(new File(IOController.getDefaultSave()));
		File file = new File(IOController.getProperties().getProperty("lastSave"));
		if (file.exists()) {
			try {			
				setCurrentDiary(IOController.loadDiary(file));
			}
			catch (InvalidDiaryException e){
				setCurrentDiary(new Diary());
			} catch(FileNotFoundException fnfe) {
				// TODO: Fehlermeldung im GUI
				fnfe.printStackTrace();
			}
		}
		else{ 
			currentDiary = new Diary();
		}
		refresh();
	}
	
	public void refresh(){
		entryListController.refresh();
		foodListController.refresh();
		ingredientListController.refresh();
		drugListController.refresh();
	}
	
	public void newDiary(){
		setCurrentDiary(new Diary());
		refresh();
	}
	

	/**
	 * gibt das aktuelle Tagebuch zurück
	 * 
	 * @return currentDiary
	 */
	public Diary getCurrentDiary() {
		return currentDiary;
	}

	/**
	 * Überschreibt das aktuelle Diary
	 * 
	 * @param currentDiary
	 *            Diary zum überschreiben
	 */
	public void setCurrentDiary(Diary currentDiary) {
		this.currentDiary = currentDiary;
		entryListController.createEntryMaps();
	}

	/**
	 * Gibt das alternative Tagebuch zurück
	 * 
	 * @return altDiary als Diary
	 */
	public Diary getAltDiary() {
		return altDiary;
	}

	/**
	 * Überschreibt das altDiary mit einem neuen Diary
	 * 
	 * @param altDiary
	 *            das Diary zum überschreiben
	 */
	public void setAltDiary(Diary altDiary) {
		this.altDiary = altDiary;
	}

	/**
	 * Gibt den DrugListController
	 * 
	 * @return DrugListController
	 */
	public DrugListController getDrugListController() {
		return drugListController;
	}

	/**
	 * Gibt den FoodListController zurück
	 * 
	 * @return  FoodListController
	 */
	public FoodListController getFoodListController() {
		return foodListController;
	}

	/**
	 * Gibt den IngredientListController zurück
	 * 
	 * @return  IngredientListController
	 */
	public IngredientListController getIngredientListController() {
		return ingredientListController;
	}

	/**
	 * gibt den EntryListController zurück
	 * @return  EntryListController
	 */
	public EntryListController getEntryListController() {
		return entryListController;
	}
}
