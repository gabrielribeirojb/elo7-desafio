package com.elo7.space_probe.domain.enums;

public enum Direction {

    NORTH {
        @Override
        public Direction turnLeft() {
            return WEST;
        }

        @Override
        public Direction turnRight() {
            return EAST;
        }
    },
    SOUTH {
        @Override
        public Direction turnLeft(){
            return EAST;
        }

        @Override
        public Direction turnRight() {
            return WEST;
        }
    },
    WEST {
        @Override
        public Direction turnLeft() {
            return SOUTH;
        }

        @Override
        public Direction turnRight() {
            return NORTH;
        }
    },
    EAST {
        @Override
        public Direction turnLeft() {
            return NORTH;
        }

        @Override
        public Direction turnRight() {
            return SOUTH;
        }
    };

    public abstract Direction turnLeft();
    public abstract Direction turnRight();

}
