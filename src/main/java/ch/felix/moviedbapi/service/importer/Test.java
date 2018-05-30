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


        System.out.println(test.getSeason(("Breaking Bad s1e1 2018 1080p.mp4").replace(".mp4", "")));
        System.out.println(test.getSeason(("Faae e e s1e1 2018 1080p.mp4").replace(".mp4", "")));
        System.out.println(test.getSeason(("Test s1e1 2018 1080p.mp4").replace(".mp4", "")));
        System.out.println(test.getSeason(("bakasd asd as s1e1 2018 1080p.mp4").replace(".mp4", "")));
    }

    private String getName(String fileName) {
        String name = fileName.replace(".mp4", "");
        return name.replace(" " + getEpisodeStr(name) + " " + getYear(name) + " " + getQuality(name), "");
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
        return getEpisodeStr(s).substring(1,1);
    }

}
