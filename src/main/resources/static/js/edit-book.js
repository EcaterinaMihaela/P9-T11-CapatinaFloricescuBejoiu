document.addEventListener("DOMContentLoaded", async () => {
    const params = new URLSearchParams(window.location.search);
    const bookId = params.get("id");

    if (!bookId) {
        alert("Book ID lipsește din URL!");
        return;
    }

    await Promise.all([
        populateSelect("/authors", "authorID"),
        populateSelect("/categories", "categoryID"),
        populateSelect("/publishers", "publisherID")
    ]);

    loadBook(bookId);

    document.getElementById("isbn").addEventListener("blur", generateCover);

    const form = document.getElementById("editBookForm");
    form.addEventListener("submit", (e) => {
        e.preventDefault();
        updateBook(bookId);
    });
});

let generatedCoverUrl = "";

async function populateSelect(endpoint, elementId) {
    try {
        const response = await fetch(endpoint);
        const data = await response.json();
        const select = document.getElementById(elementId);

        select.innerHTML = '<option value="">Selectează...</option>';

        data.forEach(item => {
            const option = document.createElement("option");
            option.value = item.publisherID || item.categoryID || item.authorID || item.id;
            option.textContent =
                item.publisherName ||
                item.categoryTitle ||
                item.authorName ||
                item.name;

            select.appendChild(option);
        });

    } catch (err) {
        console.error(err);
    }
}

function generateCover() {
    const isbn = document.getElementById("isbn").value.trim().replace(/-/g, "");

    if (isbn.length >= 10) {
        generatedCoverUrl = `https://covers.openlibrary.org/b/isbn/${isbn}-L.jpg`;

        const preview = document.getElementById("coverPreview");
        if (preview) {
            preview.src = generatedCoverUrl;
            preview.style.display = "block";
        }
    }
}

async function loadBook(id) {
    try {
        const response = await fetch(`/books/${id}`);
        const book = await response.json();

        document.getElementById("title").value = book.bookTitle || "";
        document.getElementById("description").value = book.bookDescription || "";
        document.getElementById("isbn").value = book.ISBN || "";
        document.getElementById("stock").value = book.availableStock || 0;

        if (book.author)
            document.getElementById("authorID").value = book.author.authorID;

        if (book.category)
            document.getElementById("categoryID").value = book.category.categoryID;

        if (book.publisher)
            document.getElementById("publisherID").value = book.publisher.publisherID;

        if (book.imageUrl) {
            generatedCoverUrl = book.imageUrl;

            const preview = document.getElementById("coverPreview");
            if (preview) {
                preview.src = book.imageUrl;
                preview.style.display = "block";
            }
        }

    } catch (err) {
        console.error(err);
        alert("Eroare la încărcare!");
    }
}

async function updateBook(id) {
    const updatedBook = {
        bookTitle: document.getElementById("title").value,
        bookDescription: document.getElementById("description").value,
        isbn: document.getElementById("isbn").value,
        availableStock: parseInt(document.getElementById("stock").value),
        status: "AVAILABLE",
        authorID: parseInt(document.getElementById("authorID").value),
        categoryID: parseInt(document.getElementById("categoryID").value),
        publisherID: parseInt(document.getElementById("publisherID").value),
        imageUrl: generatedCoverUrl
    };

    try {
        const response = await fetch(`/books/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedBook)
        });

        if (response.ok) {
            alert("Cartea a fost actualizată!");
            window.location.href = "/librarian-ControlPanel.html";
        } else {
            alert("Eroare la update");
        }

    } catch (err) {
        console.error(err);
        alert("Eroare server");
    }
}