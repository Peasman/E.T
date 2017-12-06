package gui.listview.druglistview;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.DrugListController;
import controller.ETController;
import controller.IOController;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistentException;
import gui.ListAUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Drug;
import model.HealthProblem;

public class DrugListViewController extends AnchorPane implements ListAUI {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="drugListView_export"
	private Button drugListView_export; // Value injected by FXMLLoader

	@FXML // fx:id="drugListView_import"
	private Button drugListView_import; // Value injected by FXMLLoader

	@FXML // fx:id="drugListView_delete"
	private Button drugListView_delete; // Value injected by FXMLLoader

	@FXML // fx:id="dugListView_add"
	private Button dugListView_add; // Value injected by FXMLLoader

	@FXML // fx:id="drugListView_drugList"
	private ListView<Drug> drugListView_drugList; // Value injected by
													// FXMLLoader

	@FXML // fx:id="drugListView_name"
	private Label drugListView_name; // Value injected by FXMLLoader

	@FXML // fx:id="drugListView_addHealthProblem"
	private Button drugListView_addHealthProblem; // Value injected by
													// FXMLLoader

	@FXML // fx:id="drugListView_accordionList"
	private Accordion drugListView_accordionList; // Value injected by
													// FXMLLoader

	@FXML // fx:id="drugListView_description"
	private TextArea drugListView_description; // Value injected by FXMLLoader

	@FXML // fx:id="drugListView_deleteHealthProblem"
	private Button drugListView_deleteHealthProblem; // Value injected by
	@FXML
	private AnchorPane drugListView_topAnchor;
	@FXML
	private AnchorPane drugListView_bottomAnchor;
	@FXML
	private VBox drugListView_VboxBottom;
	private TextField name;
	private Button save;
	// FXMLLoader
	private boolean change = false;
	@FXML
    private Button changeDrug_onAction;

	@FXML
	void deleteHealthProblem_onAction(ActionEvent event) {
		if (drugListView_accordionList.getExpandedPane() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Keine Nebenwirkung ausgewählt.");
			alert.setContentText("Wählen sie eine Nebenwirkung zum löschen aus!");
			alert.showAndWait();
		} else {
			HealthProblemContainer hpc = (HealthProblemContainer) drugListView_accordionList.getExpandedPane();
			HealthProblem healthProblem = hpc.getHealthProblem();
			System.out.println(healthProblem);
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Löschen bestätigen");
			alert.setHeaderText(healthProblem.getName() + " löschen?");
			alert.setContentText("Möchten sie den Eintrag wirklich löschen?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				drugListView_drugList.getSelectionModel().getSelectedItem().getHealthProblemList()
						.remove(healthProblem);
			}
			this.refreshList();
		}
	}
	
	@FXML
	void delete_onAction(ActionEvent event) {
		Drug d = drugListView_drugList.getSelectionModel().getSelectedItem();
		if (d != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Löschen bestätigen");
			alert.setHeaderText(d.getName() + " löschen?");
			alert.setContentText("Möchten sie den Eintrag wirklich löschen?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					etController.getDrugListController().deleteDrug(d);
				} catch (ObjectNotExistentException e) {
					Alert errorHandle = new Alert(AlertType.ERROR);
					errorHandle.setTitle("Error 404");
					errorHandle.setHeaderText("Error beim Nahrung löschen");
					errorHandle.setContentText(
							"Es ist ein Fehler beim Löschen aufgetreten. \nMöglicherweise ist das Objekt bereits gelöscht worden.");
					errorHandle.showAndWait();
					e.printStackTrace();
				}
			} else {

			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Kein Medikament ausgewählt");
			alert.showAndWait();
		}
	}

	@FXML
	void export_onAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Medikamente exportieren");
		ExtensionFilter filter = new ExtensionFilter("Tagebücher (*.drugList)", "*.drugList");
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setInitialFileName(".drugList");
		fileChooser.setSelectedExtensionFilter(filter);
		File file = fileChooser.showSaveDialog(getScene().getWindow());

		if (file != null) {
			IOController.saveList(etController.getCurrentDiary().getDrugList(), file.getPath());
		}
	}

	@FXML
	void import_onAction(ActionEvent event) throws ObjectAlreadyExistsException, ObjectNotExistentException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Medikamente importieren");
		ExtensionFilter filter = new ExtensionFilter("Tagebücher (*.drugList)", "*.drugList");
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setSelectedExtensionFilter(filter);
		File file = fileChooser.showOpenDialog(getScene().getWindow());

		if (file != null) {
			DrugListController drugListController = etController.getDrugListController();
			ArrayList<Drug> drugList = (ArrayList<Drug>) IOController.loadDrugList(file);

			for (Drug drug : drugList) {
				try {
					drugListController.createDrug(drug.getName(), drug.getDescription(), drug.getHealthProblemList());
				} catch (ObjectAlreadyExistsException e) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Überschreiben");
					alert.setHeaderText(drug.getName() + " überschreiben?");
					alert.setContentText("Möchten sie den Eintrag wirklich überschreiben?");
					alert.showAndWait();
					etController.getDrugListController().modifyDrug(drug, drug.getName(), drug.getDescription(),
							drug.getHealthProblemList());
				}

			}
		}
	}

	@FXML
	void newHealthProblem_onAction(ActionEvent event) {
		Drug drug = drugListView_drugList.getSelectionModel().getSelectedItem();
		AddHealthProblemDialog dialog = new AddHealthProblemDialog();
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == dialog.getButtonType()) {
			Collection<HealthProblem> healthProblemList = drug.getHealthProblemList();
			if (!dialog.getName().equals("")) {
				healthProblemList.add(new HealthProblem(dialog.getName(), dialog.getDesc()));
				this.setDrugList(etController.getCurrentDiary().getDrugList());
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setHeaderText("Kein Name eingegeben");
				alert.setContentText("Bitte geben sie einen gültigen Namen ein");
				Optional<ButtonType> waiter = alert.showAndWait();
				if (waiter.get() == ButtonType.OK) {
					this.newHealthProblem_onAction(null);
				}
			}
		}
	}

	@SuppressWarnings("static-access")
	@FXML
	void new_onAction(ActionEvent event) {
		toggleEditButtons();
		name.setText("");
		drugListView_topAnchor.setBottomAnchor(name, 0.0);
		drugListView_topAnchor.setTopAnchor(name, 0.0);
		drugListView_topAnchor.setLeftAnchor(name, 0.0);
		drugListView_topAnchor.setRightAnchor(name, 50.0);
		drugListView_topAnchor.setTopAnchor(save, 0.0);
		drugListView_topAnchor.setRightAnchor(save, 0.0);
		drugListView_topAnchor.setBottomAnchor(save, 0.0);
		name.setFont(new Font(20));
		drugListView_name.setText("");
		drugListView_accordionList.getPanes().clear();
		drugListView_description.setText("");
		drugListView_description.setEditable(true);
		drugListView_description.setPromptText("Beschreibung eingeben");

	}
	
	@FXML
    void changeDrug_onAction(ActionEvent event) {
		toggleEditButtons();
		name.setText(drugListView_drugList.getSelectionModel().getSelectedItem().getName());;
		name.setFont(new Font(20));
		drugListView_description.setText(drugListView_drugList.getSelectionModel().getSelectedItem().getDescription());
		drugListView_description.setEditable(true);
		change = true;
		
	}

	private void initEditButtons() {
		name = new TextField();
		name.setManaged(false);
		name.setVisible(false);
		name.setPromptText("Name eingeben");
		drugListView_topAnchor.getChildren().add(name);
		save = new Button("Speichern");
		save.setManaged(!save.isManaged());
		save.setVisible(!save.isVisible());
		drugListView_description.setEditable(true);
		drugListView_topAnchor.getChildren().add(save);

		drugListView_VboxBottom.getChildren().clear();
		drugListView_VboxBottom.getChildren().add(drugListView_description);
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					toggleEditButtons();
					if (name.getText().equals("")) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Fehler");
						alert.setHeaderText("Kein Name eingegeben");
						alert.setContentText("Geben sie einen Namen ein!");
						alert.showAndWait();
					} else {
						if(change == true){
							ArrayList<HealthProblem> healthProblems = new ArrayList<HealthProblem>();
							for (TitledPane titledPane : drugListView_accordionList.getPanes()) {
								HealthProblemContainer hpc = (HealthProblemContainer) titledPane;
								healthProblems.add(hpc.getHealthProblem());
							}
							etController.getDrugListController().modifyDrug(drugListView_drugList.getSelectionModel().getSelectedItem(),name.getText(),
									drugListView_description.getText(), healthProblems);
							drugListView_drugList.getSelectionModel().selectLast();
						}else{
						etController.getDrugListController().createDrug(name.getText(),
								drugListView_description.getText(), new ArrayList<HealthProblem>());
						drugListView_drugList.getSelectionModel().selectLast();
						}
					}
				} catch (ObjectAlreadyExistsException | ObjectNotExistentException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fehler");
					alert.setHeaderText("Lebensmittel bereits vorhanden");
					alert.setContentText("Bitte geben sie ein anderes Medikament ein");
					alert.showAndWait();

				}
				drugListView_description.setEditable(false);
				etController.refresh();
				refreshList();
			}
		});
		//

	}

	private void toggleEditButtons() {
		name.setVisible(!name.isVisible());
		name.setManaged(!name.isManaged());
		save.setManaged(!save.isManaged());
		save.setVisible(!save.isVisible());

		drugListView_name.setVisible(!drugListView_name.isVisible());
		drugListView_name.setManaged(!drugListView_name.isManaged());

	}

	private ETController etController;

	public DrugListViewController(ETController etc) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DrugListView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.etController = etc;
		etController.getDrugListController().addAUI(this);
	}

	public void setDrugList(ArrayList<Drug> drugs) {
		ObservableList<Drug> list = FXCollections.observableArrayList();
		list.addAll(drugs);
		drugListView_drugList.refresh();
		drugListView_drugList.setItems(list);
		drugListView_drugList.getSelectionModel().select(0);
		if (drugListView_drugList.getItems().size() <= 0) {
			drugListView_addHealthProblem.setVisible(false);
			drugListView_deleteHealthProblem.setVisible(false);
		} else {
			drugListView_addHealthProblem.setVisible(true);
			drugListView_deleteHealthProblem.setVisible(true);
		}
	}

	public void setDrugDisplayed(Drug d) {
		if (d != null) {
			drugListView_accordionList.getPanes().clear();
			for (HealthProblem h : d.getHealthProblemList()) {
				drugListView_accordionList.getPanes().add(new HealthProblemContainer(h));
			}
			drugListView_name.setText(d.getName());
			drugListView_description.setText(d.getDescription());
		}

	}

	@FXML // This method is called by the FXMLLoader when initialization is
			// complete
	void initialize() {
		drugListView_drugList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Drug>() {
			@Override
			public void changed(ObservableValue<? extends Drug> observable, Drug oldValue, Drug newValue) {
				setDrugDisplayed(newValue);
			}
		});
		initEditButtons();
		drugListView_description.setEditable(false);
		drugListView_description.setText("");
		drugListView_name.setText("");

	}

	private class HealthProblemContainer extends TitledPane {
		TextArea text;
		AnchorPane root;
		HealthProblem hp;

		@SuppressWarnings("static-access")
		public HealthProblemContainer(HealthProblem hp) {
			super();
			text = new TextArea();
			text.setText(hp.getDescription());
			root = new AnchorPane();
			root.getChildren().add(text);
			root.setBottomAnchor(text, 0.0);
			root.setTopAnchor(text, 0.0);
			root.setLeftAnchor(text, 0.0);
			root.setRightAnchor(text, 0.0);
			text.setMaxWidth(this.getWidth());
			text.setMinWidth(this.getWidth());
			this.setText(hp.getName());
			this.setContent(root);
			this.hp = hp;

		}

		private HealthProblem getHealthProblem() {
			return hp;
		}

	}

	@Override
	public void refreshList() {
		this.setDrugList(etController.getCurrentDiary().getDrugList());
		
	}

	public Drug getSelectedItem() {
		return drugListView_drugList.getSelectionModel().getSelectedItem();
	}

	private class AddHealthProblemDialog extends Dialog<ButtonType> {
		ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
		TextField name;
		TextArea desc;

		@SuppressWarnings("static-access")
		private AddHealthProblemDialog() {
			setTitle("Beschwerde erstellen");
			setHeaderText("Beschwerde eintragen");
			getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
			AnchorPane anchor = new AnchorPane();
			VBox vbox = new VBox();
			name = new TextField();
			desc = new TextArea();
			Label labelName = new Label("Name");
			Label labelDesc = new Label("Beschreibung");
			vbox.getChildren().addAll(labelName, name, labelDesc, desc);
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

		private String getDesc() {
			return desc.getText();
		}
	}

}
