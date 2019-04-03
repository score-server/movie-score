package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.service.filehandler.WebHandler;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.service
 * @created 21.01.2019
 **/
public class WebHandlerTest {

    @Test
    public void requestContentTest() throws Exception {
        Assert.assertThat(new WebHandler("https://jsonplaceholder.typicode.com/todos/1").getContent(),
                CoreMatchers.containsString("delectus aut autem"));
    }

}
