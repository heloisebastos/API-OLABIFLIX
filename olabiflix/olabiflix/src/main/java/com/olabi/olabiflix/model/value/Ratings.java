package com.olabi.olabiflix.model.value;

import jakarta.persistence.Embeddable;

@Embeddable
public class Ratings {
    private String ratings;

    private String linkes;

    protected Ratings() {

    }

    public Ratings(String ratings, String linkes) {
        this.ratings = ratings;
        this.linkes = linkes;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getLinkes() {
        return linkes;
    }

    public void setLinkes(String linkes) {
        this.linkes = linkes;
    }

    @Override
    public String toString() {
        return "Ratings [ratings=" + ratings + ", linkes=" + linkes + "]";
    }

}
