import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputReader {

    private static final String [] ALGORITHM_LIST = {"BFS", "A", "IDA", "DFID", "DFBnB", };
    private static final String [] TIME_LIST = {"with time", "no time"};
    private static final String [] PRINT_LIST = {"with open", "no open"};

    private ArrayList<String> input_list;
    private String my_algorithm;

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
                while ((line = reader.readLine()) != null) {
                    input_list.add(line);
                }
            } finally {
                reader.close();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
        checkInputFile();
    }

    private void checkInputFile(){
        boolean proper_input = true;

        for(String algo : ALGORITHM_LIST){
            if (algo.equals(input_list.get(0))){
                my_algorithm = algo;
            }
        }

        if(){

        }

    }

}
