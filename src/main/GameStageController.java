package main;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import main.Game;
import objects.Field;
import objects.Ship;

public class GameStageController
{
    final int stageSize = Field.stageSize;

    @FXML
    GridPane fieldsGridPane;

    @FXML
    Button koniecButton;
    @FXML
    Button nowaGraButton;

    @FXML
    Label shipsLabel;

    @FXML
    Label shotsOnTargetLabel;

    @FXML
    Label shotsLabel;

    @FXML
    Label rekordLabel;

    RectangleXY[][] seaSquares;
    final int squareSize = 30;

    public void initialize()
    {

        seaSquares = new RectangleXY[stageSize][stageSize];

        for (int x = 0; x < stageSize; x++)
            for (int y = 0; y < stageSize; y++)
            {
                final int xx = x;
                final int yy = y;
                seaSquares[x][y] = new RectangleXY(Paint.valueOf("grey"));
                fieldsGridPane.add(seaSquares[x][y], x, y);
                seaSquares[x][y].setOnMouseClicked(event -> {
                    Game.shotsCounter++;
                    final Field thisField = Field.create(xx, yy);
                    boolean struck = false;
                    for (Field field : Game.occupiedFields)
                        if (thisField.equals(field))
                        {
                            seaSquares[xx][yy].setStruck();
                            field.getShip().getStruck(field);
                            Game.struckCounter++;
                            if (field.getShip().isStruck())
                            {
                             //   if (Game.shipsCounter != 0)
                                //    throwStruckAlert();
                                setRestrictedArea(field.getShip());
                            }
                            struck = true;
                            break;
                        }
                    if(!struck)
                        seaSquares[xx][yy].setMissed();
                    shipsLabel.setText("Statków do zniszczenia:\n" + Game.shipsCounter);
                    shotsOnTargetLabel.setText("Strzały celne:\n" + Game.struckCounter);
                    shotsLabel.setText("Strzały ogółem:\n" + Game.shotsCounter);
                    rekordLabel.setText("Rekord gry:\n" + Game.highScore);

                    if(Game.shipsCounter == 0)
                    {
                        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
                        gameOverAlert.setTitle("Koniec gry");
                        gameOverAlert.setHeaderText("Gratulacje!");
                        gameOverAlert.setContentText("Udało Ci się zestrzelić wszystkie statki! Gratulacje!\n Twój wynik to " + Game.shotsCounter + " strzałów!");
                        gameOverAlert.show();
                        if (Game.shotsCounter < Game.highScore)
                            Game.highScore = Game.shotsCounter;
                        rekordLabel.setText("Rekord gry:\n" + Game.highScore);
                    }
                });
            }

        nowaGraButton.setOnAction(event -> {
            Game.prepareNewGame();
            initialize();
        });
        koniecButton.setOnAction(event -> Game.gameStage.close());

        shipsLabel.setText("Statków do zniszczenia:\n" + Game.shipsOnStage);
        shotsOnTargetLabel.setText("Strzały celne:\n0");
        shotsLabel.setText("Strzały ogółem:\n0");
        rekordLabel.setText("Rekord gry:\n" + Game.highScore);

    }

    private void throwStruckAlert()
    {
        Alert shipStruckAlert = new Alert(Alert.AlertType.INFORMATION);
        shipStruckAlert.setTitle("Zatopienie!");
        shipStruckAlert.setHeaderText("");
        shipStruckAlert.setContentText("Zatopiono statek!");
        shipStruckAlert.show();
    }

    private void setRestrictedArea(Ship struckShip)
    {
        for (Field rstrField : struckShip.getRstrFields())
            seaSquares[rstrField.getX()][rstrField.getY()].setRestricted();
    }

    private class RectangleXY extends Rectangle
    {

        private RectangleXY(Paint paint)
        {
            setWidth(squareSize);
            setHeight(squareSize);
            setFill(paint);
        }

        private void setStruck()
        {
            setFill(Paint.valueOf("red"));
            setDisable(true);
            setDisabled(true);
        }

        private void setMissed()
        {
            setFill(Paint.valueOf("green"));
            setDisable(true);
            setDisabled(true);
        }

        private void setRestricted()
        {
            setFill(Paint.valueOf("blue"));
            setDisable(true);
            setDisabled(true);
        }
    }

}
