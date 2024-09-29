package io.example.parkinglotmanagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table("parking_spots")
public class ParkingSpot {

    @Id
    private Long id;

    private String name;

    private boolean reserved;

    public ParkingSpot() {
    }

    public ParkingSpot(String name) {
        this.name = name;
        this.reserved = false;
    }

    public ParkingSpot(Long id, String name, boolean reserved) {
        this.id = id;
        this.name = name;
        this.reserved = reserved;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", reserved=" + reserved +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingSpot that)) return false;
        return isReserved() == that.isReserved() && Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), isReserved());
    }
}
