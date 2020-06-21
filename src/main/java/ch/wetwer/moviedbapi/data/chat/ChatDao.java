package ch.wetwer.moviedbapi.data.chat;

import ch.wetwer.moviedbapi.data.DaoInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatDao implements DaoInterface<Chat> {
    private ChatRepository chatRepository;

    public ChatDao(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat getById(Long id) {
        return chatRepository.findChatById(id);
    }

    @Override
    public List<Chat> getAll() {
        return chatRepository.findAllByOrderByIdDesc();
    }

    @Override
    public void save(Chat chat) {
        chatRepository.save(chat);
    }
}
