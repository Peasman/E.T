package gui.mainview.healthproblemdetailview;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.ETController;
import exceptions.ObjectNotExistentException;
import gui.EntryListAUI;
import gui.mainview.MainViewController;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.DiaryEntry;
import model.FactorEntry;
import model.HealthProblemEntry;

/**
 * Sample Skeleton for 'HealthProblemdetailView.fxml' Controller Class
 */

public class HealthProblemDetailViewController extends ScrollPane implements EntryListAUI {
	private DiaryEntry entry;
	@SuppressWarnings("unused")
	private boolean editable;
	private ETController etController;
	private MainViewController parent;
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

	@FXML // fx:id="detailView_Comment"
	private TextArea detailView_Comment; // Value injected by FXMLLoader

	@FXML
	private Button detailView_save;

	@FXML
	private Button detailView_cancel;

	@FXML
	private TextArea detailView_Description;

	@FXML
	private TextField detailView_Duration;

	@FXML
	private Label detailView_labelDuration;

	public HealthProblemDetailViewController(ETController etController, MainViewController parent) {
		this.etController = etController;
		this.parent = parent;

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HealthProblemDetailView.fxml"));
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
	}

	public void setEditable(boolean editable) {
		this.editable = editable;

		if (editable) {
			detailView_Change.setVisible(!false);
			detailView_Date.setDisable(!true);
			detailView_Time_h.setDisable(false);
			detailView_Time_m.setDisable(false);
			detailView_Comment.setEditable(!false);
			detailView_save.setVisible(!false);
			detailView_cancel.setVisible(!false);
			detailView_Modify.setVisible(!true);
			detailView_Delete.setVisible(!true);
			detailView_Duplicate.setVisible(!true);
			detailView_Description.setEditable(!false);

		} else {
			detailView_Change.setVisible(false);
			detailView_Date.setDisable(true);
			detailView_Time_h.setDisable(!false);
			detailView_Time_m.setDisable(!false);
			detailView_Comment.setEditable(false);
			detailView_save.setVisible(false);
			detailView_cancel.setVisible(false);
			detailView_Modify.setVisible(true);
			detailView_Delete.setVisible(true);
			detailView_Duplicate.setVisible(true);
			detailView_Description.setEditable(false);
		}
	}

	public DiaryEntry getEntry() {
		return entry;
	}

	public void setEntry(DiaryEntry entry) {
		if (entry != null) {
			this.entry = entry;

			if (entry instanceof HealthProblemEntry) {
				detailView_Name.setText(((HealthProblemEntry) entry).getHealthProblem().getName());
				detailView_Description.setText(((HealthProblemEntry) entry).getHealthProblem().getDescription());

				detailView_Duration.setVisible(true);
				detailView_labelDuration.setVisible(true);
				detailView_Duration.setText(((HealthProblemEntry) entry).getAmount() + "");
			} else if (entry instanceof FactorEntry) {
				detailView_Name.setText(((FactorEntry) entry).getName());
				detailView_Description.setText(((FactorEntry) entry).getDescription());

				detailView_Duration.setVisible(false);
				detailView_labelDuration.setVisible(false);
				detailView_Duration.setText("0.0");
			}
			detailView_Date.setValue(entry.getTime().toLocalDate());
			detailView_Time_h.setValue(entry.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("H")));
			detailView_Time_m.setValue(entry.getTime().toLocalTime().format(DateTimeFormatter.ofPattern("mm")));
			detailView_Comment.setText(entry.getComment());
		}
		setEditable(false);
	}

	@FXML
	void detailView_changeOnAction(ActionEvent event) {
		String title = "";
		if (entry instanceof HealthProblemEntry) {
			title = "Name der Beschwerde";
		} else if (entry instanceof FactorEntry) {
			title = "Name des Faktors";
		}
		EnterNameDialog dialog = new EnterNameDialog(title);
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == dialog.getButtonType()) {
			detailView_Name.setText(dialog.getName());
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
				etController.getEntryListController().deleteEntry(entry);
			} catch (ObjectNotExistentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@FXML
	void detailView_duplicateOnAction(ActionEvent event) {

		if (entry instanceof HealthProblemEntry) {
			etController.getEntryListController().createHealthProblemEntry(entry.getTime(),
					((HealthProblemEntry) entry).getAmount(), entry.getComment(),
					((HealthProblemEntry) entry).getHealthProblem().getName(),
					((HealthProblemEntry) entry).getHealthProblem().getDescription());
		} else if (entry instanceof FactorEntry) {
			etController.getEntryListController().createFactorEntry(entry.getTime(), ((FactorEntry) entry).getName(),
					entry.getComment(), ((FactorEntry) entry).getDescription());
		}
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
		double duration;
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
			duration = Double.parseDouble(detailView_Duration.getText());
		} catch (NumberFormatException e) {
			Alert dialog = new Alert(AlertType.ERROR);
			dialog.setTitle("Fehler beim Speichern");
			dialog.setContentText(
					"Die angegebene Dauer ist keine gültige Zahl! Geben Sie die Menge im Format \"#.#\" an.");
			dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			dialog.show();
			return;
		}

		if (entry instanceof HealthProblemEntry) {
			etController.getEntryListController().modifyHealthProblemEntry(dateTime, duration,
					detailView_Comment.getText(), detailView_Name.getText(), detailView_Description.getText(),
					(HealthProblemEntry) entry);
		} else if (entry instanceof FactorEntry) {
			etController.getEntryListController().modifyFactorEntry(dateTime, detailView_Name.getText(),
					detailView_Description.getText(), detailView_Comment.getText(), (FactorEntry) entry);
		}

		setEditable(false);
	}

	@Override
	public void refreshDiary() {
		setEntry(entry);
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
