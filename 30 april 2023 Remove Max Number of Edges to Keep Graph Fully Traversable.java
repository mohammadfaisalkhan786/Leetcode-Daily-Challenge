class Solution {
    public int maxNumEdgesToRemove(int n, int[][] edges) {
        Arrays.sort(edges, (e1, e2) -> e2[0] - e1[0]);

        int extraEdgeCount = 0;
        UnionFind aliceUf = new UnionFind(n);
        UnionFind bobUf = new UnionFind(n);
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
            if (edge[0] == 3) {
                boolean union = aliceUf.union(edge[1], edge[2]);
                bobUf.union(edge[1], edge[2]);
                if (!union) extraEdgeCount++;
            }
            else {
                UnionFind uf = edge[0] == 2 ? bobUf : aliceUf;
                if (!uf.union(edge[1], edge[2])) extraEdgeCount++;
            }
        }
        return aliceUf.isFullyConnected() && bobUf.isFullyConnected() ? extraEdgeCount : -1;
    }
}
class UnionFind {
    private int n;
    private final int[] parent;
    private final int[] rank;

    UnionFind(int n) {
        this.n = n;
        parent = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 0; i < parent.length; i++) parent[i] = i;
    }

    private int find(int node) {
        if (parent[node] != node) parent[node] = find(parent[node]);
        return parent[node];
    }

    public boolean union(int node1, int node2) {
        int p1 = find(node1), p2 = find(node2);
        if (p1 == p2) return false;

        if (rank[p1] > rank[p2]) parent[p2] = p1;
        else if (rank[p1] < rank[p2]) parent[p1] = p2;
        else {
            parent[p2] = p1;
            rank[p1] += rank[p2] + 1;
        }
        n--;
        return true;
    }

    public boolean isFullyConnected() {
        return n == 1;
    }
}
