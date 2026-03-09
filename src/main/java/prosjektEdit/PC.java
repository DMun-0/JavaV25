package prosjektEdit;
import java.util.List;
import java.util.Random;

public class PC implements P{
    
    //health og armour
    private int h;
    private int a;

    //x og y koordinater for grid movement (ikke implementert)
    private int y=1;
    private int x=1;

    //rock/paper/scissors/neutral typing
    private Character rpc='n';

    private Random r= new Random();

    //liste av typer for bruk med random ved generering
    private List<Character> temp =List.of('N','R','P','C');

    public PC(){
        //randint verdier for health og armour
        h=r.nextInt(100,149);
        a=r.nextInt(0,20);

        //random typing
        int randint= r.nextInt(4); 
        rpc=temp.get(randint);
    }
    public PC(String he, String ar, String type){
        //spesifikt for loading
        h=Integer.valueOf(he);
        a=Integer.valueOf(ar);
        rpc=type.charAt(0);
        
    }


    
    public void recieve(int amount){
        System.out.println("Start H: "+h);
        int number=amount;

        //reduserer potensiell damage med armour
        if (a!=0) {
            number-=a;

           if (number<0) {
            //unngå negativ verdi
            number=0;
           }
        }

        //hvis fortsatt noe damage igjen; påfør
        if (number>=0) {
            h-=number;
            System.out.println(number+" removed");
        }
        //hvis initial verdi var negativ; heal
        if (amount<0) {
            h-=amount;
            System.out.println(amount+" added");
        }

        System.out.println("End H: "+h);
        System.out.println();

    }

    public void use(Chip chip, P person) {
        //bruker chip på target
        chip.inflict(person);
    }

    public Character getRPC(){
        return this.rpc;
    }

    private void setRPC(char newRPC){
        //setter for testing
        rpc=newRPC;
    }


    public int getH() {
        return h;
    }
 
    public int getA() {
        return a;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }


     
    public static void main(String[] args) {
        // PC pc = new PC(10.0);
        // PC pcA = new PC(10.0,1.0 );

        // pc.setRPC('r');

        // Chip chip1 = new Chip();
        // chip1.inflict(pcA);


        // chip1.inflictMulti(pcA, 2);

    }

    //constructors for testing
    // PC(int h, int a){
    //     if (!(a==null) &&!(h==null)&&!(h<=0)&&!(a<0)) {
    //         this.h=(int)h;
    //         this.a=(int)a;   
    //     }
    //     else{
    //         System.out.println("invalid parameters");
    //     }
    // }
    
    // PC(int h){
    //     if (!(h==null)&&!(h<=0)) {
    //         this.h=(int)h;
    //     }
    //     else{
    //         System.out.println("invalid parameters");
    //     }
    // }
    

}


    