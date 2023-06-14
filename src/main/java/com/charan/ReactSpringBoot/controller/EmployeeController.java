package com.charan.ReactSpringBoot.controller;

import com.charan.ReactSpringBoot.exception.EmployeeNotFoundException;
import com.charan.ReactSpringBoot.model.Employee;
import com.charan.ReactSpringBoot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/saveEmployee")
    public Employee saveEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @GetMapping("/getEmployees")
    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping("/getEmployee/{id}")
    Employee getEmployeeById(@PathVariable int id){
        return employeeRepository.findById(id)
                .orElseThrow(()->new EmployeeNotFoundException(id));
    }

    @PutMapping("/editEmployee/{id}")
    Employee updateEmployeeRecord(@RequestBody Employee employee_to_update,@PathVariable int id){
        /*Employee employee_updated = new Employee();
        Optional<Employee> employee_frm_db = employeeRepository.findById(id);
        boolean employee_present = employee_frm_db.isPresent();
        if (employee_present){
            employee_updated = employee_frm_db.get();
            employee_updated.setName(employee.getName());
            employee_updated.setEmail(employee.getEmail());
            employee_updated.setDepartment(employee.getDepartment());

            return employeeRepository.save(employee_updated);
        }
        else {
            throw new EmployeeNotFoundException(id);
        }*/
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(employee_to_update.getName());
                    employee.setEmail(employee_to_update.getEmail());
                    employee.setDepartment(employee_to_update.getDepartment());
                    return employeeRepository.save(employee);
                }).orElseThrow(()->new EmployeeNotFoundException(id));
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable int id){
        if (!employeeRepository.existsById(id))
            throw new EmployeeNotFoundException(id);
        employeeRepository.deleteById(id);
        return "Employee with id "+id+" has been deleted successfully!!";
    }
}
