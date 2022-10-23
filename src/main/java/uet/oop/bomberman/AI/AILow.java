package uet.oop.bomberman.AI;

public class AILow extends AI {


    public AILow(Character[][] map) {
        super(map);
    }

    @Override
    public int calculateDirect() {
        int direct = (int) (Math.random() * 4);
        return direct;
    }

    @Override
    public void move() {
        int count = 0;
        while (!checkDirect(currentDirect)) {
            if (count > 2) currentDirect = (currentDirect + 1) % 4;
            else {
                currentDirect = calculateDirect();
                count ++;
            }
        }
//        if (!checkDirect(currentDirect)){
//
//        };
        switch (currentDirect) {
            case 1 -> y -= speed;
            case 2 -> x += speed;
            case 3 -> y += speed;
            case 0 -> x -= speed;
        }
    }


}
