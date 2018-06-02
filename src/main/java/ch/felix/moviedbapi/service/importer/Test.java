package ch.felix.moviedbapi.service.importer;

/**
 * @author Felix
 * @date 30.05.2018
 * <p>
 * Project: movie-db-api
 * Package: ch.felix.moviedbapi.service.importer
 **/
public class Test {
    public static void main(String[] args) {
        Test test = new Test();


        System.out.println(test.getEpisode(("Breaking Bad s25e11324 2018 1080p.mp4").replace(".mp4", "")));
        System.out.println(test.getEpisode(("Faae e e s6e34 2018 1080p.mp4").replace(".mp4", "")));
        System.out.println(test.getEpisode(("Test s7e1 2018 1080p.mp4").replace(".mp4", "")));
        System.out.println(test.getEpisode(("bakasd asd as s4435e12 2018 1080p.mp4").replace(".mp4", "")));
    }

    private String getName(String fileName) {
        return fileName.replace(" " + getEpisodeStr(fileName) + " " + getYear(fileName) + " "
                + getQuality(fileName), "");
    }

    private String getEpisodeStr(String s) {
        String[] splits = s.split(" ");
        return splits[splits.length - 3];
    }

    private String getQuality(String s) {
        String[] splits = s.split(" ");
        return splits[splits.length - 1];
    }

    private String getYear(String s) {
        String[] splits = s.split(" ");
        return splits[splits.length - 2];
    }

    private String getSeason(String s) {
        return getEpisodeStr(s).split("e")[0].replace("s", "");
    }

    private String getEpisode(String s) {
        return getEpisodeStr(s).split("e")[1];
    }

}
