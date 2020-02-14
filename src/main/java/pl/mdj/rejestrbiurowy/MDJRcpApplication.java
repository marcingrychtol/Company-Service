package pl.mdj.rejestrbiurowy;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mdj.rejestrbiurowy.runners.MdjAppRunner;
import pl.mdj.rejestrbiurowy.runners.MdjRunner;

@SpringBootApplication
@NoArgsConstructor
public class MDJRcpApplication {

    private static MdjRunner mdjRunner;

    @Autowired
    public MDJRcpApplication(MdjRunner mdjRunner) {
        MDJRcpApplication.mdjRunner = mdjRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(MDJRcpApplication.class);
        mdjRunner.run();
    }


}
