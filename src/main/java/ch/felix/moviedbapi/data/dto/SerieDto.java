package ch.felix.moviedbapi.data.dto;

import ch.felix.moviedbapi.data.entity.Serie;
import ch.felix.moviedbapi.data.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieDto implements DtoInterface<Serie> {

    private SerieRepository serieRepository;

    public SerieDto(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    @Override
    public Serie getById(Long id) {
        return serieRepository.findSerieById(id);
    }

    @Override
    public List<Serie> getAll() {
        return serieRepository.findAll();
    }

    @Override
    public void save(Serie serie) {
        serieRepository.save(serie);
    }
}
