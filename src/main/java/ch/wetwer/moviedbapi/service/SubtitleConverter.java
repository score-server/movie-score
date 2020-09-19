package ch.wetwer.moviedbapi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class SubtitleConverter {


    public byte[] convert(MultipartFile multipartFile) throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
        String line = buf.readLine();
        StringBuilder sb = new StringBuilder();
        while (line != null) {
            if (!line.matches("[0-9]+")) {
                if (line.contains("-->")) {
                    sb.append(line.replace(",", ".")).append("\n");
                } else {
                    sb.append(line).append("\n");
                }
            }

            line = buf.readLine();
        }

        return ("WEBVTT\n\n" + sb.toString()).getBytes();
    }

}
