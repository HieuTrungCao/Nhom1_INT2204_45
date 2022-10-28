package uet.oop.bomberman.AI;

import uet.oop.bomberman.BombermanGame;


public class AIFly extends AI {
    public AIFly(Character[][] map) {
        super(map);
    }



    @Override
    public int calculateDirect() {
//        int px = BombermanGame.characters.get(2).getX();
//        int py = BombermanGame.characters.get(2).getY();
        int px = BombermanGame.player.getBomberman().getX();
        int py = BombermanGame.player.getBomberman().getY();

        int bestDirect = -1;
        int minHeuristicLen = Integer.MAX_VALUE;

//        if (checkDirect(0)) {
        int heuristicLen = Math.abs(x - speed - px) + Math.abs(y - py);
//        System.out.println("0 " + heuristicLen);
        if (minHeuristicLen >= heuristicLen) {
            minHeuristicLen = heuristicLen;
            bestDirect = 0;
        }
//        }

//        if (checkDirect(1)) {
        heuristicLen = Math.abs(x - px) + Math.abs(y - speed - py);
//        System.out.println("1 " + heuristicLen);
        if (minHeuristicLen >= heuristicLen) {
            minHeuristicLen = heuristicLen;
            bestDirect = 1;
        }
//        }

//        if (checkDirect(2)) {
        heuristicLen = Math.abs(x + speed - px) + Math.abs(y - py);
//        System.out.println("2 " + heuristicLen);
        if (minHeuristicLen >= heuristicLen) {
            minHeuristicLen = heuristicLen;
            bestDirect = 2;
        }
//        }

//        if (checkDirect(3)) {
        heuristicLen = Math.abs(x - px) + Math.abs(y + speed - py);
//        System.out.println("3 " + heuristicLen);
        if (minHeuristicLen >= heuristicLen) {
            minHeuristicLen = heuristicLen;
            bestDirect = 3;
        }
//        }

//        System.out.println(minHeuristicLen + " " + bestDirect);

        return bestDirect;
    }

    @Override
    public void move() {
//        int count = 0;
//        while (!checkDirect(currentDirect)) {
//            if (count > 2) currentDirect = (currentDirect + 1) % 4;
//            else {
//                currentDirect = calculateDirect();
//                count++;
//            }
//        }
//        if (!checkDirect(currentDirect)){
//
//        };
        switch (currentDirect) {
            case 1 -> {
                if (y - speed >= 32)
                    y -= speed;
            }
            case 2 -> {
                if (x + speed <= BombermanGame.WIDTH * 32 - 32)
                    x += speed;
            }
            case 3 -> {
                if (y + speed <= BombermanGame.HEIGHT * 32 - 32)
                    y += speed;
            }
            case 0 -> {
                if (x - speed >= 32)
                    x -= speed;
            }
        }
    }
}
