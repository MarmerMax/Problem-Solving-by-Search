import java.util.ArrayList;
import java.util.Stack;

public class Ex1 {

    public static void main(String[] args) {


        InputReader inputReader = new InputReader("input.txt");

        TilePuzzle tilePuzzle = inputReader.getTilePuzzle();

//        Algorithm algorithm = inputReader.getAlgorithm();
//        Algorithm algorithm = new A();
//        Algorithm algorithm = new DFID();
        Algorithm algorithm = new IDA();
//        Algorithm algorithm = new DFBnB();
        algorithm.checkTilePuzzle(tilePuzzle);

//        Path: 10U-8L-6L-7D-11R-6U-8R-10D-6L-11L-7U-8R-11D-7L-8U
//        Nodes: 53616
//        Cost: 102
//        Time: 0.145 seconds

        System.out.println("Path: " + algorithm.getPath());
        System.out.println("Nodes: " + algorithm.getNodesAmount());
        System.out.println("Cost: " + algorithm.getPrice());
        System.out.println("Time: " + algorithm.getTime());


//        OutputWriter outputWriter = new OutputWriter(inputReader);
//        outputWriter.writeResult(algorithm);

    }
}
