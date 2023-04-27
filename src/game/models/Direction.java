package game.models;

public enum Direction {
    LEFT("left"),
    RIGHT("right"),
    UP("up"),
    DOWN("down");
    private String string;
    Direction(String string){
        this.string = string;
    }
    public String toString(){
        return string;
    }
}
