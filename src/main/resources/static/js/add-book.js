document.getElementById("addBookForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const book = {
        bookTitle: document.getElementById("title").value,
        bookDescription: document.getElementById("description").value,
        isbn: document.getElementById("isbn").value,
        availableStock: parseInt(document.getElementById("stock").value),
        status: "AVAILABLE",
        authorID: parseInt(document.getElementById("authorID").value),
        categoryID: parseInt(document.getElementById("categoryID").value),
        publisherID: parseInt(document.getElementById("publisherID").value)
    };

    try {
        const response = await fetch("/books", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(book)
        });

        if (!response.ok) {
            throw new Error("Failed to add book");
        }

        alert("Book added successfully!");
        window.location.href = "/librarian-ControlPanel.html";

    } catch (err) {
        console.error(err);
        alert("Error adding book");
    }
});