package game.entity.models;

import game.ui.panels.game.PitchPanel;

public enum EnhancedDirection {
    LEFTUP,
    LEFTDOWN,
    RIGHTUP,
    RIGHTDOWN;

    public Direction[] toDirection(){
        return switch (this) {
            case LEFTUP -> new Direction[]{Direction.LEFT, Direction.UP};
            case LEFTDOWN -> new Direction[]{Direction.LEFT, Direction.DOWN};
            case RIGHTDOWN -> new Direction[]{Direction.RIGHT, Direction.DOWN};
            case RIGHTUP -> new Direction[]{Direction.RIGHT, Direction.UP};
        };

    }

    public static EnhancedDirection toEnhancedDirection(Direction[] directions){
        Direction vertical = null;
        Direction horizontal = null;

        if(directions.length != 2){
            return null;
        }

        for (Direction d: directions) {
            switch (d) {
                case DOWN, UP -> vertical = d;
                case RIGHT, LEFT -> horizontal = d;
            }
        }

        if (vertical == Direction.UP && horizontal == Direction.RIGHT) return RIGHTUP;
        if (vertical == Direction.DOWN && horizontal == Direction.RIGHT) return RIGHTDOWN;
        if (vertical == Direction.UP && horizontal == Direction.LEFT) return LEFTUP;
        if (vertical == Direction.DOWN && horizontal == Direction.LEFT) return LEFTDOWN;

        return null;
    }


    public EnhancedDirection opposite(Direction direction){
        Direction[] array = toDirection();
        for (int i = 0; i< array.length; i++){
            if (array[i]== direction) array[i] = direction.opposite();
        }

        return toEnhancedDirection(array);

    }

    public static EnhancedDirection randomDirectionTowardsCenter(Entity entity){
        Coordinates centerEntityCoords = new Coordinates(entity.getCoords().getX()+entity.getSize()/2,entity.getCoords().getY()+entity.getSize()/2);
        Direction newHorizontalDirection;
        Direction newVerticalDirection;
        newHorizontalDirection = centerEntityCoords.getX() > PitchPanel.DIMENSION.getWidth() / 2 ? Direction.LEFT : Direction.RIGHT;
        newVerticalDirection = centerEntityCoords.getY() < PitchPanel.DIMENSION.getHeight() / 2 ? Direction.DOWN : Direction.UP;
        return toEnhancedDirection(new Direction[]{ newHorizontalDirection, newVerticalDirection});
    }
}
