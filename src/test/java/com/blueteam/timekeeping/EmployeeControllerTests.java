package com.blueteam.timekeeping;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import javax.ws.rs.core.MediaType;
//import java.awt.PageAttributes.MediaType;

import com.blueteam.timekeeping.models.Employee;
import com.blueteam.timekeeping.repositories.EmployeeRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTests {

	@MockBean
	private EmployeeRepository empRepo;
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Post Create Employee")
	public void TestCreateEmployee() throws Exception {
		Employee mockEmployee = new Employee();
		mockEmployee.setFirstName("John");
		mockEmployee.setLastName("Doe");
		mockEmployee.setId(1);
		mockEmployee.setIsSupervisor(true);
		mockEmployee.setUserName("JDoe");
		mockEmployee.setDob(LocalDate.now());
		mockEmployee.setPassword("password");
		JSONObject payload = new JSONObject();
		payload.wrap(mockEmployee);
		mockMvc.perform(post("/createemployee").contentType(MediaType.APPLICATION_JSON)
				.content(payload.toString())
			    .characterEncoding("utf-8"))
			    .andExpect(status().isOk())
			    .andReturn();;
	}
}
