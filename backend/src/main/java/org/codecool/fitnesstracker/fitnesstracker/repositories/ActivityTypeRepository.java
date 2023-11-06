package org.codecool.fitnesstracker.fitnesstracker.repositories;

import org.codecool.fitnesstracker.fitnesstracker.dao.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long> {
    List<ActivityType> findActivityTypeByActivityTypeIsLikeIgnoreCase(String activityType);
    ActivityType findActivityTypeById(long activityTypeId);

}
