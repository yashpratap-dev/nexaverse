package com.nexaverse.nexaverse.model;

import java.util.*;

public class BFSPathfinding implements PathfindingStrategy {

    @Override
    public String getStrategyName() {
        return "BFS Pathfinding";
    }

    @Override
    public List<int[]> findPath(int[][] grid, int[] start, int[] end) {
        int rows = grid.length;
        int cols = grid[0].length;

        // BFS — Queue use karenge (DSA!)
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        Map<String, int[]> parent = new HashMap<>();

        queue.add(start);
        visited[start[0]][start[1]] = true;

        int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();

            // End mila?
            if (current[0] == end[0] && current[1] == end[1]) {
                return buildPath(parent, start, end);
            }

            // Neighbors check karo
            for (int[] dir : directions) {
                int newRow = current[0] + dir[0];
                int newCol = current[1] + dir[1];

                if (newRow >= 0 && newRow < rows &&
                        newCol >= 0 && newCol < cols &&
                        !visited[newRow][newCol] &&
                        grid[newRow][newCol] == 0) {

                    visited[newRow][newCol] = true;
                    int[] next = {newRow, newCol};
                    queue.add(next);
                    parent.put(newRow + "," + newCol, current);
                }
            }
        }
        return Collections.emptyList(); // Path nahi mila
    }

    private List<int[]> buildPath(Map<String, int[]> parent, int[] start, int[] end) {
        List<int[]> path = new ArrayList<>();
        int[] current = end;

        while (!(current[0] == start[0] && current[1] == start[1])) {
            path.add(0, current);
            current = parent.get(current[0] + "," + current[1]);
        }
        path.add(0, start);
        return path;
    }
}