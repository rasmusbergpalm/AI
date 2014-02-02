import java.util.Set;

public interface Problem<P extends Problem> {
    public double getScore();
    public Set<P> getSuccessors();
    public String toString();
}
