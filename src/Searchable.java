public interface Searchable {
    public boolean findPath(Node start, Node goal);
    public int findNodeAmount();
    public int findCost();
    public double findTime();
}
