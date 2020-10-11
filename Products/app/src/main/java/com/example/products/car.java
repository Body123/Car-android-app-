package com.example.products;

public class car {
    private  int id;
    private  String model;
    private  String color;
    private  double distancePerLetter;
    private  String image;
    private String description;

    public car(int id, String model, String color, double distancePerLetter, String image, String description) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.distancePerLetter = distancePerLetter;
        this.image = image;
        this.description = description;
    }

    public car(String model, String color, double distancePerLetter, String image, String description) {
        this.model = model;
        this.color = color;
        this.distancePerLetter = distancePerLetter;
        this.image = image;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    public double getDistancePerLetter() {
        return distancePerLetter;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDistancePerLetter(double distancePerLetter) {
        this.distancePerLetter = distancePerLetter;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
