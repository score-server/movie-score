package ch.felix.moviedbapi.service.json;

import ch.felix.moviedbapi.data.entity.Serie;
import java.util.List;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.service.json
 **/

@Service
public class SerieJsonService {
    public String getSerie(Serie serie) {
        return "{\"id\": " + serie.getId() + ","
               + "\"title\": \"" + escapeHtml(serie.getTitle()) + "\","
               + "\"img_path\": \"" + escapeHtml(serie.getCaseImg()) + "\","
               + "\"description\": \"" + escapeHtml(serie.getDescript()) + "\","
               + "\"popularity\": " + serie.getPopularity() + "}";
    }

    public String getSerieList(List<Serie> requestList) {
        StringBuilder jsonSting = new StringBuilder("[");
        for (Serie serie : requestList) {
            jsonSting.append(getSerie(serie)).append(",");
        }
        jsonSting = finishJson(jsonSting);
        return jsonSting.toString();
    }

    private StringBuilder finishJson(StringBuilder jsonSting) {
        jsonSting = new StringBuilder(jsonSting.substring(0, jsonSting.length() - 1));
        jsonSting.append("]");
        return jsonSting;
    }
}
