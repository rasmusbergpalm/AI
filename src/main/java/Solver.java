public interface Solver<P extends Problem> {
    public P solve(P problem);
}
