package com.example.demo.repository;

import com.example.demo.model.SecurityLog;
import org.springframework.stereotype.Component;

@Component
public class RepositoryWrapper {

    public final UserRepository user;
    public final UserProfileRepository userProfile;

    public final AuthorRepository author;
    public final BookRepository book;
    public final CategoryRepository category;
    public final PublisherRepository publisher;
    public final LibrarianRepository librarian;
    public final LoanRepository loan;
    public final MemberRepository member;
    public final NotificationRepository notification;
    public final ReportRepository report;
    public final ReservationRepository reservation;
    public final ReviewRepository review;
    public final SecurityLogRepository securityLog;

    public RepositoryWrapper(
            UserRepository user,
            UserProfileRepository userProfile,
            AuthorRepository author,
            BookRepository book,
            CategoryRepository category,
            PublisherRepository publisher,
            LibrarianRepository librarian,
            LoanRepository loan,
            MemberRepository member,
            NotificationRepository notification,
            ReportRepository report,
            ReservationRepository reservation,
            ReviewRepository review,
            SecurityLogRepository securityLog
    ) {
        this.user = user;
        this.userProfile = userProfile;
        this.author = author;
        this.book = book;
        this.category = category;
        this.publisher = publisher;
        this.librarian = librarian;
        this.loan = loan;
        this.member = member;
        this.notification = notification;
        this.report = report;
        this.reservation = reservation;
        this.review=review;
        this.securityLog=securityLog;
    }
}