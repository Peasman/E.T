package gui.listview.ingredientlistview;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.ETController;
import controller.IOController;
import controller.IngredientListController;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.HealthProblem;
import model.Ingredient;

public class IngredientListViewController extends AnchorPane implements ListAUI {
	/**
	 * Sample Skeleton for 'ingredientList.fxml' Controller Class
	 */

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="ingredientListView_export"
	private ImageView ingredientListView_export; // Value injected by FXMLLoader

	@FXML // fx:id="ingredientListView_import"
	private ImageView ingredientListView_import; // Value injected by FXMLLoader

	@FXML // fx:id="ingredientListView_delete"
	private ImageView ingredientListView_delete; // Value injected by FXMLLoader

	@FXML // fx:id="ingredientListView_newIngredient"
	private ImageView ingredientListView_newIngredient; // Value injected by
														// FXMLLoader

	@FXML // fx:id="ingredientListView_accordionList"
	private Accordion ingredientListView_accordionList; // Value injected by
														// FXMLLoader
	
	@FXML
    private Button ingedientViewList_change;

	@FXML
	private Label ingredientListView_name;
	@FXML
	private TextArea ingredientListView_information;
	@FXML
	private ListView<Ingredient> ingredientListView_ingList;
	@FXML
	private AnchorPane ingredientListView_nameAnchor;
	@FXML
	private Button ingredientListView_addHP;
	@FXML
	private Button ingredientListView_deleteHP;
	private ETController etController;
	private TextField name;
	private Button confirm;
	private int lastIndex;
	private boolean change = false;

	@FXML
	void deleteHealthProblem_onAction(ActionEvent event) {
		if (ingredientListView_accordionList.getExpandedPane() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Keine Beschwerde ausgewählt.");
			alert.setContentText("Wählen sie eine Beschwerde zum löschen aus!");
			alert.showAndWait();
		} else {
			HealthProblemContainer hpc = (HealthProblemContainer) ingredientListView_accordionList.getExpandedPane();
			HealthProblem healthProblem = hpc.getHealthProblem();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Löschen bestätigen");
			alert.setHeaderText(healthProblem.getName() + " löschen?");
			alert.setContentText("Möchten sie den Eintrag wirklich löschen?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				ingredientListView_ingList.getSelectionModel().getSelectedItem().getHealthProblemList()
						.remove(healthProblem);
			}
			this.refreshList();
		}

	}

	@FXML
	void delete_onAction(ActionEvent event) {
		Ingredient i = ingredientListView_ingList.getSelectionModel().getSelectedItem();
		if (i != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Löschen bestätigen");
			alert.setHeaderText(i.getName() + " löschen?");
			alert.setContentText("Möchten sie den Eintrag wirklich löschen?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					etController.getIngredientListController().deleteIngredient(i);
				} catch (ObjectNotExistentException e) {
					Alert errorHandle = new Alert(AlertType.ERROR);
					errorHandle.setTitle("Error 404");
					errorHandle.setHeaderText("Error beim Inhaltsstoff löschen");
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
			alert.setHeaderText("Kein Inhaltstoff ausgewählt");
			alert.showAndWait();
		}
	}

	@FXML
	void export_onAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Inhaltsstoffe exportieren");
		ExtensionFilter filter = new ExtensionFilter("Tagebücher (*.ingredientList)", "*.ingredientList");
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setInitialFileName(".ingredientList");
		fileChooser.setSelectedExtensionFilter(filter);
		File file = fileChooser.showSaveDialog(getScene().getWindow());

		if (file != null) {
			IOController.saveList(etController.getCurrentDiary().getIngredientList(), file.getPath());
		}
	}

	@FXML
	void import_onAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Inhaltsstoffe importieren");
		ExtensionFilter filter = new ExtensionFilter("Tagebücher (*.ingredientList)", "*.ingredientList");
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setSelectedExtensionFilter(filter);
		File file = fileChooser.showOpenDialog(getScene().getWindow());

		if (file != null) {
			IngredientListController ingredientListController = etController.getIngredientListController();
			ArrayList<Ingredient> ingredientList = (ArrayList<Ingredient>) IOController.loadIngredientList(file);

			for (Ingredient ingredient : ingredientList) {
				try {
					ingredientListController.createIngredient(ingredient.getName(), ingredient.getInformation(),
							ingredient.getHealthProblemList());
				} catch (ObjectAlreadyExistsException e) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Überschreiben");
					alert.setHeaderText(ingredient.getName() + " überschreiben?");
					alert.setContentText("Möchten sie den Eintrag wirklich überschreiben?");
					alert.showAndWait();
					etController.getIngredientListController().modifyIngredient(ingredient.getName(),
							ingredient.getInformation(), ingredient.getHealthProblemList(), ingredient);
				}

			}
		}

	}

	@FXML
	void newHealthProblem_onAction(ActionEvent event) {
		Ingredient ingredient = ingredientListView_ingList.getSelectionModel().getSelectedItem();
		AddHealthProblemDialog dialog = new AddHealthProblemDialog();
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == dialog.getButtonType()) {
			Collection<HealthProblem> healthProblemList = ingredient.getHealthProblemList();
			if (!dialog.getName().equals("")) {
				healthProblemList.add(new HealthProblem(dialog.getName(), dialog.getDesc()));
				this.setIngredientList(etController.getCurrentDiary().getIngredientList());
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
		ingredientListView_nameAnchor.setBottomAnchor(name, 0.0);
		ingredientListView_nameAnchor.setTopAnchor(name, 0.0);
		ingredientListView_nameAnchor.setLeftAnchor(name, 0.0);
		ingredientListView_nameAnchor.setRightAnchor(name, 100.0);
		ingredientListView_nameAnchor.setRightAnchor(confirm, 0.0);
		ingredientListView_nameAnchor.setBottomAnchor(confirm, 0.0);
		ingredientListView_nameAnchor.setTopAnchor(confirm, 0.0);
		name.setFont(new Font(20));
		ingredientListView_information.setText("");
		name.setPromptText("Namen eingeben");
		ingredientListView_information.setPromptText("Informationen hier eingeben");
		ingredientListView_accordionList.getPanes().clear();
		ingredientListView_information.setEditable(true);

	}
	
	@SuppressWarnings("static-access")
	@FXML
	void changeIngredient_onAction(ActionEvent event) {
		toggleEditButtons();
		ingredientListView_nameAnchor.setBottomAnchor(name, 0.0);
		ingredientListView_nameAnchor.setTopAnchor(name, 0.0);
		ingredientListView_nameAnchor.setLeftAnchor(name, 0.0);
		ingredientListView_nameAnchor.setRightAnchor(name, 100.0);
		ingredientListView_nameAnchor.setRightAnchor(confirm, 0.0);
		ingredientListView_nameAnchor.setBottomAnchor(confirm, 0.0);
		ingredientListView_nameAnchor.setTopAnchor(confirm, 0.0);
		name.setText(ingredientListView_ingList.getSelectionModel().getSelectedItem().getName());;
		name.setFont(new Font(20));
		ingredientListView_information.setText(ingredientListView_ingList.getSelectionModel().getSelectedItem().getInformation());
		ingredientListView_information.setEditable(true);
		change = true;
		
	}

	public IngredientListViewController(ETController etc) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IngredientListView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.etController = etc;
		lastIndex = 0;
		initEditButtons();
		setIngredientList(etController.getCurrentDiary().getIngredientList());
		etController.getIngredientListController().addAUI(this);
		ingredientListView_nameAnchor.getChildren().add(name);

	}

	private void initEditButtons() {
		name = new TextField();
		name.setManaged(false);
		name.setVisible(false);
		confirm = new Button("bestätigen");
		ingredientListView_nameAnchor.getChildren().add(confirm);
		confirm.setManaged(false);
		confirm.setVisible(false);

		confirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				ArrayList<HealthProblem> healthProblems = new ArrayList<HealthProblem>();
				for (TitledPane titledPane : ingredientListView_accordionList.getPanes()) {
					HealthProblemContainer hpc = (HealthProblemContainer) titledPane;
					healthProblems.add(hpc.getHealthProblem());
				}
				try {
					toggleEditButtons();
					if (name.getText().equals("")) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Fehler");
						alert.setHeaderText("Kein Name eingegeben");
						alert.setContentText("Geben sie einen Namen ein!");
						alert.showAndWait();
					} else {
						if(change== true){
							etController.getIngredientListController().modifyIngredient(name.getText(),
									ingredientListView_information.getText(), healthProblems, ingredientListView_ingList.getSelectionModel().getSelectedItem());
							change = false;
							refreshList();
						}else{
						etController.getIngredientListController().createIngredient(name.getText(),
								ingredientListView_information.getText(), healthProblems);
						}
						ingredientListView_ingList.getSelectionModel().selectLast();
						ingredientListView_information.setEditable(false);
						etController.refresh();
					}
				} catch (ObjectAlreadyExistsException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fehler");
					alert.setHeaderText("Inhaltsstoff bereits vorhanden");
					alert.setContentText("Bitte geben sie einen anderen Inhaltsstoff ein");
					alert.showAndWait();

				}
			}
		});

	}

	private void toggleEditButtons() {
		name.setVisible(!name.isVisible());
		name.setManaged(!name.isManaged());
		confirm.setVisible(!confirm.isVisible());
		confirm.setManaged(!confirm.isManaged());
		ingredientListView_name.setVisible(!ingredientListView_name.isVisible());
		ingredientListView_name.setManaged(!ingredientListView_name.isManaged());
		ingredientListView_addHP.setVisible(!ingredientListView_addHP.isVisible());
		ingredientListView_addHP.setManaged(!ingredientListView_addHP.isManaged());
		ingredientListView_deleteHP.setVisible(!ingredientListView_deleteHP.isVisible());
		ingredientListView_deleteHP.setManaged(!ingredientListView_deleteHP.isManaged());
	}

	public void setIngredientList(Collection<Ingredient> ings) {
		ObservableList<Ingredient> list = FXCollections.observableArrayList();
		list.addAll(ings);
		
		ingredientListView_ingList.setItems(list);
		ingredientListView_ingList.refresh();
		if (lastIndex < ingredientListView_ingList.getItems().size()) {
			ingredientListView_ingList.getSelectionModel().select(lastIndex);
		} else {
			ingredientListView_ingList.getSelectionModel().select(0);
		}
		if (ingredientListView_ingList.getItems().size() <= 0) {
			ingredientListView_addHP.setVisible(false);
			ingredientListView_deleteHP.setVisible(false);
		} else {
			ingredientListView_addHP.setVisible(true);
			ingredientListView_deleteHP.setVisible(true);
		}
	}

	public void setIngredientDisplayed(Ingredient i) {
		if (i != null) {
			lastIndex = ingredientListView_ingList.getSelectionModel().getSelectedIndex();
			ingredientListView_accordionList.getPanes().clear();
			for (HealthProblem h : i.getHealthProblemList()) {
				ingredientListView_accordionList.getPanes().add(new HealthProblemContainer(h));
			}
			ingredientListView_name.setText(i.getName());
			ingredientListView_information.setText(i.getInformation());
			if (name.isManaged()) {
				name.setManaged(false);
				name.setVisible(false);
			}
			if (confirm.isManaged()) {
				confirm.setManaged(false);
				confirm.setVisible(false);
			}
		}

	}

	public Ingredient getSelectedItem() {
		return ingredientListView_ingList.getSelectionModel().getSelectedItem();
	}

	@FXML // This method is called by the FXMLLoader when initialization is
			// complete
	void initialize() {
		ingredientListView_ingList.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<Ingredient>()

		{
					@Override
					public void changed(ObservableValue<? extends Ingredient> observable, Ingredient oldValue,
							Ingredient newValue) {
						setIngredientDisplayed(newValue);
					}
				});
		ingredientListView_information.setEditable(true);
		ingredientListView_name.setText("");
		ingredientListView_information.setText("");
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

	private class HealthProblemContainer extends TitledPane {
		TextArea text;
		AnchorPane root;
		HealthProblem hp;

		@SuppressWarnings("static-access")
		public HealthProblemContainer(HealthProblem hp) {
			super();
			text = new TextArea();
			text.setText(hp.getDescription());
			text.setEditable(false);
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
		this.setIngredientList(etController.getCurrentDiary().getIngredientList());
	}
	
}
