package geek.springboot.h2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import geek.springboot.h2.entity.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
