import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Ex1 {

    public static void main (String [] args) {



        InputReader inputReader = new InputReader("input.txt");

        TilePuzzle tilePuzzle = inputReader.getTilePuzzle();

        Algorithm algorithm = inputReader.getAlgorithm();
        algorithm.setTilePuzzle(tilePuzzle);

        System.out.println(algorithm.getPath());
        System.out.println(algorithm.getPrice());
        System.out.println(algorithm.getNodesAmount());
        System.out.println(algorithm.getTime());



    }
}
