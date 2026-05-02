package com.example.demo.service.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.Member;
import com.example.demo.model.User;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final RepositoryWrapper repo;

    public UserServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<UserDTO> getAll() {
        return repo.user.findAll()
                .stream()
                .map(u -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(u.getUserid());
                    dto.setUsername(u.getUsername());
                    dto.setRole(u.getRole());
                    dto.setStatus(u.getStatus());
                    dto.setBanReason(u.getBanReason());

                    if (u.getProfile() != null) {
                        dto.setEmail(u.getProfile().getEmail());
                        dto.setPhoneNumber(u.getProfile().getPhoneNumber());
                        dto.setAddress(u.getProfile().getAddress());
                    }
                    return dto;
                })
                .toList();
    }

    @Override
    public UserDTO getById(Long id) {
        User u = repo.user.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDTO dto = new UserDTO();
        dto.setId(u.getUserid());
        dto.setUsername(u.getUsername());
        dto.setRole(u.getRole());
        dto.setStatus(u.getStatus());
        dto.setBanReason(u.getBanReason());

        if (u.getProfile() != null) {
            dto.setEmail(u.getProfile().getEmail());
            dto.setPhoneNumber(u.getProfile().getPhoneNumber());
            dto.setAddress(u.getProfile().getAddress());
        }
        return dto;
    }

    @Override
    public UserDTO create(UserDTO dto) {

        if(repo.user.findByUsername(dto.getUsername()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Username already exists"
            );
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole("MEMBER");

        user.setStatus("ACTIVE");
        user = repo.user.save(user);

        dto.setId(user.getUserid());
        dto.setRole(user.getRole());
        return dto;
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {

        User existing = getEntityById(id);

        existing.setUsername(dto.getUsername());
        existing.setPassword(dto.getPassword());
        existing.setRole(dto.getRole());

        User saved = repo.user.save(existing);

        UserDTO result = new UserDTO();
        result.setId(saved.getUserid());
        result.setUsername(saved.getUsername());
        result.setRole(saved.getRole());
        result.setStatus(saved.getStatus());
        result.setBanReason(saved.getBanReason());

        return result;
    }
    @Override
    public void delete(Long id) {
        repo.user.deleteById(id);
    }

    private User getEntityById(Long id) {
        return repo.user.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public void banUser(Long id, String reason) {
        User user = getEntityById(id);
        user.setStatus("BANNED");
        user.setBanReason(reason);
        repo.user.save(user);
    }
    public void unbanUser(Long id) {
        User user = getEntityById(id);
        user.setStatus("ACTIVE");
        user.setBanReason(null);
        repo.user.save(user);
    }

    @Override
    @Transactional
    public User changeRole(Long userId, String newRole) {
        User user = repo.user.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String oldRole = user.getRole();
        user.setRole(newRole);

        User savedUser = repo.user.save(user);

        // Dacă rolul devine MEMBER și nu era MEMBER înainte
        if ("MEMBER".equals(newRole) && !"MEMBER".equals(oldRole)) {
            // Verifică dacă există deja un Member pentru acest user
            boolean memberExists = repo.member.findById(userId).isPresent();

            if (!memberExists) {
                // Creează Member nou
                Member member = new Member();
                member.setMemberID(savedUser.getUserid());
                member.setUser(savedUser);
                member.setBorrowLimit(5);
                member.setStatus("ACTIVE");

                repo.member.save(member);

                System.out.println("✅ Member created for user: " + savedUser.getUsername());
            }
        }

        // Dacă rolul era MEMBER și acum devine altceva (LIBRARIAN/ADMIN)
        if ("MEMBER".equals(oldRole) && !"MEMBER".equals(newRole)) {
            // Șterge Member-ul dacă există
            repo.member.findById(userId).ifPresent(member -> {
                repo.member.delete(member);
                System.out.println("🗑️ Member deleted for user: " + savedUser.getUsername());
            });
        }

        return savedUser;
    }
}