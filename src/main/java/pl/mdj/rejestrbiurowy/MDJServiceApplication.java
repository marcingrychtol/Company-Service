package pl.mdj.rejestrbiurowy;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mdj.rejestrbiurowy.bootstrap.MdjRunner;

@SpringBootApplication
@NoArgsConstructor
public class MDJServiceApplication {

    private static MdjRunner mdjRunner; // TODO: #3 use PostConstruct

    @Autowired
    public MDJServiceApplication(MdjRunner mdjRunner) {
        MDJServiceApplication.mdjRunner = mdjRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(MDJServiceApplication.class, args);
        mdjRunner.run();
    }


}
