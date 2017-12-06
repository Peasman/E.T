package gui.filterWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.ISuggestionRequest;
import org.controlsfx.control.textfield.TextFields;

import controller.ETController;
import controller.SearchDiaryEntriesController;
import gui.FilterAUI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import model.DiaryEntry;
import model.Drug;
import model.EntryFilter;
import model.FactorEntry;
import model.Food;
import model.HealthProblem;
import model.Ingredient;

public class FilterWindowController extends GridPane implements Callback<AutoCompletionBinding.ISuggestionRequest,Collection<String>> {

	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="comboBox_filterBy"
    private ComboBox<String> comboBox_filterBy; // Value injected by FXMLLoader

    @FXML // fx:id="button_addFilter"
    private Button button_addFilter; // Value injected by FXMLLoader

    @FXML // fx:id="textField_keywords"
    private TextField textField_keywords; // Value injected by FXMLLoader

    @FXML // fx:id="button_applyFilter"
    private Button button_applyFilter; // Value injected by FXMLLoader

    @FXML // fx:id="button_deleteFilter"
    private Button button_deleteFilter; // Value injected by FXMLLoader

    @FXML // fx:id="tableView_overview"
    private TableView<FilterOption> tableView_overview; // Value injected by FXMLLoader

    @FXML // fx:id="tableColumn_filterBy"
    private TableColumn<FilterOption, String> tableColumn_filterBy; // Value injected by FXMLLoader

    @FXML // fx:id="tableColumn_keywords"
    private TableColumn<FilterOption, String> tableColumn_keywords; // Value injected by FXMLLoader

    private ObservableList<String> categories;
    
    private ObservableList<FilterOption> tableData;
    
    private ETController etController;
    
    private FilterAUI filterCallback;
    
    private Scene scene;
    
    private Stage stage;
    
    private Timeline flasher ;
    private PseudoClass flashHighlight;
    
    public FilterWindowController(ETController etController, FilterAUI filterCallback) {
    	this.etController = etController;
    	this.filterCallback = filterCallback;
    }
    
    public void openWindow(Window parent) {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FilterWindow.fxml"));
    		fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			
	        scene = new Scene(fxmlLoader.load(), 620, 400);
	        stage = new Stage();
	        stage.initModality(Modality.WINDOW_MODAL);
	        stage.initOwner(parent);
	        stage.setTitle("FilterWindow");
	        stage.setScene(scene);
	        
	        stage.setMinWidth(620);
	        stage.setMinHeight(400);

	        
	        scene.getStylesheets().add("gui/filterWindow/application.css");
	        
	        
	        flashHighlight = PseudoClass.getPseudoClass("flash-highlight");
	        flasher = new Timeline(new KeyFrame(javafx.util.Duration.seconds(0.2), e -> {
	        		tableView_overview.pseudoClassStateChanged(flashHighlight, true);
	        	}),
                new KeyFrame(javafx.util.Duration.seconds(0.3), e -> {
                	tableView_overview.pseudoClassStateChanged(flashHighlight, false);
                })
    		);
	        
	        //flasher.setCycleCount(Animation.INDEFINITE);
	        
	        flasher.setCycleCount(2);
	        
	        
	        
	        stage.show();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
    }
    
    @FXML
    void onAddFilterButtonAction(ActionEvent event) {
    	String category = comboBox_filterBy.getValue();
    	
    	if(category != null) {
	    	comboBox_filterBy.setValue(null);
	    	
	    	String keywords = textField_keywords.getText();
	    	textField_keywords.clear();
	    	
	    	if(keywords == null || keywords.isEmpty()) {
	    		keywords = "alle Einträge der Kategorie anzeigen";
	    	}
	    	
	    	tableData.add(new FilterOption(category, keywords));
    	}
    }

    @FXML
    void onDeleteFilterButtonAction(ActionEvent event) {
    	FilterOption filterOption = tableView_overview.getSelectionModel().getSelectedItem();
    	tableData.remove(filterOption);
    }

    @FXML
    void onFilterByButtonAction(ActionEvent event) {
    	if(!tableData.isEmpty()) {
    		boolean filterFoods = false;
    		boolean filterDrugs = false;
    		boolean filterIngredients = false;
    		boolean filterFactors = false;
    		boolean filterHealthProblems = false;
    		
    		ArrayList<String> foods = new ArrayList<String>();
    		ArrayList<String> ingredients = new ArrayList<String>();
    		ArrayList<String> healthProblems = new ArrayList<String>();
    		ArrayList<String> drugs = new ArrayList<String>();
    		ArrayList<String> factors = new ArrayList<String>();
    		
    		Iterator<FilterOption> iterator = tableData.iterator();
    		
    		while(iterator.hasNext()) {
    			FilterOption filterOption = (FilterOption) iterator.next();
    			String category = filterOption.getCategory();
    			
    			if(category.equals("Lebensmittel")) {
    				foods.addAll(keywordsIntoList(filterOption.getKeywords()));
    				filterFoods = true;
    			} else if(category.equals("Inhaltsstoffe")) {
    				ingredients.addAll(keywordsIntoList(filterOption.getKeywords()));
    				filterIngredients = true;
    			} else if(category.equals("Medikamente")) {
    				drugs.addAll(keywordsIntoList(filterOption.getKeywords()));
    				filterDrugs = true;
    			} else if(category.equals("Beschwerden")) {
    				healthProblems.addAll(keywordsIntoList(filterOption.getKeywords()));
    				filterHealthProblems = true;
    			} else if(category.equals("sonstige Faktoren")) {
    				factors.addAll(keywordsIntoList(filterOption.getKeywords()));
    				filterFactors = true;
    			}
    		}
    		
    		EntryFilter filter = new EntryFilter(
    				filterFoods, 
    				filterDrugs, 
    				filterIngredients, 
    				filterFactors, 
    				filterHealthProblems, 
    				foods, 
    				drugs, 
    				ingredients, 
    				factors, 
    				healthProblems
				);
    		
    		//filterCallback.receiveFilterResults(etController.getEntryListController().filterEntryList(filter));
    		
    		filterCallback.updateFilter(filter);
    		
    		stage.close();
    	} else {
            flasher.play();
    	}
    }
    
    private ArrayList<String> keywordsIntoList(String keywords) {
    	ArrayList<String> list = new ArrayList<String>();
    	StringTokenizer tokenizer = new StringTokenizer(keywords, ",");
    	
    	while(tokenizer.hasMoreElements()) {
    		String token = tokenizer.nextToken().trim();
    		
    		if(!token.equals("alle Einträge der Kategorie anzeigen")) {
    			list.add(token);
    		}
    	}
    	
    	return list;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
    	assert comboBox_filterBy != null : "fx:id=\"comboBox_filterBy\" was not injected: check your FXML file 'FilterWindow.fxml'.";
        assert button_addFilter != null : "fx:id=\"button_addFilter\" was not injected: check your FXML file 'FilterWindow.fxml'.";
        assert textField_keywords != null : "fx:id=\"textField_keywords\" was not injected: check your FXML file 'FilterWindow.fxml'.";
        assert button_applyFilter != null : "fx:id=\"button_applyFilter\" was not injected: check your FXML file 'FilterWindow.fxml'.";
        assert button_deleteFilter != null : "fx:id=\"button_deleteFilter\" was not injected: check your FXML file 'FilterWindow.fxml'.";
        assert tableView_overview != null : "fx:id=\"tableView_overview\" was not injected: check your FXML file 'FilterWindow.fxml'.";
        assert tableColumn_filterBy != null : "fx:id=\"tableColumn_filterBy\" was not injected: check your FXML file 'FilterWindow.fxml'.";
        assert tableColumn_keywords != null : "fx:id=\"tableColumn_keywords\" was not injected: check your FXML file 'FilterWindow.fxml'.";
        
//        Stage stage = (Stage) this.getScene().getWindow();
//        
//        double minWidth = stage.getWidth();
//        double minHeigth = stage.getHeight();
//        
//        stage.setMinWidth(minWidth);
//        stage.setMinHeight(minHeigth);
        
        categories = FXCollections.observableArrayList(	
    		"Lebensmittel", 
    		"Inhaltsstoffe", 
    		"Medikamente", 
    		"Beschwerden", 
    		"sonstige Faktoren"
		);
        
        comboBox_filterBy.getItems().addAll(categories);
        
        ArrayList<FilterOption> items = new ArrayList<FilterOption>();
//        items.add(new FilterOption("Lebensmittel", "food_4_name, food_1_ingredient_3_information"));
//        items.add(new FilterOption("Inhaltsstoffe", "food_4_ingredient_1_name, food_2_ingredient_1_name, food_3_ingredient_2_information"));
//        items.add(new FilterOption("Beschwerden", "healthProblem_1_name, healthProblem_2_description"));
//        items.add(new FilterOption("Medikamente", "drug_4_name, drug_3_description, drug_2_healthProblem_1_name, drug_1_healthProblem_2_description"));
//        items.add(new FilterOption("sonstige Faktoren", "factor_1_comment, factor_2_description"));
        
        tableData = FXCollections.observableArrayList(items);
        
        tableColumn_filterBy.setCellValueFactory(new PropertyValueFactory<FilterOption, String>("category"));
        tableColumn_keywords.setCellValueFactory(new PropertyValueFactory<FilterOption, String>("keywords"));
        
        tableView_overview.setItems(tableData);
        
        TextFields.bindAutoCompletion(textField_keywords, this);
    }
    
    public static class FilterOption {
    	private SimpleStringProperty category;
    	private SimpleStringProperty keywords;
    	
    	public FilterOption(String category, String keywords) {
    		this.category = new SimpleStringProperty(category);
    		this.keywords = new SimpleStringProperty(keywords);
    	}

		public String getCategory() {
			return category.get();
		}

		public void setCategory(String category) {
			this.category.set(category);
		}

		public String getKeywords() {
			return keywords.get();
		}

		public void setKeywords(String keywords) {
			this.keywords.set(keywords);
		}
    	
    }

	@Override
	public Collection<String> call(ISuggestionRequest req) {
		ArrayList<String> suggestions = new ArrayList<String>();
		String category = comboBox_filterBy.getValue();
		
		if(!req.isCancelled() && category != null) {
			ArrayList<String> keywords = keywordsIntoList(req.getUserText());
			String userText = keywords.get(Math.max(0, keywords.size() - 1));
			
			ArrayList<Object> results = new ArrayList<Object>();
			ArrayList<DiaryEntry> entryList = etController.getCurrentDiary().getDiaryEntryList();
			
			if(category.equals("Lebensmittel")) {
				results.addAll(SearchDiaryEntriesController.searchFoods(entryList, userText));
			} else if(category.equals("Inhaltsstoffe")) {
				results.addAll(SearchDiaryEntriesController.searchIngredients(entryList, userText));
			} else if(category.equals("Medikamente")) {
				results.addAll(SearchDiaryEntriesController.searchDrugs(entryList, userText));
			} else if(category.equals("Beschwerden")) {
				results.addAll(SearchDiaryEntriesController.searchHealthProblems(entryList, userText));
			} else if(category.equals("sonstige Faktoren")) {
				results.addAll(SearchDiaryEntriesController.searchFactors(entryList, userText));
			}
			
			for(Object diaryEntry : results) {
				if (diaryEntry instanceof Food) {
					Food entry = (Food) diaryEntry;
					suggestions.add(entry.getName());
		    	} else if (diaryEntry instanceof Drug) {
		    		Drug entry = (Drug) diaryEntry;
		    		suggestions.add(entry.getName());
		    	} else if (diaryEntry instanceof HealthProblem) {
		    		HealthProblem entry = (HealthProblem) diaryEntry;
		    		suggestions.add(entry.getName());
		    	} else if (diaryEntry instanceof FactorEntry) {
		    		FactorEntry entry = (FactorEntry) diaryEntry;
		    		suggestions.add(entry.getName());
		    	} else if (diaryEntry instanceof Ingredient) {
		    		Ingredient entry = (Ingredient) diaryEntry;
		    		suggestions.add(entry.getName());
		    	}
			}
		}
		
		return suggestions;
	}
}
