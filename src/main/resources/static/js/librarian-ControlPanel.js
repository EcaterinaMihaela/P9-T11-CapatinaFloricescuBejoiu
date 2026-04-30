document.addEventListener("DOMContentLoaded", () => {
    loadBooks();
});

async function loadBooks() {
    const bookList = document.getElementById("bookList");

    try {
        const response = await fetch("/books");

        if (!response.ok) {
            throw new Error("Eroare la server");
        }

        const books = await response.json();
        bookList.innerHTML = "";

        if (books.length === 0) {
            bookList.innerHTML = "<p class='text-center'>Nu există cărți în bibliotecă.</p>";
            return;
        }

        books.forEach(book => {
            const card = document.createElement("div");
            card.className = "book-card";

            // --- SCHIMBARE AICI: Folosim imageUrl din DB sau placeholder dacă e goală ---
            const coverImage = book.imageUrl ? book.imageUrl : 'https://placehold.co/55x80?text=No+Cover';

            card.innerHTML = `
                <div class="book-details">
                    <img src="${coverImage}" alt="Cover" style="object-fit: cover;">
                    <div class="book-info">
                        <b>${book.bookTitle}</b>
                        <span>
                            ${book.author ? book.author.authorName : "Unknown author"}
                            | ${book.category ? book.category.categoryTitle : "No category"}
                        </span>
                    </div>
                </div>
                <div class="book-actions">
                    <button class="edit-btn" onclick="editBook(${book.bookID})">Edit</button>
                    <button class="delete-btn" onclick="deleteBook(${book.bookID})">Delete</button>
                </div>
            `;
            bookList.appendChild(card);
        });

    } catch (error) {
        console.error("Error:", error);
        bookList.innerHTML = "<p class='text-danger text-center'>Failed to load books. Check API connection.</p>";
    }
}

// Funcții pentru butoane
function editBook(id) {
    window.location.href = `/edit-book.html?id=${id}`;
}

async function deleteBook(id) {
    if (!confirm("Sigur vrei să ștergi această carte?")) return;

    try {
        const response = await fetch(`/books/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error("Delete failed");
        }

        loadBooks();

    } catch (err) {
        console.error(err);
        alert("Eroare la ștergere!");
    }
}