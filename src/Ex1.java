import java.util.ArrayList;
import java.util.Stack;

public class Ex1 {

    public static void main(String[] args) {

        InputReader inputReader = new InputReader("input.txt");

        TilePuzzle tilePuzzle = inputReader.getTilePuzzle();
        Algorithm algorithm = inputReader.getAlgorithm();
//        Algorithm algorithm = new A();
//        Algorithm algorithm = new DFID();
//        Algorithm algorithm = new IDA();
//        Algorithm algorithm = new DFBnB();
        algorithm.checkTilePuzzle(tilePuzzle, inputReader.getWithOpen());

//        System.out.println("Path: " + algorithm.getPath());
//        System.out.println("Nodes: " + algorithm.getNodesAmount());
//        System.out.println("Cost: " + algorithm.getPrice());
//        System.out.println("Time: " + algorithm.getTime());


        OutputWriter outputWriter = new OutputWriter(inputReader);
        outputWriter.writeResult(algorithm);


    }
}
