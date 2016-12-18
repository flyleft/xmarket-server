package me.jcala.xmarket.server.ctrl;

import me.jcala.xmarket.server.entity.pojo.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class JwtApplicationTest {
    @Autowired
    private WebApplicationContext context;

    private RestTemplate restTemplate = new TestRestTemplate();

    private MockMvc mvc;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @SuppressWarnings("unchecked")
    @Test
    public void testAuthController() throws Exception {
        Result<String> result = restTemplate.getForObject("http://127.0.0.1/api/v1/auth", Result.class);
        assertNotNull(result);
        System.out.print(result);
    }
}
