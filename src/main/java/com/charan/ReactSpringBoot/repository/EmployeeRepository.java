package com.charan.ReactSpringBoot.repository;

import com.charan.ReactSpringBoot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
