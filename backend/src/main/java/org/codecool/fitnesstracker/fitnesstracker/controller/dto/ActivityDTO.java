package org.codecool.fitnesstracker.fitnesstracker.controller.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public record ActivityDTO(String activity, int activityDuration, double calories, LocalDateTime activityDateTime) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityDTO that = (ActivityDTO) o;
        return calories == that.calories && Objects.equals(activity, that.activity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activity, calories);
    }
}

