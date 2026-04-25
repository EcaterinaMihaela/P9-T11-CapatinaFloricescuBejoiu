async function loadBookDetails() {
    const urlParams = new URLSearchParams(window.location.search);
    const bookId = urlParams.get('id');

    if (!bookId) {
        console.error("ID-ul cărții lipsește din URL");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/books/${bookId}`);

        if (!response.ok) {
            throw new Error("Cartea nu a fost găsită");
        }

        const book = await response.json();

        document.getElementById('bookTitle').innerText = book.title;
        document.getElementById('bookAuthor').innerText = book.author;
        document.getElementById('bookCategory').innerText = book.category;
        document.getElementById('bookDescription').innerText = book.description;

        if (book.imageUrl) {
            document.getElementById('bookCover').src = book.imageUrl;
        }

        const statusEl = document.getElementById('bookStatus');
        statusEl.innerText = book.status;
        statusEl.className = (book.status === 'Available') ? 'status-available' : 'status-borrowed';

    } catch (error) {
        console.error("Eroare la încărcarea cărții:", error);
        document.getElementById('bookTitle').innerText = "Book not found.";
    }
}
document.addEventListener('DOMContentLoaded', loadBookDetails);