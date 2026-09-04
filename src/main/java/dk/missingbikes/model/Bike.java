package dk.missingbikes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "bikes")
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Size(max = 50, message = "Description must be 50 characters or less")
    private String description;

    private LocalDate date;
    private Double latitude;
    private Double longitude;
    private String address;

    public Bike() {
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
