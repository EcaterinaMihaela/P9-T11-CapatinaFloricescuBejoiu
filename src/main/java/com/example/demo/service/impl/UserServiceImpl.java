package com.example.demo.service.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.Librarian;
import com.example.demo.model.Member;
import com.example.demo.model.Reservation;
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
    @Transactional
    public void delete(Long userId) {
        User user = repo.user.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("=== DELETING USER ===");
        System.out.println("User ID: " + userId);
        System.out.println("Username: " + user.getUsername());

        // 1. Șterge Member-ul dacă există si librar
        repo.member.findById(userId).ifPresent(member -> {
            System.out.println("Deleting Member...");
            repo.member.delete(member);
        });

        repo.librarian.findById(userId).ifPresent(librarian -> {
            System.out.println("Deleting Librarian...");
            repo.librarian.delete(librarian);
        });

        // 2. Șterge Rezervările legate de acest membru (dacă există)
        List<Reservation> reservations = repo.reservation.findByMember_MemberID(userId);
        if (!reservations.isEmpty()) {
             System.out.println("Deleting " + reservations.size() + " reservations...");
             repo.reservation.deleteAll(reservations);
         }

        // 3. Șterge Împrumuturile legate de acest membru (dacă există)
        // List<Loan> loans = repo.loan.findByMember_MemberID(userId);
        // if (!loans.isEmpty()) {
        //     System.out.println("Deleting " + loans.size() + " loans...");
        //     repo.loan.deleteAll(loans);
        // }

        // 4. UserProfile-ul se șterge automat prin CascadeType.ALL
        if (user.getProfile() != null) {
            System.out.println("Deleting UserProfile...");
            repo.userProfile.delete(user.getProfile());
        }

        // 5. Șterge User-ul
        System.out.println("Deleting User...");
        repo.user.delete(user);

        System.out.println("=== USER DELETED SUCCESSFULLY ===");
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

        // ============= MEMBER LOGIC =============
        // Dacă rolul devine MEMBER și nu era MEMBER înainte
        if ("MEMBER".equals(newRole) && !"MEMBER".equals(oldRole)) {
            boolean memberExists = repo.member.findById(userId).isPresent();

            if (!memberExists) {
                Member member = new Member();
                member.setMemberID(savedUser.getUserid());
                member.setUser(savedUser);
                member.setBorrowLimit(5);
                member.setStatus("ACTIVE");

                repo.member.save(member);
                System.out.println("Member created for user: " + savedUser.getUsername());
            }
        }

        // Dacă rolul era MEMBER și acum devine altceva
        if ("MEMBER".equals(oldRole) && !"MEMBER".equals(newRole)) {
            repo.member.findById(userId).ifPresent(member -> {
                repo.member.delete(member);
                System.out.println("Member deleted for user: " + savedUser.getUsername());
            });
        }

        // ============= LIBRARIAN LOGIC =============
        // Dacă rolul devine LIBRARIAN și nu era LIBRARIAN înainte

        if ("LIBRARIAN".equals(newRole) && !"LIBRARIAN".equals(oldRole)) {
            boolean librarianExists = repo.librarian.findById(userId).isPresent();

            if (!librarianExists) {
                Librarian librarian = new Librarian();
                // librarian.setLibrarianID(userId); // NU mai este nevoie de asta cu @MapsId
                librarian.setUser(savedUser); // Această linie va seta automat ID-ul bibliotecarului
                librarian.setResponsibilities("General library duties");

                repo.librarian.save(librarian);
                System.out.println("Librarian created for user: " + savedUser.getUsername());
            }
        }

        // Dacă rolul era LIBRARIAN și acum devine altceva
        if ("LIBRARIAN".equals(oldRole) && !"LIBRARIAN".equals(newRole)) {
            repo.librarian.findById(userId).ifPresent(librarian -> {
                repo.librarian.delete(librarian);
                System.out.println("Librarian deleted for user: " + savedUser.getUsername());
            });
        }

        return savedUser;
    }
}