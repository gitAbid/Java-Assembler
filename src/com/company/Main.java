package com.company;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    Button convert, clear, gotomainprogram, exit;
    TextArea input, output, addresssymbol;
    String dataInput, dataOutput = "", symboOut = "", s = "", o = "", p = "";
    Text text, welcome, welcome1, welcome2, welcome3;
    Scene welcomscene, mainscene;


    public static void main(String[] args) {
        // write your code here

        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox welcomeVbox = new VBox(20);

        welcome = new Text("Welcome");
        welcome1 = new Text("To");
        welcome2 = new Text("Symbolic To Binary Program Converter");
       // welcome3 = new Text("by Md.Abid Hasan");

        welcome.setFont(Font.font("Gotham light", FontWeight.BOLD, 40));
        welcome1.setFont(Font.font("Gotham light", FontWeight.BOLD, 30));
        welcome2.setFont(Font.font("Gotham light", FontWeight.BOLD, 35));
     //   welcome3.setFont(Font.font("Gotham light", FontWeight.BOLD, 25));

        welcomeVbox.setAlignment(Pos.CENTER);

        gotomainprogram = new Button("Lets go....");

        // welcomeVbox.getChildren().addAll(welcome,welcome1,welcome2,welcome3,gotomainprogram);

        welcomeVbox.getChildren().addAll(welcome,welcome1,welcome2);
        welcomeVbox.getChildren().add(gotomainprogram);
        gotomainprogram.setOnAction(e -> {
            primaryStage.setScene(mainscene);
        });
        welcomscene = new Scene(welcomeVbox, 1100, 850);


        primaryStage.setTitle("Symbolic Program To Binary Program Converter[ASSEMBLER] by Md.Abid Hasan");

        convert = new Button("Convert");
        clear = new Button("Clear All");
        exit = new Button("EXIT");
        input = new TextArea();
        output = new TextArea();
        addresssymbol = new TextArea();
        BorderPane border = new BorderPane();
        HBox hBox = new HBox();
        HBox hBox1 = new HBox();
        HBox hBox2 = new HBox();
        text = new Text("Convert Symbolic Program To Binary Program");
        text.setFont(Font.font("Gotham light", FontWeight.BOLD, 25));


        hBox.getChildren().add(input);
        hBox1.getChildren().add(output);
        hBox1.setPadding(new Insets(0, 10, 0, 0));
        hBox2.getChildren().add(addresssymbol);


        VBox vBox = new VBox();
        VBox vBox1 = new VBox();
        vBox.getChildren().addAll(convert, clear, exit);
        vBox1.getChildren().add(text);
        vBox1.setPadding(new Insets(0, 10, 10, 10));

        hBox2.setPadding(new Insets(10, 0, 0, 0));
        hBox2.setHgrow(addresssymbol, Priority.ALWAYS);
        hBox.setHgrow(input, Priority.ALWAYS);
        hBox1.setHgrow(output, Priority.ALWAYS);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.autosize();
        vBox1.setAlignment(Pos.TOP_CENTER);

        border.setLeft(hBox);
        border.setRight(hBox1);
        border.setCenter(vBox);
        border.setBottom(hBox2);
        border.setTop(vBox1);
        border.setPadding(new Insets(10, 10, 10, 10));
        primaryStage.setResizable(false);


        input.setPromptText("Enter Your Symbolic Program Here..........");
        output.setPromptText("Converted Output Binary Program ..........");
        addresssymbol.setPromptText("Generated Symbolic Table ..........");


        input.setFocusTraversable(false);
        output.setFocusTraversable(false);
        addresssymbol.setFocusTraversable(false);


        convert.setOnAction(e -> {
                    dataReturn();
                }
        );

        clear.setOnAction(e -> {
            input.clear();
            output.clear();
            addresssymbol.clear();
        });

        exit.setOnAction(e -> {
            primaryStage.close();
        });


        mainscene = new Scene(border, 1100, 850);

        primaryStage.setScene(welcomscene);
        primaryStage.show();


    }

    public void dataReturn() {

        s = "";
        p = "";

        BufferedWriter in = null;
        try {
            in = new BufferedWriter(new FileWriter("input.txt"));
            BufferedReader out = new BufferedReader(new FileReader("output.txt"));
            BufferedReader symbol = new BufferedReader(new FileReader("symbolTable.txt"));


            dataInput = input.getText();

            in.write(dataInput);
            in.flush();


            First_Pass_Assembler first_pass_assembler = new First_Pass_Assembler();
            first_pass_assembler.First_Pass_Assembler();

            Second_Pass_Assembler second_pass_assembler = new Second_Pass_Assembler();
            second_pass_assembler.Second_Pass_Assembler();

            while ((dataInput = out.readLine()) != null) {
                s = s + "\n" + dataInput;

            }

            while ((symboOut = symbol.readLine()) != null) {
                p = p + "\n" + symboOut;
            }


            output.setText(s);
            addresssymbol.setText(p);
            output.setEditable(false);
            addresssymbol.setEditable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}






