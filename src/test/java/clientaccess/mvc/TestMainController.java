package clientaccess.mvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import pl.mdj.rejestrbiurowy.clientaccess.mvc.ControllerMVC;

//@RunWith(SpringRunner.class)
//@WebMvcTest(ControllerMVC.class)
public class TestMainController {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/"));
    }
}
