package uet.oop.bomberman.AI;

public class AILow extends AI {

    public AILow(Character[][] map) {
        super(map);
    }

    @Override
    public int calculateDirect() {
        return (int) (Math.random() * 4);
    }

}
