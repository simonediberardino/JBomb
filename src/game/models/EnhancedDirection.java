package game.models;

import game.entity.models.Entity;

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

    public static EnhancedDirection toEnhancedDirection(Direction[] directions){
        Direction vertical = null;
        Direction horizontal = null;

        for (Direction d: directions
             ) {

            switch (d){
                case DOWN:case UP:vertical=d;break;
                case RIGHT:case LEFT:horizontal=d;break;
            }

        }
        if (vertical == Direction.UP && horizontal==Direction.RIGHT)return RIGHTUP;
        if (vertical == Direction.DOWN && horizontal==Direction.RIGHT)return RIGHTDOWN;
        if (vertical == Direction.UP && horizontal==Direction.LEFT)return LEFTUP;
        if (vertical == Direction.DOWN && horizontal==Direction.LEFT)return LEFTDOWN;



        return null;
    }


    public EnhancedDirection opposite(Direction direction){
        Direction[] array = toDirection();
        for (int i = 0; i< array.length; i++){

            if (array[i]== direction) array[i] = direction.opposite();
        }
        return toEnhancedDirection(array);

    }




}
