package ch.wetwer.moviedbapi.service;

import ch.wetwer.moviedbapi.MovieDb;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.wetwer.moviedbapi.service
 * @created 21.12.2018
 **/

@Service
public class MavenPropertiesService {

    private final Model model;

    private MavenPropertiesService() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        if ((new File("pom.xml")).exists()) {

            model = reader.read(new FileReader("pom.xml"));
        } else {
            model = reader.read(new InputStreamReader(MovieDb.class.getResourceAsStream(
                    "/META-INF/maven/ch.wetwer.moviedbapi/pom.xml")));
        }
    }

    public String getVersion() {
        return model.getVersion();
    }
}
