package pl.mdj.rejestrbiurowy.bootstrap;

import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.entity.Car;
import pl.mdj.rejestrbiurowy.entity.Employee;
import pl.mdj.rejestrbiurowy.entity.enums.ECarCategory;
import pl.mdj.rejestrbiurowy.entity.enums.ECarFuel;
import pl.mdj.rejestrbiurowy.service.interfaces.*;

import java.util.Arrays;


@Component
@NoArgsConstructor
public class MdjRunnerImpl implements MdjRunner {

    private final static Logger LOG = LoggerFactory.getLogger(MdjRunnerImpl.class);

    @Autowired
    private CarService carService;

    @Autowired
    private EmployeeService employeeService;

    public void run() {
        {
            Car car1 = new Car();
            Car car2 = new Car();
            Car car3 = new Car();
            Car car4 = new Car();

            car1.setCarCategory(ECarCategory.COMPANY);
            car1.setFuel(ECarFuel.BENZYNA);
            car1.setMileage(270253);
            car1.setName("Dacia Dokker");
            car1.setRegistration("SL 1404");

            car2.setCarCategory(ECarCategory.PRIVATE_BIG);
            car2.setFuel(ECarFuel.BENZYNA);
            car2.setMileage(30000);
            car2.setName("Skoda Octavia");
            car2.setRegistration("NN 8524");

            car3.setCarCategory(ECarCategory.PRIVATE_SMALL);
            car3.setFuel(ECarFuel.DIESEL);
            car3.setMileage(300000);
            car3.setName("Audi A4");
            car3.setRegistration("WZ 4569");

            car4.setCarCategory(ECarCategory.COMPANY);
            car4.setFuel(ECarFuel.DIESEL);
            car4.setMileage(180500);
            car4.setName("VW LT");
            car4.setRegistration("KT 7777");

            for (Car car : Arrays.asList(car1, car2, car3, car4)) {
                carService.addOne(car);
            }
        } //CARS

        {
            Employee employee1 = new Employee();
            Employee employee2 = new Employee();
            Employee employee3 = new Employee();
            Employee employee4 = new Employee();
            Employee employee5 = new Employee();

            employee1.setName("Adam");
            employee1.setSecondName("Grychtoł");
            employee1.setPhoneNumber("123456789");
            employee1.setEMail("adamgrychtol@mdj.pl");

            employee2.setName("Wojciech");
            employee2.setSecondName("Kołodziej");
            employee2.setPhoneNumber("123456789");
            employee2.setEMail("wojciechkolodziej@mdj.pl");

            employee3.setName("Marcin");
            employee3.setSecondName("Grychtoł");
            employee3.setPhoneNumber("123456789");
            employee3.setEMail("marcingrychtol@mdj.pl");

            employee4.setName("Adam");
            employee4.setSecondName("Broja");
            employee4.setPhoneNumber("123456789");
            employee4.setEMail("adambroja@mdj.pl");

            employee5.setName("Norbert");
            employee5.setSecondName("Przeliorz");
            employee5.setPhoneNumber("123456789");
            employee5.setEMail("norbertprzeliorz@mdj.pl");

            for (Employee employee : Arrays.asList(employee1, employee2, employee3, employee4, employee5)) {
                employeeService.addOne(employee);

            }
        } // EMPLOYEES
    }
}
