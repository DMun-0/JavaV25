package prosjektEdit;

public interface P {
    //futureproofing for div. effecter/modifiers/whatever
    public void recieve(int amount);
    public Character getRPC();
    public void use(Chip chip, P person);
    public int getH();
    public int getA();
}
