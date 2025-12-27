package com.IshantProject.demo.rest;


import com.IshantProject.demo.Employee;
import com.IshantProject.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class EmployeeRestController {

    private EmployeeService employeeService;

    private JsonMapper jsonMapper;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService, JsonMapper theJsonMapper) {
        this.employeeService = employeeService;
        jsonMapper = theJsonMapper;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee findById(@PathVariable int employeeId) {
        return employeeService.findById(employeeId);
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee) {
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    @PostMapping("/employees")
    public Employee saveEmployee(@RequestBody Employee theEmployee) {
        theEmployee.setId(0);
        Employee dbEmployee = employeeService.save(theEmployee);
        return dbEmployee;
    }

    @PatchMapping("/employees/{employeeId}")
    public Employee patchEmployee(@PathVariable int employeeId, @RequestBody Map<String, Object> patchPayload) {

        Employee tempEmployee = employeeService.findById(employeeId);

        if (tempEmployee == null) {
            throw new RuntimeException("Employee is not Found : " + employeeId);
        }

        if (patchPayload.containsKey("id")) {
            throw new RuntimeException("Employee id not allowed in request body -" + employeeId);
        }

        Employee patchtheEmployee = jsonMapper.updateValue(tempEmployee, patchPayload);

        //Updating the Patch value in the Database
        Employee dbEmployee = employeeService.save(patchtheEmployee);

        return dbEmployee;
    }

    @DeleteMapping("/employees/{employeeId}")
    public String delete(@PathVariable int employeeId) {

        Employee tempEmployee = employeeService.findById(employeeId);

        if (tempEmployee == null) {
            throw new RuntimeException("Employee is not Found : " + employeeId);
        }
        employeeService.deleteById(employeeId);

        return "Deleted Employee Id - " + employeeId;
    }
}
