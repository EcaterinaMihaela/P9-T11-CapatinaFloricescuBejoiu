document.addEventListener("DOMContentLoaded", () => {
    loadBooks();

    const searchInput = document.getElementById("searchInput");

    if (searchInput) {
        searchInput.addEventListener("input", filterBooks);
    }

    const logoutBtn = document.getElementById("logoutBtn");

    if (logoutBtn) {
        logoutBtn.addEventListener("click", (e) => {
            e.preventDefault();
            localStorage.clear();
            window.location.href = "/login.html";
        });
    }
});

let allBooks = [];

async function loadBooks() {
    const bookGrid = document.getElementById("bookGrid");

    try {
        const response = await fetch("/books");

        if (!response.ok) {
            throw new Error("Failed to fetch books");
        }

        allBooks = await response.json();

        renderBooks(allBooks);

    } catch (err) {
        console.error(err);
        bookGrid.innerHTML = "<p class='text-danger'>Failed to load books.</p>";
    }
}

function renderBooks(books) {
    const bookGrid = document.getElementById("bookGrid");

    bookGrid.innerHTML = "";

    if (books.length === 0) {
        bookGrid.innerHTML = "<p>No books found.</p>";
        return;
    }

    books.forEach(book => {
        const card = document.createElement("div");
        card.className = "book-card";

        const image = book.imageUrl || "https://placehold.co/200x300?text=No+Cover";

        const statusClass =
            book.status === "AVAILABLE"
                ? "available"
                : "borrowed";

        card.innerHTML = `
            <img src="${image}" alt="${book.bookTitle}">
            <h3>${book.bookTitle}</h3>
            <p>${book.author ? book.author.authorName : "Unknown Author"}</p>
            <p>${book.category ? book.category.categoryTitle : "No Category"}</p>
            <p><small>ISBN: ${book.ISBN}</small></p>
            <p><small>Stock: ${book.availableStock}</small></p>
            <span class="status-badge ${statusClass}">
                ${book.status}
            </span>
            <button class="details-btn" onclick="viewDetails(${book.bookID})">
                View Details
            </button>
        `;

        bookGrid.appendChild(card);
    });
}

function filterBooks() {
    const searchValue = document
        .getElementById("searchInput")
        .value
        .toLowerCase();

    const filtered = allBooks.filter(book =>
        book.bookTitle.toLowerCase().includes(searchValue) ||
        (book.author?.authorName || "").toLowerCase().includes(searchValue) ||
        (book.category?.categoryTitle || "").toLowerCase().includes(searchValue)
    );

    renderBooks(filtered);
}

function viewDetails(bookId) {
    window.location.href = `/book-details.html?id=${bookId}`;
}