
public class Ex1 {

    public static void main (String [] args) {



        InputReader inputReader = new InputReader("input.txt");

        TilePuzzle tilePuzzle = inputReader.getTilePuzzle();

//        Algorithm algorithm = inputReader.getAlgorithm();
//        Algorithm algorithm = new A();
        Algorithm algorithm = new DFID();
        algorithm.checkTilePuzzle(tilePuzzle);

        System.out.println(algorithm.getPath());
        System.out.println(algorithm.getPrice());
        System.out.println(algorithm.getNodesAmount());
        System.out.println(algorithm.getTime());



    }
}
