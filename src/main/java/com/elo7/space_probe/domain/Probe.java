package com.elo7.space_probe.domain;

import com.elo7.space_probe.domain.enums.Direction;
import com.elo7.space_probe.exceptions.OutOfBoundaryException;
import jakarta.persistence.*;

@Entity
@Table(name = "probe")
public class Probe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planet_id", referencedColumnName = "id", nullable = false)
    private Planet planet;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Deprecated // hibernate only
    public Probe() {}

    public Probe(String name, Integer x, Integer y, Direction direction, Planet planet) {
        this.name = name;
        this.position = new Position(x, y);
        this.direction = direction;
        this.planet = planet;
    }

    public void moveProbe(){
        int nextX = getNextX();
        int nextY = getNextY();

        if (!planet.verifyIsInPlanetBoundary(nextX, nextY)) {
            throw new OutOfBoundaryException("Movimento fora dos limites do planeta");
        }

        position.setX(nextX);
        position.setY(nextY);
    }

    public Integer getNextX() {
        switch (direction) {
            case EAST:
                return position.getX() + 1;
            case WEST:
                return position.getX() - 1;
            default:
                return position.getX();
        }
    }

    public Integer getNextY() {
        switch (direction) {
            case NORTH:
                return position.getY() + 1;
            case SOUTH:
                return position.getY() - 1;
            default:
                return position.getY();
        }
    }

    public void turnLeft() {
        this.direction = this.direction.turnLeft();
    }

    public void turnRight() {
        this.direction = this.direction.turnRight();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getXPosition() {
        return position.getX();
    }

    public Integer getYPosition() {
        return position.getY();
    }

    public Integer getPlanetId() {
        return planet.getId();
    }

    public Direction getDirection() {
        return direction;
    }
}
