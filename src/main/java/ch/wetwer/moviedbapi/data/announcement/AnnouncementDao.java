package ch.wetwer.moviedbapi.data.announcement;

import ch.wetwer.moviedbapi.data.DaoInterface;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wetwer
 * @project movie-score
 * @package ch.wetwer.moviedbapi.data.announcement
 * @created 29.04.2019
 **/
@Service
public class AnnouncementDao implements DaoInterface<Announcement> {

    private AnnouncementRepository announcementRepository;

    public AnnouncementDao(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public Announcement getById(Long id) {
        return announcementRepository.getOne(id);
    }

    @Override
    public List<Announcement> getAll() {
        return announcementRepository.findAll();
    }

//    public List<Announcement> getTop3() {
//        return announcementRepository.findTop3();
//    }

    @Override
    public void save(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    public void delete(Announcement announcement) {
        announcementRepository.delete(announcement);
    }
}
