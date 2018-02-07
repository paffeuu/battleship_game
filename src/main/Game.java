package main;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Field;
import objects.Ship;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    final private static File file = new File("high_score.txt");
    public static int highScore;

    public static void main(String[] args)
    {
        loadRecord();
        launch();
        saveRecord();

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

    static void prepareNewGame()
    {
        ArrayList<Ship> ships = new ArrayList<>(shipsOnStage);
        occupiedFields = new ArrayList<>();
        restrictedFields = new ArrayList<>();
        final int aboutHalf = 3;
        Orientation orientationNeeded = null;
        for (int i = 0; i < shipsOnStage; i++)
        {
            Ship ship;
            if (i == aboutHalf)
            {
                orientationNeeded = checkOrientations(ships);
                if (orientationNeeded != null)
                    ship = new Ship(orientationNeeded);
                else
                    ship = new Ship();
            }
            else if (i > aboutHalf)
                if (orientationNeeded != null)
                    ship = new Ship(orientationNeeded);
                else
                    ship = new Ship();
            else
                ship = new Ship();
            ships.add(ship);
            occupiedFields.addAll(ship.getFields());
            restrictedFields.addAll(ship.getRstrFields());
        }

        shotsCounter = 0;
        struckCounter = 0;
        shipsCounter = shipsOnStage;
    }

    private static Orientation checkOrientations(ArrayList<Ship> ships)
    {
        Orientation firstObjectOrientation = ships.get(0).getOrientation();
        for (Ship ship : ships)
            if (!ship.getOrientation().equals(firstObjectOrientation))
                return null;
        Orientation neededOrientation;
        if (firstObjectOrientation.equals(Orientation.VERTICAL))
            neededOrientation = Orientation.HORIZONTAL;
        else
            neededOrientation = Orientation.VERTICAL;
        return neededOrientation;
    }

    private static void loadRecord()
    {
        char[] input = new char[3];
        try (FileInputStream fis = new FileInputStream(file))
        {
            int i = 0;
            while (i < input.length)
            {
                input[i++] = (char)fis.read();
            }
        }
        catch (IOException ioe)
        {ioe.printStackTrace();}
        String sInput = String.valueOf(input);
        highScore = Integer.parseInt(sInput);
    }

    private static void saveRecord()
    {
        String sOutput = String.valueOf(highScore);
        while (sOutput.length() < 3)
            sOutput = "0".concat(sOutput);
        char[] output;
        output = sOutput.toCharArray();
        try (FileOutputStream fos = new FileOutputStream(file))
        {
            int i = 0;
            while(i < output.length)
                fos.write((int) output[i++]);
        }
        catch (IOException ioe)
        {ioe.printStackTrace();}
    }

}
