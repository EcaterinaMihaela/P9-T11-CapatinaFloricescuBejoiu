const API_URL = '/publishers';

document.addEventListener('DOMContentLoaded', loadPublishers);

async function loadPublishers() {
    try {
        const res = await fetch(API_URL);
        const data = await res.json();
        const tbody = document.getElementById('publisherTableBody');
        tbody.innerHTML = '';

        data.forEach(pub => {
            const id = pub.publisherId || pub.publisherID || pub.id;

            tbody.innerHTML += `
                <tr>
                    <td>${id}</td>
                    <td>${pub.publisherName}</td>
                    <td>
                        <button class="btn-edit" onclick="prepareEdit(${id})">Edit</button>
                        <button class="btn-delete" onclick="deletePub(${id})">Delete</button>
                    </td>
                </tr>`;
        });
    } catch (err) {
        console.error("Error loading publishers:", err);
    }
}

async function prepareEdit(id) {
    try {
        const res = await fetch(`${API_URL}/${id}`);
        if (!res.ok) throw new Error("Publisher not found");

        const pub = await res.json();
        const finalId = pub.publisherId || pub.publisherID || pub.id;

        document.getElementById('publisherId').value = finalId;
        document.getElementById('publisherName').value = pub.publisherName;
        document.getElementById('form-title').innerText = "Edit Publisher Info";

        window.scrollTo({ top: 0, behavior: 'smooth' });
    } catch (err) {
        alert("Error fetching details.");
    }
}

document.getElementById('publisherForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = document.getElementById('publisherId').value;
    const dto = { publisherName: document.getElementById('publisherName').value };

    const method = id ? 'PUT' : 'POST';
    const url = id ? `${API_URL}/${id}` : API_URL;

    try {
        const res = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dto)
        });

        if (res.ok) {
            alert(id ? "Successfully updated!" : "Successfully created!");
            resetPublisherForm();
            loadPublishers();
        } else {
            alert("Save failed. Check if server security blocks the request.");
        }
    } catch (err) {
        alert("Connection error.");
    }
});

async function deletePub(id) {
    if (confirm("Are you sure? Associated books will also be deleted.")) {
        try {
            const res = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
            if (res.ok) {
                loadPublishers();
            } else {
                alert("Delete failed.");
            }
        } catch (err) {
            console.error(err);
        }
    }
}

function resetPublisherForm() {
    document.getElementById('publisherForm').reset();
    document.getElementById('publisherId').value = '';
    document.getElementById('form-title').innerText = "Add New Publisher";
}