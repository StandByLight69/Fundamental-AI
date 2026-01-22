package Assignment2;
import java.util.*;

class AIplayer {
    List<Point> availablePoints;
    List<PointsAndScores> rootsChildrenScores;
    Board b = new Board();



    public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScores.size(); ++i) {
            if (MAX < rootsChildrenScores.get(i).score) {
                MAX = rootsChildrenScores.get(i).score;
                best = i;
            }
        }
        return rootsChildrenScores.get(best).point;
    }

    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index); // Return the smallest element in the list
    }

    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;

        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }



    public int minimax(int depth, int turn, Board b, int alpha, int beta) {
        if (b.hasXWon()) return 1;
        if (b.hasOWon()) return -1;
        List<Point> pointsAvailable = b.getAvailablePoints();
        if (pointsAvailable.isEmpty()) return 0;

        for (Point point : pointsAvailable) {
            if (turn == 1) {
                b.placeAMove(point, 1);
            } else if (turn == 2) {
                b.placeAMove(point, 2);
            }


            int currentScore = minimax(depth + 1, turn == 1 ? 2 : 1, b, alpha, beta);


            if (turn == 1) {
                if (currentScore > alpha) {
                    alpha = currentScore;
                }
                if (depth == 0) {
                    rootsChildrenScores.add(new PointsAndScores(currentScore, point));
                }
            } else if (turn == 2) {
                if (currentScore < beta) {
                    beta = currentScore;
                }
            }

            b.placeAMove(point, 0);
            // Pruning
            if (beta <= alpha) {
                break;
            }
        }


        return turn == 1 ? alpha : beta;
    }

    public void callMinimax(int depth, int turn, Board b){
        rootsChildrenScores = new ArrayList<>();
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        minimax(depth, turn, b, alpha, beta);
    }
}