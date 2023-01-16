package com.ebn.calendar.service;

import com.ebn.calendar.model.dao.Tag;
import com.ebn.calendar.model.dao.User;
import com.ebn.calendar.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTagForUser(Tag tag, User user) {
        tag.setOwner(user);
        return tagRepository.create(tag);
    }

    public Tag readTagForUser(String id, User user) {
        Tag result = tagRepository.read(id);
        if (result == null || !result.getOwner().equals(user)) {
            return null;
        }
        return result;
    }

    public Tag updateTagForUser(Tag tag, User user) {
        Tag existingTag = readTagForUser(tag.getId(), user);
        if (existingTag == null) {
            return null;
        }
        tag.setOwner(user);
        return tagRepository.update(tag);
    }

    public Tag deleteTagForUser(String id, User user) {
        Tag existingTag = readTagForUser(id, user);
        if (existingTag == null) {
            return null;
        }
        return tagRepository.delete(id);
    }

    public List<Tag> readTagsForUser(User user) {
        return tagRepository.readTagsForUser(user);
    }

    public List<Tag> readTagsForUser(Set<String> tagsIds, User user) {
        return tagRepository.readTagsForUser(tagsIds, user);
    }
}
