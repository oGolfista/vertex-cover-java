import java.util.*;

public class AlgorithmResult {
    public final String name;
    public final Set<Integer> cover;
    public final List<String> steps;
    public final long timeNanos;
    public final String timeComplexity;
    public final String spaceComplexity;
    public final String approximationRatio;

    public AlgorithmResult(String name, Set<Integer> cover, List<String> steps,
                           long timeNanos, String timeComplexity, String spaceComplexity,
                           String approximationRatio) {
        this.name = name;
        this.cover = Collections.unmodifiableSet(new LinkedHashSet<>(cover));
        this.steps = Collections.unmodifiableList(new ArrayList<>(steps));
        this.timeNanos = timeNanos;
        this.timeComplexity = timeComplexity;
        this.spaceComplexity = spaceComplexity;
        this.approximationRatio = approximationRatio;
    }
}
