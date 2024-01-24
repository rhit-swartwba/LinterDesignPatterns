package domain;

import domain.hashcodeandcoupling.PerformanceRoom;
import domain.hashcodeandcoupling.Piano;

import java.io.IOException;

public class MainTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        Piano p = new Piano("Steinway", "Grand", "Craw");
        PerformanceRoom pr = new PerformanceRoom(p, "Bob");
    }
}
