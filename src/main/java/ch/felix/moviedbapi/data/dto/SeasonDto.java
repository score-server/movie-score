package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Season;
import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.repository.SeasonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonDto implements DtoInterface<Season> {

    private SeasonRepository seasonRepository;

    public SeasonDto(SeasonRepository seasonRepository) {
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
