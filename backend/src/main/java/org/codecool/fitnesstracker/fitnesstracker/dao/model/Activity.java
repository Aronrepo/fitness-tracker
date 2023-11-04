package org.codecool.fitnesstracker.fitnesstracker.dao.model;

import jakarta.persistence.*;
import lombok.*;
import org.codecool.fitnesstracker.fitnesstracker.user.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    public Activity(ActivityType activityType, LocalDateTime activityDateTime, User user) {
        this.activityType = activityType;
        this.activityDateTime = activityDateTime;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int minutesOfExercise;
    private LocalDateTime activityDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "minutesOfExercise")
    private ActivityType activityType;


}
