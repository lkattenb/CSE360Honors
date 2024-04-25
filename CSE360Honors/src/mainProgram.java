import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.geometry.*;

import java.time.LocalDate; // import the LocalDate class
import java.time.LocalDateTime;
import javafx.stage.Stage;
import java.util.Scanner;


public class mainProgram extends Application {
	
	/////////////////////////////////////////////////////
	// create some parameters to be used by the class //
	/////////////////////////////////////////////////////
	
	// credits to [ https://www.siliconvalleypower.com/residents/save-energy/appliance-energy-use-chart ]
	
	double COST_PER_WATT = 0.1316; // dollars
	double ELECTRICITY_SAVED;
	
	// light bulbs
	int AVG_NUM_LIGHTBULBS = 40; // average number of light bulbs in US
	double KWH_INCANDESCENT = 0.06 * 2; // avg 2 hr lightbulb usage per day
	double KWH_LED = 0.01 * 2; // avg 2 hr lightbulb usage per day --> NEW LED
	
	// clothes washing and drying
	double KWH_WASHER_PER_LOAD_HOT = 6.3  * 0.8; // 0.8 loads per day
	double KWH_WASHER_PER_LOAD_COLD = 2.3 * 0.8; // 0.8 loads per day --> NEW WASHER
	double KWH_DRYER_PER_LOAD = 3.2 * 0.8; // 0.8 loads per day
	double KWH_PUMP_DRYER_PER_LOAD = 1.92 * 0.8; // 0.8 loads per day
	double KWH_AIR_DRYER_LOAD = 0.0; // no electricity
	
	int totalCostSaved = 0;
	
	//TODO: find types of energy saving methods for...
	// --> AC
	
	// kithcen appliances
	double KWH_OVEN = 2.3;
	double KW_OLD_FRIDGE_DAILY = 2.4;
	double KW_NEW_FRIDGE_DAILY = 0.93; // --> NEW FRIDGE 
	double KWH_DISHWASHER_NORMAL_LOAD = 1.5;
	double KWH_DISHWASHER_SAVE_LOAD = 0.5; // --> NEW DISHWASHER
	
	// temperature control
	double KW_WATERHEATER_DAILY = 10.0;
	double KW_TANKLESSWATERHEATER_DAILY = 6.0;
	double KW_COLDERWATERHEATER_DAILY = 9.0;
	double KWH_CENTRAL = 3;
	double KWH_CENTRAL_SEALED = 2.4;
	
	// generalization of devices
	double KW_DEVICES_DAILY = 1.4;
	double KW_DEVICESUNPLUGGED_DAILY = 1.396;
	
	// variables for current usage
	double C_ELECTRICITY;
	double C_COST;
	double C_LIGHTBULBS;
	double C_WASHER;
	double C_DRYER;
	double C_OVEN;
	double C_FRIDGE;
	double C_DISHWASHER;
	double C_WATERHEATER;
	double C_CENTRAL;
	double C_DEVICES;
	
	double initialCost = 2497.768;
	
	// global labels
	Label AC_LABEL = new Label(C_CENTRAL + " kWh");
    Label WATERHEATER_LABEL = new Label(C_WATERHEATER + " kWh");
    Label DISHWASHER_LABEL = new Label(C_DISHWASHER + " kWh");
    Label FRIDGE_LABEL =new Label(C_FRIDGE + " kWh");
    Label WASHER_LABEL = new Label(C_WASHER + " kWh");
    Label DEVICES_LABEL = new Label(C_DEVICES + " kWh");
    Label DRYER_LABEL = new Label(C_DRYER + " kWh");
    Label LIGHTBULBS_LABEL = new Label(C_LIGHTBULBS + " kWh");
    Label DAILY_ELECTRICITY = new Label(C_ELECTRICITY + " kWh");
    Label MONTHLY_ELECTRICITY = new Label(C_ELECTRICITY * 30 + " kWh");
    Label YEARLY_ELECTRICITY = new Label(C_ELECTRICITY * 365 + " kWh");
    
    Label DAILY_COST = new Label("$ " + (C_ELECTRICITY * COST_PER_WATT));
    Label MONTHLY_COST = new Label("$ " + (C_ELECTRICITY * COST_PER_WATT*30));
    Label YEARLY_COST = new Label("$ " + (C_ELECTRICITY * COST_PER_WATT*365));
    
    Label COST_SAVED = new Label("$ " + (initialCost - (C_ELECTRICITY * COST_PER_WATT*365)));
	
	
	// delay function pulled from [ https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java ]
	// calling wait(ms) allows for delay in Java with no import
	// will use for creating delays in program
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
	
	
	// main function needed for javafx
	public static void main(String[] args) {
        launch(args);
    }
	
	public void print_menu() {
		System.out.println("CURRENT STATISTICS");
		System.out.println("~~~~~~~~~~~~~~~~~~");
		System.out.printf("Central heating and cooling: %.2f kWh\n", C_CENTRAL);
		System.out.printf("Water heater: %.2f kWh\n", C_WATERHEATER);
		System.out.printf("Dishwasher: %.2f kWh\n", C_DISHWASHER);
		System.out.printf("Fridge: %.2f kWh\n", C_FRIDGE);
		System.out.printf("Washer: %.2f kWh\n", C_WASHER);
		System.out.printf("Devices (misc.): %.2f kWh\n", C_DEVICES);
		System.out.printf("Dryer: %.2f kWh\n", C_DRYER);
		System.out.printf("Lightbulbs: %.2f kWh\n", C_LIGHTBULBS);
		System.out.printf("Total electricity usage per day: %.4f kWh\n", C_ELECTRICITY);
		System.out.printf("Total electricity usage per month: %.4f kWh\n", C_ELECTRICITY*30);
		System.out.printf("Total electricity usage per year: %.4f kWh\n", C_ELECTRICITY*365);
		System.out.printf("Total cost (per month): $%.2f\n", C_COST * 30);
		System.out.printf("Total cost (per year): $%.2f\n", C_COST * 365);
		LocalDate myObj = LocalDate.now(); // Create a date object
	    System.out.println(myObj); // Display the current date
	}
	
	public void save_electricity() {
		// Automatic blinds
		// Setting water heater to lower temperature
		// Purchase a new fridge
		// Unplug unused devices
		// Replace lightbulbs
		// Reseal potential cracks in house
	}
	
	public void amount_saved(int savedElectricity) {
		// savedElectricity > x ? "You saved enough energy to power Z" !
	}
	
	public void update_usage() {
		System.out.println("update_usage()");
		C_ELECTRICITY = C_LIGHTBULBS + C_DEVICES + C_WASHER + C_DRYER + C_OVEN + C_FRIDGE + C_DISHWASHER + C_WATERHEATER + C_CENTRAL;
		//System.out.println(C_ELECTRICITY);
		C_COST = C_ELECTRICITY * COST_PER_WATT;
		
		
		String AC_STRING = String.format("%.2f kWh",C_CENTRAL);
		AC_LABEL.setText(AC_STRING);
		
		String WATERHEATER_STRING = String.format("%.2f kWh",C_WATERHEATER);
	    WATERHEATER_LABEL.setText(WATERHEATER_STRING);
	    
	    String DISHWASHER_STRING = String.format("%.2f kWh",C_DISHWASHER);
	    DISHWASHER_LABEL.setText(DISHWASHER_STRING);
	    
	    String FRIDGE_STRING = String.format("%.2f kWh",C_FRIDGE);
	    FRIDGE_LABEL.setText(FRIDGE_STRING);
	    
	    String WASHER_STRING = String.format("%.2f kWh",C_WASHER);
	    WASHER_LABEL.setText(WASHER_STRING);
	    
	    String DEVICES_STRING = String.format("%.2f kWh",C_DEVICES);
	    DEVICES_LABEL.setText(C_DEVICES + " kWh");
	    
	    String DRYER_STRING = String.format("%.2f kWh",C_DRYER);
	    DRYER_LABEL.setText(DRYER_STRING);
	    
	    String LIGHTBULBS_STRING = String.format("%.2f kWh",C_LIGHTBULBS);
	    LIGHTBULBS_LABEL.setText(LIGHTBULBS_STRING);
	    
	    String DAILY_ELECTRICITY_STRING = String.format("%.2f kWh",C_ELECTRICITY);
	    DAILY_ELECTRICITY.setText(DAILY_ELECTRICITY_STRING);
	    
	    String MONTHLY_ELECTRICITY_STRING = String.format("%.2f kWh",C_ELECTRICITY*30);
	    MONTHLY_ELECTRICITY.setText(MONTHLY_ELECTRICITY_STRING);
	    
	    String YEARLY_ELECTRICITY_STRING = String.format("%.2f kWh",C_ELECTRICITY*365);
	    YEARLY_ELECTRICITY.setText(YEARLY_ELECTRICITY_STRING);
	    
	    String DAILY_COST_STRING = String.format("$ %.2f",C_ELECTRICITY * COST_PER_WATT);
	    DAILY_COST.setText(DAILY_COST_STRING);
	    
	    String MONTHLY_COST_STRING = String.format("$ %.2f",C_ELECTRICITY * COST_PER_WATT*30);
	    MONTHLY_COST.setText(MONTHLY_COST_STRING);
	    
	    String YEARLY_COST_STRING = String.format("$ %.2f",C_ELECTRICITY * COST_PER_WATT*365);
	    YEARLY_COST.setText(YEARLY_COST_STRING);
	    
	    String COST_SAVED_STRING = String.format("$ %.2f", (initialCost - (C_ELECTRICITY * COST_PER_WATT*365)));
	    COST_SAVED.setText(COST_SAVED_STRING);
	    
	    print_menu();
	}
	
	public void reset_values() {
		// SET TO ALL AVERAGES OVER 24 hr period
		C_ELECTRICITY = 0;
		C_COST = 0;
		C_LIGHTBULBS =  AVG_NUM_LIGHTBULBS * 0.5 * KWH_INCANDESCENT + AVG_NUM_LIGHTBULBS * 0.5 * KWH_LED ; 
		C_WASHER = KWH_WASHER_PER_LOAD_HOT; 
		C_DRYER = KWH_DRYER_PER_LOAD;
		C_OVEN = KWH_OVEN;
		C_FRIDGE = KW_OLD_FRIDGE_DAILY;
		C_DISHWASHER = KWH_DISHWASHER_NORMAL_LOAD;
		C_WATERHEATER = KW_WATERHEATER_DAILY;
		C_CENTRAL = KWH_CENTRAL * 8; // running AC for 8 hours (non-ideal)
		C_DEVICES = KW_DEVICES_DAILY;
	}
	
	public void start(Stage primaryStage) {
		System.out.println("Program has started");
		reset_values(); // set all values to original
		update_usage();
		print_menu();
		//primaryStage.setTitle("ASU Honors Project");
		
		
		//<<<<<<<<<<<< TABPANE >>>>>>>>>>>>>//
		
        TabPane tabPane = new TabPane();

        // Create individual tabs
        Tab tab1 = new Tab("Electricity Usage Graphs");
        Tab tab2 = new Tab("Your Appliances and Usage");
        Tab tab3 = new Tab("Ways to Save Electricity");

        // Add content to each tab (you can replace this with your own views)
        tab1.setContent(createPage1()); // Custom method to create content for Page 1
        tab2.setContent(createPage2()); // Custom method to create content for Page 2
        tab3.setContent(createPage3()); // Custom method to create content for Page 3

        // Add tabs to the TabPane
        tabPane.getTabs().addAll(tab1, tab2, tab3);

        // Create the main layout
        BorderPane root = new BorderPane();
        root.setCenter(tabPane);

        // Create the scene
        Scene scene = new Scene(root, 800, 600);
        
        //<<<<<<<<<<<< ----- >>>>>>>>>>>>>//
        
        //<<<<<<<<<<<< WELCOME SCREEN >>>>>>>>>>>>>//
        BorderPane welcomeScreen = new BorderPane();
        
        Button welcomeBtn = new Button("Begin");        
        Image image = new Image(getClass().getResourceAsStream("ElectricityProgram.png"));
        // Create an ImageView to display the image
        ImageView imageView = new ImageView(image);
        welcomeBtn.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
        welcomeBtn.setFont(new Font(24)); // Set the font size
        //welcomeBtn.setPadding(new Insets(20, 20, 20, 20));
        
        VBox welcomeBtnHBox = new VBox(imageView, welcomeBtn);
        welcomeBtnHBox.setAlignment(Pos.CENTER); // Center the button horizontally
        //welcomeBtnHBox.setPadding(new Insets(0, 0, 600, 0)); // Add some padding

        
        imageView.setFitWidth(800); // Set the desired width
        imageView.setFitHeight(550); // Set the desired height
        
        welcomeScreen.setCenter(welcomeBtnHBox);
        
        Scene welcomeScene = new Scene(welcomeScreen, 850, 650);
        
        primaryStage.setScene(welcomeScene);
        welcomeScene.setFill(Color.BLACK); // Set the scene background color
      //<<<<<<<<<<<< ----- >>>>>>>>>>>>>//
        
        primaryStage.setTitle("Electricity Monitoring Program");
        welcomeBtn.setOnAction(e -> {
//        	primaryStage.setHeight(1080);
//        	primaryStage.setWidth(1920);
        	primaryStage.setScene(scene);
        	primaryStage.setTitle("Tabbed Application");
        });
        

        // Set the scene and show the stage
        
        primaryStage.show();
	}
	
	private BorderPane createPage1() {
		
		/*
    	 * Need to add following implementation...
    	 * 
    	 * - Be able to change the "current" time in a good UI
    	 * - Warn user of upcoming surge times
    	 * - (Optional) tell user percentage increase of price at certain time
    	 * 
    	 */
		
		
		// Create axes
        NumberAxis xAxis = new NumberAxis(1, 24, 1);
        xAxis.setLabel("Time (hrs)");
        NumberAxis yAxis = new NumberAxis(250, 400, 50); // Set lower bound to 250
        yAxis.setLabel("Electricity usage (MWh/10)");

        // Create line chart
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Electricity Usage of Southwest Over 24 Hours");

        // Create data series
        XYChart.Series<Number, Number> usageSeries = new XYChart.Series<>();
        //usageSeries.setName("Electricity Usage");

        // Sample data (replace with your actual data)
        double[] timeArray = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        //double[] usageArray = {306, 295, 286, 283, 289, 302, 332, 335, 316, 317, 296, 297, 295, 277, 266, 279, 297, 339, 351, 371, 363, 329, 300};
        double[] usageArray = {306, 295, 286, 283, 289, 302, 332, 335, 316, 317, 296, 297, 295, 277, 266, 279, 297, 339, 351, 371, 366, 363, 350, 329};

        for (int i = 0; i < timeArray.length; i++) {
            usageSeries.getData().add(new XYChart.Data<>(timeArray[i], usageArray[i]));
        }

        // Add series to the chart
        lineChart.getData().add(usageSeries);

        // Create data series
        XYChart.Series<Number, Number> userUsageSeries = new XYChart.Series<>();
        //userUsageSeries.setName("Electricity Usage");
        
        // Create labels
        Label titleLabel1 = new Label("Electricity Usage of Southwest");
        
        // Apply formatting
        titleLabel1.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 24));
        titleLabel1.setUnderline(true);
        titleLabel1.setTextAlignment(TextAlignment.CENTER);

        Label didYouKnow = new Label("DID YOU KNOW... ");
        didYouKnow.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));
        didYouKnow.setTextAlignment(TextAlignment.CENTER);
        // Create placeholders for paragraphs
        Label infoParagraph1 = new Label("Peak electricity hours can lead to a 2-4 cent increase per kWh in the winter and an increase of 7-13 cents per kWh\n"
        								+ " in the summer? Saving money on electricity starts with knowing when these surges happen!");
        
        // Create sliders
        Slider timeSlider = new Slider();
        timeSlider.setMin(1);
        timeSlider.setMax(timeArray.length-1);
        //timeSlider.setSnapToTicks(true);
        timeSlider.setMajorTickUnit(4);
        timeSlider.setMinorTickCount(0);
        timeSlider.setShowTickMarks(true);
        timeSlider.setShowTickLabels(true);
        timeSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                int index = object.intValue();
                return String.valueOf(timeArray[index]);
            }

            @Override
            public Double fromString(String string) {
                // Not used
                return null;
            }
        });

        //TODO: Need to add surge timer functionality
        // --> Show current time while adjusting slider
        // --> Warn about surges (change text color?)
        // --> Give information about surges (price)
        
        Label surgeLabel = new Label("");
        Label timeLabel = new Label("");
        timeSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            int currentTime = (int)(timeArray[newValue.intValue()]);
            if(currentTime < 5) {
            	surgeLabel.setText("Not in surge");
            	surgeLabel.setStyle("-fx-text-fill: green;");
            } else if (currentTime < 10) {
            	surgeLabel.setText("IN SURGE");
            	surgeLabel.setStyle("-fx-text-fill: red;");
            } else if (currentTime < 16) {
            	surgeLabel.setText("Not in surge");
            	surgeLabel.setStyle("-fx-text-fill: green;");
            } else {
            	surgeLabel.setText("IN SURGE");
            	surgeLabel.setStyle("-fx-text-fill: red;");
            }
            timeLabel.setText("Current time: " + String.valueOf(currentTime));
        });
        
        Label titleLabel2 = new Label("Projected Electricity Usage of US");
        titleLabel2.setPadding(new Insets(25,25,25,5));
        
        // Apply formatting
        titleLabel2.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 24));
        titleLabel2.setUnderline(true);
        titleLabel2.setTextAlignment(TextAlignment.CENTER);
        
        Image image2 = new Image(getClass().getResourceAsStream("USUsageGraph.png"));
        // Create an ImageView to display the image
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(400); // Set the desired width
        imageView2.setFitHeight(250); // Set the desired height
        
        Label infoParagraph2 = new Label("This shocking graph from the New York Times shows the path\n"
        								+ "that the US is on with current trends in electricity usage.\n"
        								+ "This graph predicts that the electricity usage in the US will\n"
        								+ "increase by almost 3x from 2019 to 2029: 300% increase in\n"
        								+ "10 years. Where is this large increase coming from? Data \n"
        								+ "centers. The NYT states that the increase in commerical \n"
        								+ " artificial intelligence has led to an increased production\n"
        								+ "and usage of these data centers. As this resource heavy \n"
        								+ "technology grows, so will its electricity usage. This can be \n"
        								+ "discouraging to homeowners attempting to cut electricity usage \n"
        								+ "in order to better the environment. Hopefully this graph will\n"
        								+ "give you a second thought before using an AI tool.");
        
        HBox shockingBox = new HBox(15, imageView2, infoParagraph2);

        // Create VBox for labels and paragraphs
        HBox tempHBox = new HBox(50, new Label("Clock simulation: "),timeSlider, timeLabel, surgeLabel);
        VBox labelsAndInfo = new VBox(10, titleLabel1, lineChart, tempHBox, didYouKnow, infoParagraph1, titleLabel2, shockingBox);
        labelsAndInfo.setAlignment(Pos.CENTER);
        labelsAndInfo.setPadding(new Insets(10, 25, 10, 25));
        
		
        ScrollPane PAGE1 = new ScrollPane(labelsAndInfo);
        
        return new BorderPane(PAGE1);
    }
	
	//TODO: Create a better UI for this page
	// --> Implement the cost in this page
	// --> Section off electricity usage and cost
	// --> (OPTIONAL) Show total potential savings

    private BorderPane createPage2() {
    	
    	/*
    	 * Need to add following implementation...
    	 * 
    	 * - Make a section for appliance electricity usage
    	 * - A clear UI with each appliance and electricity usage
    	 * - Make a section for current cost
    	 * - (Optional) add a section where the biggest performance can be improved
    	 * 
    	 */
    	
    	print_menu();
    	
        BorderPane PAGE2 = new BorderPane();
        
        ScrollPane scrollPage2 = new ScrollPane();
        
        LocalDate myObj = LocalDate.now(); // Create a date object
        Label titleLabel1 = new Label("Your Electricity Usage on " + myObj.toString());
        titleLabel1.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 24));
        titleLabel1.setUnderline(true);
        titleLabel1.setTextAlignment(TextAlignment.CENTER);
        titleLabel1.setPadding(new Insets(10, 50, 10, 50));
        
        Label electricityLabel = new Label("Energy Usage Statistics");
        electricityLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));
        //electricityLabel.setUnderline(true);
        electricityLabel.setTextAlignment(TextAlignment.CENTER);
        
        Label costLabel = new Label("Cost Statistics");
        costLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));
        //costLabel.setUnderline(true);
        costLabel.setTextAlignment(TextAlignment.CENTER);
        
     // Create the main grid pane
        GridPane mainGrid = new GridPane();
        mainGrid.setHgap(20);
        mainGrid.setVgap(10);
        mainGrid.setPadding(new Insets(20));

        // Labels and values
        mainGrid.addColumn(0,
            new Label("Central heating and cooling:"),
            new Label("Water heater:"),
            new Label("Dishwasher:"),
            new Label("Fridge:"),
            new Label("Washer:"),
            new Label("Devices (misc.):"),
            new Label("Dryer:"),
            new Label("Lightbulbs:"),
            new Label("Total electricity usage per day:"),
            new Label("Total electricity usage per month:"),
            new Label("Total electricity usage per year:"));
        
        mainGrid.addColumn(1, AC_LABEL, WATERHEATER_LABEL, DISHWASHER_LABEL, FRIDGE_LABEL,
        		WASHER_LABEL, DEVICES_LABEL, DRYER_LABEL, LIGHTBULBS_LABEL, DAILY_ELECTRICITY,
        		MONTHLY_ELECTRICITY, YEARLY_ELECTRICITY);
        
        //mainGrid.setGridLinesVisible(true);
        
        // Create the cost grid pane
        GridPane costGridPane = new GridPane();
        costGridPane.setHgap(20);
        costGridPane.setVgap(10);
        costGridPane.setPadding(new Insets(20));
        
        costGridPane.addColumn(0, new Label("Total electricity cost per day: "), new Label("Total electricity cost per month: "), 
        							new Label("Total electricity cost per year: "), new Label("TOTAL COST SAVED PER YEAR: "));
        costGridPane.addColumn(1, DAILY_COST, MONTHLY_COST, YEARLY_COST, COST_SAVED);
        
        VBox page2VBox = new VBox(10, titleLabel1, electricityLabel, mainGrid, costLabel, costGridPane);
        page2VBox.setPadding(new Insets(10, 0, 10, 25));
        
        scrollPage2.setContent(page2VBox);
        
        
        return new BorderPane(scrollPage2);
    }

    private BorderPane createPage3() {
    	
    	/*
    	 * Need to add following implementation...
    	 * 
    	 * - List with ways to save electricity
    	 * - Buttons to update the current electricity usage
    	 * - Sections for each appliance and information
    	 * - (Optional) Order by most important
    	 * 
    	 */
        
    	Label titleLabel1 = new Label("Ways to Save Electricity");
        
        // Apply formatting
        titleLabel1.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 24));
        titleLabel1.setUnderline(true);
        titleLabel1.setTextAlignment(TextAlignment.CENTER);
        
        Label infoParagraph1 = new Label("You have been given a household that is using appliances in the most inefficient manner possible.\n"
        								+ "Sadly, however, this is how the average Arizona household uses these appliances. Your mission is to\n"
        								+ "go through this page and discover ways to save electricity -and money!");
        
        Label acLabel = new Label("1. A/C Unit");
	    acLabel.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.REGULAR, 20));
	    //acLabel.setUnderline(true);
	    acLabel.setTextAlignment(TextAlignment.CENTER);
	    Label acInfo = new Label("According to the National Resources Defense Council (NRDC), you can save up to 20% on your\n"
	    						+ "air conditioning and heating bill simply by sealing any cracks in your home and resealing\n"
	    						+ "any windows or doors that have openings to the outside world!");
	    Button acSave = new Button("Save energy!");
	    Button acUndo = new Button("Undo");
	    VBox acVBox = new VBox(acLabel, acInfo, acSave, acUndo);
	    acVBox.setSpacing(15);
	    
	    Label acInfo2 = new Label("Another statistic from the NRDC: Did you know that by using a 'smart' air conditioning and\n"
				+ "heating strategy, you can reduce your cooling and heating bill by 20%? These strategies\n"
				+ "include turning the fan, opening windows, and closing blinds instead of air conditioning.");
	    Button acSave2 = new Button("Save energy!");
	    Button acUndo2 = new Button("Undo");
	    VBox acVBox2 = new VBox(acInfo2, acSave2, acUndo2);
	    acVBox2.setSpacing(15);
	    
	    Label lightBulbsLabel = new Label("2. Lightbulbs");
	    lightBulbsLabel.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.REGULAR, 20));
	    //lightBulbsLabel.setUnderline(true);
	    lightBulbsLabel.setTextAlignment(TextAlignment.CENTER);
	    Label lightBulbsInfo = new Label("Lightbulbs have undergone significant improvements over the last 20 years. The\n"
	    								+ "most notable of all of these improvements are the LED lightbulbs which use 90% \n"
	    								+ "less electricity than older modeled lightbulbs!");
	    Button lightBulbsSave = new Button("Save energy!");
	    Button lightBulbsUndo = new Button("Undo");
	    VBox lightBulbsVBox = new VBox(lightBulbsLabel, lightBulbsInfo, lightBulbsSave, lightBulbsUndo);
	    lightBulbsVBox.setSpacing(15);
	    
	    Label washerLabel = new Label("3. Washer");
	    washerLabel.setFont(Font.font("Arial", FontWeight.BOLD,FontPosture.REGULAR, 20));
	    //washerLabel.setUnderline(true);
	    washerLabel.setTextAlignment(TextAlignment.CENTER);
	    Label washerInfo = new Label("Have you ever used the cold function on your washer? Did you know that the cold function\n"
	    							+ "minimizes wear on clothes and allows for you to mix colors and whites? Finally, did you\n"
	    							+ "know that you can use over 50% less energy simply by using the cold setting on your washer?");
	    Button washerSave = new Button("Save energy!");
	    Button washerUndo = new Button("Undo");
	    VBox washerVBox = new VBox(washerLabel, washerInfo, washerSave, washerUndo);
	    washerVBox.setSpacing(15);
	    
	    Label dryerLabel = new Label("4. Dryer");
	    dryerLabel.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.REGULAR, 20));
	    //dryerLabel.setUnderline(true);
	    dryerLabel.setTextAlignment(TextAlignment.CENTER);
	    Label dryerInfo = new Label("The design of the dryer has been reworked in the last few years. One interesting design is the\n"
	    							+ "heat pump electric dryer. This dryer uses no generated heat to dry clothes and reduces electricity\n"
	    							+ "usage by up to 40%. Though, there may be an even cheaper alternative...");
	    Button dryerSave = new Button("Save energy!");
	    Button dryerUndo = new Button("Undo");
	    VBox dryerVBox = new VBox(dryerLabel, dryerInfo, dryerSave, dryerUndo);
	    dryerVBox.setSpacing(15);
	    
	    Label dryerInfo2 = new Label("There is a more primitive way to save electricity with your dryer, and that is by not using one\n"
	    							+ "at all! Hang drying your clothes can reduce your electricty usage from your dryer by 100%!");
	    Button dryerSave2 = new Button("Save energy!");
	    Button dryerUndo2 = new Button("Undo");
	    VBox dryerVBox2 = new VBox(dryerInfo2, dryerSave2, dryerUndo2);
	    dryerVBox2.setSpacing(15);
	    
	    Label ovenLabel = new Label("5. Oven");
	    ovenLabel.setFont(Font.font("Arial", FontWeight.BOLD,FontPosture.REGULAR, 20));
	    //ovenLabel.setUnderline(true);
	    ovenLabel.setTextAlignment(TextAlignment.CENTER);
	    Label ovenInfo = new Label("Unfortunately, we have reached the point in electric ovens where the electricity to heat conversion\n"
	    						+ "cannot become any more efficient. However, opting for a gas oven/stove uses resources 3x more efficiently!\n"
	    						+ "Some say that gas ovens/stoves even cook better.");
	    Button ovenSave = new Button("Save energy!");
	    Button ovenUndo = new Button("Undo");
	    VBox ovenVBox = new VBox(ovenLabel, ovenInfo, ovenSave, ovenUndo);
	    ovenVBox.setSpacing(15);
	    
	    Label dishwasherLabel = new Label("6. Dishwasher");
	    dishwasherLabel.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.REGULAR, 20));
	    //dishwasherLabel.setUnderline(true);
	    dishwasherLabel.setTextAlignment(TextAlignment.CENTER);
	    Label dishwasherInfo = new Label("Sometimes your old dishwasher can lead to you having to do an entirely second wash. Whether this is\n"
										+ "before you load the plates, or after, a new effective dishwasher could fix this. A new dishwasher could\n"
										+ "also lead to energy use reduction of 66%!");
	    Button dishwasherSave = new Button("Save energy!");
	    Button dishwasherUndo = new Button("Undo");
	    VBox dishwasherVBox = new VBox(dishwasherLabel, dishwasherInfo, dishwasherSave, dishwasherUndo);
	    dishwasherVBox.setSpacing(15);
	    
	    Label waterHeaterLabel = new Label("7. Water Heater");
	    waterHeaterLabel.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.REGULAR, 20));
	    //waterHeaterLabel.setUnderline(true);
	    waterHeaterLabel.setTextAlignment(TextAlignment.CENTER);
	    Label waterHeaterInfo = new Label("Isn't it the worst when you are taking a nice hot shower and then it turns cold? This is because of the\n"
	    								+ "outdated tank water heater. With the new tankless water heater, you can have hot water at any time since\n"
	    								+ "it works by warming water as it passes, instead of storing it for later. It also reduces electricity\n"
	    								+ "usage by 40%!");
	    Button waterHeaterSave = new Button("Save energy!");
	    Button waterHeaterUndo = new Button("Undo");
	    VBox waterHeaterVBox = new VBox(waterHeaterLabel, waterHeaterInfo, waterHeaterSave, waterHeaterUndo);
	    waterHeaterVBox.setSpacing(15);
	    
	    Label waterHeaterInfo2 = new Label("Did you know that most water heaters by default are set to 140 degrees? This is widely considered to\n"
	    								+ "be too hot, and most plummers recommend turning the temperature down to 120 degrees. This will not only\n"
	    								+ "save your skin, but reduce electricity usage by 10%");
	    Button waterHeaterSave2 = new Button("Save energy!");
	    Button waterHeaterUndo2 = new Button("Undo");
	    VBox waterHeaterVBox2 = new VBox(waterHeaterInfo2, waterHeaterSave2, waterHeaterUndo2);
	    waterHeaterVBox2.setSpacing(15);
	    
	    Label devicesLabel = new Label("8. Devices");
	    devicesLabel.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.REGULAR, 20));
	    //devicesLabel.setUnderline(true);
	    devicesLabel.setTextAlignment(TextAlignment.CENTER);
	    Label devicesInfo = new Label("True or false: leaving chargers plugged into the wall while unused uses electricity? While this is true,\n"
	    							+ "the amount of electricity lost is negligible compared to the other changes you could make in the home.\n"
	    							+ "Though, this is one of the most well known 'methods of saving electricity'.");
	    Button devicesSave = new Button("Save energy!");
	    Button devicesUndo = new Button("Undo");
	    VBox devicesVBox = new VBox(devicesLabel, devicesInfo, devicesSave, devicesUndo);
	    devicesVBox.setSpacing(15);
    	
        VBox PAGE3 = new VBox();
        PAGE3.getChildren().addAll(titleLabel1, infoParagraph1, acVBox, acVBox2, lightBulbsVBox, washerVBox, dryerVBox, dryerVBox2, 
        							ovenVBox, dishwasherVBox, waterHeaterVBox, waterHeaterVBox2, devicesVBox);
        PAGE3.setSpacing(20);
        PAGE3.setPadding(new Insets(10, 10, 10, 10));
        
        /*
         * All button functionalities
         */
        
        // A/C 1
        acSave.setOnAction(e -> {
        	changeACSeal(true);
        	update_usage();
        	acInfo.setText("You have now reduced your air conditioning and heating from using 3 kWh to 2.4 kWh.\n"
						+ "That's $230 saved per year from such a simple fix!");
        	acUndo.setDisable(false);
        	acSave.setDisable(true);
        	//totalCostSaved += 230;
        	
        });
        acUndo.setDisable(true);
        acUndo.setOnAction(e -> {
        	changeACSeal(false);
        	update_usage();
        	acInfo.setText("According to the National Resources Defense Council (NRDC), you can save up to 20% on your\n"
    						+ "air conditioning and heating bill simply by sealing any cracks in your home. This includes\n"
    						+ "resealing any windows, doors, or misc. areas that have openings to the outside world!");
        	acSave.setDisable(false);
        	acUndo.setDisable(true);
        	//totalCostSaved -= 230;
        });
        
        // A/C 2
        acSave2.setOnAction(e -> {
        	changeACSmart(true);
        	update_usage();
        	acInfo2.setText("You have now reduced your air conditioning and heating from using 3 kWh to 2.4 kWh.\n"
    					+ "That's $230 saved per year from using smart air conditioning and heating strategies!");
        	acUndo2.setDisable(false);
        	acSave2.setDisable(true);
        });
        acUndo2.setDisable(true);
        acUndo2.setOnAction(e -> {
        	changeACSmart(false); // undo
        	update_usage();
        	acInfo2.setText("Another statistic from the NRDC: Did you know that by using a 'smart' air conditioning and\n"
					+ "heating strategy, you can reduce your cooling and heating bill by 20%? These strategies\n"
					+ "include turning the fan, opening windows, and closing blinds instead of air conditioning.");
        	acSave2.setDisable(false);
        	acUndo2.setDisable(true);
        });
        
        // Lightbulbs
        lightBulbsSave.setOnAction(e -> {
        	changeLightBulbs(true);
        	update_usage();
        	lightBulbsInfo.setText("You have now replaced all of your lightbulbs to LEDS and reduced your electricity \n"
        						+ "consumption by 90%! In an average household, this would save around $200 a year!");
        	lightBulbsSave.setDisable(true);
        	lightBulbsUndo.setDisable(false);
        });
        lightBulbsUndo.setDisable(true);
        lightBulbsUndo.setOnAction(e -> {
        	changeLightBulbs(false); // undo
        	update_usage();
        	lightBulbsInfo.setText("Lightbulbs have undergone significant improvements over the last 20 years. The\n"
	    								+ "most notable of all of these improvements are the LED lightbulbs which use 90% \n"
	    								+ "less electricity than older modeled lightbulbs!");
        	lightBulbsSave.setDisable(false);
        	lightBulbsUndo.setDisable(true);
        });
        
        // Washer
        washerSave.setOnAction(e -> {
        	changeWasher(true);
        	update_usage();
        	washerInfo.setText("Using the cold setting, you have saved over 50% of the electricity you would have normally\n"
        					+ "used while washing your clothes. You have also saved around $150 over the span of a year!");
        	washerSave.setDisable(true);
        	washerUndo.setDisable(false);
        });
        washerUndo.setDisable(true);
        washerUndo.setOnAction(e -> {
        	changeWasher(false); // undo
        	update_usage();
        	washerInfo.setText("Have you ever used the cold function on your washer? Did you know that the cold function\n"
	    							+ "minimizes wear on clothes and allows for you to mix colors and whites? Finally, did you\n"
	    							+ "know that you can use over 50% less energy simply by using the cold setting on your washer?");
        	washerSave.setDisable(false);
        	washerUndo.setDisable(true);
        });
        
        // Dryer 1
        dryerSave.setOnAction(e -> {
        	changeDryer(true);
        	update_usage();
        	dryerInfo.setText("Now that you have invested in a new dryer, you can enjoy dry clothes and know that you are\n"
        				+ "saving yourself $50 a year!");
        	dryerSave.setDisable(true);
        	dryerUndo.setDisable(false);
        });
        dryerUndo.setDisable(true);
        dryerUndo.setOnAction(e -> {
        	changeDryer(false); // undo
        	update_usage();
        	dryerInfo.setText("The design of the dryer has been reworked in the last few years. One interesting design is the\n"
	    							+ "heat pump electric dryer. This dryer uses no generated heat to dry clothes and reduces electricity\n"
	    							+ "usage by up to 40%. Though, there may be an even cheaper alternative...");
        	dryerSave.setDisable(false);
        	dryerUndo.setDisable(true);
        });
        
        // Dryer 2
        dryerSave2.setOnAction(e -> {
        	changeDryerHangUp(true);
        	update_usage();
        	dryerInfo2.setText("You are now hang drying your clothes! This could be a fun way to dry your clothes and leaves you\n"
        			+ "with an extra $130 to spend a year!");
        	dryerSave2.setDisable(true);
        	dryerUndo2.setDisable(false);
        });
        dryerUndo2.setDisable(true);
        dryerUndo2.setOnAction(e -> {
        	changeDryerHangUp(false); // undo
        	update_usage();
        	dryerInfo2.setText("There is a more primitive way to save electricity with your dryer, and that is by not using one\n"
	    							+ "at all! Hang drying your clothes can reduce your electricty usage from your dryer by 100%!");
        	dryerSave2.setDisable(false);
        	dryerUndo2.setDisable(true);
        });
        
        // Oven
        ovenSave.setOnAction(e -> {
        	changeOven(true);
        	update_usage();
        	ovenInfo.setText("You have now switched to a gas oven/stove! While this will not impact your overall yearly costs when\n"
        					+ "loooking at electricity, gas ovens can be cheaper than electric ovens! Your electric oven cost will no\n"
        					+ "longer be considered.");
        	ovenSave.setDisable(true);
        	ovenUndo.setDisable(false);
        });
        ovenUndo.setDisable(true);
        ovenUndo.setOnAction(e -> {
        	changeOven(false); // undo
        	update_usage();
        	ovenInfo.setText("Unfortunately, we have reached the point in electric ovens where the electricity to heat conversion\n"
					+ "cannot become any more efficient. However, opting for a gas oven/stove uses resources 3x more efficiently!\n"
					+ "Some say that gas ovens/stoves even cook better.");
        	ovenSave.setDisable(false);
        	ovenUndo.setDisable(true);
        });
        
        // Dishwasher
        dishwasherSave.setOnAction(e -> {
        	changeDishWasher(true);
        	update_usage();
        	dishwasherInfo.setText("By using a new dishwasher, you'll have cleaner dishes and lower electricity costs! You will save\n"
        							+ "around $50 a year. Did you know that washing dishes by hand uses 3x more electricity than a \n"
        							+ "dishwasher does? Invest in a new effective dishwasher!");
        	dishwasherSave.setDisable(true);
        	dishwasherUndo.setDisable(false);
        });
        dishwasherUndo.setDisable(true);
        dishwasherUndo.setOnAction(e -> {
        	changeDishWasher(false); // undo
        	update_usage();
        	dishwasherInfo.setText("Sometimes your old dishwasher can lead to you having to do an entirely second wash. Whether this is\n"
        						+ "before you load the plates, or after, a new effective dishwasher could fix this. A new dishwasher could\n"
        						+ "also lead to energy use reduction of 66%!");
        	dishwasherSave.setDisable(false);
        	dishwasherUndo.setDisable(true);
        });
        
        // Waterheater 1
        waterHeaterSave.setOnAction(e -> {
        	changeWaterHeater(true);
        	update_usage();
        	waterHeaterInfo.setText("After installing your new water heater you will now enjoy hot water on the fly and an extra $200 to spend!");
        	waterHeaterSave.setDisable(true);
        	waterHeaterUndo.setDisable(false);
        });
        waterHeaterUndo.setDisable(true);
        waterHeaterUndo.setOnAction(e -> {
        	changeWaterHeater(false); // undo
        	update_usage();
        	waterHeaterInfo.setText("Isn't it the worst when you are taking a nice hot shower and then it turns cold? This is because of the\n"
					+ "outdated tank water heater. With the new tankless water heater, you can have hot water at any time since\n"
					+ "it works by warming water as it passes, instead of storing it for later. It also reduces electricity\n"
					+ "usage by 40%!");
        	waterHeaterSave.setDisable(false);
        	waterHeaterUndo.setDisable(true);
        });
        
        // Waterheater 2
        waterHeaterSave2.setOnAction(e -> {
        	changeWaterHeaterColder(true);
        	update_usage();
        	waterHeaterInfo.setText("This no brainer of a change to the water heater will save you $50 a year and allow for you to enjoy your\n"
        							+ "hot water without boiling your skin. There is no reason not to do this!");
        	waterHeaterSave2.setDisable(true);
        	waterHeaterUndo2.setDisable(false);
        });
        waterHeaterUndo2.setDisable(true);
        waterHeaterUndo2.setOnAction(e -> {
        	changeWaterHeaterColder(false); // undo
        	update_usage();
        	waterHeaterInfo.setText("Did you know that most water heaters by default are set to 140 degrees? This is widely considered to\n"
	    								+ "be too hot, and most plummers recommend turning the temperature down to 120 degrees. This will not only\n"
	    								+ "save your skin, but reduce electricity usage by 10%");
        	waterHeaterSave2.setDisable(false);
        	waterHeaterUndo2.setDisable(true);
        });
        
        // Devices
        devicesSave.setOnAction(e -> {
        	changeDevices(true);
        	update_usage();
        	devicesInfo.setText("If you are very adament about unplugging unused devices and unplugging your chargers throughout an entire year,\n"
        						+ "you will find yourself saving about 50 cents per year. Compared to all of the other changes you could make in\n"
        						+ "this list, this is the least effective method.");
        	devicesSave.setDisable(true);
        	devicesUndo.setDisable(false);
        });
        devicesUndo.setDisable(true);
        devicesUndo.setOnAction(e -> {
        	changeDevices(false); // undo
        	update_usage();
        	devicesInfo.setText("True or false: leaving chargers plugged into the wall while unused uses electricity? While this is true,\n"
	    							+ "the amount of electricity lost is negligible compared to the other changes you could make in the home.\n"
	    							+ "Though, this is one of the most well known 'methods of saving electricity'.");
        	devicesSave.setDisable(false);
        	devicesUndo.setDisable(true);
        });
        
        /*
         * End of button functionalities
         */
        
        ScrollPane scrollPane3 = new ScrollPane();
        scrollPane3.setContent(PAGE3);
        
        return new BorderPane(scrollPane3);
    }
    
    /*
     * ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~~ ~ ~ ~ ~ ~ ~
     * ~ METHODS TO BE USED BY BUTTONS ~
     * ~ Change current elec. values ~ ~
     * ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
     */
    
    private void changeACSeal(boolean toNew) {
    	if(toNew) {
    		// set new C_CENTRAL
    		C_CENTRAL = KWH_CENTRAL*8*0.8;
    	} else {
    		// sealing up the house
    		C_CENTRAL = KWH_CENTRAL * 8;
    	}
    }
    
    private void changeACSmart(boolean toNew) {
    	if(toNew) {
    		// set new C_CENTRAL
    		C_CENTRAL = KWH_CENTRAL*8*0.8; // save by another 20%
    	} else {
    		// sealing up the house
    		C_CENTRAL = KWH_CENTRAL * 8;
    	}
    }
    
    private void changeLightBulbs(boolean toNew) {
    	if(toNew) {
    		// Set new value of lightbulbs --> TO ALL LED
    		C_LIGHTBULBS = AVG_NUM_LIGHTBULBS * KWH_LED; 
    	} else {
    		// reset to old values
    		C_LIGHTBULBS = AVG_NUM_LIGHTBULBS * 0.5 * KWH_INCANDESCENT + AVG_NUM_LIGHTBULBS * 0.5 * KWH_LED; 
    	}
    }
    
    private void changeWasher(boolean toNew) {
    	if(toNew) {
    		// set new C_WASHER
    		C_WASHER = KWH_WASHER_PER_LOAD_COLD;
    	} else {
    		// reset to old values
    		C_WASHER = KWH_WASHER_PER_LOAD_HOT;
    	}
    }
    
    private void changeDryer(boolean toNew) {
    	if(toNew) {
    		// set new dryer that uses heat pump
    		C_DRYER = KWH_PUMP_DRYER_PER_LOAD;
    	} else {
    		// reset to old values
    		C_DRYER = KWH_DRYER_PER_LOAD;
    	}
    }
    
    private void changeDryerHangUp(boolean toNew) {
    	if(toNew) {
    		// value of 0
    		C_DRYER = KWH_AIR_DRYER_LOAD;
    	} else {
    		// reset to old values
    		C_DRYER = KWH_DRYER_PER_LOAD;
    	}
    }
    
    private void changeOven(boolean toNew) {
    	if(toNew) {
    		// if a gas stove is bought
    		C_OVEN = 0;
    	} else {
    		// reset to old values
    		C_OVEN = KWH_DRYER_PER_LOAD;
    	}
    }
    
    private void changeDishWasher(boolean toNew) {
    	if(toNew) {
    		// set new C_DISHWASHER
    		C_DISHWASHER = KWH_DISHWASHER_SAVE_LOAD;
    	} else {
    		// reset to old values
    		C_DISHWASHER = KWH_DISHWASHER_NORMAL_LOAD;
    	}
    }
    
    private void changeWaterHeater(boolean toNew) {
    	if(toNew) {
    		// purcahse new water heater
    		C_WATERHEATER = KW_TANKLESSWATERHEATER_DAILY;
    	} else {
    		// reset to old values
    		C_WATERHEATER = KW_WATERHEATER_DAILY;
    	}
    }
    
    private void changeWaterHeaterColder(boolean toNew) {
    	if(toNew) {
    		// set new C_WATERHEATER
    		C_WATERHEATER = KW_COLDERWATERHEATER_DAILY;
    	} else {
    		// reset to old values
    		C_WATERHEATER = KW_WATERHEATER_DAILY;
    	}
    }
    
    private void changeDevices(boolean toNew) {
    	if(toNew) {
    		// set new C_DEVICES
    		C_DEVICES = KW_DEVICESUNPLUGGED_DAILY;
    	} else {
    		// reset to old values
    		C_DEVICES = KW_DEVICES_DAILY;
    	}
    }

}

