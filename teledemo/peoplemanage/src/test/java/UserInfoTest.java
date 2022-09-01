import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import teleDemo.controller.userInfoController;
import teleDemo.entities.GetVo;
import teleDemo.service.UserInfoService;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UserInfoTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

//    @Before
//    public void setUp(){
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }

    @Test
    public void getTbUserServiceTest(){
//        UserInfoService userInfoService = new UserInfoService();
//        userInfoService.gettbUSertbInfos(10,1);
    }
    @Test
    public void getVoTest(){
        GetVo getVo = new GetVo(123, "msg",1,null ) ;
        assert(getVo.getCode() == 123);

    }
}
