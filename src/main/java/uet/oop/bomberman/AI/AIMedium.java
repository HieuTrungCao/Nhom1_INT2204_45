package uet.oop.bomberman.AI;


import javafx.util.Pair;
import uet.oop.bomberman.Management;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.PriorityQueue;

public class AIMedium extends AI {
    public AIMedium(Character[][] map) {
        super(map);
        timeToUpdateDirect = timePassAUnit;
    }

    @Override
    public int calculateDirect() {
        Pair<Integer, Integer> directNode = pathFinding();
        if (directNode == null) {
            timeToUpdateDirect = timePassAUnit * 3;
            return (int) (Math.random() * 4);
        }
        timeToUpdateDirect = timePassAUnit;
        int xFuture = directNode.getKey() * Sprite.SCALED_SIZE;
        int yFuture = directNode.getValue() * Sprite.SCALED_SIZE;
        if (yFuture < y) return 1;
        if (yFuture > y) return 3;
        if (xFuture > x) return 2;
        if (xFuture < x) return 0;
        return -1;
    }

    private Pair<Integer, Integer> pathFinding() {
        updateNearestPlayer();

        int xSource = (x) / Sprite.SCALED_SIZE;
        int ySource = (y) / Sprite.SCALED_SIZE;

//        System.out.println("Source : " + x + " " + y + "  Unit : " + xSource + " " + ySource);

        Pair<Integer, Integer> startNode = new Pair<>(xSource, ySource);
        Pair<Integer, Integer> destNode = new Pair<>(px / Sprite.SCALED_SIZE, py / Sprite.SCALED_SIZE);

        int[][] distance = new int[Management.HEIGHT][Management.WIDTH];
        Arrays.stream(distance).forEach(row -> Arrays.fill(row, 1000000));
        distance[ySource][xSource] = 0;

        PriorityQueue<Pair<Integer, Integer>> openList = new PriorityQueue<>((a, b)
                -> (heuristicDis(a, destNode) + distance[a.getValue()][a.getKey()])
                - (heuristicDis(b, destNode) + distance[b.getValue()][b.getKey()]));
        openList.add(startNode);

        ArrayList<Pair<Integer, Integer>> closedList = new ArrayList<>();

        Pair<Integer, Integer>[][] parent = new Pair[Management.HEIGHT][Management.WIDTH];

        while (!openList.isEmpty()) {
            Pair<Integer, Integer> currNode = openList.remove();
            if (currNode.equals(destNode)) {
                Pair<Integer, Integer> itNode = destNode;
//                System.out.print(itNode + "<-");
                while (parent[itNode.getValue()][itNode.getKey()] != null && !Objects.equals(parent[itNode.getValue()][itNode.getKey()], startNode)) {
                    itNode = parent[itNode.getValue()][itNode.getKey()];
//                    System.out.print(itNode + "<-");
                }
//                System.out.println();
                return itNode;
            } else {
//                System.out.println("currNode:" + currNode);
                closedList.add(currNode);
                Pair<Integer, Integer>[] neighbors = new Pair[4];
                neighbors[0] = new Pair<>(currNode.getKey() - 1, currNode.getValue());
                neighbors[1] = new Pair<>(currNode.getKey(), currNode.getValue() - 1);
                neighbors[2] = new Pair<>(currNode.getKey() + 1, currNode.getValue());
                neighbors[3] = new Pair<>(currNode.getKey(), currNode.getValue() + 1);

                for (Pair<Integer, Integer> neighbor : neighbors) {
                    int xNeighbor = neighbor.getKey();
                    int yNeighbor = neighbor.getValue();
                    if (isBlock(map[yNeighbor][xNeighbor])) continue;
                    int newCost = distance[currNode.getValue()][currNode.getKey()] + 1;

                    if (!closedList.contains(neighbor) && !openList.contains(neighbor)) {
                        parent[yNeighbor][xNeighbor] = currNode;
                        distance[yNeighbor][xNeighbor] = newCost;
                        openList.add(neighbor);
                    } else {
                        if (newCost < distance[yNeighbor][xNeighbor]) {
                            parent[yNeighbor][xNeighbor] = currNode;
                            distance[yNeighbor][xNeighbor] = newCost;
                            if (closedList.contains(neighbor)) {
                                closedList.remove(neighbor);
                                openList.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    private int heuristicDis(Pair<Integer, Integer> a, Pair<Integer, Integer> b) {
        return Math.abs(a.getKey() - b.getKey()) + Math.abs(a.getValue() - b.getValue());
    }


}
