package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.activitylog.ActivityLogDao;
import ch.wetwer.moviedbapi.data.session.SessionDao;
import ch.wetwer.moviedbapi.data.timeline.TimeLineDao;
import ch.wetwer.moviedbapi.data.user.UserDao;
import ch.wetwer.moviedbapi.service.ActivityService;
import ch.wetwer.moviedbapi.service.SearchService;
import ch.wetwer.moviedbapi.service.auth.ShaService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.controller
 * @created 05.02.2019
 **/
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TimeLineDao timeLineDao;

    @MockBean
    private ActivityLogDao logDao;

    @MockBean
    private UserDao userDao;

    @MockBean
    private SessionDao sessionDao;

    @MockBean
    private ShaService shaService;

    @MockBean
    private SearchService searchService;

    @MockBean
    private UserAuthService userAuthService;

    @MockBean
    private ActivityService activityService;

    @Test
    public void getHome() throws Exception {
        when(userAuthService.isUser(any(), any())).thenReturn(true);

        this.mockMvc.perform(get("/").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());

    }


}
