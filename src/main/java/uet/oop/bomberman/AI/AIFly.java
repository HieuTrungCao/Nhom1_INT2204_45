package uet.oop.bomberman.AI;

import uet.oop.bomberman.graphics.Sprite;


public class AIFly extends AI {
    public AIFly(Character[][] map) {
        super(map);
        timeToUpdateDirect = timePassAUnit;
        cantFly = false;
    }

    @Override
    public int calculateDirect() {
        updateNearestPlayer();

        if (Math.abs(px - x) + Math.abs(py - y) > 10 * Sprite.SCALED_SIZE) return (int) (Math.random() * 4);

        int bestDirect = -1;
        int minHeuristicLen = Integer.MAX_VALUE;

        int heuristicLen = Math.abs(x - speed - px) + Math.abs(y - py);
        minHeuristicLen = heuristicLen;
        bestDirect = 0;

        heuristicLen = Math.abs(x - px) + Math.abs(y - speed - py);
        if (minHeuristicLen >= heuristicLen) {
            minHeuristicLen = heuristicLen;
            bestDirect = 1;
        }

        heuristicLen = Math.abs(x + speed - px) + Math.abs(y - py);
        if (minHeuristicLen >= heuristicLen) {
            minHeuristicLen = heuristicLen;
            bestDirect = 2;
        }

        heuristicLen = Math.abs(x - px) + Math.abs(y + speed - py);
        if (minHeuristicLen >= heuristicLen) {
            bestDirect = 3;
        }

        return bestDirect;
    }

}