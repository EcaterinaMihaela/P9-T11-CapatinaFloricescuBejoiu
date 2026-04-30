document.addEventListener("DOMContentLoaded", loadDashboard);

async function loadDashboard() {
    try {
        const [booksRes, authorsRes, categoriesRes] = await Promise.all([
            fetch("/books"),
            fetch("/authors"),
            fetch("/categories")
        ]);

        const books = await booksRes.json();
        const authors = await authorsRes.json();
        const categories = await categoriesRes.json();

        document.getElementById("totalBooks").textContent = books.length;
        document.getElementById("availableBooks").textContent =
            books.filter(book => book.status === "AVAILABLE").length;

        document.getElementById("totalAuthors").textContent = authors.length;
        document.getElementById("totalCategories").textContent = categories.length;

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