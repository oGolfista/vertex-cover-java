import java.util.*;

public class Main {

    private static final String DIV  = "=".repeat(65);
    private static final String SDIV = "-".repeat(65);

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        printBanner();
        Graph graph = readGraph(sc);

        System.out.println("\n" + DIV);
        System.out.println("  REPRESENTACAO DO GRAFO");
        System.out.println(DIV);
        printGraphInfo(graph);

        AlgorithmResult r1 = GreedyVertexCover.maximalMatching(graph);
        AlgorithmResult r2 = GreedyVertexCover.maximumDegree(graph);

        printResult(graph, r1, 1);
        printResult(graph, r2, 2);

        printComparison(r1, r2);
        printComplexityAnalysis(graph, r1, r2);

        sc.close();
    }

    private static Graph readGraph(Scanner sc) {
        System.out.println("\n  ENTRADA DO GRAFO");
        System.out.println(SDIV);

        int V = readInt(sc, "  Numero de vertices: ", 1, 10_000);
        int E = readInt(sc, "  Numero de arestas : ", 0, V * (V - 1) / 2);

        Graph graph = new Graph(V);

        if (E > 0) {
            System.out.println("\n  Digite as arestas no formato  u v  (vertices de 1 a " + V + "):");
            int added = 0;
            while (added < E) {
                System.out.printf("  Aresta %2d/%2d: ", added + 1, E);
                try {
                    int u = sc.nextInt();
                    int v = sc.nextInt();
                    graph.addEdge(u, v);
                    added++;
                } catch (IllegalArgumentException ex) {
                    System.out.println("  [ERRO] " + ex.getMessage() + " - tente novamente.");
                    sc.nextLine();
                } catch (InputMismatchException ex) {
                    System.out.println("  [ERRO] Entrada invalida - use dois inteiros separados por espaco.");
                    sc.nextLine();
                }
            }
        }
        return graph;
    }

    private static int readInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = sc.nextInt();
                if (val >= min && val <= max) return val;
                System.out.printf("  [ERRO] Valor deve ser entre %d e %d.%n", min, max);
            } catch (InputMismatchException ex) {
                System.out.println("  [ERRO] Insira um numero inteiro.");
                sc.nextLine();
            }
        }
    }

    private static void printGraphInfo(Graph g) {
        System.out.printf("%n  |V| = %d vertices   |E| = %d arestas%n%n", g.getV(), g.getE());
        System.out.print(g.toAdjListString());

        List<int[]> edges = g.getEdges();
        if (!edges.isEmpty()) {
            System.out.print("\n  Arestas: { ");
            StringJoiner sj = new StringJoiner(", ");
            for (int[] e : edges) sj.add("(" + e[0] + "," + e[1] + ")");
            System.out.println(sj + " }");
        }

        System.out.println("\n  Graus:");
        StringBuilder degs = new StringBuilder("  ");
        for (int v = 1; v <= g.getV(); v++) {
            degs.append(String.format("v%d=%d  ", v, g.getDegree(v)));
            if (v % 10 == 0) degs.append("\n  ");
        }
        System.out.println(degs.toString().stripTrailing());
    }

    private static void printResult(Graph graph, AlgorithmResult r, int num) {
        System.out.println("\n" + DIV);
        System.out.printf("  ALGORITMO %d: %s%n", num, r.name);
        System.out.println(DIV);

        if (r.steps.isEmpty()) {
            System.out.println("  (nenhuma aresta encontrada - cobertura vazia)");
        } else {
            System.out.println("\n  Execucao passo a passo:");
            for (String step : r.steps) System.out.println("  " + step);
        }

        System.out.println("\n  " + SDIV);
        System.out.println("  Vertices na cobertura : " + formatSet(r.cover));
        System.out.println("  Tamanho da cobertura  : " + r.cover.size());

        boolean valid = graph.isValidCover(r.cover);
        System.out.print("  Cobertura valida      : ");
        if (valid) {
            System.out.println("SIM  (todas as arestas estao cobertas)");
        } else {
            System.out.println("NAO  - arestas descobertas:");
            for (int[] e : graph.uncoveredEdges(r.cover))
                System.out.println("      (" + e[0] + "," + e[1] + ")");
        }
        System.out.printf("  Tempo de execucao     : %s%n", formatTime(r.timeNanos));
    }

    private static void printComparison(AlgorithmResult r1, AlgorithmResult r2) {
        System.out.println("\n" + DIV);
        System.out.println("  COMPARACAO DOS ALGORITMOS");
        System.out.println(DIV);

        System.out.printf("  %-35s  %-15s  %-15s%n", "Metrica", "Alg.1 Matching", "Alg.2 Grau Max");
        System.out.println("  " + SDIV);
        System.out.printf("  %-35s  %-15d  %-15d%n", "Tamanho da cobertura",
                r1.cover.size(), r2.cover.size());
        System.out.printf("  %-35s  %-15s  %-15s%n", "Complexidade de tempo",
                r1.timeComplexity, r2.timeComplexity);
        System.out.printf("  %-35s  %-15s  %-15s%n", "Complexidade de espaco",
                r1.spaceComplexity, r2.spaceComplexity);
        System.out.printf("  %-35s  %-15s  %-15s%n", "Razao de aproximacao",
                "<= 2 x OPT", "O(log n) x OPT");
        System.out.printf("  %-35s  %-15s  %-15s%n", "Tempo medido",
                formatTime(r1.timeNanos), formatTime(r2.timeNanos));

        System.out.println("\n  Melhor resultado: " +
            (r1.cover.size() < r2.cover.size() ? "Algoritmo 1 (menor cobertura)" :
             r2.cover.size() < r1.cover.size() ? "Algoritmo 2 (menor cobertura)" :
             "Empate"));
    }

    private static void printComplexityAnalysis(Graph graph, AlgorithmResult r1, AlgorithmResult r2) {
        int V = graph.getV(), E = graph.getE();
        System.out.println("\n" + DIV);
        System.out.println("  ANALISE DE COMPLEXIDADE");
        System.out.println(DIV);

        System.out.println("  O problema Vertex Cover e NP-dificil:");
        System.out.println("  - Nao existe algoritmo polinomial exato (a menos que P = NP).");
        System.out.println("  - Os algoritmos abaixo sao aproximacoes de tempo polinomial.");
        System.out.println();

        System.out.println("  +---------------------------------------------------------------+");
        System.out.println("  | ALGORITMO 1 - Emparelhamento Maximal (2-Aproximacao)          |");
        System.out.println("  +---------------------------------------------------------------+");
        System.out.printf ("  | Etapa 1: varrer todas as arestas         -> O(E) = O(%d)%n", E);
        System.out.printf ("  | Etapa 2: marcar vertices (array boolean) -> O(V) = O(%d)%n", V);
        System.out.println("  | ------------------------------------------------------------ |");
        System.out.printf ("  | Total : O(V + E) = O(%d + %d) = O(%d)%n", V, E, V + E);
        System.out.println("  | Espaco: O(V) para o array inCover                             |");
        System.out.println("  | Garantia: |cobertura| <= 2 x |OPT|                            |");
        System.out.println("  |   Prova: cada aresta escolhida tem >= 1 vertice no OPT,       |");
        System.out.println("  |   e adicionamos exatamente 2 por aresta escolhida.            |");
        System.out.println("  +---------------------------------------------------------------+");

        System.out.println();
        System.out.println("  +---------------------------------------------------------------+");
        System.out.println("  | ALGORITMO 2 - Grau Maximo Guloso                              |");
        System.out.println("  +---------------------------------------------------------------+");
        System.out.println("  | Etapa 1: construir heap maximo por grau  -> O(V log V)        |");
        System.out.println("  | Etapa 2: a cada selecao, atualizar graus -> O(E log V)        |");
        System.out.println("  |   - cada aresta e removida no maximo 1 vez                    |");
        System.out.println("  |   - cada remocao => 1 insercao no heap (log V)                |");
        System.out.println("  | ------------------------------------------------------------ |");
        System.out.printf ("  | Total : O(E log V) = O(%d x %.0f) ~ O(%d)%n",
                E, Math.max(1, Math.log(V) / Math.log(2)),
                (int)(E * Math.max(1, Math.log(V) / Math.log(2))));
        System.out.println("  | Espaco: O(V + E) para adj[], degree[], heap                   |");
        System.out.println("  | Razao : O(log n) - sem garantia de fator constante            |");
        System.out.println("  +---------------------------------------------------------------+");

        System.out.println();
        System.out.println("  CONTEXTO - Por que Vertex Cover e dificil?");
        System.out.println("  " + SDIV);
        System.out.println("  - Equivalente a Independent Set e Clique (reducoes polinomiais).");
        System.out.println("  - Inaproximavel abaixo de ~1.36 x OPT (sob ETH/Conjectura UGC).");
        System.out.println("  - Alg.1 atinge o melhor fator de aproximacao polinomial (fator 2).");
        System.out.println("  - Parametrizado por k: solucionavel em O(2^k * (V+E)) via kernel,");
        System.out.println("    mas impraticavel para k grande.");
    }

    private static void printBanner() {
        System.out.println("\n" + DIV);
        System.out.println("      VERTEX COVER PROBLEM - Algoritmos Gulosos em Java");
        System.out.println(DIV);
        System.out.println("  Estrutura : Lista de Adjacencia");
        System.out.println("  Algoritmos: (1) Emparelhamento Maximal  (2) Grau Maximo");
    }

    private static String formatSet(Set<Integer> set) {
        if (set.isEmpty()) return "{} (vazio)";
        StringJoiner sj = new StringJoiner(", ", "{ ", " }");
        List<Integer> sorted = new ArrayList<>(set);
        Collections.sort(sorted);
        for (int v : sorted) sj.add("v" + v);
        return sj.toString();
    }

    private static String formatTime(long nanos) {
        if (nanos < 1_000) return nanos + " ns";
        if (nanos < 1_000_000) return String.format("%.2f us", nanos / 1_000.0);
        return String.format("%.3f ms", nanos / 1_000_000.0);
    }
}
