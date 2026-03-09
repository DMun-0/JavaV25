package prosjekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PO  implements P{
    private double h;
    private double a=0;
    private int y=1;
    private int x=1;
    private Character rpc='n';
    Random r= new Random();


    PO(){
        

    }

    public void refreshRPC(){
        ArrayList<Character> temp = (ArrayList<Character>)List.of('n','r','p','c');
        int randint= r.nextInt(4); 
        rpc=temp.get(randint);
    }


    @Override
    public void recieve(Double amount){
        System.out.println("Start H (Opp.): "+h);
        double number=amount;
        if (a!=0) {
            number-=a;
        }
        System.out.println(number+" removed");
        h-=number;
        System.out.println("End H (Opp.): "+h);
        System.out.println();

    }
    @Override
    public Character getRPC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRPC'");
    }

    @Override
    public void use(Chip chip, P person) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'use'");
    }
    
}
