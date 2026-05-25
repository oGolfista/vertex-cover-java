# Vertex Cover - Estruturas de Dados II

**Disciplina:** Estruturas de Dados II  
**Curso:** Engenharia de Software - 5 Semestre

---

## Integrantes

| Nome | RA |
|---|---|
| José Cont Junior | 24191509-2 |
| Kayk Daniel Martins | 24054347-2 |

---

## Sobre o Trabalho

O trabalho simula uma situacao de War Room onde uma empresa sofreu um ataque cibernetico na sua rede. A rede e representada como um grafo onde os vertices sao servidores e as arestas sao as conexoes entre eles.

O objetivo e resolver o problema de Vertex Cover, que consiste em encontrar o menor conjunto de vertices que cubra todas as arestas do grafo. Na pratica isso significa: escolher o minimo de dispositivos para monitorar todas as conexoes da rede.

---

## Como funciona

O programa le um grafo pelo terminal (vertices e arestas) e roda dois algoritmos gulosos para encontrar a cobertura de vertices.

**Algoritmo 1 - Emparelhamento Maximal:**  
Percorre as arestas e, quando encontra uma aresta sem cobertura, adiciona os dois vertices dela. Complexidade O(V + E).

**Algoritmo 2 - Grau Maximo:**  
Sempre escolhe o vertice com mais conexoes, adiciona na cobertura e remove as arestas dele. Usa uma fila de prioridade (heap). Complexidade O(E log V).

No final o programa compara os dois resultados e mostra qual teve a menor cobertura.

---

## Estrutura dos arquivos

```
Graph.java              - representacao do grafo com lista de adjacencia
GreedyVertexCover.java  - os dois algoritmos gulosos
AlgorithmResult.java    - guarda o resultado de cada algoritmo
Main.java               - entrada de dados e exibicao dos resultados
exemplo_entrada.txt     - exemplo de grafo para testar
rodar.bat               - script para compilar e rodar no Windows
```

---

## Como rodar

Precisa ter o Java instalado.

**Compilar:**
```
javac *.java
```

**Rodar:**
```
java Main
```

**Rodar com o exemplo pronto:**
```
java Main < exemplo_entrada.txt
```

**No Windows pode dar duplo clique em:**
```
rodar.bat
```

---

## Formato da entrada

```
numero de vertices
numero de arestas
u v  (para cada aresta)
```

**Exemplo:**
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

## O que o programa exibe

- Lista de adjacencia do grafo
- Passo a passo de cada algoritmo
- Vertices escolhidos na cobertura
- Tamanho da cobertura
- Se a cobertura e valida (cobre todas as arestas)
- Comparacao entre os dois algoritmos
- Analise de complexidade de tempo e espaco
