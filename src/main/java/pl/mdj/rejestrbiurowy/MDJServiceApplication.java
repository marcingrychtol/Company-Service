package pl.mdj.rejestrbiurowy;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mdj.rejestrbiurowy.bootstrap.MdjRunner;

import javax.annotation.PostConstruct;

@SpringBootApplication
@NoArgsConstructor
public class MDJServiceApplication {

    private MdjRunner mdjRunner;

    @Autowired
    public MDJServiceApplication(MdjRunner mdjRunner) {
        this.mdjRunner = mdjRunner;
    }

    @PostConstruct
    private void postConstruct(){
        mdjRunner.run();
    }

    public static void main(String[] args) {
        SpringApplication.run(MDJServiceApplication.class, args);
    }


}
