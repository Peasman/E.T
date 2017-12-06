package gui.mainview.diaryview;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.ISuggestionRequest;
import org.controlsfx.control.textfield.TextFields;

import controller.ETController;
import controller.SearchDiaryEntriesController;
import gui.EntryListAUI;
import gui.FilterAUI;
import gui.filterWindow.FilterWindowController;
import gui.mainview.MainViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.Diary;
import model.DiaryEntry;
import model.Drug;
import model.DrugEntry;
import model.EntryFilter;
import model.FactorEntry;
import model.Food;
import model.FoodEntry;
import model.HealthProblem;
import model.HealthProblemEntry;
import model.Ingredient;

public class DiaryViewController extends AnchorPane
		implements FilterAUI, EntryListAUI, Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>> {

	@FXML
	private Button diaryview_searchbutton;

	@FXML
	private Button diaryview_filterbutton;

	@FXML
	private TextField diaryview_searchwindow;

	@FXML
	private TableView<DiaryEntry> diaryview_table;

	@FXML
	private TableColumn<DiaryEntry, String> diaryview_timecolumn;

	@FXML
	private TableColumn<DiaryEntry, String> diaryview_amountcolumn;

	@FXML
	private TableColumn<DiaryEntry, String> diaryview_typecolumn;

	@FXML
	private TableColumn<DiaryEntry, String> diaryview_namecolumn;

	@FXML
	private AnchorPane diaryview_anchor;

	@FXML
	private Button diaryview_clearFilter;
	private boolean useAltDiary;

	private ETController etController;
	private MainViewController parent;
	private EntryFilter filter;
	private boolean filterActive = false;
	private LocalDate dateShown;
	
	
	public DiaryViewController(ETController etController, MainViewController parent, boolean useAltDiary) {
		this.etController = etController;
		this.parent = parent;
		this.useAltDiary = useAltDiary;

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DiaryView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		diaryview_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			DiaryEntry selectedEntry = diaryview_table.getSelectionModel().getSelectedItem();
			parent.showDetailForEntry(selectedEntry, useAltDiary);
		});

	}

	public void navigateForward() {
		diaryview_table.getSelectionModel().select(diaryview_table.getSelectionModel().getSelectedIndex() + 1);
		parent.showDetailForEntry(diaryview_table.getSelectionModel().getSelectedItem(), useAltDiary);
	}

	public void navigateBackwards() {
		diaryview_table.getSelectionModel().select(diaryview_table.getSelectionModel().getSelectedIndex() - 1);
		parent.showDetailForEntry(diaryview_table.getSelectionModel().getSelectedItem(), useAltDiary);
	}

	public void select(DiaryEntry entry) {
		if (diaryview_table.getItems().contains(entry))
			diaryview_table.getSelectionModel().select(entry);
	}

	@FXML
	void diaryview_tableclicked(MouseEvent event) {
		// Unused
	}

	@FXML
	void diaryview_onfilterbuttonpressed(ActionEvent event) {
		new FilterWindowController(etController, this).openWindow(getScene().getWindow());
	}

	@FXML
	void diaryview_searchbuttonpressed(ActionEvent event) {
		ArrayList<String> userText = new ArrayList<String>();
		userText.add(diaryview_searchwindow.getText().trim());
		diaryview_searchwindow.setText(null);

		EntryFilter filter = new EntryFilter(true, true, true, true, true, userText, userText, userText, userText,
				userText);
		updateFilter(filter);
	}

	private String getDisplayNameForEntry(DiaryEntry entry) {
		if (entry instanceof DrugEntry) {
			return "Medikament";
		} else if (entry instanceof FoodEntry) {
			return "Lebensmittel";
		} else if (entry instanceof FactorEntry) {
			return "Sonstiger Faktor";
		} else if (entry instanceof HealthProblemEntry) {
			return "Beschwerde";
		}
		return "";
	}

	@FXML // This method is called by the FXMLLoader when initialization is
			// complete
	void initialize() {
		diaryview_timecolumn.setCellValueFactory(cell -> new SimpleStringProperty(
				cell.getValue().getTime().format(DateTimeFormatter.ofPattern("d MMM uuuu H:mm"))));
		diaryview_timecolumn.setComparator(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				LocalDateTime d1 = LocalDateTime.parse(o1, DateTimeFormatter.ofPattern("d MMM uuuu H:mm"));
				LocalDateTime d2 = LocalDateTime.parse(o2, DateTimeFormatter.ofPattern("d MMM uuuu H:mm"));
				return d1.compareTo(d2);
			}
		});

		diaryview_amountcolumn.setCellValueFactory(
				cell -> new SimpleStringProperty(cell.getValue().getAmount() + " " + cell.getValue().getUnit()));
		diaryview_typecolumn
				.setCellValueFactory(cell -> new SimpleStringProperty(getDisplayNameForEntry(cell.getValue())));
		diaryview_namecolumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));

		Diary diary;
		if (useAltDiary)
			diary = etController.getAltDiary();
		else
			diary = etController.getCurrentDiary();

		diaryview_table.setItems(FXCollections.observableArrayList(diary.getDiaryEntryList()));

		this.diaryview_clearFilter.setVisible(false);

		etController.getEntryListController().addAUI(this);

		TextFields.bindAutoCompletion(diaryview_searchwindow, this);
	}

	@FXML
	void diaryview_clearFilterOnAction(ActionEvent event) {
		filterActive = false;
		diaryview_clearFilter.setVisible(false);
		refreshDiary();
	}


	public void setDateShown(LocalDate newDate) {
		dateShown = newDate;
	}
	private boolean showAll = false;
	public void setShowAll(boolean showAll){
		
		this.showAll = showAll;
	}
	
	
	@Override
	public void refreshDiary() {
		DiaryEntry selectedEntry = diaryview_table.getSelectionModel().getSelectedItem();
		int selectedIndex = diaryview_table.getSelectionModel().getSelectedIndex();
		
		diaryview_table.getItems().clear();
		if (filterActive){
			if (useAltDiary){
				diaryview_table.setItems(FXCollections.observableArrayList(etController.getEntryListController().filterEntryList(filter, useAltDiary)));
}
			else{
				if (showAll){
					diaryview_table.setItems(FXCollections.observableArrayList(etController.getEntryListController().filterEntryList(filter, useAltDiary)));
				}else{
					diaryview_table.setItems(FXCollections.observableArrayList(etController.getEntryListController().getEntrysOfDay(etController.getEntryListController().filterEntryList(filter, useAltDiary), dateShown)));
				}
			}
		}
		else{
			if (useAltDiary){
				if (showAll){
					diaryview_table.setItems(FXCollections.observableArrayList(etController.getAltDiary().getDiaryEntryList()));
				}else{
					diaryview_table.setItems(FXCollections.observableArrayList(etController.getEntryListController().getEntrysOfDay(etController.getAltDiary().getDiaryEntryList(), dateShown)));	
				}
			}
			else{
				if (showAll){
					diaryview_table.setItems(FXCollections.observableArrayList(etController.getCurrentDiary().getDiaryEntryList()));
				}else{
					diaryview_table.setItems(FXCollections.observableArrayList(etController.getEntryListController().getEntrysOfDay(etController.getCurrentDiary().getDiaryEntryList(), dateShown)));
				}
				
			}
		}
			
		if(selectedEntry != null && diaryview_table.getItems().contains(selectedEntry)){
			diaryview_table.getSelectionModel().select(selectedEntry);
			parent.showDetailForEntry(selectedEntry, useAltDiary);
		}
		else if (diaryview_table.getItems().size() != 0){
			diaryview_table.getSelectionModel().select(Math.min(diaryview_table.getItems().size()-1, selectedIndex));
			parent.showDetailForEntry(diaryview_table.getSelectionModel().getSelectedItem(), useAltDiary);
		}
		else {
			parent.hideDetailView();
		}
	}

	public void applyFilter() {
		diaryview_table.getItems().clear();
		diaryview_table.setItems(FXCollections
				.observableArrayList(etController.getEntryListController().filterEntryList(filter, useAltDiary)));
		parent.hideDetailView();
	}

	@Override
	public void updateFilter(EntryFilter filter) {
		this.filter = filter;
		this.filterActive = true;
		this.diaryview_clearFilter.setVisible(true);
		;
		applyFilter();
	}

	@Override
	public Collection<String> call(ISuggestionRequest req) {
		ArrayList<String> suggestions = new ArrayList<String>();

		if (!req.isCancelled()) {
			ArrayList<Object> results = search(req.getUserText());

			for (Object diaryEntry : results) {
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

	private ArrayList<Object> search(String keyword) {
		ArrayList<String> userText = new ArrayList<String>();
		userText.add(keyword);

		// EntryFilter filter = new EntryFilter(true, true, true, true, true,
		// userText, userText, userText, userText, userText);
		ArrayList<Object> results = new ArrayList<Object>();
		ArrayList<DiaryEntry> entryList = etController.getCurrentDiary().getDiaryEntryList();

		results.addAll(SearchDiaryEntriesController.searchFoods(entryList, userText.get(0)));
		results.addAll(SearchDiaryEntriesController.searchIngredients(entryList, userText.get(0)));
		results.addAll(SearchDiaryEntriesController.searchDrugs(entryList, userText.get(0)));
		results.addAll(SearchDiaryEntriesController.searchHealthProblems(entryList, userText.get(0)));
		results.addAll(SearchDiaryEntriesController.searchFactors(entryList, userText.get(0)));

		return results;
	}

	@SuppressWarnings("unused")
	private ArrayList<DiaryEntry> filter(String keyword) {
		ArrayList<String> userText = new ArrayList<String>();
		userText.add(keyword);

		EntryFilter filter = new EntryFilter(true, true, true, true, true, userText, userText, userText, userText,
				userText);
		ArrayList<DiaryEntry> results = (ArrayList<DiaryEntry>) etController.getEntryListController()
				.filterEntryList(filter, useAltDiary);

		return results;
	}
}
