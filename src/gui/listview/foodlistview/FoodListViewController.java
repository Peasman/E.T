package gui.listview.foodlistview;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Sample Skeleton for 'foodListView.fxml' Controller Class
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import controller.ETController;
import controller.FoodListController;
import controller.IOController;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistentException;
import gui.ListAUI;
import gui.listview.ingredientlistview.IngredientListViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Food;
import model.HealthProblem;
import model.Ingredient;

public class FoodListViewController extends AnchorPane implements ListAUI {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="ingredientListView_export"
	private Button foodListView_export; // Value injected by FXMLLoader

	@FXML // fx:id="ingredientListView_import"
	private Button foodListView_import; // Value injected by FXMLLoader

	@FXML // fx:id="ingredientListView_delete"
	private Button foodListView_delete; // Value injected by FXMLLoader

	@FXML // fx:id="ingredientListView_newIngredient"
	private Button foodListView_newIngredient; // Value injected by
												// FXMLLoader

	@FXML // fx:id="foodListView_foodList"
	private ListView<Food> foodListView_foodList; // Value injected by
													// FXMLLoader

	@FXML // fx:id="foodListView_name"
	private Label foodListView_name; // Value injected by FXMLLoader

	@FXML // fx:id="foodListView_addImage"
	private Button foodListView_addImage; // Value injected by FXMLLoader

	@FXML // fx:id="foodListView_innerFoods"
	private ListView<Food> foodListView_innerFoods; // Value injected by
													// FXMLLoader

	@FXML // fx:id="foodListView_addFood"
	private Button foodListView_addFood; // Value injected by FXMLLoader

	@FXML // fx:id="foodListView_ingredients"
	private ListView<Ingredient> foodListView_ingredients; // Value injected by
	// FXMLLoader

	@FXML // fx:id="foodListView_healthProblems"
	private ListView<HealthProblem> foodListView_healthProblems; // Value
																	// injected
																	// by
	@FXML
	private Button foodListView_deleteImage;
	// FXMLLoader
	@FXML
	private Button foodListView_addInnerFood;

	@FXML
	private Button foodListView_deleteInnerFood;
	@FXML
	private ImageView foodListView_image;

	@FXML
	private AnchorPane foodListView_topAnchor;
	private TextField name;
	private Button confirm;
	private Food invisibleFood;

	@FXML
	void delete_onAction(ActionEvent event) {
		Food f = foodListView_foodList.getSelectionModel().getSelectedItem();
		if (f != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Löschen bestätigen");
			alert.setHeaderText(f.getName() + " löschen?");
			alert.setContentText("Möchten sie den Eintrag wirklich löschen?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				try {
					etController.getFoodListController().deleteFood(f);
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
			alert.setHeaderText("Kein Lebensmittel ausgewählt");
			alert.showAndWait();
		}
	}

	@FXML
	void export_onAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Nahrungsmittel exportieren");
		ExtensionFilter filter = new ExtensionFilter("Tagebücher (*.foodList)", "*.foodList");
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setInitialFileName(".foodList");
		fileChooser.setSelectedExtensionFilter(filter);
		File file = fileChooser.showSaveDialog(getScene().getWindow());

		if (file != null) {
			IOController.saveList(etController.getCurrentDiary().getFoodList(), file.getPath());
		}
	}

	@FXML
	void import_onAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Nahrungsmittel importieren");
		ExtensionFilter filter = new ExtensionFilter("Tagebücher (*.foodList)", "*.foodList");
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setSelectedExtensionFilter(filter);
		File file = fileChooser.showOpenDialog(getScene().getWindow());

		if (file != null) {
			FoodListController foodListController = etController.getFoodListController();
			ArrayList<Food> foodList = (ArrayList<Food>) IOController.loadFoodList(file);
			for (Food food : foodList) {
				try {
					foodListController.createFood(food.getName(), food.getIngredientPicturePath(),
							food.getIngredientsOfFoodList(), food.getIngredients());
				} catch (ObjectAlreadyExistsException e) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Überschreiben");
					alert.setHeaderText(food.getName() + " überschreiben?");
					alert.setContentText("Möchten sie den Eintrag wirklich überschreiben?");
					alert.showAndWait();
					etController.getFoodListController().modifyFood(food, food.getName(), food.getIngredientPicturePath(),
							food.getIngredientsOfFoodList(), food.getIngredients());
				}

			}
		}
	}

	@FXML
	void addImage_onAction(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Nahrungsmittel importieren");
		ExtensionFilter filter = new ExtensionFilter("Bilder (*.jpg, *.png, *.jpeg)", "*.jpg");
		fileChooser.getExtensionFilters().add(filter);
		fileChooser.setSelectedExtensionFilter(filter);
		File file = fileChooser.showOpenDialog(getScene().getWindow());

		if (file != null) {
			Food f = foodListView_foodList.getSelectionModel().getSelectedItem();
			BufferedImage bufferedImage = null;
			try {
				bufferedImage = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			foodListView_image.setImage(image);
			foodListView_image.setManaged(true);
			foodListView_image.setVisible(true);
			etController.getFoodListController().modifyFood(f, f.getName(), file.getPath(), f.getIngredientsOfFoodList(),
					f.getIngredients());
		}
	}

	@FXML
	void deleteImage_OnAction(ActionEvent event) {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Löschen bestätigen");
		dialog.setHeaderText("Löschen des Bildes");
		dialog.setContentText("Möchten sie das Bild wirklich löschen?");
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == ButtonType.OK) {
			Food f = foodListView_foodList.getSelectionModel().getSelectedItem();
			foodListView_image.setManaged(false);
			foodListView_image.setVisible(false);
			foodListView_image.setImage(null);
			etController.getFoodListController().modifyFood(f, f.getName(), null, f.getIngredientsOfFoodList(),
					f.getIngredients());

		}

	}

	@FXML
	void deleteInnerFood_onAction(ActionEvent event) {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Löschen bestätigen");
		dialog.setHeaderText("Löschen der Zutat");
		dialog.setContentText("Möchten sie die Zutat wirklich löschen?");
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == ButtonType.OK) {
			Food f = foodListView_foodList.getSelectionModel().getSelectedItem();
			Food oldFood = foodListView_innerFoods.getSelectionModel().getSelectedItem();
			Collection<Food> innerFoods = f.getIngredientsOfFoodList();
			innerFoods.remove(oldFood);
			etController.getFoodListController().modifyFood(f, f.getName(), null, innerFoods, f.getIngredients());
		}
	}

	@FXML
	void addInnerFood_onAction(ActionEvent event) {
		SelectFoodDialog dialog = new SelectFoodDialog(foodListView_foodList.getSelectionModel().getSelectedItem());
		dialog.setTitle("Lebensmittel auswählen");
		dialog.setHeaderText("Bitte wählen sie das Lebensmittel aus, welches sie hinzufügen wollen");
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == ButtonType.OK && dialog.getEntry() != null) {
			Food newFood = dialog.getEntry();
			Food food = foodListView_foodList.getSelectionModel().getSelectedItem();
			Collection<Food> ings = food.getIngredientsOfFoodList();
			ings.add(newFood);
			etController.getFoodListController().modifyFood(food, food.getName(), food.getIngredientPicturePath(), ings,
					food.getIngredients());
		}
	}

	@FXML
	void addInnerIngredient_onAction(ActionEvent event) {
		SelectIngredientDialog dialog = new SelectIngredientDialog(
				foodListView_foodList.getSelectionModel().getSelectedItem());
		dialog.setTitle("Inhaltsstoff auswählen");
		dialog.setHeaderText("Bitte wählen sie den Inhaltsstoff aus, welchen sie hinzufügen wollen");
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == ButtonType.OK) {
			Ingredient newIng = dialog.getEntry();
			Food food = foodListView_foodList.getSelectionModel().getSelectedItem();
			Collection<Ingredient> ings = food.getIngredients();
			ings.add(newIng);
			etController.getFoodListController().modifyFood(food, food.getName(), food.getIngredientPicturePath(),
					food.getIngredientsOfFoodList(), ings);
		}
	}

	@FXML
	void deleteInnerIngredient_onAction(ActionEvent event) {
		Alert dialog = new Alert(AlertType.CONFIRMATION);
		dialog.setTitle("Löschen bestätigen");
		dialog.setHeaderText("Löschen des Inhaltstoffes");
		dialog.setContentText("Möchten sie den Inhaltsstoff wirklich löschen?");
		Optional<ButtonType> result = dialog.showAndWait();
		if (result.get() == ButtonType.OK) {
			Ingredient oldIngre = foodListView_ingredients.getSelectionModel().getSelectedItem();
			Food foodToEdit = foodListView_foodList.getSelectionModel().getSelectedItem();
			Collection<Ingredient> innerIng = foodToEdit.getIngredients();
			innerIng.remove(oldIngre);
			etController.getFoodListController().modifyFood(foodToEdit, foodToEdit.getName(), null,
					foodToEdit.getIngredientsOfFoodList(), innerIng);
		}
	}

	@SuppressWarnings("static-access")
	@FXML
	void new_onAction(ActionEvent event) {
		toggleEditButtons();
		name.setText("");
		foodListView_topAnchor.setBottomAnchor(name, 0.0);
		foodListView_topAnchor.setTopAnchor(name, 0.0);
		foodListView_topAnchor.setLeftAnchor(name, 0.0);
		foodListView_topAnchor.setRightAnchor(name, 100.0);
		foodListView_topAnchor.setRightAnchor(confirm, 0.0);
		foodListView_topAnchor.setBottomAnchor(confirm, 0.0);
		foodListView_topAnchor.setTopAnchor(confirm, 0.0);
		name.setFont(new Font(20));
		foodListView_name.setText("");
		foodListView_innerFoods.getItems().clear();
		foodListView_ingredients.getItems().clear();
		foodListView_healthProblems.getItems().clear();

	}

	private void initEditButtons() {
		name = new TextField();
		name.setManaged(false);
		name.setVisible(false);
		name.setPromptText("Name eingeben");
		confirm = new Button("bestätigen");
		foodListView_topAnchor.getChildren().add(confirm);
		confirm.setManaged(false);
		confirm.setVisible(false);
		foodListView_topAnchor.getChildren().add(name);
		//
		confirm.setOnAction(new EventHandler<ActionEvent>() {
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
						etController.getFoodListController().createFood(name.getText(), null, new ArrayList<Food>(),
								new ArrayList<Ingredient>());
					}
					foodListView_foodList.getSelectionModel().selectLast();
				} catch (ObjectAlreadyExistsException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Fehler");
					alert.setHeaderText("Lebensmittel bereits vorhanden");
					alert.setContentText("Bitte geben sie einen anderen Lebensmittel ein");
					alert.showAndWait();
				}
			}
		});

	}

	private ETController etController;

	public FoodListViewController(ETController etc) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FoodListView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.etController = etc;
		setFoodList(etController.getCurrentDiary().getFoodList());
		foodListView_foodList.getSelectionModel().select(0);
		etController.getFoodListController().addAUI(this);
	}

	public void setFoodList(Collection<Food> foods) {
		ObservableList<Food> list = FXCollections.observableArrayList();
		list.addAll(foods);
		foodListView_foodList.setItems(list);
	}

	public void setFoodDisplayed(Food f) {
		if (f != null) {
			if (f.getIngredientPicturePath() == null) {
				foodListView_image.setManaged(false);
				foodListView_image.setVisible(false);
				foodListView_deleteImage.setManaged(false);
				foodListView_deleteImage.setVisible(false);

			} else {
				BufferedImage bufferedImage = null;
				try {
					bufferedImage = ImageIO.read(new File(f.getIngredientPicturePath()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				foodListView_image.setImage(image);
				foodListView_image.setManaged(true);
				foodListView_image.setVisible(true);

				foodListView_deleteImage.setManaged(true);
				foodListView_deleteImage.setVisible(true);

			}
			ObservableList<Food> foods = FXCollections.observableArrayList();
			foods.addAll(f.getIngredientsOfFoodList());
			foodListView_innerFoods.setItems(foods);
			ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
			HashSet<Ingredient> ings = FoodListController.extractIngredients(10,f);
			ingredients.addAll(ings);
			foodListView_ingredients.setItems(ingredients);
			ObservableList<HealthProblem> healthProblems = FXCollections.observableArrayList();
			healthProblems.addAll(FoodListController.extractHealthProblems(ings));
			foodListView_healthProblems.setItems(healthProblems);
			foodListView_name.setText(f.getName());
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is
			// complete
	void initialize() {

		foodListView_foodList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Food>() {
			@Override
			public void changed(ObservableValue<? extends Food> observable, Food oldValue, Food newValue) {
				setFoodDisplayed(newValue);
			}
		});
		initEditButtons();
		foodListView_name.setText("");

	}

	public Food getSelectedItem() {
		return foodListView_foodList.getSelectionModel().getSelectedItem();
	}

	public ListView<Food> getFoodList() {
		return this.foodListView_foodList;
	}

	private void toggleEditButtons() {
		name.setVisible(!name.isVisible());
		name.setManaged(!name.isManaged());
		confirm.setVisible(!confirm.isVisible());
		confirm.setManaged(!confirm.isManaged());
		foodListView_name.setVisible(!foodListView_name.isVisible());
		foodListView_name.setManaged(!foodListView_name.isManaged());
		foodListView_addInnerFood.setVisible(!foodListView_addInnerFood.isVisible());
		foodListView_addInnerFood.setManaged(!foodListView_addInnerFood.isManaged());
		foodListView_deleteInnerFood.setVisible(!foodListView_deleteInnerFood.isVisible());
		foodListView_deleteInnerFood.setManaged(!foodListView_deleteInnerFood.isManaged());
	}

	@Override
	public void refreshList() {
		setFoodList(etController.getCurrentDiary().getFoodList());
		if (invisibleFood != null) {
			this.foodListView_foodList.getItems().remove(invisibleFood);
		}
	}

	public void setInvisibleFood(Food f) {
		this.invisibleFood = f;
	}

	private class SelectFoodDialog extends Dialog<ButtonType> {
		FoodListViewController foodListViewController;

		private SelectFoodDialog(Food f) {
			setTitle("Lebensmittel auswählen");
			// setHeaderText("Medikament auswählen");
			getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
			foodListViewController = new FoodListViewController(etController);
			getDialogPane().setContent(foodListViewController);
			foodListViewController.getFoodList().getItems().remove(f);
			foodListViewController.setInvisibleFood(f);
		}

		private SelectFoodDialog() {
			setTitle("Lebensmittel auswählen");
			// setHeaderText("Medikament auswählen");
			getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
			foodListViewController = new FoodListViewController(etController);
			getDialogPane().setContent(foodListViewController);
		}

		public Food getEntry() {
			return foodListViewController.getSelectedItem();
		}
	}

	private class SelectIngredientDialog extends Dialog<ButtonType> {
		IngredientListViewController ingredientListViewController;

		private SelectIngredientDialog(Food f) {
			setTitle("Inhaltsstoff auswählen");
			// setHeaderText("Medikament auswählen");
			getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
			ingredientListViewController = new IngredientListViewController(etController);
			getDialogPane().setContent(ingredientListViewController);
		}

		private SelectIngredientDialog() {
			setTitle("Inhaltsstoff auswählen");
			// setHeaderText("Medikament auswählen");
			getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
			ingredientListViewController = new IngredientListViewController(etController);
			getDialogPane().setContent(ingredientListViewController);
		}

		public Ingredient getEntry() {
			return ingredientListViewController.getSelectedItem();
		}
	}
}
