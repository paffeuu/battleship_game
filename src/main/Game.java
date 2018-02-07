package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Field;
import objects.Ship;

import java.io.IOException;
import java.util.ArrayList;

public class Game extends Application
{
    public static ArrayList<Field> occupiedFields;
    public static ArrayList<Field> restrictedFields;
    public static Stage gameStage;
    public static int shotsCounter;
    public static int struckCounter;
    public static int shipsCounter;
    final public static int shipsOnStage = 5;

    public static void main(String[] args)
    {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("GameStage.fxml"));
        gameStage = new Stage();
        gameStage.setScene(new Scene(root, 950, 650));
        gameStage.setTitle("Gra w Statki");
        gameStage.show();

        prepareNewGame();
    }

    public static void prepareNewGame()
    {
        ArrayList<Ship> ships = new ArrayList<>(shipsOnStage);
        occupiedFields = new ArrayList<>();
        restrictedFields = new ArrayList<>();
        for (int i = 0; i < shipsOnStage; i++)
        {
            Ship ship = new Ship();
            ships.add(ship);
            occupiedFields.addAll(ship.getFields());
            restrictedFields.addAll(ship.getRestrictedFields());
            //           ship.getFields().forEach(field -> System.out.println(field.toString()));
            //           System.out.println();
        }

        shotsCounter = 0;
        struckCounter = 0;
        shipsCounter = shipsOnStage;
    }

}
