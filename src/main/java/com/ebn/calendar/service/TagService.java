package com.ebn.calendar.service;

import com.ebn.calendar.model.dao.Tag;
import com.ebn.calendar.model.dao.User;
import com.ebn.calendar.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag create(Tag tag, User user) {
        tag.setOwner(user);
        return tagRepository.create(tag);
    }

    public Tag read(String id, User user) {
        Tag result = tagRepository.read(id);
        if (result == null || !result.getOwner().equals(user)) {
            return null;
        }
        return result;
    }

    public Tag update(Tag tag, User user) {
        Tag existingTag = read(tag.getId(), user);
        if (existingTag == null) {
            return null;
        }
        tag.setOwner(user);
        return tagRepository.update(tag);
    }

    public Tag delete(String id, User user) {
        Tag existingTag = read(id, user);
        if (existingTag == null) {
            return null;
        }
        return tagRepository.delete(id);
    }

    public List<Tag> readAll(User user) {
        return tagRepository.readUserTags(user);
    }
}
