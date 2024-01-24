package domain.hashcodeandcoupling;

import java.util.ArrayList;
import java.util.List;

public class PerformanceRoom {

    Piano p;
    String name;
    List<Guitar> gList;
    public PerformanceRoom(Piano p, String name) {
        this.p = p;
        this.name = name;
        this.gList = new ArrayList<Guitar>();
    }

    public boolean equals(PerformanceRoom pr) {
        return pr.getPiano().equals(this.p);
    }

    public int hashCode() {
        return (int)(Math.random()*10);
    }

    public Piano getPiano() {
        return p;
    }

    public void playPiano(String brand) {
        System.out.println("Playing the " + brand + " piano");
    }
    public void setPiano(Piano p)
    {
        this.p = p;
    }

    public void addNewGuitar() {
        Guitar g = new Guitar("New");
        gList.add(g);
    }
}
