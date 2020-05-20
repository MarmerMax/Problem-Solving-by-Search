import java.util.*;
import java.util.concurrent.TimeUnit;

public class Ex1 {

    public static void main (String [] args) {



        InputReader inputReader = new InputReader("input.txt");

        TilePuzzle tilePuzzle = inputReader.getTilePuzzle();

//        Algorithm algorithm = inputReader.getAlgorithm();
        Algorithm algorithm = new A();
        algorithm.setTilePuzzle(tilePuzzle);

        System.out.println(algorithm.getPath());
        System.out.println(algorithm.getPrice());
        System.out.println(algorithm.getNodesAmount());
        System.out.println(algorithm.getTime());



    }
}
