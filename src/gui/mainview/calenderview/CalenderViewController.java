package gui.mainview.calenderview;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import controller.ETController;
import controller.EntryListController;
import gui.EntryListAUI;
import gui.mainview.MainViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CalenderViewController extends AnchorPane implements EntryListAUI {

	@FXML
	private Button calenderViewWeek_ButMon;

	@FXML
	private Button calenderViewWeek_ButTue;

	@FXML
	private Button calenderViewWeek_ButWed;

	@FXML
	private Button calenderViewWeek_ButThu;

	@FXML
	private Button calenderViewWeek_ButFri;

	@FXML
	private Button calenderViewWeek_ButSat;

	@FXML
	private Button calenderViewWeek_ButSun;

	@FXML
	private Rectangle calenderViewWeek_FoodInd1;

	@FXML
	private Rectangle calenderViewWeek_MedInd1;

	@FXML
	private Rectangle calenderViewWeek_ProbInd1;

	@FXML
	private Rectangle calenderViewWeek_FoodInd2;

	@FXML
	private Rectangle calenderViewWeek_MedInd2;

	@FXML
	private Rectangle calenderViewWeek_ProbInd2;

	@FXML
	private Rectangle calenderViewWeek_FoodInd3;

	@FXML
	private Rectangle calenderViewWeek_MedInd3;

	@FXML
	private Rectangle calenderViewWeek_ProbInd3;

	@FXML
	private Rectangle calenderViewWeek_FoodInd4;

	@FXML
	private Rectangle calenderViewWeek_MedInd4;

	@FXML
	private Rectangle calenderViewWeek_ProbInd4;

	@FXML
	private Rectangle calenderViewWeek_FoodInd5;

	@FXML
	private Rectangle calenderViewWeek_MedInd5;

	@FXML
	private Rectangle calenderViewWeek_ProbInd5;

	@FXML
	private Rectangle calenderViewWeek_FoodInd6;

	@FXML
	private Rectangle calenderViewWeek_MedInd6;

	@FXML
	private Rectangle calenderViewWeek_ProbInd6;

	@FXML
	private Rectangle calenderViewWeek_FoodInd7;

	@FXML
	private Rectangle calenderViewWeek_MedInd7;

	@FXML
	private Rectangle calenderViewWeek_ProbInd7;

	@FXML
	private DatePicker calenderView_DatePicker;

	@FXML
	private AnchorPane calenderView_monthAnchor;

	private ETController etController;
	private EntryListController elc;
	private MainViewController mvc;
	private LocalDate firstOfMonth, localDate;
	private DayOfWeek actDay;

	@FXML
	private GridPane calenderView_grid;

	public CalenderViewController(ETController etc, MainViewController mvc) {
		this.mvc = mvc;
		this.etController = etc;
		elc = etc.getEntryListController();

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Kalender.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}

		elc.addAUI(this);
		calenderView_DatePicker.setValue(LocalDate.now());
		localDate = calenderView_DatePicker.getValue();
		actDay = calenderView_DatePicker.getValue().getDayOfWeek();
		firstOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth());
		fillCalender();
	}

	@FXML
	void calenderViewWeek_ButFriGo(ActionEvent event) {
	}

	@FXML
	void calenderViewWeek_ButMonGo(ActionEvent event) {
	}

	@FXML
	void calenderViewWeek_ButSatGo(ActionEvent event) {
	}

	@FXML
	void calenderViewWeek_ButSunGo(ActionEvent event) {
	}

	@FXML
	void calenderViewWeek_ButThuGo(ActionEvent event) {
	}

	@FXML
	void calenderViewWeek_ButTueGo(ActionEvent event) {
	}

	@FXML
	void calenderViewWeek_ButWedGo(ActionEvent event) {
	}

	@SuppressWarnings("static-access")
	private void fillCalender() {
		int start = 0;
		String dayName = "";
		int month = localDate.getMonthValue();
		int year = localDate.getYear();
		int days = localDate.lengthOfMonth();
		DayOfWeek fDay = firstOfMonth.getDayOfWeek();
		localDate.format(DateTimeFormatter.ofPattern("d.M"));
		switch (actDay) {
		case MONDAY: {
			calenderViewWeek_ButMon.setText("Montag " + localDate.format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButMon.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate);
					etController.refresh();
				}
			});
			calenderViewWeek_ButTue.setText("Dienstag " + localDate.plusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButTue.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(1));
					etController.refresh();
				}
			});
			calenderViewWeek_ButWed.setText("Mittwoch " + localDate.plusDays(2).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButWed.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(2));
					etController.refresh();
				}
			});
			calenderViewWeek_ButThu.setText("Donnerstag " + localDate.plusDays(3).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButThu.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(3));
					etController.refresh();
				}
			});
			calenderViewWeek_ButFri.setText("Freitag " + localDate.plusDays(4).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButFri.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(4));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSat.setText("Samstag " + localDate.plusDays(5).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSat.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(5));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSun.setText("Sonntag " + localDate.plusDays(6).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSun.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(5));
					etController.refresh();
				}
			});
			setFalse();
			if (elc.existsDrugEntry(localDate)) {
				calenderViewWeek_MedInd1.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate)) {
				calenderViewWeek_FoodInd1.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate)) {
				calenderViewWeek_ProbInd1.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(1))) {
				calenderViewWeek_MedInd2.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(1))) {
				calenderViewWeek_FoodInd2.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(1))) {
				calenderViewWeek_ProbInd2.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(2))) {
				calenderViewWeek_MedInd3.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(2))) {
				calenderViewWeek_FoodInd3.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(2))) {
				calenderViewWeek_ProbInd3.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(3))) {
				calenderViewWeek_MedInd4.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(3))) {
				calenderViewWeek_FoodInd4.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(3))) {
				calenderViewWeek_ProbInd4.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(4))) {
				calenderViewWeek_MedInd5.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(4))) {
				calenderViewWeek_FoodInd5.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(4))) {
				calenderViewWeek_ProbInd5.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(5))) {
				calenderViewWeek_MedInd6.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(5))) {
				calenderViewWeek_FoodInd6.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(5))) {
				calenderViewWeek_ProbInd6.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(6))) {
				calenderViewWeek_MedInd7.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(6))) {
				calenderViewWeek_FoodInd7.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(6))) {
				calenderViewWeek_ProbInd7.setVisible(true);
			}
			break;
		}
		case TUESDAY: {
			calenderViewWeek_ButMon.setText("Montag " + localDate.minusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButMon.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(1));
					etController.refresh();
				}
			});
			calenderViewWeek_ButTue.setText("Dienstag " + localDate.format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButTue.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate);
					etController.refresh();
				}
			});
			calenderViewWeek_ButWed.setText("Mittwoch " + localDate.plusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButWed.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(1));
					etController.refresh();
				}
			});
			calenderViewWeek_ButThu.setText("Donnerstag " + localDate.plusDays(2).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButThu.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(2));
					etController.refresh();
				}
			});
			calenderViewWeek_ButFri.setText("Freitag " + localDate.plusDays(3).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButFri.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(3));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSat.setText("Samstag " + localDate.plusDays(4).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSat.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(4));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSun.setText("Sonntag " + localDate.plusDays(5).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSun.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(5));
					etController.refresh();
				}
			});
			setFalse();
			if (elc.existsDrugEntry(localDate.minusDays(1))) {
				calenderViewWeek_MedInd1.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(1))) {
				calenderViewWeek_FoodInd1.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(1))) {
				calenderViewWeek_ProbInd1.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate)) {
				calenderViewWeek_MedInd2.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate)) {
				calenderViewWeek_FoodInd2.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate)) {
				calenderViewWeek_ProbInd2.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(1))) {
				calenderViewWeek_MedInd3.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(1))) {
				calenderViewWeek_FoodInd3.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(1))) {
				calenderViewWeek_ProbInd3.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(2))) {
				calenderViewWeek_MedInd4.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(2))) {
				calenderViewWeek_FoodInd4.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(2))) {
				calenderViewWeek_ProbInd4.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(3))) {
				calenderViewWeek_MedInd5.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(3))) {
				calenderViewWeek_FoodInd5.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(3))) {
				calenderViewWeek_ProbInd5.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(4))) {
				calenderViewWeek_MedInd6.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(4))) {
				calenderViewWeek_FoodInd6.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(4))) {
				calenderViewWeek_ProbInd6.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(5))) {
				calenderViewWeek_MedInd7.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(5))) {
				calenderViewWeek_FoodInd7.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(5))) {
				calenderViewWeek_ProbInd7.setVisible(true);
			}
			break;
		}
		case WEDNESDAY: {
			calenderViewWeek_ButMon.setText("Montag " + localDate.minusDays(2).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButMon.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(2));
					etController.refresh();
				}
			});
			calenderViewWeek_ButTue.setText("Dienstag " + localDate.minusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButTue.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(1));
					etController.refresh();
				}
			});
			calenderViewWeek_ButWed.setText("Mittwoch " + localDate.format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButWed.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate);
					etController.refresh();
				}
			});
			calenderViewWeek_ButThu.setText("Donnerstag " + localDate.plusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButThu.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(1));
					etController.refresh();
				}
			});
			calenderViewWeek_ButFri.setText("Freitag " + localDate.plusDays(2).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButFri.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(2));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSat.setText("Samstag " + localDate.plusDays(3).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSat.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(3));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSun.setText("Sonntag " + localDate.plusDays(4).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSun.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(4));
					etController.refresh();
				}
			});
			setFalse();
			if (elc.existsDrugEntry(localDate.minusDays(2))) {
				calenderViewWeek_MedInd1.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(2))) {
				calenderViewWeek_FoodInd1.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(2))) {
				calenderViewWeek_ProbInd1.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(1))) {
				calenderViewWeek_MedInd2.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(1))) {
				calenderViewWeek_FoodInd2.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(1))) {
				calenderViewWeek_ProbInd2.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate)) {
				calenderViewWeek_MedInd3.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate)) {
				calenderViewWeek_FoodInd3.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate)) {
				calenderViewWeek_ProbInd3.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(1))) {
				calenderViewWeek_MedInd4.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(1))) {
				calenderViewWeek_FoodInd4.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(1))) {
				calenderViewWeek_ProbInd4.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(2))) {
				calenderViewWeek_MedInd5.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(2))) {
				calenderViewWeek_FoodInd5.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(2))) {
				calenderViewWeek_ProbInd5.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(3))) {
				calenderViewWeek_MedInd6.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(3))) {
				calenderViewWeek_FoodInd6.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(3))) {
				calenderViewWeek_ProbInd6.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(4))) {
				calenderViewWeek_MedInd7.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(4))) {
				calenderViewWeek_FoodInd7.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(4))) {
				calenderViewWeek_ProbInd7.setVisible(true);
			}
			break;
		}
		case THURSDAY: {
			calenderViewWeek_ButMon.setText("Montag " + localDate.minusDays(3).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButMon.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(3));
					etController.refresh();
				}
			});
			calenderViewWeek_ButTue.setText("Dienstag " + localDate.minusDays(2).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButTue.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(2));
					etController.refresh();
				}
			});
			calenderViewWeek_ButWed.setText("Mittwoch " + localDate.minusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButWed.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(1));
					etController.refresh();
				}
			});
			calenderViewWeek_ButThu.setText("Donnerstag " + localDate.format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButThu.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate);
					etController.refresh();
				}
			});
			calenderViewWeek_ButFri.setText("Freitag " + localDate.plusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButFri.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(1));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSat.setText("Samstag " + localDate.plusDays(2).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSat.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(2));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSun.setText("Sonntag " + localDate.plusDays(3).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSun.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(3));
					etController.refresh();
				}
			});
			setFalse();
			if (elc.existsDrugEntry(localDate.minusDays(3))) {
				calenderViewWeek_MedInd1.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(3))) {
				calenderViewWeek_FoodInd1.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(3))) {
				calenderViewWeek_ProbInd1.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(2))) {
				calenderViewWeek_MedInd2.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(2))) {
				calenderViewWeek_FoodInd2.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(2))) {
				calenderViewWeek_ProbInd2.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(1))) {
				calenderViewWeek_MedInd3.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(1))) {
				calenderViewWeek_FoodInd3.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(1))) {
				calenderViewWeek_ProbInd3.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate)) {
				calenderViewWeek_MedInd4.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate)) {
				calenderViewWeek_FoodInd4.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate)) {
				calenderViewWeek_ProbInd4.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(1))) {
				calenderViewWeek_MedInd5.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(1))) {
				calenderViewWeek_FoodInd5.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(1))) {
				calenderViewWeek_ProbInd5.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(2))) {
				calenderViewWeek_MedInd6.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(2))) {
				calenderViewWeek_FoodInd6.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(2))) {
				calenderViewWeek_ProbInd6.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(3))) {
				calenderViewWeek_MedInd7.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(3))) {
				calenderViewWeek_FoodInd7.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(3))) {
				calenderViewWeek_ProbInd7.setVisible(true);
			}
			break;
		}
		case FRIDAY: {
			calenderViewWeek_ButMon.setText("Montag " + localDate.minusDays(4).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButMon.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(4));
					etController.refresh();
				}
			});
			calenderViewWeek_ButTue.setText("Dienstag " + localDate.minusDays(3).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButTue.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(3));
					etController.refresh();
				}
			});
			calenderViewWeek_ButWed.setText("Mittwoch " + localDate.minusDays(2).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButWed.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(2));
					etController.refresh();
				}
			});
			calenderViewWeek_ButThu.setText("Donnerstag " + localDate.minusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButThu.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(1));
					etController.refresh();
				}
			});
			calenderViewWeek_ButFri.setText("Freitag " + localDate.format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButFri.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate);
					etController.refresh();
				}
			});
			calenderViewWeek_ButSat.setText("Samstag " + localDate.plusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSat.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(1));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSun.setText("Sonntag " + localDate.plusDays(2).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSun.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(2));
					etController.refresh();
				}
			});
			setFalse();
			if (elc.existsDrugEntry(localDate.minusDays(4))) {
				calenderViewWeek_MedInd1.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(4))) {
				calenderViewWeek_FoodInd1.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(4))) {
				calenderViewWeek_ProbInd1.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(3))) {
				calenderViewWeek_MedInd2.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(3))) {
				calenderViewWeek_FoodInd2.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(3))) {
				calenderViewWeek_ProbInd2.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(2))) {
				calenderViewWeek_MedInd3.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(2))) {
				calenderViewWeek_FoodInd3.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(2))) {
				calenderViewWeek_ProbInd3.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(1))) {
				calenderViewWeek_MedInd4.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(1))) {
				calenderViewWeek_FoodInd4.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(1))) {
				calenderViewWeek_ProbInd4.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate)) {
				calenderViewWeek_MedInd5.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate)) {
				calenderViewWeek_FoodInd5.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate)) {
				calenderViewWeek_ProbInd5.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(1))) {
				calenderViewWeek_MedInd6.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(1))) {
				calenderViewWeek_FoodInd6.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(1))) {
				calenderViewWeek_ProbInd6.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(2))) {
				calenderViewWeek_MedInd7.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(2))) {
				calenderViewWeek_FoodInd7.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(2))) {
				calenderViewWeek_ProbInd7.setVisible(true);
			}
			break;
		}
		case SATURDAY: {
			calenderViewWeek_ButMon.setText("Montag " + localDate.minusDays(5).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButMon.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(5));
					etController.refresh();
				}
			});
			calenderViewWeek_ButTue.setText("Dienstag " + localDate.minusDays(4).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButTue.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(4));
					etController.refresh();
				}
			});
			calenderViewWeek_ButWed.setText("Mittwoch " + localDate.minusDays(3).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButWed.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(3));
					etController.refresh();
				}
			});
			calenderViewWeek_ButThu.setText("Donnerstag " + localDate.minusDays(2).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButThu.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(2));
					etController.refresh();
				}
			});
			calenderViewWeek_ButFri.setText("Freitag " + localDate.minusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButFri.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(1));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSat.setText("Samstag " + localDate.format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSat.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate);
					etController.refresh();
				}
			});
			calenderViewWeek_ButSun.setText("Sonntag " + localDate.plusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSun.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.plusDays(1));
					etController.refresh();
				}
			});
			setFalse();
			if (elc.existsDrugEntry(localDate.minusDays(5))) {
				calenderViewWeek_MedInd1.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(5))) {
				calenderViewWeek_FoodInd1.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(5))) {
				calenderViewWeek_ProbInd1.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(4))) {
				calenderViewWeek_MedInd2.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(4))) {
				calenderViewWeek_FoodInd2.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(4))) {
				calenderViewWeek_ProbInd2.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(3))) {
				calenderViewWeek_MedInd3.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(3))) {
				calenderViewWeek_FoodInd3.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(3))) {
				calenderViewWeek_ProbInd3.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(2))) {
				calenderViewWeek_MedInd4.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(2))) {
				calenderViewWeek_FoodInd4.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(2))) {
				calenderViewWeek_ProbInd4.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(1))) {
				calenderViewWeek_MedInd5.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(1))) {
				calenderViewWeek_FoodInd5.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(1))) {
				calenderViewWeek_ProbInd5.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate)) {
				calenderViewWeek_MedInd6.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate)) {
				calenderViewWeek_FoodInd6.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate)) {
				calenderViewWeek_ProbInd6.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.plusDays(1))) {
				calenderViewWeek_MedInd7.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.plusDays(1))) {
				calenderViewWeek_FoodInd7.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.plusDays(1))) {
				calenderViewWeek_ProbInd7.setVisible(true);
			}
			break;
		}
		case SUNDAY: {
			calenderViewWeek_ButMon.setText("Montag " + localDate.minusDays(6).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButMon.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(6));
					etController.refresh();
				}
			});
			calenderViewWeek_ButTue.setText("Dienstag " + localDate.minusDays(5).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButTue.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(5));
					etController.refresh();
				}
			});
			calenderViewWeek_ButWed.setText("Mittwoch " + localDate.minusDays(4).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButWed.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(4));
					etController.refresh();
				}
			});
			calenderViewWeek_ButThu.setText("Donnerstag " + localDate.minusDays(3).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButThu.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(3));
					etController.refresh();
				}
			});
			calenderViewWeek_ButFri.setText("Freitag " + localDate.minusDays(2).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButFri.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(2));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSat.setText("Samstag " + localDate.minusDays(1).format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSat.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate.minusDays(1));
					etController.refresh();
				}
			});
			calenderViewWeek_ButSun.setText("Sonntag " + localDate.format(DateTimeFormatter.ofPattern("d.M")));
			calenderViewWeek_ButSun.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					mvc.setDateShown(localDate);
					etController.refresh();
				}
			});
			setFalse();
			if (elc.existsDrugEntry(localDate.minusDays(6))) {
				calenderViewWeek_MedInd1.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(6))) {
				calenderViewWeek_FoodInd1.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(6))) {
				calenderViewWeek_ProbInd1.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(5))) {
				calenderViewWeek_MedInd2.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(5))) {
				calenderViewWeek_FoodInd2.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(5))) {
				calenderViewWeek_ProbInd2.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(4))) {
				calenderViewWeek_MedInd3.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(4))) {
				calenderViewWeek_FoodInd3.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(4))) {
				calenderViewWeek_ProbInd3.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(3))) {
				calenderViewWeek_MedInd4.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(3))) {
				calenderViewWeek_FoodInd4.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(3))) {
				calenderViewWeek_ProbInd4.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(2))) {
				calenderViewWeek_MedInd5.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(2))) {
				calenderViewWeek_FoodInd5.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(2))) {
				calenderViewWeek_ProbInd5.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate.minusDays(1))) {
				calenderViewWeek_MedInd6.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate.minusDays(1))) {
				calenderViewWeek_FoodInd6.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate.minusDays(1))) {
				calenderViewWeek_ProbInd6.setVisible(true);
			}
			if (elc.existsDrugEntry(localDate)) {
				calenderViewWeek_MedInd7.setVisible(true);
			}
			if (elc.existsFoodEntry(localDate)) {
				calenderViewWeek_FoodInd7.setVisible(true);
			}
			if (elc.existsHealthProblemEntry(localDate)) {
				calenderViewWeek_ProbInd7.setVisible(true);
			}
			break;
		}
		}

		switch (fDay) {
		case MONDAY: {
			start = 0;
			break;
		}
		case TUESDAY: {
			start = 1;
			break;
		}
		case WEDNESDAY: {
			start = 2;
			break;
		}
		case THURSDAY: {
			start = 3;
			break;
		}
		case FRIDAY: {
			start = 4;
			break;
		}
		case SATURDAY: {
			start = 5;
			break;
		}
		case SUNDAY: {
			start = 6;
			break;
		}

		}
		double recwidth = 27;
		double rechight = 25;
		AnchorPane[][] anchors = new AnchorPane[6][7];
		Button[][] buttons = new Button[6][7];
		Rectangle[][][] rectangles = new Rectangle[6][7][3];
		calenderView_grid.getChildren().clear();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (start == 0) {
					if (days > 0) {
						if (j == 0) {
							dayName = "Montag";
						}
						if (j == 1) {
							dayName = "Dienstag";
						}
						if (j == 2) {
							dayName = "Mittwoch";
						}
						if (j == 3) {
							dayName = "Donnerstag";
						}
						if (j == 4) {
							dayName = "Freitag";
						}
						if (j == 5) {
							dayName = "Samstag";
						}
						if (j == 6) {
							dayName = "Sonntag";
						}
						anchors[i][j] = new AnchorPane();
						buttons[i][j] = new Button();
						anchors[i][j].getChildren().add(buttons[i][j]);
						anchors[i][j].setTopAnchor(buttons[i][j], 0.0);
						anchors[i][j].setLeftAnchor(buttons[i][j], 0.0);
						anchors[i][j].setRightAnchor(buttons[i][j], 0.0);
						anchors[i][j].setBottomAnchor(buttons[i][j], 25.0);
						int count = days;
						LocalDate currentdate = LocalDate.of(year, month, (localDate.lengthOfMonth() - count) + 1);
						buttons[i][j].setText(dayName + "\n" + currentdate.format(DateTimeFormatter.ofPattern("d.M")));
						HBox box = new HBox();
						for (int k = 0; k < 3; k++) {
							rectangles[i][j][k] = new Rectangle(recwidth, rechight);
							if (k == 0) {
								rectangles[i][j][k].setFill(Color.GREEN);
								if (elc.existsFoodEntry(currentdate)) {
									rectangles[i][j][k].setVisible(true);
								} else
									rectangles[i][j][k].setVisible(false);
								box.getChildren().add(rectangles[i][j][k]);
							}
							if (k == 1) {
								rectangles[i][j][k].setFill(Color.YELLOW);
								if (elc.existsDrugEntry(currentdate)) {
									rectangles[i][j][k].setVisible(true);
								} else
									rectangles[i][j][k].setVisible(false);
								box.getChildren().add(rectangles[i][j][k]);
							}
							if (k == 2) {
								rectangles[i][j][k].setFill(Color.RED);
								if (elc.existsHealthProblemEntry(currentdate)) {
									rectangles[i][j][k].setVisible(true);
								} else
									rectangles[i][j][k].setVisible(false);
								box.getChildren().add(rectangles[i][j][k]);
							}
						}
						anchors[i][j].setLeftAnchor(box, 0.0);
						anchors[i][j].setRightAnchor(box, 0.0);
						anchors[i][j].setBottomAnchor(box, 0.0);
						anchors[i][j].getChildren().add(box);
						buttons[i][j].setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent e) {
								mvc.setDateShown(currentdate);
								etController.refresh();
							}
						});
						calenderView_grid.add(anchors[i][j], i, j);
						days--;
					}
				} else {
					start--;
				}
			}
		}
	}

	@FXML
	void calenderView_DatePickerGo(ActionEvent event) {
		localDate = calenderView_DatePicker.getValue();
		firstOfMonth = localDate.with(TemporalAdjusters.firstDayOfMonth());
		calenderView_grid.getChildren().clear();
		actDay = calenderView_DatePicker.getValue().getDayOfWeek();
		mvc.setDateShown(localDate);
		etController.refresh();
		fillCalender();
	}

	@Override
	public void refreshDiary() {
		fillCalender();

	}

	private void setFalse() {
		calenderViewWeek_MedInd1.setVisible(false);
		calenderViewWeek_FoodInd1.setVisible(false);
		calenderViewWeek_ProbInd1.setVisible(false);
		calenderViewWeek_MedInd2.setVisible(false);
		calenderViewWeek_FoodInd2.setVisible(false);
		calenderViewWeek_ProbInd2.setVisible(false);
		calenderViewWeek_MedInd3.setVisible(false);
		calenderViewWeek_FoodInd3.setVisible(false);
		calenderViewWeek_ProbInd3.setVisible(false);
		calenderViewWeek_MedInd4.setVisible(false);
		calenderViewWeek_FoodInd4.setVisible(false);
		calenderViewWeek_ProbInd4.setVisible(false);
		calenderViewWeek_MedInd5.setVisible(false);
		calenderViewWeek_FoodInd5.setVisible(false);
		calenderViewWeek_ProbInd5.setVisible(false);
		calenderViewWeek_MedInd6.setVisible(false);
		calenderViewWeek_FoodInd6.setVisible(false);
		calenderViewWeek_ProbInd6.setVisible(false);
		calenderViewWeek_MedInd7.setVisible(false);
		calenderViewWeek_FoodInd7.setVisible(false);
		calenderViewWeek_ProbInd7.setVisible(false);
	}

}
