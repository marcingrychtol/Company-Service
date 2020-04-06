package clientaccess.mvc;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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
