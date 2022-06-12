package nl.tudelft.sem.template.authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    private transient User user1;
    private transient User user2;
    private transient User user3;

    private final transient String userParam1 = "id";
    private final transient String userParam2 = "firstName";
    private final transient String userParam3 = "lastName";
    private final transient String userParam4 = "password";
    private final transient String userParam5 = "role";
    private final transient String userParam6 = "netId";

    private final transient String addUserPath = "/demo/register";
    private final transient String getUserPath = "/demo/all";
    private final transient String updateUserPath = "/demo/update";
    private final transient String deleteUserPath = "/demo/delete";
    private final transient String getUserId = "/demo/getUserId";

    private final transient String contentType = "application/json";



    @Autowired
    private transient MockMvc mockMvc;

    @MockBean
    private transient UserController userController;

    private transient String authorization = "authorization";


    /**
     * .
     * before each method to run before each method
     */
    @BeforeEach
    public void init() {
        user1 = new User(1, "test", "best", "bassword", "user", "123456");
        user2 = new User(2, "test2", "best2", "password", "user", "333333");
        user3 = new User(3, "test3", "best3", "password", "user", "246890");
    }

    @Test
    public void whenValidInput_CapturesRightInput() throws Exception {
        String username = "netid";
        String password = "pwd";

        String body = "{\n" + "    \"username\": \"" + username + "\",\n"
                + "    \"password\": \"" + password + "\"\n"
                + "}";

        MvcResult mvcResult = mockMvc.perform(post("/auth")
                .contentType(contentType)
                .content(body)).andExpect(status().isOk()).andReturn();
        String token = mvcResult.getResponse().getHeader(authorization);
        System.out.println(token);

        mockMvc.perform(post(addUserPath).contentType(contentType)
                .param(userParam1, Integer.toString(user1.getId()))
                .param(userParam2, user1.getFirstName())
                .param(userParam3, user1.getLastName())
                .param(userParam4, user1.getPassword())
                .param(userParam5, user1.getRole())
                .param(userParam6, user1.getNetId()).header(authorization, token))
                .andExpect(status().isOk());
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> firstNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> lastNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> roleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> netIdCaptor = ArgumentCaptor.forClass(String.class);
        verify(userController, times(1)).addNewUser(idCaptor.capture(),
                firstNameCaptor.capture(),
                lastNameCaptor.capture(),
                passwordCaptor.capture(),
                roleCaptor.capture(),
                netIdCaptor.capture());
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(idCaptor.getValue(), user1.getId());
        assertEquals(firstNameCaptor.getValue(), user1.getFirstName());
        assertEquals("", res);

    }

    @Test
    public void whenValidInput_thenReturnsSaved() throws Exception {
        String username = "netid";
        String password = "pwd";

        String body = "{\n" + "    \"username\": \"" + username + "\",\n"
                + "    \"password\": \"" + password + "\"\n"
                + "}";

        MvcResult mvcResult = mockMvc.perform(post("/auth")
                .contentType(contentType)
                .content(body)).andExpect(status().isOk()).andReturn();
        String token = mvcResult.getResponse().getHeader(authorization);
        System.out.println(token);

        mockMvc.perform(put(updateUserPath).contentType(contentType)
                .param(userParam1, Integer.toString(user1.getId()))
                .param(userParam2, user1.getFirstName())
                .param(userParam3, user1.getLastName())
                .param(userParam4, user1.getPassword())
                .param(userParam5, user1.getRole())
                .param(userParam6, user1.getNetId()).header(authorization, token))
                .andExpect(status().isOk());
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> firstNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> lastNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> roleCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> netIdCaptor = ArgumentCaptor.forClass(String.class);
        verify(userController, times(1)).updateUser(idCaptor.capture(),
                firstNameCaptor.capture(),
                lastNameCaptor.capture(),
                passwordCaptor.capture(),
                roleCaptor.capture(),
                netIdCaptor.capture());
        assertEquals(idCaptor.getValue(), user1.getId());
        assertEquals(firstNameCaptor.getValue(), user1.getFirstName());
    }

    @Test
    public void deleteUserControllerTest() throws Exception {
        String username = "netid";
        String password = "pwd";

        String body = "{\n" + "    \"username\": \"" + username + "\",\n"
                + "    \"password\": \"" + password + "\"\n"
                + "}";

        MvcResult mvcResult = mockMvc.perform(post("/auth")
                .contentType(contentType)
                .content(body)).andExpect(status().isOk()).andReturn();
        String token = mvcResult.getResponse().getHeader(authorization);
        System.out.println(token);

        mockMvc.perform(delete(deleteUserPath).contentType(contentType)
                .param(userParam1, Integer.toString(user1.getId()))
                .header("Authorization", token))
                .andExpect(status().isOk());

        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(userController, times(1)).deleteUser(idCaptor.capture());
        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("", response);
    }




}

