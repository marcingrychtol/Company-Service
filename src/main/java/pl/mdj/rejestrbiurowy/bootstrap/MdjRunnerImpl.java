package pl.mdj.rejestrbiurowy.bootstrap;

import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.entity.Car;
import pl.mdj.rejestrbiurowy.entity.Employee;
import pl.mdj.rejestrbiurowy.entity.Trip;
import pl.mdj.rejestrbiurowy.entity.enums.CarCategory;
import pl.mdj.rejestrbiurowy.entity.enums.CarFuel;
import pl.mdj.rejestrbiurowy.service.interfaces.*;

import java.time.LocalDateTime;
import java.util.Arrays;


@Component
@NoArgsConstructor
public class MdjRunnerImpl implements MdjRunner {

    private final static Logger LOG = LoggerFactory.getLogger(MdjRunnerImpl.class);

    @Autowired
    private CarService carService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TripService tripService;

    public void run() {
        {
            Car car1 = new Car();
            Car car2 = new Car();
            Car car3 = new Car();
            Car car4 = new Car();

            car1.setCarCategory(CarCategory.COMPANY);
            car1.setFuel(CarFuel.BENZYNA);
            car1.setMileage(270253);
            car1.setName("Dacia Dokker");
            car1.setRegistration("SL 98765");

            car2.setCarCategory(CarCategory.PRIVATE_BIG);
            car2.setFuel(CarFuel.BENZYNA);
            car2.setMileage(30000);
            car2.setName("Skoda Octavia");
            car2.setRegistration("NN 8524");

            car3.setCarCategory(CarCategory.PRIVATE_SMALL);
            car3.setFuel(CarFuel.DIESEL);
            car3.setMileage(300000);
            car3.setName("Audi A4");
            car3.setRegistration("WZ 6545");

            car4.setCarCategory(CarCategory.COMPANY);
            car4.setFuel(CarFuel.DIESEL);
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
            Employee employee5 = new Employee();

            employee1.setName("Adam");
            employee1.setSecondName("Adamski");
            employee1.setPhoneNumber("+48 123 456 789");
            employee1.setEmail("adam@mdj.pl");

            employee2.setName("Wojciech");
            employee2.setSecondName("Cieszyński");
            employee2.setPhoneNumber("+48 123 456 789");
            employee2.setEmail("wojciech@mdj.pl");

            employee3.setName("Marcin");
            employee3.setSecondName("Madagaskar");
            employee3.setPhoneNumber("+48 123 456 789");
            employee3.setEmail("marcing@mdj.pl");

            employee5.setName("Norbert");
            employee5.setSecondName("Bezpośredni");
            employee5.setPhoneNumber("+48 123 456 789");
            employee5.setEmail("norbert@mdj.pl");

            for (Employee employee : Arrays.asList(employee1, employee2, employee3, employee5)) {
                employeeService.addOne(employee);

            }
        } // EMPLOYEES

        {
            Trip trip1 = new Trip();
            Trip trip2 = new Trip();
            Trip trip3 = new Trip();

            trip1.setCar(carService.getOne((long) 1));
            trip1.setEmployee(employeeService.getOne((long) 5));
            trip1.setStartingDateTime(LocalDateTime.now());
            trip1.setEndingDateTime(LocalDateTime.now().plusDays(1));

            trip2.setCar(carService.getOne((long) 2));
            trip2.setEmployee(employeeService.getOne((long) 6));
            trip2.setStartingDateTime(LocalDateTime.now().plusDays(2));
            trip2.setEndingDateTime(LocalDateTime.now().plusDays(4));

            trip3.setCar(carService.getOne((long) 3));
            trip3.setEmployee(employeeService.getOne((long) 7));
            trip3.setStartingDateTime(LocalDateTime.now().plusDays(5));
            trip3.setEndingDateTime(LocalDateTime.now().plusDays(9));

            for (Trip trip :
                    Arrays.asList(trip1, trip2, trip3)) {
                tripService.addOne(trip);
            }
            for (Trip trip :
                    Arrays.asList(trip1, trip2, trip3)) {
                tripService.addOne(trip);
            }
        } // TRIPS
    }
}
