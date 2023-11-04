package org.codecool.fitnesstracker.fitnesstracker.repositories;

import org.codecool.fitnesstracker.fitnesstracker.dao.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long> {
    ActivityType findActivityTypeById(long activityTypeId);
}
