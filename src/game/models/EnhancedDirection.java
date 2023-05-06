package game.models;

public enum EnhancedDirection {
    LEFTUP,
    LEFTDOWN,
    RIGHTUP,
    RIGHTDOWN;


    public Direction[] toDirection(){
        switch (this){
            case LEFTUP: return new Direction[]{Direction.LEFT,Direction.UP};
            case LEFTDOWN: return new Direction[]{Direction.LEFT,Direction.DOWN};
            case RIGHTDOWN: return new Direction[]{Direction.RIGHT,Direction.DOWN};
            case RIGHTUP: return new Direction[]{Direction.RIGHT,Direction.UP};
        }

        return null;
    }

}
