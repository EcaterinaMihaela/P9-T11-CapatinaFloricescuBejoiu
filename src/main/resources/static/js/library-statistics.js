document.addEventListener("DOMContentLoaded", loadDashboard);

async function loadDashboard() {
    try {
        const [booksRes, authorsRes, categoriesRes,reservationsRes] = await Promise.all([
            fetch("/books"),
            fetch("/authors"),
            fetch("/categories"),
            fetch("/reservations")
        ]);

        const books = await booksRes.json();
        const authors = await authorsRes.json();
        const categories = await categoriesRes.json();
        const reservations = await reservationsRes.json();

        document.getElementById("totalBooks").textContent = books.length;
        document.getElementById("availableBooks").textContent =
            books.filter(book => book.status === "AVAILABLE").length;

        document.getElementById("totalAuthors").textContent = authors.length;
        document.getElementById("totalCategories").textContent = categories.length;

const pendingCount = reservations.filter(res => res.status === "PENDING").length;
        const acceptedCount = reservations.filter(res => res.status === "APPROVED").length;

        document.getElementById("pendingReservations").textContent = pendingCount;
        document.getElementById("acceptedReservations").textContent = acceptedCount;
        loadRecentBooks(books);

    } catch (err) {
        console.error("Dashboard error:", err);
    }
}

function loadRecentBooks(books) {
    const container = document.getElementById("recentBooks");
    container.innerHTML = "";

    books.slice(-5).reverse().forEach(book => {
        const div = document.createElement("div");
        div.className = "notif-item";

        div.innerHTML = `
            <div class="book-row">
                <img src="${book.imageUrl || 'https://placehold.co/40x60'}" class="book-cover">
                <div>
                    <strong>${book.bookTitle}</strong><br>
                    ${book.author ? book.author.authorName : "Unknown Author"}
                </div>
            </div>
        `;

        container.appendChild(div);
    });
}