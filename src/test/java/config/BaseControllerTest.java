package config;

import com.example.whatsappbackendtest.service.GroupService;
import com.example.whatsappbackendtest.service.MessageService;
import com.example.whatsappbackendtest.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@Transactional
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest {
    @Autowired
    protected WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @MockBean
    protected UserService userService;
    @MockBean
    protected GroupService groupService;
    @MockBean
    protected MessageService messageService;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .alwaysDo(MockMvcResultHandlers.print()).build();
    }

    protected <T>T convertTo(String source, TypeReference<T> type) throws JsonProcessingException {
        return objectMapper.readValue(source, type);
    }

    protected String asJsonString(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

}
