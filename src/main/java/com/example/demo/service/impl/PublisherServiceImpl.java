package com.example.demo.service.impl;

import com.example.demo.model.Publisher;
import com.example.demo.repository.PublisherRepository;
import com.example.demo.service.PublisherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository repo;

    public PublisherServiceImpl(PublisherRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Publisher> getAll() {
        return repo.findAll();
    }

    @Override
    public Publisher getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Publisher create(Publisher publisher) {
        return repo.save(publisher);
    }

    @Override
    public Publisher update(Long id, Publisher newPublisher) {
        return repo.findById(id).map(publisher -> {
            publisher.setPublisherName(newPublisher.getPublisherName());
            return repo.save(publisher);
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}