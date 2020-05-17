import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InputReader {

    private static final String[] ALGORITHM_LIST = {"BFS", "A", "IDA", "DFID", "DFBnB"};
    private static final String[] TIME_LIST = {"with time", "no time"};
    private static final String[] PRINT_LIST = {"with open", "no open"};

    private ArrayList<String> input_list;
    private Algorithm algorithm;
    private TilePuzzle tilePuzzle;


    public InputReader(String path) {
        input_list = new ArrayList<>();
        readInputFile(path);
    }

    private void readInputFile(String path) {
        if (path == null || path.equals("")) {
            throw new RuntimeException("Input path is wrong");
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            try {
                String line = null;
                input_list.add(line);
                while ((line = reader.readLine()) != null) {
                    input_list.add(line);
                }
            } finally {
                reader.close();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }


        chooseAlgorithm();


        buildTilePuzzle();
    }

    private Set<Integer> checkRedNumbers() {
        String line = input_list.get(6);
        if (line.length() > 4) {
            line = line.substring(5);
            return numbersFromLine(line);

        }
        return null;
    }

    private Set<Integer> checkBlackNumbers() {
        String line = input_list.get(5);
        if (line.length() > 6) {
            line = line.substring(7);
            return numbersFromLine(line);
        }
        return null;
    }

    private Set<Integer> numbersFromLine(String line){
        String[] numbers = line.split(",");
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < numbers.length; i++) {
            set.add(Integer.parseInt(numbers[i]));
        }
        return set;
    }

    private void buildTilePuzzle() {
        String[] sizes = input_list.get(4).split("x");

        int rows = Integer.parseInt(sizes[0]);
        int columns = Integer.parseInt(sizes[1]);
        int[][] matrix = new int[rows][columns];

        for (int i = 0; i < rows; i++) {

            String[] line = input_list.get(7 + i).split(",");

            for (int j = 0; j < columns; j++) {
                if (line[j].equals("_")) {
                    matrix[i][j] = -1;
                } else {
                    matrix[i][j] = Integer.parseInt(line[j]);
                }
            }
        }

        Set<Integer> black_numbers = checkBlackNumbers();
        Set<Integer> red_numbers = checkRedNumbers();

        tilePuzzle = new TilePuzzle(matrix, black_numbers, red_numbers);
    }

    private void chooseAlgorithm() {

        switch (input_list.get(1)) {
            case "BFS": {
                algorithm = new BFS();
                break;
            }
            case "IDA": {
//                algorithm = new IDA();
                break;
            }
            case "A": {
//                algorithm = new A();
                break;
            }
            case "DFID": {
//                algorithm = new DFID();
                break;
            }
            case "DFBnB": {
//                algorithm = new DFBnB();
                break;
            }
            default: {
                throw new RuntimeException("Bad input algorithm");
            }
        }
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }


    public TilePuzzle getTilePuzzle() {
        return tilePuzzle;
    }
}
