package org.codecool.fitnesstracker.fitnesstracker.repositories;

import org.codecool.fitnesstracker.fitnesstracker.dao.model.Calorie;
import org.codecool.fitnesstracker.fitnesstracker.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface CalorieRepository extends JpaRepository<Calorie, Long> {
    List<Calorie> findByUserEmail(String email);

    List<Calorie> findByUserAndMealDateTimeAfter(User user, LocalDateTime localDateTime);

    List<Calorie> findByUserEmailAndMealDateTimeAfter(String userEmail, LocalDateTime localDateTime);

}
