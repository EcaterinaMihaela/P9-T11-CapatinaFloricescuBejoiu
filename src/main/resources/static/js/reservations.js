async function loadReservations() {

    const res = await fetch("/reservations");
    const data = await res.json();

    const table = document.getElementById("reservationsTable");
    table.innerHTML = "";

    data.forEach(r => {

        table.innerHTML += `
        <tr>
            <td>${r.book?.bookTitle ?? "Unknown"}</td>
            <td>${r.reservationDate}</td>
            <td>
                <span class="status ${r.status === 'PENDING' ? 'pending' : 'active'}">
                    ${r.status}
                </span>
            </td>
            <td>
                <button onclick="cancelReservation(${r.reservationID})" class="suspend-btn">
                    Cancel
                </button>
            </td>
        </tr>
        `;
    });
}

async function cancelReservation(id) {

    await fetch(`/reservations/${id}`, {
        method: "DELETE"
    });

    loadReservations();
}

document.addEventListener("DOMContentLoaded", loadReservations);