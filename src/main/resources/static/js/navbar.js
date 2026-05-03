document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("navbar");

    fetch("/navbar.html")
        .then(res => res.text())
        .then(html => {
            container.innerHTML = html;
            // Inițializăm funcțiile după ce HTML-ul a fost încărcat în DOM
            setupLogout();
            updateNavbarUser();
        })
        .catch(err => console.error(err));
});

function setupLogout() {
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", (e) => {
            e.preventDefault();
            localStorage.clear(); // Șterge tot dintr-o singură comandă
            window.location.href = "/login.html";
        });
    }
}

function updateNavbarUser() {
    const username = localStorage.getItem("username");
    const email = localStorage.getItem("email");
    const role = localStorage.getItem("role"); // Valori posibile: ADMIN, LIBRARIAN, MEMBER

    const userInfo = document.getElementById("userInfo");
    const logoutBtn = document.getElementById("logoutBtn");

    // Referințe către link-urile condiționate
    const adminLink = document.getElementById("adminLink");
    const librarianLink = document.getElementById("librarianLink");
    const memberLink = document.getElementById("memberLink");
    const librarianReservationsLink = document.getElementById("librarianReservationsLink");
    const loansLink = document.getElementById("loansLink");

    if (username && userInfo && logoutBtn) {
        userInfo.style.setProperty("display", "flex", "important");
        logoutBtn.style.display = "inline-block";
        userInfo.innerHTML = `
            <span class="fw-bold">👤 ${username}</span>
            <span style="font-size: 0.75rem; opacity: 0.8;">${email || ''}</span>
        `;

        // Resetăm vizibilitatea (le ascundem pe toate înainte de verificare)
        if(adminLink) adminLink.style.display = "none";
        if(librarianLink) librarianLink.style.display = "none";
        if(memberLink) memberLink.style.display = "none";
        if (loansLink) loansLink.style.display = "none";

        // LOGICA DE ROLURI
        if (role === "ADMIN") {
            if(adminLink) adminLink.style.display = "block";
        }
        else if (role === "LIBRARIAN") {
            if(librarianLink) librarianLink.style.display = "block";
            if (librarianReservationsLink) librarianReservationsLink.style.display = "block";
        }
        else if (role === "MEMBER") {
            if(memberLink) memberLink.style.display = "block";
            if (loansLink) loansLink.style.display = "block";
        }

        setTimeout(() => {
            const librarianOnly = document.querySelectorAll(".librarian-only");

            librarianOnly.forEach(el => {
                el.style.display = (role === "LIBRARIAN") ? "block" : "none";
            });
        }, 0);

    } else {
        // Dacă nu e logat, ascundem tot
        if(userInfo) userInfo.style.setProperty("display", "none", "important");
        if(logoutBtn) logoutBtn.style.display = "none";
        if(adminLink) adminLink.style.display = "none";
        if(librarianLink) librarianLink.style.display = "none";
        if(memberLink) memberLink.style.display = "none";
        if(librarianReservationsLink) librarianReservationsLink.style.display = "none";
    }
}
