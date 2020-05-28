import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class OutputWriter {

    InputReader inputReader;

    public OutputWriter(InputReader ir){
        this.inputReader = ir;
    }

    public void writeResult(Algorithm algorithm){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("output.txt", "UTF-8");
            writer.println(algorithm.getPath());
            writer.println("Num: " + algorithm.getNodesAmount());
            writer.println("Cost: " + algorithm.getPrice());
            if(inputReader.getWithTime()){
                writer.println(algorithm.getTime());
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
