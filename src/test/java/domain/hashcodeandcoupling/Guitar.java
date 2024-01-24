package domain.hashcodeandcoupling;

public class Guitar {

    String name;
    Piano p;
    private int id;

    public Guitar(String name) {
        this.name = name;
        this.id = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add() {
        String test = "Add Guitar";
        Piano p = new Piano("Wow", "ts", "Go");
        System.out.println(test);
    }

    public int hashCode() {
        return 1;
    }

}
