package prosjektEdit;

import java.util.ArrayList;

public class gameController {
    private P p1, p2;
    private ArrayList<Chip> chips = new ArrayList<>();
    private Chip chosen = null;
    private int killCounter = 0;
    private int actionCounter;
    private boolean killconfirmer = false;
    private gameFileManager manager = new gameFileManager();

    public void initGame() {
        //rigger oppsett
        p1 = new PC();
        p2 = new PC();
        killCounter = 0;
        actionCounter = 3;
        refreshChips();
    }

    public void oppTurn() {
        //spiller chip, reseter actioncounter
        Chip oppChip = new Chip();
        oppChip.inflict(p1);
        resetActionCounter();
    }

    public void playerTurn() {
        //spiller chip, incr. actioncounter
        chosen.inflict(p2);
        actionTaken();
    }

    public void refreshOpp() {
        //ny opponent
        p2 = new PC();
        resetActionCounter();
        youDied();
    }

    public void refreshChips() {
        //lager nye chips
        Chip newChip1 = new Chip();
        Chip newChip2 = new Chip();
        Chip newChip3 = new Chip();
        chips.clear();
        chips.add(newChip1);
        chips.add(newChip2);
        chips.add(newChip3);
    }
    public boolean deadCheck(){
        if (p1.getH()<=0) {
            return true;
        }
        return false;
    }
    public boolean youDied(){
        if (deadCheck()) {
            p1=new PC();
            resetActionCounter();
            resetKillCounter();
            return true;
        }
        return false;
    }

    private void resetKillCounter() {
        killCounter=0;
    }

    public P getP1() {
        return p1;
    }

    public P getP2() {
        return p2;
    }

    public void setP1(P p) {
        p1 = p;
    }

    public void setP2(P p) {
        p2 = p;
    }

    public ArrayList<Chip> getChips() {
        return chips;
    }

    public Chip getChosen() {
        return chosen;
    }

    public int getKillCounter() {
        return killCounter;
    }

    public int getActionCounter() {
        return actionCounter;
    }

    public boolean isKillconfirmer() {
        return killconfirmer;
    }

    public void setKillconfirmer(boolean bool) {
        killconfirmer = bool;
    }

    public void actionTaken() {
        actionCounter--;
    }

    public void resetActionCounter() {
        actionCounter = 3;
    }

    public void setChosen(Chip chip) {
        chosen = chip;
    }

    public void addKill() {
        killCounter++;
    }

    public void setKillCounter(int k) {
        killCounter = k;
    }

    public void setActionCounter(int a) {
        actionCounter = a;
    }

    public String saveFile() {
        return manager.saveFile(this);
    }

    public String loadFile() {
        return manager.loadFile(this);
    }

    public static void main(String[] args) {
        gameController game = new gameController();

        System.out.println("a");
        PC p1 = new PC();
        PC p2 = new PC();
        String object1 = p1.getH() + "." + p1.getA() + "." + p1.getRPC() + ".";
        String object2 = p2.getH() + "." + p2.getA() + "." + p2.getRPC() + ".";
        String text = object1 + object2 + 10 + "." + 7;

        System.out.println(text);
        String[] out2 = text.split("[.*]");
        System.out.println();
        System.out.println(out2[0]);

        p1 = new PC(out2[0], out2[1], out2[2]);
        p2 = new PC(out2[3], out2[4], out2[5]);
        game.setKillCounter(Integer.valueOf(out2[6]));
        game.setActionCounter(Integer.valueOf(out2[7]));
        System.out.println(game.getKillCounter());
    }
}
