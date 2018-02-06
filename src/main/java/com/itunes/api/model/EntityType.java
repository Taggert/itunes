package com.itunes.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EntityType {

    MOVIE("movie"),
    SONG("song"),
    EBOOK("ebook"),
    ALBUM("album");

    private String name;

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static EntityType getByName(String name) {
        if (name == null) {
            return null;
        }

        for (EntityType entityType : EntityType.values()) {
            if (entityType.getName().equals(name)) {
                return entityType;
            }
        }
        throw new IllegalArgumentException("Entity type not supported");
    }
}