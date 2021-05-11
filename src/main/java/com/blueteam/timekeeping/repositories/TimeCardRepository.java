/* Author: Matt Wolf
 * Date: 4/17/21
 * Desc: TimeCardRepository extends the JpaRepository to handle all standard CRUD operations 
 * Revision History:
 * 	V0.1.0
*/

package com.blueteam.timekeeping.repositories;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.models.TimeCard;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TimeCardRepository extends JpaRepository<TimeCard, Integer> {

	List<TimeCard> getAllByNeedsApprovedTrue();
	//@Query("select t from timecards  t where t.date = t.start")
	//List<TimeCard> getAllByDateStartGreaterThenAndLessThenEnd(LocalDateTime start, LocalDateTime end);
	
}
