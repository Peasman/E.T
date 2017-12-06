package gui.mainview;
/**
 * Sample Skeleton for 'MainView.fxml' Controller Class
 */

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.ETController;
import controller.IOController;
import exceptions.InvalidDiaryException;
import gui.listview.druglistview.DrugListViewController;
import gui.listview.foodlistview.FoodListViewController;
import gui.listview.ingredientlistview.IngredientListViewController;
import gui.mainview.calenderview.CalenderViewController;
import gui.mainview.diaryview.DiaryViewController;
import gui.mainview.drugdetailview.DrugDetailViewController;
import gui.mainview.fooddetailview.FoodDetailViewController;
import gui.mainview.healthproblemdetailview.HealthProblemDetailViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.DiaryEntry;
import model.Drug;
import model.DrugEntry;
import model.FactorEntry;
import model.Food;
import model.FoodEntry;
import model.HealthProblemEntry;
import model.Unit;

public class MainViewController extends SplitPane {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="mainView"
	private SplitPane mainView; // Value injected by FXMLLoader

	@FXML
	private AnchorPane mainView_CenterSubWindow;

	@FXML // fx:id="mainView_newDiary"
	private Button mainView_newDiary; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_newDiary_icon"
	private ImageView mainView_newDiary_icon; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_loadDiary"
	private Button mainView_loadDiary; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_loadDiary_icon"
	private ImageView mainView_loadDiary_icon; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_saveDiary"
	private Button mainView_saveDiary; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_saveDiary_icon"
	private ImageView mainView_saveDiary_icon; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_saveDiaryAs"
	private Button mainView_saveDiaryAs; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_saveDiaryAs_icon"
	private ImageView mainView_saveDiaryAs_icon; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_compareDiary"
	private Button mainView_compareDiary; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_compareDiary_icon"
	private ImageView mainView_compareDiary_icon; // Value injected by
													// FXMLLoader

	@FXML // fx:id="mainView_printDiary"
	private Button mainView_printDiary; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_printDiary_icon"
	private ImageView mainView_printDiary_icon; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_addEntry"
	private MenuButton mainView_addEntry; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_addEntry_food"
	private MenuItem mainView_addEntry_food; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_addEntry_food_icon"
	private ImageView mainView_addEntry_food_icon; // Value injected by
													// FXMLLoader

	@FXML // fx:id="mainView_addEntry_drug"
	private MenuItem mainView_addEntry_drug; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_addEntry_drug_icon"
	private ImageView mainView_addEntry_drug_icon; // Value injected by
													// FXMLLoader

	@FXML // fx:id="mainView_addEntry_healthProblem"
	private MenuItem mainView_addEntry_healthProblem; // Value injected by
														// FXMLLoader

	@FXML // fx:id="mainView_addEntry_healthProblem_icon"
	private ImageView mainView_addEntry_healthProblem_icon; // Value injected by
															// FXMLLoader

	@FXML // fx:id="mainView_addEntry_factor"
	private MenuItem mainView_addEntry_factor; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_addEntry_factor_icon"
	private ImageView mainView_addEntry_factor_icon; // Value injected by
														// FXMLLoader

	@FXML // fx:id="mainView_addEntry_icon"
	private ImageView mainView_addEntry_icon; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_myLists"
	private MenuButton mainView_myLists; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_myLists_food"
	private MenuItem mainView_myLists_food; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_myLists_food_icon"
	private ImageView mainView_myLists_food_icon; // Value injected by
													// FXMLLoader

	@FXML // fx:id="mainView_myLists_drugs"
	private MenuItem mainView_myLists_drugs; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_myLists_drugs_icon"
	private ImageView mainView_myLists_drugs_icon; // Value injected by
													// FXMLLoader

	@FXML // fx:id="mainView_myLists_ingridients"
	private MenuItem mainView_myLists_ingridients; // Value injected by
													// FXMLLoader

	@FXML // fx:id="mainView_myLists_ingridients_icon"
	private ImageView mainView_myLists_ingridients_icon; // Value injected by
															// FXMLLoader

	@FXML // fx:id="mainView_myLists_icon"
	private ImageView mainView_myLists_icon; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_help"
	private Button mainView_help; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_help_icon"
	private ImageView mainView_help_icon; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_LeftWindow"
	private AnchorPane mainView_LeftWindow; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_MiddleWindow"
	private AnchorPane mainView_MiddleWindow; // Value injected by FXMLLoader

	@FXML // fx:id="mainView_toggleCollapseLeft"
	private ToggleButton mainView_toggleCollapseLeft; // Value injected by
														// FXMLLoader

	@FXML // fx:id="mainView_toggleCollapseRight"
	private ToggleButton mainView_toggleCollapseRight; // Value injected by
														// FXMLLoader
	@FXML
	private Label labelDate;
	
	@FXML // fx:id="mainView_RightWindow"
	private AnchorPane mainView_RightWindow; // Value injected by FXMLLoader

	private ETController etController;
	private FoodDetailViewController foodDetailView;
	private DiaryViewController diaryView;
	private DiaryViewController diaryViewAlt;
	private DrugDetailViewController drugDetailView;
	private HealthProblemDetailViewController hpDetailView;
	private CalenderViewController calenderView;
	private Button closeButton;
	private LocalDate dateShown;

	public MainViewController(ETController etc) {
		this.etController = etc;
		foodDetailView = new FoodDetailViewController(etController, this);
		drugDetailView = new DrugDetailViewController(etController, this);
		hpDetailView = new HealthProblemDetailViewController(etController, this);
		diaryView = new DiaryViewController(etController, this, false);
		calenderView = new CalenderViewController(etController, this);

		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		dateShown = LocalDate.now();
		setDateShown(dateShown);
		diaryView.refreshDiary();
	}

	@SuppressWarnings("static-access")
	@FXML
	void compareDiary_onAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Tagebuch zum Vergleichen auswählen");
		ExtensionFilter filter = new ExtensionFilter("Tagebücher (*.diary)", "*.diary");
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setSelectedExtensionFilter(filter);
		File file = fileChooser.showOpenDialog(getScene().getWindow());
		
		if (file != null) {

			try {
				etController.setAltDiary(IOController.loadDiary(file));
				diaryViewAlt = new DiaryViewController(etController, this, true);
				mainView_LeftWindow.getChildren().clear();
				mainView_LeftWindow.getChildren().add(diaryViewAlt);
				mainView_LeftWindow.setBottomAnchor(diaryViewAlt, 0.0);
				mainView_LeftWindow.setTopAnchor(diaryViewAlt, 25.0);
				mainView_LeftWindow.setLeftAnchor(diaryViewAlt, 0.0);
				mainView_LeftWindow.setRightAnchor(diaryViewAlt, 0.0);
				closeButton = new Button("X");
				mainView_LeftWindow.getChildren().add(closeButton);
				mainView_LeftWindow.setTopAnchor(closeButton, 0.0);
				mainView_LeftWindow.setRightAnchor(closeButton, 0.0);

				diaryView.setShowAll(true);
				diaryViewAlt.setShowAll(true);
				diaryView.refreshDiary();
				diaryViewAlt.refreshDiary();
				closeButton.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						mainView_LeftWindow.getChildren().clear();
						mainView_LeftWindow.getChildren().add(calenderView);
						mainView_LeftWindow.setBottomAnchor(calenderView, 0.0);
						mainView_LeftWindow.setTopAnchor(calenderView, 0.0);
						mainView_LeftWindow.setLeftAnchor(calenderView, 0.0);
						mainView_LeftWindow.setRightAnchor(calenderView, 0.0);
						
						diaryView.setShowAll(false);
						diaryViewAlt.setShowAll(false);
						diaryView.refreshDiary();
						diaryViewAlt.refreshDiary();
						
					}
					
				});
					
				
				
			} catch (InvalidDiaryException e) {
				Alert dialog = new Alert(AlertType.ERROR);
				dialog.setTitle("Ups, es ist ein Fehler aufgetreten!");
				dialog.setContentText("Beim Laden ist ein Fehler aufgetreten!");
				dialog.show();
			} catch(FileNotFoundException fnfe) {
				Alert dialog = new Alert(AlertType.ERROR);
				dialog.setTitle("Ups, es ist ein Fehler aufgetreten!");
				dialog.setContentText("Die angegebene Datei konnte nicht gefunden werden!");
				dialog.show();
			}
		} 	

	}

	@FXML
	void loadDiary_onAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Tagebuch öffnen");
		ExtensionFilter filter = new ExtensionFilter("Tagebücher (*.diary)", "*.diary");
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setSelectedExtensionFilter(filter);
		File file = fileChooser.showOpenDialog(getScene().getWindow());

		if (file != null) {

			try {
				etController.setCurrentDiary(IOController.loadDiary(file));
				etController.refresh();
			} catch (InvalidDiaryException e) {
				Alert dialog = new Alert(AlertType.ERROR);
				dialog.setTitle("Ups, es ist ein Fehler aufgetreten!");
				dialog.setContentText("Beim Laden ist ein Fehler aufgetreten!");
				dialog.show();
			} catch(FileNotFoundException fnfe) {
				Alert dialog = new Alert(AlertType.ERROR);
				dialog.setTitle("Ups, es ist ein Fehler aufgetreten!");
				dialog.setContentText("Die angegebene Datei konnte nicht gefunden werden!");
				dialog.show();
			}
		} 
	}

	@FXML
	void newDiary_onAction(ActionEvent event) {
		SaveBeforeCloseDialog dialog = new SaveBeforeCloseDialog();
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == dialog.getYesButtonType()) {
			saveDiaryAs_onAction(null);
			etController.newDiary();
		} else if (result.get() == dialog.getNoButtonType()) {
			etController.newDiary();
		}
	}

	@FXML
	void myEntries_FactorList_onAction(ActionEvent event) {
		EnterNameDialog dialog = new EnterNameDialog("Name des Faktors");
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == dialog.getButtonType()) {
			mainView_RightWindow.getChildren().clear();
			
			LocalTime now = LocalDateTime.now().toLocalTime();
			LocalDateTime newTime = LocalDateTime.of(dateShown, now);
			
			FactorEntry newEntry = etController.getEntryListController().createFactorEntry(newTime,
					dialog.getName(), "", "");
			diaryView.select(newEntry);
			hpDetailView.setEditable(true);
		}
	}

	@FXML
	void myEntries_drugList_onAction(ActionEvent event) {
		SelectDrugDialog dialog = new SelectDrugDialog();
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == dialog.getButtonType() && dialog.getEntry() != null) {
			mainView_RightWindow.getChildren().clear();
			LocalTime now = LocalDateTime.now().toLocalTime();
			LocalDateTime newTime = LocalDateTime.of(dateShown, now);
			
			DrugEntry newEntry = etController.getEntryListController().createDrugEntry(newTime, 0.0,
					Unit.PIECES, "", dialog.getEntry());
			diaryView.select(newEntry);
			drugDetailView.setEditable(true);
		}
	}

	@FXML
	void myEntries_foodList_onAction(ActionEvent event) {
		SelectFoodDialog dialog = new SelectFoodDialog();
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == dialog.getButtonType() && dialog.getEntry() != null) {
			mainView_RightWindow.getChildren().clear();
			LocalTime now = LocalDateTime.now().toLocalTime();
			LocalDateTime newTime = LocalDateTime.of(dateShown, now);
			
			FoodEntry newEntry = etController.getEntryListController().createFoodEntry(newTime, 0.0,
					Unit.PIECES, "", dialog.getEntry());
			diaryView.select(newEntry);
			foodDetailView.setEditable(true);
		}
	}

	@FXML
	void myEntries_healthProblemList_onAction(ActionEvent event) {
		EnterNameDialog dialog = new EnterNameDialog("Name der Beschwerde");
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == dialog.getButtonType()) {
			mainView_RightWindow.getChildren().clear();
			LocalTime now = LocalDateTime.now().toLocalTime();
			LocalDateTime newTime = LocalDateTime.of(dateShown, now);
			
			HealthProblemEntry newEntry = etController.getEntryListController()
					.createHealthProblemEntry(newTime, 0.0, "",dialog.getName(), "");
			diaryView.select(newEntry);
			hpDetailView.setEditable(true);
		}
	}

	@FXML
	void myLists_drugList_onAction(ActionEvent event) {
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(getScene().getWindow());

		DrugListViewController drugListView = new DrugListViewController(etController);
		drugListView.setDrugList(etController.getCurrentDiary().getDrugList());
		Scene scene = new Scene(drugListView);

		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void myLists_foodList_onAction(ActionEvent event) {
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(getScene().getWindow());

		FoodListViewController foodListView = new FoodListViewController(etController);
		foodListView.setFoodList(etController.getCurrentDiary().getFoodList());

		Scene scene = new Scene(foodListView);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void myLists_ingredientList_onAction(ActionEvent event) {
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(getScene().getWindow());

		IngredientListViewController ingredientListView = new IngredientListViewController(etController);
		ingredientListView.setIngredientList(etController.getCurrentDiary().getIngredientList());

		Scene scene = new Scene(ingredientListView);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void help_onAction(ActionEvent event) {
		if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
			new Thread(() -> {
				// Neuen Thread starten und Datei öffenen
				try {
					File myFile = new File("resources/manual/Handbuch.pdf");
					Desktop.getDesktop().open(myFile);
				} catch (IOException ex) {
					// no application registered for PDFs
					ex.printStackTrace();
				}
			}).start();

		}
	}

	@FXML
	void printDiary_onAction(ActionEvent event) {
		IOController.printDiary(etController.getCurrentDiary().getDiaryEntryList());
	}

	@FXML
	void saveDiaryAs_onAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Tagebuch speichern");
		ExtensionFilter filter = new ExtensionFilter("Tagebücher (*.diary)", "*.diary");
		fileChooser.setInitialFileName(".diary");
		fileChooser.getExtensionFilters().add(filter);

		fileChooser.setSelectedExtensionFilter(filter);
		File file = fileChooser.showSaveDialog(getScene().getWindow());

		if (file != null) {
			try {
				IOController.saveDiary(etController.getCurrentDiary(), file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void saveDiary_onAction(ActionEvent event) {
		try {
			IOController.saveDiary(etController.getCurrentDiary(),
					new File(IOController.getProperties().getProperty("lastSave")));
		} catch (IOException e) {
			Alert dialog = new Alert(AlertType.ERROR);
			dialog.setTitle("Ups, es ist ein Fehler aufgetreten!");
			dialog.setContentText("Beim Speichern ist ein Fehler aufgetreten!");
			dialog.show();
		}
	}

	public void showDetailForEntry(DiaryEntry entry, boolean isAltDiaryEntry) {
		if (entry != null) {
			if (entry instanceof DrugEntry) {
				drugDetailView.setEntry((DrugEntry) entry);
				mainView_RightWindow.getChildren().clear();
				mainView_RightWindow.getChildren().add(drugDetailView);
				drugDetailView.setMinHeight(this.getPrefHeight());
				drugDetailView.setIsAltDiary(isAltDiaryEntry);
			} else if (entry instanceof FoodEntry) {
				foodDetailView.setEntry((FoodEntry) entry);
				mainView_RightWindow.getChildren().clear();
				mainView_RightWindow.getChildren().add(foodDetailView);
				foodDetailView.setMinHeight(this.getPrefHeight());
				foodDetailView.setIsAltDiary(isAltDiaryEntry);
			} else if (entry instanceof FactorEntry) {
				hpDetailView.setEntry((FactorEntry) entry);
				mainView_RightWindow.getChildren().clear();
				mainView_RightWindow.getChildren().add(hpDetailView);
				hpDetailView.setMinHeight(this.getPrefHeight());
				hpDetailView.setIsAltDiary(isAltDiaryEntry);
			} else if (entry instanceof HealthProblemEntry) {
				hpDetailView.setEntry((HealthProblemEntry) entry);
				mainView_RightWindow.getChildren().clear();
				mainView_RightWindow.getChildren().add(hpDetailView);
				hpDetailView.setMinHeight(this.getPrefHeight());
				hpDetailView.setIsAltDiary(isAltDiaryEntry);
			} else
				hideDetailView();
		} else {
			hideDetailView();
		}

	}

	private double widthLeft;
	private double minWidthLeft = -42;

	@FXML
	void toggleCollapseLeft_onAction(ActionEvent event) {
		if (minWidthLeft == -42) {
			minWidthLeft = mainView_LeftWindow.getMinWidth();
		}
		if (mainView_LeftWindow.getWidth() == 0) {
			mainView_LeftWindow.setMinWidth(minWidthLeft);
			mainView_LeftWindow.setPrefWidth(widthLeft);
			mainView_LeftWindow.setMaxWidth(Double.MAX_VALUE);

			mainView_toggleCollapseLeft.setText("<");

		} else {
			widthLeft = mainView_LeftWindow.getWidth();
			mainView_LeftWindow.setMinWidth(0);
			mainView_LeftWindow.setPrefWidth(0);
			mainView_LeftWindow.setMaxWidth(0);

			mainView_toggleCollapseLeft.setText(">");

		}
	}

	private double widthRight = 332;

	@FXML
	void toggleCollapseRight_onAction(ActionEvent event) {
		if (mainView_RightWindow.getWidth() == 0) {
			mainView_RightWindow.setMinWidth(widthRight);
			mainView_RightWindow.setPrefWidth(widthRight);
			mainView_RightWindow.setMaxWidth(widthRight);
			mainView_toggleCollapseRight.setText(">");

		} else {
			mainView_RightWindow.setMinWidth(0);
			mainView_RightWindow.setPrefWidth(0);
			mainView_RightWindow.setMaxWidth(0);
			mainView_toggleCollapseRight.setText("<");

		}
	}

	public void navigateForward(boolean inAltDiary) {
		if (inAltDiary)
			diaryViewAlt.navigateForward();
		else
			diaryView.navigateForward();
	}

	public void navigateBack(boolean inAltDiary) {
		if (inAltDiary)
			diaryViewAlt.navigateBackwards();
		else
			diaryView.navigateBackwards();
	}

	public void hideDetailView() {
		mainView_RightWindow.getChildren().clear();
	}
	 
	public void setDateShown(LocalDate newDate){
		dateShown = newDate;
		labelDate.setText(dateShown.format(DateTimeFormatter.ofPattern("d MMM uuuu")));
		diaryView.setDateShown(dateShown);
	}
	

	@SuppressWarnings("static-access")
	@FXML // This method is called by the FXMLLoader when initialization is
			// complete
	void initialize() {
		mainView_MiddleWindow.getChildren().add(diaryView);
		mainView_RightWindow.setBottomAnchor(foodDetailView, 0.0);
		mainView_RightWindow.setTopAnchor(foodDetailView, 0.0);
		mainView_MiddleWindow.setBottomAnchor(diaryView, 0.0);
		mainView_MiddleWindow.setTopAnchor(diaryView, 0.0);
		mainView_MiddleWindow.setLeftAnchor(diaryView, 0.0);
		mainView_MiddleWindow.setRightAnchor(diaryView, 0.0);
		mainView_LeftWindow.getChildren().add(calenderView);
		mainView_LeftWindow.setBottomAnchor(calenderView, 0.0);
		mainView_LeftWindow.setTopAnchor(calenderView, 0.0);
		mainView_LeftWindow.setLeftAnchor(calenderView, 0.0);
		mainView_LeftWindow.setRightAnchor(calenderView, 0.0);
		
		mainView_RightWindow.setMinWidth(0);
		mainView_RightWindow.setPrefWidth(0);
		mainView_RightWindow.setMaxWidth(0);
		mainView_toggleCollapseRight.setText("<");
	}

	private class EnterNameDialog extends Dialog<ButtonType> {
		ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
		TextField name;

		@SuppressWarnings("static-access")
		private EnterNameDialog(String title) {
			setTitle(title);
			getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
			AnchorPane anchor = new AnchorPane();
			VBox vbox = new VBox();
			name = new TextField();
			Label labelName = new Label("Name");
			vbox.getChildren().addAll(labelName, name);
			anchor.getChildren().add(vbox);
			anchor.setTopAnchor(vbox, 0.0);
			anchor.setBottomAnchor(vbox, 0.0);
			anchor.setLeftAnchor(vbox, 0.0);
			anchor.setRightAnchor(vbox, 0.0);
			getDialogPane().setContent(anchor);
		}

		private ButtonType getButtonType() {
			return okButtonType;
		}

		private String getName() {
			return name.getText();
		}
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

	private class SelectDrugDialog extends Dialog<ButtonType> {
		ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
		DrugListViewController drugListView;

		private SelectDrugDialog() {
			setTitle("Medikament auswählen");
			drugListView = new DrugListViewController(etController);
			drugListView.setDrugList(etController.getCurrentDiary().getDrugList());
			// setHeaderText("Medikament auswählen");
			getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
			getDialogPane().setContent(drugListView);
		}

		private ButtonType getButtonType() {
			return okButtonType;
		}

		public Drug getEntry() {
			return drugListView.getSelectedItem();
		}
	}

	private class SaveBeforeCloseDialog extends Dialog<ButtonType> {
		ButtonType yesButtonType = new ButtonType("Ja", ButtonData.YES);
		ButtonType noButtonType = new ButtonType("Nein", ButtonData.YES);

		@SuppressWarnings("static-access")
		private SaveBeforeCloseDialog() {
			setTitle("Neues Tagebuch anlegen");
			getDialogPane().getButtonTypes().addAll(yesButtonType, noButtonType, ButtonType.CANCEL);
			AnchorPane anchor = new AnchorPane();
			VBox vbox = new VBox();
			Label labelName = new Label("Möchten Sie das geöffnete Tagebuch speichern?");
			vbox.getChildren().addAll(labelName);
			anchor.getChildren().add(vbox);
			anchor.setTopAnchor(vbox, 0.0);
			anchor.setBottomAnchor(vbox, 0.0);
			anchor.setLeftAnchor(vbox, 0.0);
			anchor.setRightAnchor(vbox, 0.0);
			getDialogPane().setContent(anchor);
		}

		private ButtonType getYesButtonType() {
			return yesButtonType;

		}

		private ButtonType getNoButtonType() {
			return noButtonType;
		}
	}

}
