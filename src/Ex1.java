public class Ex1 {

    public static void main(String[] args) {

        //object for reading the input file
        InputReader inputReader = new InputReader("input.txt");

        //get tile puzzle from input reader
        TilePuzzle tilePuzzle = inputReader.getTilePuzzle();

        //get algorithm from input reader
        Algorithm algorithm = inputReader.getAlgorithm();

        //start the algorithm on the given tile puzzle and send the with-open option
        algorithm.checkTilePuzzle(tilePuzzle, inputReader.getWithOpen());

        //create object for output
        OutputWriter outputWriter = new OutputWriter(inputReader);

        //write results of algorithm
        outputWriter.writeResult(algorithm);
    }
}
