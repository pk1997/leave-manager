package com.hashedin.hu;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
public interface EmployeeRepository extends CrudRepository<Employee, Long> {


}
