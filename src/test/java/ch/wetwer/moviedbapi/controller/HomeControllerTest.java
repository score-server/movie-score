package ch.wetwer.moviedbapi.controller;

import ch.wetwer.moviedbapi.data.user.User;
import ch.wetwer.moviedbapi.model.StartedVideo;
import ch.wetwer.moviedbapi.service.SearchService;
import ch.wetwer.moviedbapi.service.auth.UserAuthService;
import ch.wetwer.moviedbapi.service.auth.UserIndicator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.controller
 * @created 04.02.2019
 **/

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @MockBean
    private UserAuthService userAuthService;

    @Test
    public void getHome() throws Exception {
        when(userAuthService.isUser(any(), any())).thenReturn(true);

        UserIndicator userIndicator = new UserIndicator();
        userIndicator.setUser(User.builder().id(1L).name("Test").build());

        when(userAuthService.getUser(any())).thenReturn(userIndicator);

        when(searchService.findStartedVideos(any())).thenReturn(new ArrayList<>());
        when(searchService.getGenres(any())).thenReturn(new ArrayList<>());
        when(searchService.searchMoviesTop(any(), any())).thenReturn(new ArrayList<>());
        when(searchService.searchSerieTop(any())).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk());

    }
}
