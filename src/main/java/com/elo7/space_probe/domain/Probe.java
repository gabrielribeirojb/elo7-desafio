package com.elo7.space_probe.domain;

import com.elo7.space_probe.domain.enums.Direction;
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
        if (!planet.verifyIsInPlanetBoundary(position.getX(), position.getY())) {
            throw new IllegalArgumentException("Movimento fora dos limites do planeta");
        }

        switch(direction){
            case NORTH -> position.setY(position.getY() + 1);
            case SOUTH -> position.setY(position.getY() - 1);
            case EAST -> position.setX(position.getX() + 1);
            case WEST -> position.setX(position.getX() -1);
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
}
