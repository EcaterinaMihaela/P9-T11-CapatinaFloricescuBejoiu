package com.example.demo.service;

import com.example.demo.dto.PublisherDTO;
import com.example.demo.model.Publisher;

import java.util.List;

public interface PublisherService {

    List<Publisher> getAll();

    Publisher getById(Long id);

    Publisher create(PublisherDTO dto);

    Publisher update(Long id, PublisherDTO dto);

    void delete(Long id);
}