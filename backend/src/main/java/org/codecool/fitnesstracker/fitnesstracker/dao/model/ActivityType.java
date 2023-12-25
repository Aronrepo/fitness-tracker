package org.codecool.fitnesstracker.fitnesstracker.dao.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activityType;
    private double calories;

    @OneToMany(mappedBy = "activityType", cascade = CascadeType.MERGE)
    private Set<Activity> activity = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityType that = (ActivityType) o;
        return Double.compare(that.calories, calories) == 0 && Objects.equals(activityType, that.activityType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityType, calories);
    }
}
