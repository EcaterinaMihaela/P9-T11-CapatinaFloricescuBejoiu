package com.example.demo.service.impl;

import com.example.demo.dto.LibrarianDTO;
import com.example.demo.model.Librarian;
import com.example.demo.model.User;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.LibrarianService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibrarianServiceImpl implements LibrarianService {

    private final RepositoryWrapper repo;

    public LibrarianServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<Librarian> getAll() {
        return repo.librarian.findAllSafe();
    }

    @Override
    public Librarian getById(Long id) {
        return repo.librarian.findByIdSafe(id).orElse(null);
    }

    @Override
    public Librarian create(LibrarianDTO dto) {

        User user = repo.user.findByIdSafe(dto.getLibrarianId())
                .orElse(null);

        Librarian librarian = new Librarian();
        librarian.setUser(user);
        librarian.setResponsibilities(dto.getResponsibilities());

        return repo.librarian.saveSafe(librarian);
    }

    @Override
    public Librarian update(Long id, LibrarianDTO dto) {

        return repo.librarian.findByIdSafe(id).map(l -> {

            l.setResponsibilities(dto.getResponsibilities());

            return repo.librarian.saveSafe(l);

        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.librarian.deleteSafe(id);
    }
}