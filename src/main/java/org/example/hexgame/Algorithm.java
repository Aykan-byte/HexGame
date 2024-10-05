package org.example.hexgame;

public class Algorithm {
    //This method checks whether a given player (red or blue) has won.
    //A player wins if their pieces form a connection from the left edge to the right edge (for red) or from the top edge to the bottom edge (for blue).
    protected static boolean checkWin(int[][] board, int player, int size) {
        boolean[][] visited = new boolean[size][size];
        if (player == 2) {
            for (int i = 0; i < size; i++) {
                if (board[i][0] == player && dfs(board, i, 0, player, visited, size)) {
                    return true;
                }
            }
        } else if (player == 1) {
            for (int j = 0; j < size; j++) {
                if (board[0][j] == player && dfs(board, 0, j, player, visited, size)) {
                    return true;
                }
            }
        }
        return false;
    }
    //This method utilizes the depth-first search algorithm to check whether the player's pieces form a connected path.
    // It starts at the coordinates (x, y) and traverses the connected cells of a specific player's pieces, marking the path as it goes along.
    private static boolean dfs(int[][] board, int x, int y, int player, boolean[][] visited, int size) {
        int[][] directions = {{0, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, 0}, {-1, 1}};
        visited[x][y] = true;

        if (player == 2 && y == size - 2) {
            return true;
        }
        if (player == 1 && x == size - 2) {
            return true;
        }

        for (int[] direction : directions) {
            int newX = x + direction[0];
            int newY = y + direction[1];
            if (isWithinBounds(newX, newY, size) && board[newX][newY] == player && !visited[newX][newY]) {
                if (dfs(board, newX, newY, player, visited, size)) {
                    return true;
                }
            }
        }
        return false;
    }
    //This method checks whether the given coordinates are within the boundaries of the game board.
    //If the coordinates are inside the game board, it returns "true"; otherwise, it returns "false".
    private static boolean isWithinBounds(int x, int y, int size) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }
}
