package com.elo7.space_probe.domain;

import com.elo7.space_probe.domain.enums.Direction;
import com.elo7.space_probe.exceptions.CollisionException;
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
        int newX = getXPosition();
        int newY = getYPosition();

        switch (direction) {
            case EAST -> newX += 1;
            case WEST -> newX -= 1;
            case NORTH -> newY += 1;
            case SOUTH -> newY -= 1;
        }

        Position newPosition = new Position(newX, newY);

        if (!planet.isInPlanetBoundary(newPosition)) {
            throw new OutOfBoundaryException("Movimento fora dos limites do planeta");
        }

        if(planet.isPositionOccupied(newPosition)){
            throw new CollisionException("Posição " + newPosition + "está ocupada");
        }

        this.position = newPosition;
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

    public Planet getPlanet(){
        return planet;
    }

    public Direction getDirection() {
        return direction;
    }

    public Position getPosition(){
        return position;
    }
}
