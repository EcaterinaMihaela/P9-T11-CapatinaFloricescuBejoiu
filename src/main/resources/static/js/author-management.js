const API_URL = '/authors';

document.addEventListener('DOMContentLoaded', loadAuthors);

async function loadAuthors() {
    try {
        const res = await fetch(API_URL);
        const authors = await res.json();
        const tbody = document.getElementById('authorTableBody');
        tbody.innerHTML = '';

        authors.forEach(author => {
            const id = author.authorId || author.authorID || author.id;

            tbody.innerHTML += `
                <tr>
                    <td><strong>${id}</strong></td>
                    <td>${author.authorName}</td>
                    <td>
                        <button class="btn-edit" onclick="prepareEditAuthor(${id})">Edit</button>
                        <button class="btn-delete" onclick="deleteAuthor(${id})">Delete</button>
                    </td>
                </tr>`;
        });
    } catch (err) {
        console.error("Error loading authors:", err);
    }
}

async function prepareEditAuthor(id) {
    if (!id) return;
    try {
        const res = await fetch(`${API_URL}/${id}`);
        if (!res.ok) throw new Error("Could not fetch author");

        const author = await res.json();

        const finalId = author.authorId || author.authorID || author.id;

        document.getElementById('authorId').value = finalId;
        document.getElementById('authorName').value = author.authorName;
        document.getElementById('form-title').innerText = "Edit Author Info";

        window.scrollTo({ top: 0, behavior: 'smooth' });
    } catch (err) {
        console.error("Fetch error:", err);
        alert("Could not fetch author details.");
    }
}

document.getElementById('authorForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = document.getElementById('authorId').value;
    const dto = {
        authorName: document.getElementById('authorName').value
    };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_URL}/${id}` : API_URL;

    try {
        const res = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dto)
        });

        if (res.ok) {
            alert(id ? "Author updated!" : "Author created!");
            resetAuthorForm();
            loadAuthors();
        } else {
            alert("Operation failed. Make sure the server is running.");
        }
    } catch (err) {
        alert("Connection error.");
    }
});

async function deleteAuthor(id) {
    if (!id) return;
    if (confirm("Delete this author?")) {
        try {
            const res = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE'
            });

            if (res.ok) {
                alert("Deleted successfully!");
                loadAuthors();
            } else {
                alert("Cannot delete! This author is still linked to some books.");
            }
        } catch (err) {
            alert("Delete request failed.");
        }
    }
}

function resetAuthorForm() {
    document.getElementById('authorForm').reset();
    document.getElementById('authorId').value = '';
    document.getElementById('form-title').innerText = "Add New Author";
}