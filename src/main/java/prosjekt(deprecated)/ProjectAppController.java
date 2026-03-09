package prosjekt;

import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.util.Duration;

public class ProjectAppController {

    @FXML
    private ProgressBar customBar=new ProgressBar();

    @FXML
    private ChoiceBox<Chip> chipBox;

    @FXML
    private DialogPane outputText;

    @FXML
    private DialogPane p1InfoBox, p2InfoBox, chip1InfoBox, chip2InfoBox, chip3InfoBox;

    private P p1, p2;
    private ArrayList<Chip> chips = new ArrayList<>();
    private Chip chosen = null;
    private int killCounter = 0;
    private int actionCounter = 3;
    private boolean killconfirmer=false;


    private void initGame() {
        p1 = new PC();
        p2 = new PC();
        refreshInfoText();
        refreshChips();
        outputText.setContentText(
                "Armor: flat damage reduction\nModifiers:\n2x\n1x\n0.5x");
        System.out.println("test");
        customStart();
    }

    // public static void println(String s){
    // Platform.runLater(new Runnable() {//in case you call from other thread
    // @Override
    // public void run() {
    // outputText.setContentText(outputText.getContentText()+s+"\n");
    // System.out.println(s);//for echo if you want
    // }
    // });
    // }

    private void refreshInfoText() {

        String text = "P1 Health: " + p1.getH() +
                "\nP1 Armor: " + p1.getA() +
                "\nP1 Type: " + p1.getRPC();

        String text2 = "P2 Health: " + p2.getH() +
                "\nP2 Armor: " + p2.getA() +
                "\nP2 Type: " + p2.getRPC();

        p1InfoBox.setContentText(text);
        p2InfoBox.setContentText(text2);
        p1InfoBox.setHeaderText("Killcounter: " + String.valueOf(killCounter));
        p2InfoBox.setHeaderText(actionCounter+" turns before retaliation");

    }

    private void refreshChips() {
        Chip newChip1 = new Chip();
        Chip newChip2 = new Chip();
        Chip newChip3 = new Chip();
        chips.clear();
        chips.add(newChip1);
        chips.add(newChip2);
        chips.add(newChip3);

        chip1InfoBox.setContentText(chips.get(0).toString());
        chip2InfoBox.setContentText(chips.get(1).toString());
        chip3InfoBox.setContentText(chips.get(2).toString());

        chipBox.getItems().clear();

        chipBox.getItems().add(newChip1);
        chipBox.getItems().add(newChip2);
        chipBox.getItems().add(newChip3);

    }

    private void refreshOpp() {
        p2 = new PC();
        outputText.setContentText("refreshing to new opponent");
        sleeper();
        actionCounter=3;
        refreshInfoText();
    }
    private void oppTurn(){
        Chip oppChip= new Chip();
        oppChip.inflict(p1);
        actionCounter=3;
    }

    @FXML
    private void handleButtonClickTest() {
        outputText.setContentText("initting");
        sleeper();
        initGame();

    }

    @FXML
    private void handleSelectedChip() {
        chosen = chipBox.getSelectionModel().getSelectedItem();
    }

    @FXML
    private boolean handleButtonBeef() {
        if (chosen == null) {
            outputText.setContentText("ingen chip valgt");
            return false;
        }
        outputText.setContentText("chip playing");
        sleeper();

        chosen.inflict(p2);
        actionCounter--;

        if (p2.getH() <= 0) {
            refreshOpp();
            killCounter++;
            killconfirmer=true;
        }
        if (actionCounter==0) {
            oppTurn();
        }


        outputText.setContentText("chip played");
        if (killconfirmer) {
        outputText.setContentText("opponent refreshed\nkillcounter updated");
        }
        refreshInfoText();
        refreshChips();
        chosen = null;
        customRefresh();
        return true;
    }

    public void sleeper() {
        try {
            // Thread.sleep(1500);
        } catch (Exception e) {
            throw new UnknownError("Hvordan havner man her?");
        }
    }

    public void customRefresh(){
        customBar.progressProperty().setValue(0);
        customStart();

    }

    public void customEnd(){
        actionCounter--;
        if (actionCounter==0) {
            oppTurn();
        }
        refreshChips();
        refreshInfoText();
        customBar.progressProperty().setValue(0);
        customRefresh();
    }



    public void customStart(){

    //yoinked noe kode som smoother ut progressbar
    Timeline timeline = new Timeline(
        new KeyFrame(Duration.ZERO, new KeyValue(customBar.progressProperty(), 0)),
        new KeyFrame(Duration.seconds(10), e-> {
            // do anything you need here on completion...
            System.out.println("Time over");
            customEnd();
        }, new KeyValue(customBar.progressProperty(), 1))    
    );
    
    timeline.setCycleCount(1);
    timeline.playFromStart();
    }
}