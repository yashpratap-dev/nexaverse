package com.nexaverse.nexaverse.model;

import java.util.List;

// Strategy Pattern — ADA concept!
public interface PathfindingStrategy {
    List<int[]> findPath(int[][] grid, int[] start, int[] end);
    String getStrategyName();
}