package urujdas.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.AfterMethod;
import urujdas.config.WebConfig;
import urujdas.model.users.AgeRange;
import urujdas.model.users.Gender;
import urujdas.model.users.GenderPreferences;
import urujdas.model.users.RelationsPreferences;
import urujdas.model.users.UserFilter;
import urujdas.service.DatingService;
import urujdas.web.common.CommonExceptionHandler;
import urujdas.web.common.WebCommons;
import urujdas.web.user.DatingController;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebConfig.class, DatingControllerTest.LocalContext.class })
@WebAppConfiguration
public class DatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DatingService datingService;

    @AfterMethod
    public void tearDown() throws Exception {
        verifyNoMoreInteractions(datingService);
        reset(datingService);
    }

    @Test
    public void getLatestUsersByFilter_hp() throws Exception {
        UserFilter userFilter = UserFilter.builder()
                .withGender(Gender.FEMALE)
                .withGenderPreferences(GenderPreferences.FEMALES)
                .withRelationsPreferences(RelationsPreferences.INTERESTS_RELATIONS)
                .withAgeRange(new AgeRange(1, 2))
                .build();

        String requestInJson = objectMapper.writeValueAsString(userFilter);

        mockMvc.perform(get(WebCommons.VERSION_PREFIX + "/users/dating")
                .content(requestInJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Configuration
    static class LocalContext {
        @Bean
        public DatingController datingController() {
            return new DatingController();
        }

        @Bean
        public CommonExceptionHandler commonExceptionHandler() {
            return new CommonExceptionHandler();
        }

        @Bean
        public MockMvc mockMvc(WebApplicationContext ctx) {
            return MockMvcBuilders.webAppContextSetup(ctx)
                    .build();
        }

        @Bean
        public DatingService datingService() {
            return mock(DatingService.class);
        }
    }

}