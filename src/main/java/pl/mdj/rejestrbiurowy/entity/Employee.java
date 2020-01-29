package pl.mdj.rejestrbiurowy.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.mdj.rejestrbiurowy.entity.enums.EEmployeeCategory;
import pl.mdj.rejestrbiurowy.entity.interfaces.MyEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Employee extends MyEntity {

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    private String name;
//    @Column(name = "employee_category")
    @Enumerated(EnumType.STRING)
    private EEmployeeCategory employeeCategory;
    private String phoneNumber;
    private String eMail;
    private BigDecimal salary;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "employee_task",
            joinColumns = @JoinColumn(name = "taskId"),
            inverseJoinColumns = @JoinColumn(name = "employeeId")
    )
    private List<Task> tasks;

    public List<Task> getTasks() {
        if (tasks == null){
            tasks = new ArrayList<>();
        }
        return tasks;
    }

    /*
    public Employee() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public EEmployeeCategory getEmployeeCategory() {
        return employeeCategory;
    }
    public void setEmployeeCategory(EEmployeeCategory employeeCategory) {
        this.employeeCategory = employeeCategory;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String geteMail() {
        return eMail;
    }
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    public BigDecimal getSalary() {
        return salary;
    }
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public int hashCode() {
        return super.hashCode()+31;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }*/
}
