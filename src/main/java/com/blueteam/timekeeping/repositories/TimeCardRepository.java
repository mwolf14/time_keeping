package com.blueteam.timekeeping.repositories;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.models.TimeCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeCardRepository extends JpaRepository<TimeCard, Integer> {
}
