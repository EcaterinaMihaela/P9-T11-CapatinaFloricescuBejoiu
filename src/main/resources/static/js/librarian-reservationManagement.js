async function loadReservations() {

    const response = await fetch("/reservations");
    const reservations = await response.json();

    const container = document.getElementById("reservationTable");

    if (!container) return;

    container.innerHTML = "";

    if (!reservations || reservations.length === 0) {
        container.innerHTML = `<div class="text-center">No reservations found</div>`;
        return;
    }

    reservations.forEach(r => {

        const bookTitle = r.book?.bookTitle ?? "Unknown book";
        const image = r.book?.imageUrl ?? "";
        const userName = r.member?.user?.username ?? "Unknown user";
        const date = r.reservationDate ?? "-";
        const status = r.status ?? "-";

        let statusClass = "";
        if (status === "PENDING") statusClass = "status-pending";
        else if (status === "APPROVED") statusClass = "status-approved";
        else if (status === "REJECTED") statusClass = "status-rejected";

        container.innerHTML += `
            <div class="reservation-card">

                <div class="reservation-details">
                    <img src="${image}" alt="book cover">

                    <div class="reservation-info">
                        <b>${bookTitle}</b>
                        <span>User: ${userName}</span>
                        <span>Date: ${date}</span>
                    </div>
                </div>

                <div>
                    <div class="${statusClass}">
                        ${status}
                    </div>
                </div>

                <div>
                    <button class="btn-approve"
                        onclick="approveReservation(${r.reservationID})"
                        ${status !== "PENDING" ? "disabled" : ""}>
                        Approve
                    </button>

                    <button class="btn-reject"
                        onclick="rejectReservation(${r.reservationID})"
                        ${status !== "PENDING" ? "disabled" : ""}>
                        Reject
                    </button>
                </div>

            </div>
        `;
    });
}

document.addEventListener("DOMContentLoaded", loadReservations);

async function approveReservation(id) {
    await fetch(`/reservations/${id}/approve`, {
        method: "PUT"
    });

    loadReservations();
}

async function rejectReservation(id) {
    await fetch(`/reservations/${id}/reject`, {
        method: "PUT"
    });

    loadReservations();
}