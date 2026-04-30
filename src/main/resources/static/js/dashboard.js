document.addEventListener("DOMContentLoaded", () => {
    loadFilterData();
    loadBooks();

    document.getElementById("authorFilter").addEventListener("change", filterBooks);
    document.getElementById("categoryFilter").addEventListener("change", filterBooks);
    document.getElementById("publisherFilter").addEventListener("change", filterBooks);

    const searchInput = document.getElementById("searchInput");
    if (searchInput) {
        searchInput.addEventListener("input", filterBooks);
    }
});

let allBooks = [];

async function loadFilterData() {
    try {
        const [authors, categories, publishers] = await Promise.all([
            fetch('/authors').then(res => res.json()),
            fetch('/categories').then(res => res.json()),
            fetch('/publishers').then(res => res.json())
        ]);

        const populate = (id, data, idKey, nameKey) => {
            const select = document.getElementById(id);
            if (!select) return;
            data.forEach(item => {
                const idVal = item[idKey] || item.id;
                const nameVal = item[nameKey] || item.categoryName || item.categoryTitle || item.publisherName || item.name;
                select.innerHTML += `<option value="${idVal}">${nameVal}</option>`;
            });
        };

        populate("authorFilter", authors, "authorID", "authorName");
        populate("categoryFilter", categories, "categoryID", "categoryName");
        populate("publisherFilter", publishers, "publisherID", "publisherName");
    } catch (err) {
        console.error("Filter error:", err);
    }
}

async function loadBooks() {
    try {
        const response = await fetch("/books");
        allBooks = await response.json();
        renderBooks(allBooks);
    } catch (err) {
        console.error("Error in books:", err);
    }
}

function filterBooks() {
    const searchValue = document.getElementById("searchInput").value.toLowerCase();
    const selectedAuthor = document.getElementById("authorFilter").value;
    const selectedCategory = document.getElementById("categoryFilter").value;
    const selectedPublisher = document.getElementById("publisherFilter").value;

    const filtered = allBooks.filter(book => {
        const matchesSearch =
            book.bookTitle.toLowerCase().includes(searchValue) ||
            (book.author?.authorName || "").toLowerCase().includes(searchValue) ||
            (book.ISBN || book.isbn || "").toLowerCase().includes(searchValue);

        const authorId = (book.author?.authorID || book.author?.id)?.toString();
        const matchesAuthor = selectedAuthor === "all" || authorId === selectedAuthor;

        const categoryId = (book.category?.categoryID || book.category?.id)?.toString();
        const matchesCategory = selectedCategory === "all" || categoryId === selectedCategory;

        const publisherId = (book.publisher?.publisherID || book.publisher?.id)?.toString();
        const matchesPublisher = selectedPublisher === "all" || publisherId === selectedPublisher;

        return matchesSearch && matchesAuthor && matchesCategory && matchesPublisher;
    });

    renderBooks(filtered);
}
function renderBooks(books) {
    const bookGrid = document.getElementById("bookGrid");
    if (!bookGrid) return;
    bookGrid.innerHTML = "";

    if (books.length === 0) {
        bookGrid.innerHTML = "<p class='text-center w-100'>No books found.</p>";
        return;
    }

    books.forEach(book => {
        const card = document.createElement("div");
        card.className = "book-card";

        const image = book.imageUrl || "https://placehold.co/300x450?text=No+Cover";
        const catName = book.category ? (book.category.categoryName || book.category.categoryTitle || "General") : "N/A";
        const authName = book.author ? (book.author.authorName || "Unknown Author") : "N/A";

        const isAvailable = book.availableStock > 0;
        const statusText = isAvailable ? "Available" : "Borrowed";
        const statusClass = isAvailable ? "status-available" : "status-borrowed";

        card.innerHTML = `
            <img src="${image}" alt="${book.bookTitle}">
            <h3>${book.bookTitle}</h3>
            <p>${authName}</p>
            <p>${catName}</p>
            <p class="status-text ${statusClass}">${statusText}</p>
            <button class="details-btn" onclick="viewDetails(${book.bookID})">View Details</button>
        `;
        bookGrid.appendChild(card);
    });
}

function viewDetails(bookId) {
    window.location.href = `/book-details.html?id=${bookId}`;
}