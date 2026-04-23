package com.example.demo.service.impl;

import com.example.demo.dto.PublisherDTO;
import com.example.demo.model.Publisher;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.PublisherService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final RepositoryWrapper repo;

    public PublisherServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Publisher> getAll() {
        return repo.publisher.findAll();
    }

    @Override
    public Publisher getById(Long id) {
        return repo.publisher.findById(id).orElse(null);
    }

    @Override
    public Publisher create(PublisherDTO dto) {

        Publisher publisher = new Publisher();
        publisher.setPublisherName(dto.getPublisherName());

        return repo.publisher.save(publisher);
    }

    @Override
    public Publisher update(Long id, PublisherDTO dto) {

        return repo.publisher.findById(id).map(p -> {

            p.setPublisherName(dto.getPublisherName());

            return repo.publisher.save(p);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.publisher.deleteById(id);
    }
}