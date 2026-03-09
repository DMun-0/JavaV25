package prosjektEdit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class gameFileManager {

    public String saveFile(gameController game) {
        //lagrer tilstand
        P p1 = game.getP1();
        P p2 = game.getP2();
        int killCounter = game.getKillCounter();
        int actionCounter = game.getActionCounter();
        try {
            FileWriter file = new FileWriter("save.txt");
            String object1 = p1.getH() + "." + p1.getA() + "." + p1.getRPC() + ".";
            String object2 = p2.getH() + "." + p2.getA() + "." + p2.getRPC() + ".";
            String text = object1 + object2 + killCounter + "." + actionCounter;
            file.write(text);
            file.close();
            System.out.println("save success");
            return text;
        } catch (IOException e) {
            System.out.println("failed saving");
            e.printStackTrace();
            return"whoops";
        }

    }

    public String loadFile(gameController game) {
        //erstatter tilstand med lagret
        //reroller likevel chips for savescumming (feature not a bug)
        try {
            File save = new File("save.txt");
            Scanner reader = new Scanner(save);
            String out1 = "";
            while (reader.hasNextLine()) {
                out1 += reader.nextLine();
            }
            reader.close();

            String[] out2 = out1.split("[.*]");
            for (String item : out2) {
                item.replaceAll("[.*]", "");
            }

            P p1 = new PC(out2[0], out2[1], out2[2]);
            P p2 = new PC(out2[3], out2[4], out2[5]);
            game.setP1(p1);
            game.setP2(p2);
            game.setKillCounter(Integer.valueOf(out2[6]));
            game.setActionCounter(Integer.valueOf(out2[7]));
            game.refreshChips();
            System.out.println("load success");
            return out1;

        } catch (FileNotFoundException e) {
            System.out.println("load failed");
            return "whoops";
        }
    }

}
