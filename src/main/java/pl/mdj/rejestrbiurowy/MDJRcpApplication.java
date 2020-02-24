package pl.mdj.rejestrbiurowy;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mdj.rejestrbiurowy.bootstrap.MdjRunner;

@SpringBootApplication
@NoArgsConstructor
public class MDJRcpApplication {

    private static MdjRunner mdjRunner; // TODO: #3

    @Autowired
    public MDJRcpApplication(MdjRunner mdjRunner) {
        MDJRcpApplication.mdjRunner = mdjRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(MDJRcpApplication.class);
        mdjRunner.run();
    }


}
