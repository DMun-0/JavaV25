package prosjekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Chip implements ChipInterface{
    private Character rpc='n';
    private int damage=0;
    private Random r= new Random();
    private List<Character> temp =List.of('N','R','P','C');
    private boolean dispersed=false;
    private int dispRange1;
    private int dispRange2;




    Chip(){
        int dispersionRoll=r.nextInt(5);

        //velger randdouble mellom 1-4
        damage= r.nextInt(50,80); 
        
        //velger random rpc
        int randint= r.nextInt(4); 
        rpc=temp.get(randint);

        //hvis dispersion er rolled:
        if (dispersionRoll==1) {
            dispRange1=r.nextInt(100)*-1;
            dispRange2=r.nextInt(100);
            damage= r.nextInt(dispRange1,dispRange2); 
            dispersed=true;

        }
    }


    public double checkMultiplier(P person){
        //multiplier check rock paper scissors
        if ((rpc=='R')&&(person.getRPC()=='C')){
            return 2;
        }
        if ((rpc=='P')&&(person.getRPC()=='R')){
            return 2;
        }
        if ((rpc=='C')&&(person.getRPC()=='P')){
            return 2;
        }
        if (rpc==person.getRPC()||person.getRPC()=='N'||rpc=='N'){
            return 1;
        }
        return 0.5;
    };


    @Override
    public void inflict(P person) {
        double multi= checkMultiplier(person);
        
        if (!(multi==1)) {
            System.out.println(multi+"x multiplier");
        }

        int out=(int)(damage*multi);
        System.out.println("Total: "+out);
        System.out.println();
        person.recieve(out);

    }

    public void inflictMulti(P person, int multi) {
        //multiplier vanlig output med int multi

        int out=damage*(multi);
        if (!(multi==1)) {
            System.out.println(multi+"x multiplier");
        }
        System.out.println("Total: "+out);
        System.out.println();
        person.recieve(out);

    }

    public int getDamage(){
        return damage;
    }

    // public boolean isDispersed(){
    //     return dispersed;
    // }

    @Override
    public String toString(){
        if (dispersed) {
            String text=
            "RANDOM"+
            "\nBase: "+dispRange1+"?? "+(dispRange2-1)+"???";
            return text;
        }
        String text=
        "Chip Type: "+rpc+
        "\nBase: "+damage;
        return text;
    }

    //setters for test
    // public void setDamage(int amount){
    //     this.damage=amount;
    // }

    // public void setRPC(char rpc) {
    //     this.rpc=rpc;
    // }

    

    
        //legacy manuell dispersion constructor
    // Chip(int Dispersion){
    //     //velger randdouble med dispersion som range
    //     damage= r.nextDouble(Dispersion*-1, Dispersion+1); 
        
    //     //velger random rpc
    //     int randint= r.nextInt(4); 
    //     rpc=temp.get(randint);
    // }

    // constructor for test:
    // Chip(int dmg, Character rpc){damage=dmg;this.rpc=rpc;}

}
    

    

