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
import pl.mdj.rejestrbiurowy.repository.CarRepository;
import pl.mdj.rejestrbiurowy.repository.EmployeeRepository;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.service.interfaces.*;
import pl.mdj.rejestrbiurowy.service.mappers.CarMapper;
import pl.mdj.rejestrbiurowy.service.mappers.EmployeeMapper;
import pl.mdj.rejestrbiurowy.service.mappers.TripMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@NoArgsConstructor
public class MdjRunnerImpl implements MdjRunner {

    private final static Logger LOG = LoggerFactory.getLogger(MdjRunnerImpl.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TripRepository tripRepository;


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
                carRepository.save(car);
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
                    .forEach(e -> employeeRepository.save(e));

        } // EMPLOYEES

        {
            Trip trip1 = new Trip();
            Trip trip2 = new Trip();
            Trip trip3 = new Trip();
            Trip trip4 = new Trip();

            trip1.setCar(carRepository.findById((long) 2).orElse(new Car()));
            trip1.setEmployee(employeeRepository.findById((long) 2).orElse(new Employee()));
            trip1.setStartingDate(LocalDate.now());
            trip1.setEndingDate(LocalDate.now().plusDays(1));

            trip2.setCar(carRepository.findById((long) 2).orElse(new Car()));
            trip2.setEmployee(employeeRepository.findById((long) 2).orElse(new Employee()));
            trip2.setStartingDate(LocalDate.now().plusDays(2));
            trip2.setEndingDate(LocalDate.now().plusDays(4));

            trip4.setCar(carRepository.findById((long) 3).orElse(new Car()));
            trip4.setEmployee(employeeRepository.findById((long) 3).orElse(new Employee()));
            trip4.setStartingDate(LocalDate.now().plusDays(2));
            trip4.setEndingDate(LocalDate.now().plusDays(4));

            trip3.setCar(carRepository.findById((long) 4).orElse(new Car()));
            trip3.setEmployee(employeeRepository.findById((long) 4).orElse(new Employee()));
            trip3.setStartingDate(LocalDate.now().plusDays(5));
            trip3.setEndingDate(LocalDate.now().plusDays(9));

            for (Trip trip :
                    Arrays.asList(trip1, trip2, trip3, trip4)) {
                tripRepository.save(trip);
            }
        } // TRIPS
    }
}
