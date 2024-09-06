package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.print.DocFlavor.STRING;

public class Hard {
    // 1605. Find Valid Matrix Given Row and Column Sums
    public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
        // int m = rowSum.length, n = colSum.length;
        // int[][] res = new int[m][n];
        // boolean flag = true;
        // while (flag) {
        //     flag = false;
        //     int minM = -1, minN = -1;
        //     for (int i = 0; i < m; i++) {
        //         if (rowSum[i] > 0 && (minM == -1 || rowSum[i] <= rowSum[minM])) {
        //             minM = i;
        //         }
        //     }
        //     for (int i = 0; i < n; i++) {
        //         if (colSum[i] > 0 && (minN == -1 || colSum[i] <= colSum[minN])) {
        //             minN = i;
        //         }
        //     }
        //     if(minM == -1){
        //         break;
        //     }
        //     int temp = Math.min(rowSum[minM], colSum[minN]);
        //     res[minM][minN] = temp;
        //     rowSum[minM] -= temp;
        //     colSum[minN] -= temp;
        // }
        // return res;
        public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
            int r = rowSum.length;
            int c = colSum.length;
            int[][] ans = new int [r][c];
    
            int i=0,j=0;
    
            while(i<r && j<c){
                int val = Math.min(rowSum[i], colSum[j]);
                ans[i][j] = val;
                rowSum[i] -= val;
                colSum[j] -= val;
    
                if(rowSum[i] == 0){
                    i++;
                }
                if(colSum[j] == 0){
                    j++;
                }
            }
            return ans;
        }
    }

    // 564. Find the Closest Palindrome
    public String nearestPalindromic(String n) {
        int len = n.length();
        long number = Long.parseLong(n);
        if (number <= 10) {
            return Integer.toString((int) number - 1);
        } else if (number == 11) {
            return "9";
        }
        long[] candidates = new long[5];
        boolean odd = (len % 2 == 1);
        String leftHalfStr = n.substring(0, (len + 1) / 2);
        long leftHalf = Long.parseLong(leftHalfStr);
        candidates[0] = (long) Math.pow(10, len) + 1;
        candidates[1] = (long) Math.pow(10, len - 1) - 1;
        candidates[2] = nearestPalindromicGenerate(leftHalf, odd);
        candidates[3] = nearestPalindromicGenerate(leftHalf - 1, odd);
        candidates[4] = nearestPalindromicGenerate(leftHalf + 1, odd);

        long res = 0, minDiff = Long.MAX_VALUE;
        for (long candidate : candidates) {
            if (candidate != number) {
                long diff = Math.abs(candidate - number);
                if (minDiff > diff) {
                    minDiff = diff;
                    res = candidate;
                } else if (minDiff == diff) {
                    res = Math.min(res, candidate);
                }
            }
        }

        return Long.toString(res);
    }

    public long nearestPalindromicGenerate(long half, boolean odd) {
        long res = half;
        if (odd) {
            half /= 10;
        }
        while (half > 0) {
            res = res * 10 + half % 10;
            half /= 10;
        }
        return res;
    }

    // 2192. All Ancestors of a Node in a Directed Acyclic Graph
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        // Step 1: Create the adjacency list and reverse adjacency list
        List<Set<Integer>> adjList = new ArrayList<>();
        List<Set<Integer>> reverseAdjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new HashSet<>());
            reverseAdjList.add(new HashSet<>());
        }

        // Populate the adjacency lists
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adjList.get(u).add(v);
            reverseAdjList.get(v).add(u);
        }

        // Step 2: Use a topological sort
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(new ArrayList<>());
        }

        // Find all nodes with no incoming edges
        Queue<Integer> queue = new LinkedList<>();
        boolean[] inDegreeZero = new boolean[n];
        int[] inDegree = new int[n];

        for (int i = 0; i < n; i++) {
            inDegree[i] = reverseAdjList.get(i).size();
            if (inDegree[i] == 0) {
                queue.add(i);
                inDegreeZero[i] = true;
            }
        }

        // Perform topological sort and accumulate ancestors
        while (!queue.isEmpty()) {
            int node = queue.poll();

            // For each node, propagate its ancestors to its descendants
            Set<Integer> ancestors = new HashSet<>(reverseAdjList.get(node));
            for (int ancestor : reverseAdjList.get(node)) {
                ancestors.addAll(result.get(ancestor));
            }
            result.get(node).addAll(ancestors);

            // Update the queue and in-degree for nodes that are dependent on the current
            // node
            for (int neighbor : adjList.get(node)) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Convert result sets to lists and sort
        for (List<Integer> list : result) {
            Collections.sort(list);
        }

        return result;
    }
}
