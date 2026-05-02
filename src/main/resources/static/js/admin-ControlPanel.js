document.addEventListener("DOMContentLoaded", async () => {

    const res = await fetch("/users");
    const users = await res.json();

    const tbody = document.getElementById("usersTable");
    tbody.innerHTML = "";

    const rows = users.map(u => `
        <tr>
            <td>${u.username}</td>

            <td>
                <select onchange="changeRole(${u.id}, this.value)">
                    <option ${u.role === "MEMBER" ? "selected" : ""}>MEMBER</option>
                    <option ${u.role === "LIBRARIAN" ? "selected" : ""}>LIBRARIAN</option>
                    <option ${u.role === "ADMIN" ? "selected" : ""}>ADMIN</option>
                </select>
            </td>

            <td>
                <span class="status ${u.status || "ACTIVE"}">
                    ${u.status ?? "ACTIVE"}
                </span>
            </td>

            <td>${u.email ?? "-"}</td>
            <td>${u.phoneNumber ?? "-"}</td>
            <td>${u.address ?? "-"}</td>
            <td>${u.banReason ?? "-"}</td>

            <td>
                <button class="btn-action ban-btn" onclick="banUser(${u.id})">Ban</button>
                <button class="btn-action unban-btn" onclick="unbanUser(${u.id})">Unban</button>
                <button class="btn-action delete-btn" onclick="deleteUser(${u.id})">Delete</button>
            </td>
        </tr>
    `).join("");

    tbody.innerHTML = rows;

    const logoutBtn = document.getElementById("logoutBtn");

    if (logoutBtn) {
        logoutBtn.addEventListener("click", (e) => {
            e.preventDefault();

            console.log("BEFORE:", localStorage);

                localStorage.clear();

                console.log("AFTER:", localStorage);

                setTimeout(() => {
                    window.location.href = "/login.html";
                }, 200);
        });
    }
});

/* DELETE USER */
async function deleteUser(id) {

    const role = localStorage.getItem("role");

    if (role !== "ADMIN") {
        alert("Nu ai dreptul sa stergi utilizatori!");
        return;
    }

    await fetch(`/users/${id}?role=${role}`, {
        method: "DELETE"
    });

    location.reload();
}

async function changeRole(id, role) {
    // Folosește URL relativ ca la celelalte funcții
    const response = await fetch(`/users/${id}/role`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            role: role
        })
    });

    if (!response.ok) {
        alert("Eroare la schimbarea rolului!");
    } else {
        console.log("Rol actualizat cu succes");
    }
}


/* BAN USER */
async function banUser(id) {
    const reason = prompt("Reason for ban:");
    if (!reason) return;

    await fetch(`/users/${id}/ban?reason=${encodeURIComponent(reason)}`, {
        method: "PUT"
    });

    location.reload();
}

/* UNBAN USER */
async function unbanUser(id) {
    await fetch(`/users/${id}/unban`, {
        method: "PUT"
    });

    location.reload();
}

document.addEventListener("click", (e) => {
    if (e.target && e.target.id === "logoutBtn") {
        e.preventDefault();
        localStorage.clear();
        window.location.href = "/login.html";
    }
});