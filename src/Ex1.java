import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Stack;

public class Ex1 {

    public static void main(String[] args) {


        InputReader inputReader = new InputReader("input.txt");

        TilePuzzle tilePuzzle = inputReader.getTilePuzzle();

        Algorithm algorithm = inputReader.getAlgorithm();
//        Algorithm algorithm = new A();
//        Algorithm algorithm = new DFID();
//        Algorithm algorithm = new IDA();
        algorithm.checkTilePuzzle(tilePuzzle);

        System.out.println("Path: " + algorithm.getPath());
        System.out.println("Nodes: " + algorithm.getNodesAmount());
        System.out.println("Cost: " + algorithm.getPrice());
        System.out.println("Time: " + algorithm.getTime());


        OutputWriter outputWriter = new OutputWriter(inputReader);
        outputWriter.writeResult(algorithm);

    }
}
