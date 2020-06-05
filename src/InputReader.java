import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InputReader {

    private static final Set<String> TIME_LIST = new HashSet<>(Arrays.asList("with time", "no time"));
    private static final Set<String> OPEN_LIST = new HashSet<>(Arrays.asList("with open", "no open"));

    private ArrayList<String> input_list;
    private Algorithm algorithm;
    private TilePuzzle tile_puzzle;
    private boolean with_time = false;
    private boolean with_open = false;

    public InputReader(String path) {
        input_list = new ArrayList<>();
        readInputFile(path);
    }

    //read all information from input file
    private void readInputFile(String path) {
        if (path == null || path.equals("")) {
            throw new RuntimeException("Input path is wrong...");
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
        with_time = timeFromLine();
        with_open = openFromLine();
        buildTilePuzzle();
    }

    //create red numbers set from input file
    private Set<Integer> checkRedNumbers() {
        String line = input_list.get(6);
        if (line.length() > 4) {
            line = line.substring(5);
            return numbersFromLine(line);

        }
        return null;
    }

    //create black numbers set from input file
    private Set<Integer> checkBlackNumbers() {
        String line = input_list.get(5);
        if (line.length() > 6) {
            line = line.substring(7);
            return numbersFromLine(line);
        }
        return null;
    }

    //create numbers set from the line
    private Set<Integer> numbersFromLine(String line) {
        if(line.trim().equals("")){
            return null;
        }
        String[] numbers = line.split(",");
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < numbers.length; i++) {
            set.add(Integer.parseInt(numbers[i]));
        }
        return set;
    }

    //check the matrix size from input file
    private int[] matrixSizeFromLine() {
        String[] string_sizes = input_list.get(4).split("x");
        int rows = Integer.parseInt(string_sizes[0]);
        int columns = Integer.parseInt(string_sizes[1]);
        int[] sizes = {rows, columns};
        return sizes;
    }

    //check the time mode from input file
    private boolean timeFromLine() {
        String with = input_list.get(2);
        if (!TIME_LIST.contains(with)) {
            throw new RuntimeException("Bad time option input...");
        }

        return with.equals("with time");
    }

    //check the open mode from input file
    private boolean openFromLine() {
        String open = input_list.get(3);
        if (!OPEN_LIST.contains(open)) {
            throw new RuntimeException("Bad open option input...");
        }

        return open.equals("with open");
    }

    //build tile puzzle from input file
    private void buildTilePuzzle() {
        int[] sizes = matrixSizeFromLine();

        int[][] matrix = new int[sizes[0]][sizes[1]];

        for (int i = 0; i < matrix.length; i++) {

            String[] line = input_list.get(7 + i).split(",");

            //if matrix columns has another size than wrote in input sizes then throw run time exception
            if (line.length != sizes[1]) {
                throw new RuntimeException("Bad matrix size...");
            }

            for (int j = 0; j < matrix[i].length; j++) {
                if (line[j].equals("_")) {
                    matrix[i][j] = -1;
                } else {
                    matrix[i][j] = Integer.parseInt(line[j]);
                }
            }
        }

        //check if the input file has the right number of lines
        if (input_list.size() != 7 + sizes[0]) {
            throw new RuntimeException("Bad input...");
        }

        Set<Integer> black_numbers = checkBlackNumbers();
        Set<Integer> red_numbers = checkRedNumbers();

        tile_puzzle = new TilePuzzle(matrix, black_numbers, red_numbers);
    }

    //choose algorithm from the input file
    private void chooseAlgorithm() {

        switch (input_list.get(1)) {
            case "BFS": {
                algorithm = new BFS();
                break;
            }
            case "IDA*": {
                algorithm = new IDA();
                break;
            }
            case "A*": {
                algorithm = new A();
                break;
            }
            case "DFID": {
                algorithm = new DFID();
                break;
            }
            case "DFBnB": {
                algorithm = new DFBnB();
                break;
            }
            default: {
                throw new RuntimeException("Bad input algorithm...");
            }
        }
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public TilePuzzle getTilePuzzle() {
        return tile_puzzle;
    }

    public boolean getWithTime() {
        return with_time;
    }

    public boolean getWithOpen() {
        return with_open;
    }
}
