package ch.wetwer.moviedbapi.data.season;

import ch.wetwer.moviedbapi.data.DaoInterface;
import ch.wetwer.moviedbapi.data.serie.Serie;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 */

@Service
public class SeasonDao implements DaoInterface<Season> {

    private SeasonRepository seasonRepository;

    public SeasonDao(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Override
    public Season getById(Long id) {
        return seasonRepository.findSeasonById(id);
    }

    @Override
    public List<Season> getAll() {
        return seasonRepository.findAll();
    }

    @Override
    public void save(Season season) {
        seasonRepository.save(season);
    }

    public List<Season> getBySerie(Serie serie) {
        return seasonRepository.findSeasonsBySerieOrderBySeason(serie);
    }
}
