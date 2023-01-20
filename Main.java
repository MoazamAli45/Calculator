package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


public class Main extends Application {
	private TextField textField=new TextField();
	private TextField historyField=new TextField();
	private long num1=0;
	private String opString="";
	private boolean start=true;
	@Override
	public void start(Stage primaryStage) {
		textField.setFont(Font.font(20));
		textField.setPrefHeight(50);
		
	   historyField.setFont(Font.font(15));
		
		textField.setStyle("-fx-base: Black; -fx-text-fill:White;");
		historyField.setStyle("-fx-base:#74b9ff;");
		
		// by default text left 
		textField.setAlignment(Pos.CENTER_RIGHT);
		
		// User can't type
		textField.setEditable(false);
		historyField.setEditable(false);
		
		// For History Field
		GridPane gd1=new GridPane();
	    gd1.setPadding(new Insets(10));
		gd1.addRow(0,historyField);
		/// For text Field
		gd1.addRow(1,textField);
   	    
	gd1.setAlignment(Pos.CENTER);
   	   gd1.setVgap(10);
 
//   	    for Buttons
      GridPane gridPane=new GridPane();
      gridPane.setHgap(10);
      gridPane.setVgap(10);
      
   gridPane.setAlignment(Pos.CENTER);
   gridPane.addRow(0,makeButtonNumber("7"),
		   makeButtonNumber("8"),
		   makeButtonNumber("9"),
		   makeButtonOp("/")
		   );
   gridPane.addRow(1,makeButtonNumber("4"),
		   makeButtonNumber("5"),
		   makeButtonNumber("6"),
		   makeButtonOp("x")
		   );
   gridPane.addRow(2,makeButtonNumber("1"),
		   makeButtonNumber("2"),
		   makeButtonNumber("3"),
		   makeButtonOp("-")
		   );
   gridPane.addRow(3,makeButtonNumber("0"),
		   makeButtonClear("Clear"),
		   makeButtonOp("="),
		   makeButtonOp("+")
		   );
   
		
		
		BorderPane borderPane=new BorderPane();
	
		borderPane.setTop(gd1);
		borderPane.setCenter(gridPane);

		Scene scene=new Scene(borderPane,250,350);
		primaryStage.setTitle("Calculator Assignment ");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
		
	}
	private Button makeButtonNumber(String num) {
		Button btn=new Button(num);
		btn.setFont(Font.font(18));
		btn.setStyle("-fx-base: #3498db;");
		btn.setPrefWidth(50);
		btn.setPrefHeight(50);
		// method reference operator in java 
		//refer to method 
		btn.setOnAction(this::processNumbers);
	
		return btn;
	}
// process method for numbers
	private void processNumbers(ActionEvent e) {
	if(start) {
		textField.setText("");
		start=false;
	}
//	   Converting Target into button
	String valueString=((Button)e.getSource()).getText();
//	System.out.println(e.getSource());
	
	textField.setText(textField.getText()+valueString);
}
	
	private Button makeButtonOp(String ch) {
		Button btn=new Button(ch);
		btn.setStyle("-fx-base: #34495e;");
		btn.setFont(Font.font(18));
		btn.setPrefWidth(50);
		btn.setPrefHeight(50);
		btn.setOnAction(this::processOp);
		return btn;
	}
	
	private void processOp(ActionEvent e)
	{   	String valueString=((Button)e.getSource()).getText();

	// Guard Condition	
	if(!valueString.equals("=")){
		 
		if(!opString.isEmpty())  // if not empty then return  as if already plus is pressed
			 return;
		 
		num1=Long.parseLong(textField.getText());
			opString=valueString
					;
			//////Converting number into text
//			textField.setText(textField.getText()+opString);
			textField.setText("");
		}
	else {
//		  After Equal to 
		if(opString.isEmpty())  
			return;
		
		long num2=Long.parseLong(textField.getText());
		float result=calculate(num1,num2,opString);
		 // For INFINITY
		if(result==0 && opString=="/" && num2==0) {
			textField.setText("INFINITY");
			historytext("INFINITY");
		}
		else {
		historytext(num1, num2, result,opString);
		textField.setText(num1+" "+opString+" "+num2+" = "+String.valueOf(result));
		
		}
		num1=0;
		num2=0;
		opString="";
		start=true;
	}
		
	}
// For calculation
	public float calculate(long num1,long num2, String op ) {
		switch(op) {
		case "+":   return num1+num2;
		case "-":   return num1-num2;
		case "x":   return num1*num2;
		case "/": 
			if(num2==0)
  			return 0;
			return num1/num2;
		default:  return 0;
		}
	}
	
	private Button makeButtonClear (String ch) {
		Button btn=new Button(ch);
//		btn.setFont(Font.font(18));
		 btn.setStyle("-fx-base: #ee2211;");
		btn.setPrefWidth(50);
		btn.setPrefHeight(50);
	    btn.setOnAction(e->{
	    textField.setText(" ");
	    opString="";
	    start=true;
	    });
		return btn;
	}
	public void historytext(long num1,long num2,float result,String op) {
		historyField.setText(" ");
		historyField.setText("Last Result: "+num1+op+num2+"="+result);
	}
	public void historytext(String value) {
		historyField.setText(value);
	}
}


