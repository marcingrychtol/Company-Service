package pl.mdj.rejestrbiurowy.bootstrap;

import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mdj.rejestrbiurowy.exceptions.CannotFindEntityException;
import pl.mdj.rejestrbiurowy.exceptions.EntityConflictException;
import pl.mdj.rejestrbiurowy.exceptions.EntityNotCompleteException;
import pl.mdj.rejestrbiurowy.model.dto.TripDto;
import pl.mdj.rejestrbiurowy.model.entity.*;
import pl.mdj.rejestrbiurowy.model.entity.enums.CarCategory;
import pl.mdj.rejestrbiurowy.model.entity.enums.CarFuel;
import pl.mdj.rejestrbiurowy.model.mappers.CarMapper;
import pl.mdj.rejestrbiurowy.model.mappers.DateMapper;
import pl.mdj.rejestrbiurowy.model.mappers.EmployeeMapper;
import pl.mdj.rejestrbiurowy.model.mappers.TripMapper;
import pl.mdj.rejestrbiurowy.repository.CarRepository;
import pl.mdj.rejestrbiurowy.repository.EmployeeRepository;
import pl.mdj.rejestrbiurowy.repository.TripRepository;
import pl.mdj.rejestrbiurowy.service.CarService;
import pl.mdj.rejestrbiurowy.service.CarServiceImpl;
import pl.mdj.rejestrbiurowy.service.EmployeeService;
import pl.mdj.rejestrbiurowy.service.TripService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
@NoArgsConstructor
public class MdjRunnerImpl implements MdjRunner {

    private final static Logger LOG = LoggerFactory.getLogger(MdjRunnerImpl.class);

    private CarService carService;
    private CarMapper carMapper;
    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper;
    private TripService tripService;
    private TripMapper tripMapper;
    private DateMapper dateMapper;

    @Autowired
    public MdjRunnerImpl(CarService carService, CarMapper carMapper, EmployeeService employeeService, EmployeeMapper employeeMapper, TripService tripService, TripMapper tripMapper, DateMapper dateMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
        this.tripService = tripService;
        this.tripMapper = tripMapper;
        this.dateMapper = dateMapper;
    }

    public void run() {
        {
            Car car1 = new Car();
            Car car2 = new Car();
            Car car3 = new Car();
            Car car4 = new Car();

            car1.setCarCategory(CarCategory.COMPANY);
            car1.setFuel(CarFuel.BENZYNA);
            car1.setMileage((long)270253);
            car1.setName("Dacia Dokker");
            car1.setRegistration("SL 98765");

            car2.setCarCategory(CarCategory.PRIVATE_BIG);
            car2.setFuel(CarFuel.BENZYNA);
            car2.setMileage((long)30000);
            car2.setName("Skoda Octavia");
            car2.setRegistration("NN 8524");

            car3.setCarCategory(CarCategory.PRIVATE_SMALL);
            car3.setFuel(CarFuel.DIESEL);
            car3.setMileage((long)300000);
            car3.setName("Audi A4");
            car3.setRegistration("WZ 6545");

            car4.setCarCategory(CarCategory.COMPANY);
            car4.setFuel(CarFuel.DIESEL);
            car4.setMileage((long)180500);
            car4.setName("VW LT");
            car4.setRegistration("KT 7777");

            for (Car car : Arrays.asList(car1, car2, car3, car4)) {
                try {
                    carService.addOne(carMapper.mapToDto(car));
                } catch (EntityNotCompleteException | EntityConflictException e) {
                    LOG.error(e.getMessage());
                }
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
                    .forEach(e -> {
                        try {
                            employeeService.addOne(employeeMapper.mapToDto(e));
                        } catch (EntityNotCompleteException | EntityConflictException ex) {
                            LOG.error(ex.getMessage());
                        }
                    });

        } // EMPLOYEES

        {
            TripDto trip1 = new TripDto();
            TripDto trip2 = new TripDto();
            TripDto trip3 = new TripDto();
            TripDto trip4 = new TripDto();

            try {
                trip1.setCarId((long) 2);
                trip1.setEmployeeId((long) 2);
                trip1.setStartingDate(dateMapper.toDate(LocalDate.now()));
                trip1.setEndingDate(dateMapper.toDate(LocalDate.now().plusDays(1)));

                trip2.setCarId((long) 2);
                trip2.setEmployeeId((long) 2);
                trip2.setStartingDate(dateMapper.toDate(LocalDate.now().plusDays(2)));
                trip2.setEndingDate(dateMapper.toDate(LocalDate.now().plusDays(4)));

                trip4.setCarId((long) 3);
                trip4.setEmployeeId((long) 3);
                trip4.setStartingDate(dateMapper.toDate(LocalDate.now().plusDays(2)));
                trip4.setEndingDate(dateMapper.toDate(LocalDate.now().plusDays(4)));

                trip3.setCarId((long) 4);
                trip3.setEmployeeId((long) 4);
                trip3.setStartingDate(dateMapper.toDate(LocalDate.now().plusDays(5)));
                trip3.setEndingDate(dateMapper.toDate(LocalDate.now().plusDays(9)));

                for (TripDto trip :
                        Arrays.asList(trip1, trip2, trip3, trip4)) {

                    tripService.addOne(trip);

                }
            } catch (EntityNotCompleteException | EntityConflictException e) {
                LOG.error(e.getMessage());
            }
        } // TRIPS
    }
}
