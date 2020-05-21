
public class Path {
    public static String buildPath(String path){
        String p = "";
        if (path.startsWith("-")) {
            p = path.substring(1);
        }
        return p;
    }
}
