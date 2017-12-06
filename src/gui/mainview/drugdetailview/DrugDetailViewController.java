package gui.mainview.drugdetailview;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.ETController;
import exceptions.ObjectNotExistentException;
import gui.EntryListAUI;
import gui.listview.druglistview.DrugListViewController;
import gui.mainview.MainViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Drug;
import model.DrugEntry;
import model.HealthProblem;
import model.Unit;

/**
 * Sample Skeleton for 'DrugdetailView.fxml' Controller Class
 */
public class DrugDetailViewController extends ScrollPane implements EntryListAUI {
	@SuppressWarnings("unused")
	private boolean editable = false;
	private DrugEntry drugEntry;
	private ETController etController;
	private MainViewController parent;
	private boolean isAltDiary;
	private Drug tempDrug;

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

	@FXML // fx:id="detailView_Problems"
	private ListView<String> detailView_Problems; // Value injected by
													// FXMLLoader

	@FXML
	private Button detailView_save;

	@FXML
	private Button detailView_cancel;

	@FXML // fx:id="detailView_Comment"
	private TextArea detailView_Comment; // Value injected by FXMLLoader

	public DrugDetailViewController(ETController etController, MainViewController parent) {
		this.etController = etController;
		this.parent = parent;

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DrugDetailView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		drugEntry = null;
	}

	public void setEntry(DrugEntry drugEntry) {
		if (drugEntry != null) {
			this.drugEntry = drugEntry;
			detailView_Name.setText(drugEntry.getDrug().getName());
			detailView_Date.setValue(drugEntry.getTime().toLocalDate());
			detailView_Unit.setValue(drugEntry.getUnit());

			detailView_Time_h.setValue(drugEntry.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("H")));
			detailView_Time_m.setValue(drugEntry.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("mm")));
			detailView_Amount.setText(drugEntry.getAmount() + "");
			ObservableList<String> hpList = FXCollections.observableArrayList();
			for (HealthProblem h : drugEntry.getDrug().getHealthProblemList()) {
				if (!hpList.contains(h.getName())) {
					hpList.add(h.getName());

				}
			}
			detailView_Problems.setItems(hpList);
			detailView_Comment.setText(drugEntry.getComment());
		} else {
			this.drugEntry = null;
			detailView_Name.setText("");
			detailView_Date.setValue(LocalDate.now());
			detailView_Unit.setValue(Unit.VOID);
			detailView_Time_h.setValue(LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("H")));
			detailView_Time_m.setValue(LocalDateTime.now().toLocalTime().format(DateTimeFormatter.ofPattern("mm")));
			detailView_Amount.setText("0.0");
			detailView_Problems.getItems().clear();
			detailView_Comment.setText("");
		}
		setEditable(false);
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
		SelectDrugDialog dialog = new SelectDrugDialog();
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == dialog.getButtonType() && dialog.getEntry() != null) {
			tempDrug = dialog.getEntry();
			detailView_Name.setText(tempDrug.getName());
			ObservableList<String> hpList = FXCollections.observableArrayList();
			for (HealthProblem h : tempDrug.getHealthProblemList()) {
				if (!hpList.contains(h.getName())) {
					hpList.add(h.getName());

				}
			}
			detailView_Problems.setItems(hpList);
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
				etController.getEntryListController().deleteEntry(drugEntry);
			} catch (ObjectNotExistentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	void detailView_duplicateOnAction(ActionEvent event) {
		etController.getEntryListController().createDrugEntry(drugEntry.getTime(), drugEntry.getAmount(),
				drugEntry.getUnit(), drugEntry.getComment(), drugEntry.getDrug());
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
	void detailView_navbackOnAction(ActionEvent event) {
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
		if (tempDrug != null) {
			etController.getEntryListController().modifyDrugEntry(dateTime, amount, detailView_Unit.getValue(),
					detailView_Comment.getText(), tempDrug, drugEntry);
		} else {
			etController.getEntryListController().modifyDrugEntry(dateTime, amount, detailView_Unit.getValue(),
					detailView_Comment.getText(), drugEntry.getDrug(), drugEntry);
		}

		setEditable(false);
	}

	public DrugEntry getEntry() {
		return this.drugEntry;
	}

	@FXML // This method is called by the FXMLLoader when initialization is
			// complete
	void initialize() {
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
		detailView_Unit.getItems().setAll(Unit.values());
		setEditable(false);
		etController.getEntryListController().addAUI(this);
	}

	@Override
	public void refreshDiary() {
		setEntry(drugEntry);
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

	public void setIsAltDiary(boolean isAltDiary) {
		this.isAltDiary = isAltDiary;
		setEditButtonsVisible(!isAltDiary);
	}

	public boolean getIsAltDiary() {
		return isAltDiary;
	}

	private void setEditButtonsVisible(boolean visible) {
		detailView_Modify.setVisible(visible);
		detailView_Delete.setVisible(visible);
		detailView_Duplicate.setVisible(visible);
	}
}
