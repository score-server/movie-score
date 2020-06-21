package ch.wetwer.moviedbapi.data.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat findChatById(Long id);

    List<Chat> findAllByOrderByIdDesc();

}
