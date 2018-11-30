package ch.wetwer.moviedbapi.service;

import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Wetwer
 * @project movie-db-api
 * @package ch.felix.moviedbapi.service
 * @created 28.11.2018
 **/

@Service
public class AvalibleService {

    public String checkOnline(String ip, Integer port) {
        String status = "Online";
        try {
            InetSocketAddress sa = new InetSocketAddress(ip, port);
            Socket socket = new Socket();
            socket.connect(sa, 200);
            socket.close();
        } catch (Exception e) {
            status = "Offline";
        }
        return status;
    }

}
