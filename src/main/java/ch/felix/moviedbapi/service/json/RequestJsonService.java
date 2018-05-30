package ch.felix.moviedbapi.service.json;

import ch.felix.moviedbapi.data.entity.Request;
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
public class RequestJsonService {


    public String getRequest(Request request) {
        return "{\"id\": " + request.getId() + ","
               + "\"request\": \"" + escapeHtml(request.getRequest()) + "\","
               + "\"active\": \"" + request.getActive() + "\","
               + "\"user_id\": \"" + request.getUserFk() + "\"}";
    }

    public String getRequestList(List<Request> requestList) {
        StringBuilder jsonSting = new StringBuilder("[");
        for (Request request : requestList) {
            jsonSting.append(getRequest(request)).append(",");
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
