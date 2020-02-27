package pl.mdj.rejestrbiurowy.bootstrap;

import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.*;
import pl.mdj.rejestrbiurowy.model.entity.enums.CarCategory;
import pl.mdj.rejestrbiurowy.model.entity.enums.CarFuel;
import pl.mdj.rejestrbiurowy.service.interfaces.*;
import pl.mdj.rejestrbiurowy.service.mappers.TripMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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

    @Autowired
    private TripMapper tripMapper;

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

            List<Employee> employeeList = new ArrayList<>();

            employee1.setName("Adam");
            employee1.setSecondName("Adamski");
            employee1.setPhoneNumber("+48 123 456 789");
            employee1.setEmail("adam@mdj.pl");
            employeeList.add(employee1);

            employee2.setName("Wojciech");
            employee2.setSecondName("Cieszyński");
            employee2.setPhoneNumber("+48 123 456 789");
            employee2.setEmail("wojciech@mdj.pl");
            employeeList.add(employee2);

            employee3.setName("Marcin");
            employee3.setSecondName("Madagaskar");
            employee3.setPhoneNumber("+48 123 456 789");
            employee3.setEmail("marcing@mdj.pl");
            employeeList.add(employee3);

            employee5.setName("Norbert");
            employee5.setSecondName("Bezpośredni");
            employee5.setPhoneNumber("+48 123 456 789");
            employee5.setEmail("norbert@mdj.pl");
            employeeList.add(employee5);

            employeeList.stream()
                    .forEach(e -> employeeService.addOne(e));

        } // EMPLOYEES

        {
            TripDto trip1 = new TripDto();
            TripDto trip2 = new TripDto();
            TripDto trip3 = new TripDto();
            TripDto trip4 = new TripDto();

            trip1.setCarId(carService.findOne(1L));
            trip1.setEmployeeId(employeeService.findOne((long) 1));
            trip1.setStartingDate(LocalDateTime.now());
            trip1.setEndingDate(LocalDateTime.now().plusDays(1));

            trip2.setCar(carService.findOne((long) 2));
            trip2.setEmployee(employeeService.findOne((long) 2));
            trip2.setStartingDateTime(LocalDateTime.now().plusDays(2));
            trip2.setEndingDateTime(LocalDateTime.now().plusDays(4));

            trip4.setCar(carService.findOne((long) 3));
            trip4.setEmployee(employeeService.findOne((long) 3));
            trip4.setStartingDateTime(LocalDateTime.now().plusDays(2));
            trip4.setEndingDateTime(LocalDateTime.now().plusDays(4));

            trip3.setCar(carService.findOne((long) 4));
            trip3.setEmployee(employeeService.findOne((long) 4));
            trip3.setStartingDateTime(LocalDateTime.now().plusDays(5));
            trip3.setEndingDateTime(LocalDateTime.now().plusDays(9));

            for (Trip trip :
                    Arrays.asList(trip1, trip2, trip3, trip4)) {
                tripService.addOne(trip);
            }
        } // TRIPS
    }
}
