package ch.wetwer.moviedbapi.service.filehandler;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Wetwer
 * @project movie-score
 */
public class WebHandler {

    private URL url;

    /**
     * This class is good to use for XML or Json Rest services. Rest results cant be handled here
     *
     * @param url represents the URL of the requested website
     */
    public WebHandler(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @returns the Text content on the website
     */
    public String getContent() {
        try {
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            return IOUtils.toString(in, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
