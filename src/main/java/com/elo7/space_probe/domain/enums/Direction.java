package com.elo7.space_probe.domain.enums;

public enum Direction {

    NORTH,
    SOUTH,
    WEST,
    EAST;

    public Direction turnLeft(){
        return switch(this) {
            case NORTH -> WEST;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
            case EAST -> NORTH;
        };
    }

    public Direction turnRight(){
        return switch(this) {
            case NORTH -> EAST;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            case EAST -> SOUTH;
        };
    }

}
