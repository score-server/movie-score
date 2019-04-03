package ch.wetwer.moviedbapi.controller.api;

import ch.wetwer.moviedbapi.service.filehandler.WebHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author Wetwer
 * @project movie-score
 */

@CrossOrigin
@RestController
@RequestMapping("api/steam")
public class SteamApiController {

    @ResponseBody
    @GetMapping("group/{groupName}")
    public String getGroup(@PathVariable String groupName) {
        StringBuilder request = new StringBuilder("https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/" +
                "?key=24B3DE7EC365BCD4597E83D3CC5DD5C8&format=json" +
                "&steamids=");
        String groupApi = "https://steamcommunity.com/groups/" + groupName + "/memberslistxml/?xml=1";
        for (String key : getKeyFromGroup(new WebHandler(groupApi).getContent())) {
            request.append(key).append(",");
        }
        return new WebHandler(request.toString()).getContent();
    }

    @ResponseBody
    @GetMapping(value = "user/{id}", produces = "application/json")
    public String getUser(@PathVariable String id) {
        return new WebHandler("https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v2/" +
                "?key=24B3DE7EC365BCD4597E83D3CC5DD5C8&format=json" +
                "&steamids=" + id).getContent();
    }

    private ArrayList<String> getKeyFromGroup(String xml) {
        String[] parts = xml.split("<steamID64>");
        ArrayList<String> keys = new ArrayList<>();
        boolean first = true;
        for (String part : parts) {
            try {
                if (first || part.split("</steamID64>")[0].length() != 17) {
                    first = false;
                } else {
                    keys.add(part.substring(0, 17));
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {

            }
        }
        return keys;
    }
}
