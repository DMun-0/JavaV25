package prosjektEdit;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class ProjectAppController {

    gameController game = new gameController();
    P p1 = game.getP1();
    P p2 = game.getP2();
    private ArrayList<Chip> chips = game.getChips();
    private Chip chosen = game.getChosen();
    private boolean killconfirmer = false;
    private Timeline timeline;
    private boolean gameOn=false;

    @FXML
    private ProgressBar customBar = new ProgressBar();

    @FXML
    private ChoiceBox<Chip> chipBox;

    @FXML
    private DialogPane outputText;

    @FXML
    private DialogPane p1InfoBox, p2InfoBox, chip1InfoBox, chip2InfoBox, chip3InfoBox;

    private void initGameApp() {
        game.initGame();   
        
        updateVars();
        refreshInfoTextApp();
        refreshChipsApp();
        outputText.setContentText(
                "Armor: flat damage reduction\nModifiers:\n2x\n1x\n0.5x");

        //løste flere timelines problemet
        if (!gameOn) {
            customStart();
        }
        else{
            customRestart();
        }
        gameOn=true;
    }
    private void updateVars(){
        p1 = game.getP1();
        p2 = game.getP2();
        chips = game.getChips();
        chosen = game.getChosen();
    }

    private void refreshInfoTextApp() {

        
        //kunne ha hatt nedre lagt inn som toString() og migrert ut av AppController
        String text = "P1 Health: " + p1.getH() +
                "\nP1 Armor: " + p1.getA() +
                "\nP1 Type: " + p1.getRPC();

        String text2 = "P2 Health: " + p2.getH() +
                "\nP2 Armor: " + p2.getA() +
                "\nP2 Type: " + p2.getRPC();

        p1InfoBox.setContentText(text);
        p2InfoBox.setContentText(text2);
        p1InfoBox.setHeaderText("Killcounter: " + String.valueOf(game.getKillCounter()));
        p2InfoBox.setHeaderText(game.getActionCounter() + " turns before retaliation");

    }

    private void refreshChipsApp() {
        game.refreshChips();
        updateVars();
        Chip chip1 = chips.get(0);
        Chip chip2 = chips.get(1);
        Chip chip3 = chips.get(2);

        chip1InfoBox.setContentText(chip1.toString());
        chip2InfoBox.setContentText(chip2.toString());
        chip3InfoBox.setContentText(chip3.toString());

        chipBox.getItems().clear();

        chipBox.getItems().add(chip1);
        chipBox.getItems().add(chip2);
        chipBox.getItems().add(chip3);

    }

    private void refreshOppApp() {
        game.refreshOpp();
        updateVars();

        //skal egentlig oppdatere tekst langsomt for å vise ulike stadier av gamestates
        //ved hjelp av sleeper. Tipper det må lages separate threads?
        outputText.setContentText("refreshing to new opponent");
        sleeper();
        refreshInfoTextApp();
    }

    @FXML
    private void handleButtonClickTest() {
        outputText.setContentText("initting");
        sleeper();
        initGameApp();

    }

    @FXML
    private void handleSelectedChip() {
        game.setChosen(chipBox.getSelectionModel().getSelectedItem());
        updateVars();
    }

    @FXML
    private boolean handleButtonBeef() {
        if (chosen == null) {
            outputText.setContentText("ingen chip valgt");
            return false;
        }

        outputText.setContentText("chip playing");
        sleeper();

        //kunne ha migrert ut nedre logikk som en getDamageDiff()
        int start = p2.getH();
        game.playerTurn();
        start -= p2.getH();

        if (p2.getH() <= 0) {
            refreshOppApp();
            game.addKill();
            game.setKillconfirmer(true);
        }
        if (game.getActionCounter() == 0) {
            game.oppTurn();
        }

        outputText.setContentText("Chip played.\n" + start + " done.");
        if (killconfirmer) {
            outputText.setContentText("opponent refreshed\nkillcounter updated");
        }
        game.setKillconfirmer(false);

        refreshInfoTextApp();
        refreshChipsApp();
        game.setChosen(null);

        customRestart();

        return true;
    }

    //forsøk på artifical delay ved input
    public void sleeper() {
        try {
            // Thread.sleep(1500);
        } catch (Exception e) {
            throw new UnknownError("Hvordan havner man her?");
        }
    }

    public void customEnd() {

        game.actionTaken();

        if (game.getActionCounter() == 0) {
            game.oppTurn();
            game.youDied();
        }

        refreshChipsApp();
        refreshInfoTextApp();

        customRestart();
    }

    public void customRestart() {
        customBar.progressProperty().setValue(0);
        timeline.stop();
        customStart();
        timeline.playFromStart();
    }

    public void customStart() {

        // låner noe kode som smoother ut progressbar
        //https://stackoverflow.com/questions/38773124/how-to-get-javafx-timeline-to-increase-by-second-and-to-bind-to-a-progressbar
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(customBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(30), e -> {
                    // do anything you need here on completion...
                    System.out.println("Time over");
                    outputText.setContentText("Timer ran out.\nTurn skipped.");
                    customEnd();
                }, new KeyValue(customBar.progressProperty(), 1)));

        timeline.setCycleCount(1);
        timeline.playFromStart();
    }

    public void saveFileApp() {
        outputText.setContentText(
            "game loaded");
            if (game.saveFile()=="whoops") {
                outputText.setContentText(
                    "failed saving");
            }
    }

    public void loadFileApp() {
        if (!gameOn) {
            initGameApp();
        }

        if (game.loadFile()=="whoops") {
            outputText.setContentText(
                "ingen fil lagret");
        }
        else{
            p1 = game.getP1();
            p2 = game.getP2();
            chips = game.getChips();
            refreshChipsApp();
            refreshInfoTextApp();
            customRestart();
            outputText.setContentText(
                    "game loaded");
        
            }
    }
}