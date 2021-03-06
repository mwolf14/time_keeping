/* Author: Matt Wolf
 * Date: 4/17/21
 * Desc: EmployeeRepository extends the JpaRepository to handle all standard CRUD operations, 
 * along with any custom defined queries that rely on the interface to JpaRepository
 * Revision History: 
 * V0.1.0
*/
package com.blueteam.timekeeping.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blueteam.timekeeping.models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	Employee findByUserName(String username);

	List<Employee> getAllByLastName(String string);

	List<Employee> getByApprovedFalse();
	
}
