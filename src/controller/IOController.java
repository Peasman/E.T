package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import exceptions.InvalidDiaryException;
import gui.printView.PrintViewController;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Diary;
import model.DiaryEntry;
import model.Drug;
import model.Food;
import model.Ingredient;

/** Bietet statische Methoden zum Laden und Speichern des Tagebuchs, 
 *  seiner Listen und der Konfiguration des Programms.
 *  Dateienpfade sind relativ
 *  @author Miran Shefketi
 */
public class IOController {
	
	/** Enum zur bestimmung des Typen der manuel zu importierenden Liste
	 */
	public static enum ListType {
		FOOD,
		DRUG,
		INGREDIENT
	}
	
	/** Relativer Pfad der Konfigurationsdatei des Programms
	 */
	private static final String PROPERTIES_PATH = "./";
	
	/** java.util.Properties Objekt, welches die Eigenschaften des Programms enthält
	 */
	private static Properties etProperties = defaultProperties();
	
	/** Initialisiert die Eigenschaften des Programms auf die gewünschten Standardwerte
	 * @return Ein aus Standardwerte initialisiertes Properties-Objekt
	 */
	private static Properties defaultProperties() {
		Properties defaultProps = new Properties();
		defaultProps.setProperty("lastSave", "./meinTagebuch.diary");
		return defaultProps;
	}
    
    /** Speichert Das gegebene Tagebuch in eine Tagebuchdatei mit .json-Format im angegebenen Pfad
     * @param diary Das zu speichernde Tagebuch
     * @param path Der (relative?) Pfad, in dem das Tagebuch gespeichert werden soll
     * @throws IOException, falls die Datei nicht erstellt werden kann. (Zugriffsrecht oder Festplattenplatz)
     */
    public static void saveDiary(Diary diary, File file) throws IOException {
    	Gson gson = new GsonBuilder()
    		    .registerTypeAdapter(DiaryEntry.class, new PropertyBasedInterfaceMarshal())
    		    .create();
    	String jsonString = gson.toJson(diary, Diary.class);
    	try(FileWriter jsonWriter = new FileWriter(file)) {
    		if(!file.exists()) {
    			file.createNewFile();
    		}
    		jsonWriter.write(jsonString);
    		jsonWriter.close();
    		etProperties.setProperty("lastSave", file.getAbsolutePath());
    		saveProperties();
    	} catch(IOException ioe) {
    		ioe.printStackTrace();
    	}
    }
	
    /** Lädt ein Diary-Objekt aus einer entsprechenden Tagebuchdatei im .json Format
     * @param file Datei, aus welcher ein Tagebuch zu lesen ist.
     * @return Ein gültiges Diary-Objekt, welches aus der angegebenen Datei konstruiert wurde.
     * @throws InvalidDiaryException falls die zu ladende Datei kein gültiges Tagebuch enthält
     * @throws FileNotFoundException falls die gewünschte Datei nicht existiert
     */
    public static Diary loadDiary(File file) throws InvalidDiaryException, FileNotFoundException {
    	if(!file.exists()) {
    		throw new FileNotFoundException(file.toString()+" konnte nicht gefunden werden!");
    	}
    	Gson gson = new GsonBuilder()
    		    .registerTypeAdapter(DiaryEntry.class, new PropertyBasedInterfaceMarshal())
    		    .create();
        StringBuilder jsonStringBuilder = new StringBuilder();
        try(BufferedReader jsonReader = new BufferedReader(new FileReader(file))) {
        	String nextLine = jsonReader.readLine();
        	while(nextLine != null) {
        		jsonStringBuilder.append(nextLine);
        		nextLine = jsonReader.readLine();
        	}
        } catch(IOException ioe) {
        	ioe.printStackTrace();
        }
        Diary loadedDiary;
        try {
        	loadedDiary = gson.fromJson(jsonStringBuilder.toString(), Diary.class);
        } catch(JsonSyntaxException jse) {
        	throw new InvalidDiaryException("Tagebuch konnte nicht gelesen werden. \nDie Datei könnte korrupt sein.");
        }
        
        return loadedDiary;
    }
    
    /** Speicher eine beliegibe Liste in eine Datei mit .json-Format
     * @param list Die zu speichernede Liste
     * @param path Der Pfad, in dem die Liste zu speichern ist
     */
    public static <T> void saveList(List<T> list, String path) {
    	Type listType = new TypeToken<List<T>>() {}.getType();
    	Gson gson = new Gson();
    	String jsonString = gson.toJson(list, listType);
    	File jsonFile = new File(path);
    	try(FileWriter jsonWriter = new FileWriter(jsonFile)) {
    		if(!jsonFile.exists()) {
    			jsonFile.createNewFile();
    		}
    		jsonWriter.write(jsonString);
    		jsonWriter.close();
    	} catch(IOException ioe) {
    		ioe.printStackTrace();
    	}
    }
    
    /** Lädt eien Essensliste aus einer Datei im .json-Format
     * @param file Die Datei, aus der die Liste zu laden ist
     * @return die geladene Liste
     */
    public static List<Food> loadFoodList(File file) {
    	List<Food> result = loadList(ListType.FOOD, file);
    	return result;
    }
    
    /** Lädt eien Medikamentenliste aus einer Datei im .json-Format
     * @param file Die Datei, aus der die Liste zu laden ist
     * @return die geladene Liste
     */
    public static List<Drug> loadDrugList(File file) {
    	List<Drug> result = loadList(ListType.DRUG, file);
    	return result;
    }
    
    /** Lädt eien Inhaltsstoffliste aus einer Datei im .json-Format
     * @param file Die Datei, aus der die Liste zu laden ist
     * @return die geladene Liste
     */
    public static List<Ingredient> loadIngredientList(File file) {
    	List<Ingredient> result = loadList(ListType.INGREDIENT, file);
    	return result;
    }
    
    /** Lädt eien Liste aus einer Datei im .json-Format
     * @param type Typ der erwarteten Liste, zum switchen des listTypes
     * @param file Die Datei, aus der die Liste zu laden ist
     * @return die geladene Liste
     */
    public static <T> List<T> loadList(ListType type, File file) {
    	Type listType;
    	switch(type) {
    		default: 
    			listType = new TypeToken<List<Object>>() {}.getType();
    			break;
    		case FOOD:
    			listType = new TypeToken<List<Food>>() {}.getType();
    			break;
    		case DRUG:
    			listType = new TypeToken<List<Drug>>() {}.getType();
    			break;
    		case INGREDIENT:
    			listType = new TypeToken<List<Ingredient>>() {}.getType();
    			break;
    	}
        Gson gson = new Gson();
        StringBuilder jsonStringBuilder = new StringBuilder();
        try(BufferedReader jsonReader = new BufferedReader(new FileReader(file))) {
        	String nextLine = jsonReader.readLine();
        	while(nextLine != null) {
        		jsonStringBuilder.append(nextLine);
        		nextLine = jsonReader.readLine();
        	}
        } catch(IOException ioe) {
        	ioe.printStackTrace();
        }
        return gson.fromJson(jsonStringBuilder.toString(), listType);
    }
    
    /** druckt die in der Liste enthaltenen Einträge auf ein DIN A4 Blatt
     * @param diaryEntries die Liste der zu druckenden Einträge
     */
    public static void printDiary(ArrayList<DiaryEntry> diaryEntries) {
    	ArrayList<Printer> printers = (ArrayList<Printer>)Printer.getAllPrinters().stream().collect(Collectors.toList());
    	if(printers.isEmpty()) {
    		System.err.println("Kein Drucker gefunden!");
    		return;
    	}
    	PrinterJob printJob = PrinterJob.createPrinterJob(printers.get(0));
    	if (printJob != null) {        	
        	JobSettings printJobSettings = printJob.getJobSettings();
        	PageLayout pageLayout = printJob.getPrinter().createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
        	printJobSettings.setPageLayout(pageLayout);
        	
        	String diaryName = getProperties().getProperty("lastSave");
        	diaryName = diaryName.substring(diaryName.lastIndexOf('/')+1);
    		PrintViewController printView = new PrintViewController(diaryEntries, diaryName);
	    	printView.scaleForPage(pageLayout);
	 
	    	Stage preview = new Stage();
	    	preview.setScene(new Scene(printView));
	    	preview.setAlwaysOnTop(true);
	    	preview.setX(50);
	    	preview.show();
	    	boolean printDialog = printJob.showPrintDialog(null);
    		if(printDialog) {
    			boolean success = printJob.printPage(printView);
    			if (success) {
    				printJob.endJob();
    			} else {
    				//System.err.println("Job nicht erfolgreich. Status: "+printJob.getJobStatus().toString());
    			}
    		}
    		preview.close();
			preview = null;
    	} else {
    		//System.err.println("Druckauftrag konnte nicht erstellt werden!");
    	}
    }
    
    /** 
     * Speichert die momentanen Einstellungen des Programms im PROPERTIES_PATH als "ET-Application.properties"
     */
    public static void saveProperties() {
    	File propertiesFile = new File(PROPERTIES_PATH+"ET-Application.properties");
    	try {
    		if(!propertiesFile.exists()) {
        		propertiesFile.createNewFile();
        	}
    		FileWriter propertiesWriter = new FileWriter(propertiesFile);
    		etProperties.store(propertiesWriter, "Konfigurationsdatei für das Programm 'E.T.-Das clevere Ernährungstagebuch'");
    		propertiesWriter.close();
    	} catch(IOException ioe) {
    		System.err.println("IOException bei saveProperties!");
    		ioe.printStackTrace();
    	}
    }
    
    /** 
     *  Lädt die momentanen Einstellungen des Programms aus "ET-Application.properties" in PROPERTIES_PATH
     */
    public static void loadProperties() {
    	File propertiesFile = new File(PROPERTIES_PATH+"ET-Application.properties");
    	if(propertiesFile.exists()) {
    		try(BufferedReader propertiesReader = new BufferedReader(new FileReader(propertiesFile))) {
    			etProperties.load(propertiesReader);
    		} catch(IOException ioe) {
    			System.err.println("IOException bei loadDiary!");
    		}
        } else {
        	saveProperties();
        }
    }
    
    /** Gibt das Properties-Objekt des Programms zurück
     * @return Das Properties-Objekt des Programms
     */
    public static Properties getProperties() {
		return etProperties;
	}
    
}

class PropertyBasedInterfaceMarshal implements JsonSerializer<Object>, JsonDeserializer<Object> {

	private static final String CLASS_META_KEY = "CLASS_META_KEY";

	@Override
	public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
		JsonObject jsonObj = jsonElement.getAsJsonObject();
		String className = jsonObj.get(CLASS_META_KEY).getAsString();
		try {
			Class<?> clz = Class.forName(className);
			return jsonDeserializationContext.deserialize(jsonElement, clz);
		} catch (ClassNotFoundException e) {
			throw new JsonParseException(e);
		}
	}

	@Override
	public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
		JsonElement jsonEle = jsonSerializationContext.serialize(object, object.getClass());
		jsonEle.getAsJsonObject().addProperty(CLASS_META_KEY, object.getClass().getCanonicalName());
		return jsonEle;
	}

}