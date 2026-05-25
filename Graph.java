import java.util.*;

public class Graph {
    private final int V;
    private final List<List<Integer>> adj;
    private int E;

    public Graph(int V) {
        if (V < 1) throw new IllegalArgumentException("O grafo deve ter pelo menos 1 vertice.");
        this.V = V;
        this.E = 0;
        this.adj = new ArrayList<>();
        for (int i = 0; i <= V; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v) {
        validateVertex(u);
        validateVertex(v);
        if (u == v) throw new IllegalArgumentException("Lacos nao sao permitidos: (" + u + "," + v + ")");
        adj.get(u).add(v);
        adj.get(v).add(u);
        E++;
    }

    public List<Integer> getNeighbors(int v) { return Collections.unmodifiableList(adj.get(v)); }
    public int getDegree(int v) { return adj.get(v).size(); }
    public int getV() { return V; }
    public int getE() { return E; }

    public List<int[]> getEdges() {
        List<int[]> edges = new ArrayList<>();
        boolean[] visited = new boolean[V + 1];
        for (int u = 1; u <= V; u++) {
            for (int v : adj.get(u)) {
                if (!visited[v]) edges.add(new int[]{u, v});
            }
            visited[u] = true;
        }
        return edges;
    }

    public boolean isValidCover(Set<Integer> cover) {
        for (int[] edge : getEdges()) {
            if (!cover.contains(edge[0]) && !cover.contains(edge[1])) return false;
        }
        return true;
    }

    public List<int[]> uncoveredEdges(Set<Integer> cover) {
        List<int[]> uncovered = new ArrayList<>();
        for (int[] edge : getEdges()) {
            if (!cover.contains(edge[0]) && !cover.contains(edge[1])) uncovered.add(edge);
        }
        return uncovered;
    }

    public String toAdjListString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  Vertice  |  Vizinhos\n");
        sb.append("  ---------+------------------------------\n");
        for (int i = 1; i <= V; i++) {
            sb.append(String.format("  v%-7d|  ", i));
            if (adj.get(i).isEmpty()) {
                sb.append("(isolado)");
            } else {
                List<Integer> sorted = new ArrayList<>(adj.get(i));
                Collections.sort(sorted);
                StringJoiner sj = new StringJoiner(" -> ");
                for (int n : sorted) sj.add("v" + n);
                sb.append(sj);
            }
            sb.append("\n");
        }
        sb.append("  ---------+------------------------------\n");
        return sb.toString();
    }

    private void validateVertex(int v) {
        if (v < 1 || v > V)
            throw new IllegalArgumentException("Vertice " + v + " invalido. Use valores entre 1 e " + V + ".");
    }
}
