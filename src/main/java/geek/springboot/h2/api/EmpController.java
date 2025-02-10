package geek.springboot.h2.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import geek.springboot.h2.entity.Employee;
import geek.springboot.h2.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/emp")
@Tag(name = "Sample Rest Controller", description = "Sample Rest Controller for CRUD Operations")
public class EmpController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Create Employee", description = "Create a new employee record")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = {
				@Content(schema = @Schema(implementation = Employee.class), mediaType = MediaType.APPLICATION_JSON_VALUE) })})
	Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@PutMapping("/update/{id}")
	@Operation(summary = "Update Employee", description = "Update existing employee by id")
	@ApiResponses({
		@ApiResponse(responseCode = "200", content = {
				@Content(schema = @Schema(implementation = Employee.class), mediaType = MediaType.APPLICATION_JSON_VALUE) }),
		@ApiResponse(responseCode = "404", description = "Employee not found", content = {
				@Content(schema = @Schema()) }) })
	Employee updateEmployee(@PathVariable("id") Long employeeId, @RequestBody Employee employee) {
		Optional<Employee> optEmployee = employeeRepository.findById(employeeId);
		if(optEmployee.isPresent()) {
			employee.setId(employeeId);
			return employeeRepository.save(employee);
		}else {
			throw new InternalError("Employee not found");
		}
	}
	
	@DeleteMapping(path = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Delete User", description = "Delete by Id")
	void deleteById(@Parameter(description = "EmployeeId") @PathVariable Long id) {
		employeeRepository.deleteById(id);
	}
	
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get Employee", description = "Get Employee by Id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Employee.class), mediaType = MediaType.APPLICATION_JSON_VALUE) })})
	Employee getUser(@Parameter(description = "User ID") @PathVariable Long id) {
		return employeeRepository.findById(id).orElse(null);
	}
	
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get all employees", description = "Get all employees")
	List<Employee> findAllEmployee() {
		return (List<Employee>) employeeRepository.findAll();
	}

}
