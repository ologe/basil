package dev.olog.basil.domain.entity;

public class Recipe {

    private final long id;
    private final String name;

    public Recipe(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
