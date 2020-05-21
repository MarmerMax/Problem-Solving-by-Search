
public class Path {
    public static String buildPath(Node node){
        String p = "";
        if (node.getName().startsWith("-")) {
            p = node.getName().substring(1);
        }
        return p;
    }
}
