# Simulacao de War Room com Grafos
## Problema da Cobertura de Vertices (Vertex Cover)

**Disciplina:** Estruturas de Dados II  
**Curso:** Engenharia de Software — 5º Semestre  

---

## Integrantes

| Nome | RA |
|---|---|
| José Cont Junior | 24191509-2 |
| Kayk Daniel Martins | 24054347-2 |

---

## Contexto

Uma empresa de infraestrutura critica sofreu um ataque cibernetico que comprometeu parte de sua rede de comunicacao. O setor de resposta a incidentes montou um **War Room** para analisar rapidamente a situacao e tomar decisoes estrategicas.

A rede da empresa e representada por um grafo onde:
- Cada **vertice** representa um servidor, roteador ou estacao
- Cada **aresta** representa uma conexao entre dispositivos

O desafio e encontrar o **menor conjunto de vertices** capaz de monitorar todas as conexoes simultaneamente — o problema classico de **Vertex Cover**.

---

## Problema — Vertex Cover

Dado um grafo nao direcionado G = (V, E):

> Encontrar o menor subconjunto S de vertices tal que, para toda aresta (u, v), pelo menos um dos extremos pertenca a S.

Este problema pertence a classe dos **Problemas NP-Completos** — nao existe algoritmo polinomial exato conhecido. O sistema utiliza **algoritmos gulosos de aproximacao**.

---

## Estrutura do Projeto

```
vertex-cover/
├── Graph.java               # Grafo com lista de adjacencia
├── GreedyVertexCover.java   # Algoritmos gulosos
├── AlgorithmResult.java     # Resultado de cada algoritmo
├── Main.java                # Entrada dinamica e exibicao
├── exemplo_entrada.txt      # Grafo de exemplo (7 vertices, 9 arestas)
└── rodar.bat                # Script para executar no Windows
```

---

## Algoritmos Implementados

### Algoritmo 1 — Emparelhamento Maximal (2-Aproximacao)

Para cada aresta (u, v) ainda nao coberta, adiciona **ambos** os vertices a cobertura.

- **Complexidade de tempo:** O(V + E)
- **Complexidade de espaco:** O(V)
- **Garantia:** `|cobertura| <= 2 x |OPT|` — nunca erra mais que o dobro do otimo

### Algoritmo 2 — Grau Maximo Guloso

Seleciona repetidamente o vertice com **maior grau** (mais conexoes), adiciona a cobertura e remove todas as arestas incidentes. Usa heap maximo com remocao lazy.

- **Complexidade de tempo:** O(E log V)
- **Complexidade de espaco:** O(V + E)
- **Razao:** O(log n) x OPT — sem garantia de fator constante, mas frequentemente produz cobertura menor na pratica

---

## Como Compilar e Executar

### Pre-requisito
- Java JDK 8 ou superior instalado

### Compilar
```bash
javac *.java
```

### Executar (entrada manual)
```bash
java Main
```

### Executar com exemplo pronto
```bash
java Main < exemplo_entrada.txt
```

### Windows — duplo clique
```
rodar.bat
```

---

## Formato de Entrada

```
<numero de vertices>
<numero de arestas>
<u1> <v1>
<u2> <v2>
...
```

### Exemplo (7 vertices, 9 arestas)
```
7
9
1 2
1 3
2 4
3 4
3 5
4 6
5 6
5 7
6 7
```

---

## Exemplo de Saida

```
=================================================================
      VERTEX COVER PROBLEM - Algoritmos Gulosos em Java
=================================================================

  |V| = 7 vertices   |E| = 9 arestas

  Vertice  |  Vizinhos
  ---------+------------------------------
  v1      |  v2 -> v3
  v2      |  v1 -> v4
  v3      |  v1 -> v4 -> v5
  v4      |  v2 -> v3 -> v6
  v5      |  v3 -> v6 -> v7
  v6      |  v4 -> v5 -> v7
  v7      |  v5 -> v6

ALGORITMO 1 — Emparelhamento Maximal:
  Vertices na cobertura : { v1, v2, v3, v4, v5, v6 }
  Tamanho da cobertura  : 6
  Cobertura valida      : SIM

ALGORITMO 2 — Grau Maximo Guloso:
  Vertices na cobertura : { v2, v3, v6, v7 }
  Tamanho da cobertura  : 4
  Cobertura valida      : SIM

  Melhor resultado: Algoritmo 2 (menor cobertura)
```

---

## Analise de Complexidade

| | Algoritmo 1 | Algoritmo 2 |
|---|---|---|
| Tempo | O(V + E) | O(E log V) |
| Espaco | O(V) | O(V + E) |
| Aproximacao | <= 2 x OPT (garantido) | O(log n) x OPT |
| Estrutura principal | Array booleano | Heap maximo |

> **Por que Vertex Cover e dificil?**  
> E equivalente aos problemas Independent Set e Clique via reducoes polinomiais.  
> Inaproximavel abaixo de ~1.36 x OPT sob a Conjectura do Unique Games (UGC).
