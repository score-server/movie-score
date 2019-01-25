package ch.wetwer.moviedbapi.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerLoadsTest {

    @Autowired
    private AboutController aboutController;

    @Autowired
    private BlogController blogController;

    @Autowired
    private CommentController commentController;

    @Autowired
    private ControlCenterSettings controlCenterSettings;

    @Autowired
    private DownloadController downloadController;

    @Autowired
    private EpisodeController episodeController;

    @Autowired
    private FastLoginController fastLoginController;

    @Autowired
    private GroupController groupController;

    @Autowired
    private HomeController homeController;

    @Autowired
    private ImportController importController;

    @Autowired
    private ListController listController;

    @Autowired
    private LoginController loginController;

    @Autowired
    private MovieController movieController;

    @Autowired
    private MoviesController moviesController;

    @Autowired
    private ProfileImgController profileImgController;

    @Autowired
    private RegisterController registerController;

    @Autowired
    private RequestController requestController;

    @Autowired
    private SeasonController seasonController;

    @Autowired
    private SeriesController seriesController;

    @Autowired
    private SubtitleController subtitleController;

    @Autowired
    private TimelineController timelineController;

    @Autowired
    private UploadController uploadController;

    @Autowired
    private UserController userController;

    @Autowired
    private VideoController videoController;

    @Autowired
    private WidgetController widgetController;


    @Test
    public void contexLoads() {
        assertNotNull(aboutController);
        assertNotNull(blogController);
        assertNotNull(commentController);
        assertNotNull(controlCenterSettings);
        assertNotNull(downloadController);
        assertNotNull(episodeController);
        assertNotNull(fastLoginController);
        assertNotNull(groupController);
        assertNotNull(homeController);
        assertNotNull(importController);
        assertNotNull(listController);
        assertNotNull(loginController);
        assertNotNull(movieController);
        assertNotNull(moviesController);
        assertNotNull(profileImgController);
        assertNotNull(registerController);
        assertNotNull(requestController);
        assertNotNull(seasonController);
        assertNotNull(seriesController);
        assertNotNull(subtitleController);
        assertNotNull(timelineController);
        assertNotNull(uploadController);
        assertNotNull(userController);
        assertNotNull(videoController);
        assertNotNull(widgetController);
    }
}

