package prosjektEdit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProsjektTest {
    gameController game;
    gameFileManager manager;
    P p1;
    P p2;
    ArrayList<Chip> chips;

    @BeforeEach
    public void start(){
        manager=new gameFileManager();
        game= new gameController();
        game.initGame();
        p1=game.getP1();
        p2=game.getP2();
        chips=game.getChips();

    }

    @Test
    public void testPlayerH(){
        //sjekke at hp er i range
        assertTrue(p1.getH()>=100&&p1.getH()<=149);
        assertTrue(p2.getH()>=100&&p2.getH()<=149);        
    }
    @Test
    public void testPlayerA(){
        //sjekke at armour er i range
        assertTrue(p1.getA()>=0&&p1.getA()<=20);
        assertTrue(p2.getA()>=0&&p2.getA()<=20);        
    }

    @Test
    public void testChip(){
        //sjekke at 3 chips dukker opp
        assertEquals(3, chips.size());

        //sjekke at chip blir refreshed
        Chip previousChip=chips.get(0);
        game.refreshChips();
        Chip currentChip = chips.get(0);
        assertTrue(previousChip!=currentChip);
   
    }
    
    @Test
    public void testDmg(){
        //sjekke at dmg blir påført og at ar fungerer as intended
        while (p1.getA()!=0&&p1.getRPC()!='N') {
            p1= new PC();
        }
        while (chips.get(0).isDispersed()) {
            game.refreshChips();
            chips=game.getChips();
        }
        int previous = p1.getH();
        chips.get(0).inflict(p1);
        int current = p1.getH();
        assertTrue(previous>current);


        
        //sjekke multipliers
        //2x
        Chip theChip=chips.get(0);
        while (p2.getRPC()!='P') {
            p2=new PC();
        }
        while (theChip.getRPC()!="C".charAt(0)||(theChip.isDispersed())) {
            game.refreshChips();
            chips=game.getChips();
            theChip=chips.get(0);
        }
        int preMulti=p2.getH();
        theChip.inflict(p2);
        int postMulti=p2.getH();
        assertTrue(preMulti-postMulti==((theChip.getDamage()*2)-p2.getA()));

        //1x
        theChip=chips.get(0);
        while (p2.getRPC()!='N') {
            p2=new PC();
        }
        preMulti=p2.getH();
        theChip.inflict(p2);
        postMulti=p2.getH();
        assertTrue(preMulti-postMulti==((theChip.getDamage())-p2.getA()));

        //0.5x
        p2=new PC();
        theChip=chips.get(0);
        while (p2.getRPC()!='P') {
            p2=new PC();
        }
        while (theChip.getRPC()!='R' || (theChip.isDispersed()) ||!(theChip.getDamage()==50)) {
            game.refreshChips();
            chips=game.getChips();
            theChip=chips.get(0);
        }
        preMulti=p2.getH();
        theChip.inflict(p2);
        postMulti=p2.getH();
        assertTrue(preMulti-postMulti==((theChip.getDamage()*0.5)-p2.getA()));        

    }

    @Test
    public void testLagring(){
        //sjekke at formatet stemmer for lagring til fil
        assertEquals(game.saveFile(), game.loadFile());
    }

    @Test
    public void test(){
        // :)
    }
}
