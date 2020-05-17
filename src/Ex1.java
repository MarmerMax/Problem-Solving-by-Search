import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Ex1 {

    public static void main (String [] args) {

        InputReader inputReader = new InputReader("input.txt");

        TilePuzzle tilePuzzle = inputReader.getTilePuzzle();

        Algorithm algorithm = inputReader.getAlgorithm();
        algorithm.setTilePuzzle(tilePuzzle);


        System.out.println(algorithm.findPath());


    }
}
