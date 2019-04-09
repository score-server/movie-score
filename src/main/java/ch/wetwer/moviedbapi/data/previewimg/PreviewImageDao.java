package ch.wetwer.moviedbapi.data.previewimg;

import ch.wetwer.moviedbapi.data.DaoInterface;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.data.previewimg
 * @created 09.04.2019
 **/

@Service
public class PreviewImageDao implements DaoInterface<PreviewImage> {

    private PreviewImageRepository previewImageRepository;

    public PreviewImageDao(PreviewImageRepository previewImageRepository) {
        this.previewImageRepository = previewImageRepository;
    }

    @Override
    public PreviewImage getById(Long id) {
        return previewImageRepository.getOne(id);
    }

    @Override
    public List<PreviewImage> getAll() {
        return previewImageRepository.findAll();
    }

    @Override
    public void save(PreviewImage previewImage) {
        previewImageRepository.save(previewImage);
    }
}
