package com.itunes.api.model.web;

import com.itunes.api.model.EntityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LookupRequest {

    @NotBlank
    private Integer id;

    private EntityType entity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LookupRequest that = (LookupRequest) o;
        return Objects.equals(id, that.id) &&
                entity == that.entity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, entity);
    }
}