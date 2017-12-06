package gui.mainview.fooddetailview;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.ETController;
import controller.FoodListController;
import exceptions.ObjectNotExistentException;
import gui.EntryListAUI;
import gui.listview.foodlistview.FoodListViewController;
import gui.mainview.MainViewController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.Food;
import model.FoodEntry;
import model.HealthProblem;
import model.Ingredient;
import model.Unit;

/**
 * Sample Skeleton for 'FooddetailView.fxml' Controller Class
 */
public class FoodDetailViewController extends ScrollPane implements EntryListAUI {
	private FoodEntry foodEntry;
	@SuppressWarnings("unused")
	private boolean editable;
	private ETController etController;
	private MainViewController parent;
	private Food tempFood;
	private boolean isAltDiary;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="detailView_main_pane"
	private ScrollPane detailView_main_pane; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Backward"
	private Button detailView_Backward; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Forward"
	private Button detailView_Forward; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Duplicate"
	private Button detailView_Duplicate; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Delete"
	private Button detailView_Delete; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Modify"
	private Button detailView_Modify; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Name"
	private Label detailView_Name; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Change"
	private Button detailView_Change; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Date"
	private DatePicker detailView_Date; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Time_h"
	private ChoiceBox<String> detailView_Time_h; // Value injected by FXMLLoader
	
	@FXML // fx:id="detailView_Time_m"
	private ChoiceBox<String> detailView_Time_m; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Amount"
	private TextField detailView_Amount; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Unit"
	private ChoiceBox<Unit> detailView_Unit; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_IngredientBox"
	private VBox detailView_IngredientBox; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Foods"
	private ListView<String> detailView_Foods; // Value injected by FXMLLoader

	@FXML // fx:id="detailView_Ingredients"
	private ListView<String> detailView_Ingredients; // Value injected by
														// FXMLLoader

	@FXML // fx:id="detailView_Problems"
	private ListView<String> detailView_Problems; // Value injected by
													// FXMLLoader

	@FXML // fx:id="detailView_Comment"
	private TextArea detailView_Comment; // Value injected by FXMLLoader

	@FXML
	private Button detailView_save;

	@FXML
	private Button detailView_cancel;

	public FoodDetailViewController(ETController etController, MainViewController parent) {
		this.etController = etController;
		this.parent = parent;

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FoodDetailView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is
			// complete
	void initialize() {
		detailView_Unit.getItems().setAll(Unit.values());
		for(int i=0; i<24; i++) {
			detailView_Time_h.getItems().add(""+i);
		}
		for(int i=0; i<60; i++) {
			if(i<10) {
				detailView_Time_m.getItems().add("0"+i);
			} else {
				detailView_Time_m.getItems().add(""+i);
			}
		}
		setEditable(false);
		etController.getEntryListController().addAUI(this);
	}

	public void setEntry(FoodEntry entry) {
		if (entry != null) {

			this.foodEntry = entry;

			detailView_Name.setText(entry.getFood().getName());
			detailView_Date.setValue(entry.getTime().toLocalDate());
			detailView_Time_h.setValue(entry.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("H")));
			detailView_Time_m.setValue(entry.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("mm")));

			detailView_Amount.setText(entry.getAmount() + "");
			detailView_Unit.setValue(entry.getUnit());
			detailView_Comment.setText(entry.getComment());

			ArrayList<Food> foods = (ArrayList<Food>) entry.getFood().getIngredientsOfFoodList();
			ArrayList<String> foodNames = new ArrayList<String>();
			for (Food f : foods) {
				foodNames.add(f.getName());
			}
			detailView_Foods.setItems(FXCollections.observableArrayList(foodNames));

			HashSet<Ingredient> ingredients = FoodListController.extractIngredients(10,entry.getFood());
			ArrayList<String> ingredientNames = new ArrayList<String>();

			for (Ingredient i : ingredients) {
				ingredientNames.add(i.getName());
			}

			detailView_Ingredients.setItems(FXCollections.observableArrayList(ingredientNames));

			HashSet<HealthProblem> healthProblems = FoodListController.extractHealthProblems(ingredients);
			ArrayList<String> healthProblemNames = new ArrayList<String>();

			for (HealthProblem h : healthProblems) {
				healthProblemNames.add(h.getName());
			}
			detailView_Problems.setItems(FXCollections.observableArrayList(healthProblemNames));
		}
		setEditable(false);
	}

	public FoodEntry getEntry() {
		return foodEntry;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;

		if (editable) {
			detailView_Change.setVisible(!false);
			detailView_Date.setDisable(!true);
			detailView_Time_h.setDisable(false);
			detailView_Time_m.setDisable(false);
			detailView_Amount.setEditable(!false);
			detailView_Unit.setDisable(!true);
			detailView_Foods.setEditable(!false);
			detailView_Ingredients.setEditable(!false);
			detailView_Problems.setEditable(!false);
			detailView_Comment.setEditable(!false);
			detailView_save.setVisible(!false);
			detailView_cancel.setVisible(!false);
			detailView_Modify.setVisible(!true);
			detailView_Delete.setVisible(!true);
			detailView_Duplicate.setVisible(!true);
		} else {
			detailView_Change.setVisible(false);
			detailView_Date.setDisable(true);			
			detailView_Time_h.setDisable(!false);
			detailView_Time_m.setDisable(!false);
			detailView_Amount.setEditable(false);
			detailView_Unit.setDisable(true);
			detailView_Foods.setEditable(false);
			detailView_Ingredients.setEditable(false);
			detailView_Problems.setEditable(false);
			detailView_Comment.setEditable(false);
			detailView_save.setVisible(false);
			detailView_cancel.setVisible(false);
			detailView_Modify.setVisible(true);
			detailView_Delete.setVisible(true);
			detailView_Duplicate.setVisible(true);

		}
	}

	@FXML
	void detailView_changeOnAction(ActionEvent event) {
		SelectFoodDialog dialog = new SelectFoodDialog();
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == dialog.getButtonType() && dialog.getEntry() != null) {
			tempFood = dialog.getEntry();
			detailView_Name.setText(tempFood.getName());
			ArrayList<Food> foods = (ArrayList<Food>) tempFood.getIngredientsOfFoodList();
			ArrayList<String> foodNames = new ArrayList<String>();
			for (Food f : foods) {
				foodNames.add(f.getName());
			}
			detailView_Foods.setItems(FXCollections.observableArrayList(foodNames));

			HashSet<Ingredient> ingredients = FoodListController.extractIngredients(10,tempFood);
			ArrayList<String> ingredientNames = new ArrayList<String>();

			for (Ingredient i : ingredients) {
				ingredientNames.add(i.getName());
			}

			detailView_Ingredients.setItems(FXCollections.observableArrayList(ingredientNames));

			HashSet<HealthProblem> healthProblems = FoodListController.extractHealthProblems(ingredients);
			ArrayList<String> healthProblemNames = new ArrayList<String>();

			for (HealthProblem h : healthProblems) {
				healthProblemNames.add(h.getName());
			}
			detailView_Problems.setItems(FXCollections.observableArrayList(healthProblemNames));
		}
	}

	@FXML
	void detailView_deleteOnAction(ActionEvent event) {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Löschen bestätigen");
		dialog.setContentText("Möchten Sie diesen Eintrag wirklich löschen?");
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {
				etController.getEntryListController().deleteEntry(foodEntry);
			} catch (ObjectNotExistentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	void detailView_duplicateOnAction(ActionEvent event) {
		etController.getEntryListController().createFoodEntry(foodEntry.getTime(), foodEntry.getAmount(),
				foodEntry.getUnit(), foodEntry.getComment(), foodEntry.getFood());
	}

	@FXML
	void detailView_modifyOnAction(ActionEvent event) {
		setEditable(true);
	}

	@FXML
	void detailView_navForOnAction(ActionEvent event) {
		parent.navigateForward(isAltDiary);
	}

	@FXML
	void detailView_navBackOnAction(ActionEvent event) {
		parent.navigateBack(isAltDiary);
	}

	@FXML
	void detailView_cancelOnAction(ActionEvent event) {
		setEditable(false);
		refreshDiary();
	}

	@FXML
	void detailView_saveOnAction(ActionEvent event) {
		LocalTime time;
		double amount;

		try {
			// time = LocalTime.parse("12:03");
			time = LocalTime.parse(""+detailView_Time_h.getValue()+":"+detailView_Time_m.getValue(), DateTimeFormatter.ofPattern("H:mm"));
		} catch (DateTimeParseException e) {

			Alert dialog = new Alert(AlertType.ERROR);
			dialog.setTitle("Fehler beim Speichern");
			dialog.setContentText(
					"Die angegebene Uhrzeit ist nicht gültig! Geben Sie die Uhrzeit im Format \"H:mm\" an.");
			dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			dialog.show();
			return;
		}
		LocalDateTime dateTime = LocalDateTime.of(detailView_Date.getValue(), time);

		try {
			amount = Double.parseDouble(detailView_Amount.getText());
		} catch (NumberFormatException e) {
			Alert dialog = new Alert(AlertType.ERROR);
			dialog.setTitle("Fehler beim Speichern");
			dialog.setContentText(
					"Die angegebene Menge ist keine gültige Zahl! Geben Sie die Menge im Format \"#.#\" an.");
			dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			dialog.show();
			return;
		}
		if (tempFood != null) {
			etController.getEntryListController().modifyFoodEntry(dateTime, amount, detailView_Unit.getValue(),
					detailView_Comment.getText(), tempFood, foodEntry);
		} else {
			etController.getEntryListController().modifyFoodEntry(dateTime, amount, detailView_Unit.getValue(),
					detailView_Comment.getText(), foodEntry.getFood(), foodEntry);
		}

		setEditable(false);
	}

	@Override
	public void refreshDiary() {
		setEntry(foodEntry);
	}

	private class SelectFoodDialog extends Dialog<ButtonType> {
		ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
		FoodListViewController foodListView;

		private SelectFoodDialog() {
			setTitle("Lebensmittel auswählen");
			foodListView = new FoodListViewController(etController);
			foodListView.setFoodList(etController.getCurrentDiary().getFoodList());
			// setHeaderText("Medikament auswählen");
			getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
			getDialogPane().setContent(foodListView);
		}

		private ButtonType getButtonType() {
			return okButtonType;
		}

		public Food getEntry() {
			return foodListView.getSelectedItem();
		}
	}
	public void setIsAltDiary(boolean isAltDiary){
		this.isAltDiary = isAltDiary;
		setEditButtonsVisible(!isAltDiary);
	}
	public boolean getIsAltDiary(){
		return isAltDiary;
	}
	
	private void setEditButtonsVisible(boolean visible){
		detailView_Modify.setVisible(visible);
		detailView_Delete.setVisible(visible);
		detailView_Duplicate.setVisible(visible);
	}
}
