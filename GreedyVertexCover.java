import java.util.*;

public class GreedyVertexCover {

    public static AlgorithmResult maximalMatching(Graph graph) {
        long start = System.nanoTime();

        Set<Integer> cover = new LinkedHashSet<>();
        List<String> steps = new ArrayList<>();
        boolean[] inCover = new boolean[graph.getV() + 1];
        int stepNum = 1;

        for (int[] edge : graph.getEdges()) {
            int u = edge[0], v = edge[1];
            if (!inCover[u] && !inCover[v]) {
                inCover[u] = true;
                inCover[v] = true;
                cover.add(u);
                cover.add(v);
                steps.add(String.format(
                    "Passo %2d: Aresta (%d,%d) descoberta -> adicionados v%d e v%d  [cobertura: %s]",
                    stepNum++, u, v, u, v, cover));
            }
        }

        return new AlgorithmResult(
            "Emparelhamento Maximal (2-Aproximacao)",
            cover, steps,
            System.nanoTime() - start,
            "O(V + E)",
            "O(V)",
            "<= 2 x OPT  (garantido formalmente)"
        );
    }

    public static AlgorithmResult maximumDegree(Graph graph) {
        long start = System.nanoTime();

        Set<Integer> cover = new LinkedHashSet<>();
        List<String> steps = new ArrayList<>();

        int V = graph.getV();
        int[] degree = new int[V + 1];
        boolean[] removed = new boolean[V + 1];

        @SuppressWarnings("unchecked")
        Set<Integer>[] adj = new Set[V + 1];
        for (int v = 1; v <= V; v++) {
            adj[v] = new HashSet<>(graph.getNeighbors(v));
            degree[v] = adj[v].size();
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        for (int v = 1; v <= V; v++) {
            if (degree[v] > 0) pq.offer(new int[]{degree[v], v});
        }

        int remainingEdges = graph.getE();
        int stepNum = 1;

        while (remainingEdges > 0 && !pq.isEmpty()) {
            int[] top = pq.poll();
            int v = top[1];

            if (removed[v] || top[0] != degree[v]) continue;
            if (degree[v] == 0) continue;

            cover.add(v);
            removed[v] = true;
            int originalDegree = degree[v];
            int edgesCovered = 0;

            for (int neighbor : adj[v]) {
                if (!removed[neighbor]) {
                    degree[neighbor]--;
                    remainingEdges--;
                    edgesCovered++;
                    adj[neighbor].remove(v);

                    if (degree[neighbor] > 0) {
                        pq.offer(new int[]{degree[neighbor], neighbor});
                    }
                }
            }

            steps.add(String.format(
                "Passo %2d: Vertice v%d (grau %d) selecionado -> %d aresta(s) cobertas  [cobertura: %s]",
                stepNum++, v, originalDegree, edgesCovered, cover));

            degree[v] = 0;
        }

        return new AlgorithmResult(
            "Grau Maximo Guloso",
            cover, steps,
            System.nanoTime() - start,
            "O(E log V)",
            "O(V + E)",
            "O(log n) x OPT (sem garantia de fator constante)"
        );
    }
}
