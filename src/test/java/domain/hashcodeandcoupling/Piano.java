package domain.hashcodeandcoupling;

public class Piano {
    private String brand;
    private String type;

    private Guitar g;
    private PerformanceRoom pr;

    public Piano(String brand, String type, String prName) {
        this.brand = brand;
        this.type = type;
        this.pr = new PerformanceRoom(this, prName);
    }

    public String getBrand() {
        return this.brand;
    }

    public String getType() {
        return this.type;
    }

    public void playPiano() {
        this.pr.playPiano(brand);
    }

    public void playPianoInOtherRoom(PerformanceRoom newPR) {
        newPR.playPiano(brand);
    }

    public boolean equals(Piano piano) {
        return piano.getType().equals(this.type) && piano.getBrand().equals(this.brand);
    }
}
