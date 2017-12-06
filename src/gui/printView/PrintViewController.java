package gui.printView;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.DiaryEntry;

public class PrintViewController extends AnchorPane {

	private ArrayList<DiaryEntry> entryList;
	private String diaryName;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane printView_root;
	
	@FXML
    private Label printView_label;

	@FXML
	private TableView<DiaryEntry> printView_table;

	@FXML
	private TableColumn<DiaryEntry, String> printView_table_date;

	@FXML
	private TableColumn<DiaryEntry, String> printView_table_time;

	@FXML
	private TableColumn<DiaryEntry, String> printView_table_amount;

	@FXML
	private TableColumn<DiaryEntry, String> printView_table_name;

	@FXML
	private TableColumn<DiaryEntry, String> printView_table_comment;

	@FXML
	void initialize() {
		assert printView_root != null : "fx:id=\"printView_root\" was not injected: check your FXML file 'printView.fxml'.";
		assert printView_label != null : "fx:id=\"printView_label\" was not injected: check your FXML file 'printView.fxml'.";
		assert printView_table != null : "fx:id=\"printView_table\" was not injected: check your FXML file 'printView.fxml'.";
		assert printView_table_date != null : "fx:id=\"printView_table_date\" was not injected: check your FXML file 'printView.fxml'.";
		assert printView_table_time != null : "fx:id=\"printView_table_time\" was not injected: check your FXML file 'printView.fxml'.";
		assert printView_table_amount != null : "fx:id=\"printView_table_amount\" was not injected: check your FXML file 'printView.fxml'.";
		assert printView_table_name != null : "fx:id=\"printView_table_name\" was not injected: check your FXML file 'printView.fxml'.";
		assert printView_table_comment != null : "fx:id=\"printView_table_comment\" was not injected: check your FXML file 'printView.fxml'.";
		printView_label.setText("Ernährungstagebuch: "+diaryName);
		printView_table_date.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTime().format(DateTimeFormatter.ofPattern("d MMM uuuu"))));
		printView_table_time.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTime().format(DateTimeFormatter.ofPattern("H:mm"))));
		printView_table_amount.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getAmount() + " " + cell.getValue().getUnit()));
		printView_table_name.setCellFactory((arg0) -> {
			return new TableCell<DiaryEntry, String>() {
                private Text text;
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!isEmpty()) {
                        text = new Text(item.toString());
                        text.setWrappingWidth(printView_table_name.getWidth()-5.0);
                        this.setWrapText(true);
                        setGraphic(text);
                    }}};});
		printView_table_name.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getName()));
		printView_table_comment.setCellFactory((arg0) -> {
			return new TableCell<DiaryEntry, String>() {
                private Text text;
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!isEmpty()) {
                        text = new Text(item.toString());
                        text.setWrappingWidth(printView_table_comment.getWidth()-5.0);
                        this.setWrapText(true);
                        setGraphic(text);
                }}};});
		printView_table_comment.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getComment()));
		printView_table.setItems(FXCollections.observableArrayList(entryList));
	}
	
	public PrintViewController(ArrayList<DiaryEntry> entries, String name) {
		entryList = entries;
		diaryName = name;
		entryList.sort(new Comparator<DiaryEntry>() {
			@Override
			public int compare(DiaryEntry o1, DiaryEntry o2) {
				return o1.getTime().compareTo(o2.getTime());
			}
			
		});
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("printView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void scaleForPage(PageLayout layout) {
		double height = layout.getPrintableHeight();
    	double width = layout.getPrintableWidth();
    	height -= layout.getTopMargin();
    	height -= layout.getBottomMargin();
    	width -= layout.getLeftMargin();
    	width -= layout.getRightMargin();
		this.setMaxSize(width, height);
		this.setPrefSize(width, height);
		this.setMinSize(width, height);
		int newFontSize = (int)(Math.round(width/100.0)*100)/60;
		double newDateSize = newFontSize * 7.5;
		double newTimeSize = newFontSize * 4;
		double newAmtSize = newFontSize * 4.5;
		double newNameSize = (width-newDateSize-newTimeSize-newAmtSize)*0.30;
		double newCommSize = (width-newDateSize-newTimeSize-newAmtSize)*0.70;
		printView_table.setStyle("-fx-font-size: "+newFontSize);
		printView_table_date.setPrefWidth(newDateSize);
		printView_table_time.setPrefWidth(newTimeSize);
		printView_table_amount.setPrefWidth(newAmtSize);
		printView_table_name.setPrefWidth(newNameSize);
		printView_table_comment.setPrefWidth(newCommSize-2.0);
		printView_table.getSelectionModel().select(null);
	}

}
