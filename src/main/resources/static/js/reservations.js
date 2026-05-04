async function loadReservations() {

    const memberId = sessionStorage.getItem("memberId");

    if (!memberId) {
        console.error("No memberId in sessionStorage");
        return;
    }

    const res = await fetch(`/reservations/member/${memberId}`);
    const data = await res.json();

    const table = document.getElementById("reservationsTable");

    if (!table) {
        console.error("reservationsTable not found in HTML");
        return;
    }

    table.innerHTML = "";

    if (!data || data.length === 0) {
        table.innerHTML = `
            <tr>
                <td colspan="5" class="text-center">No reservations found</td>
            </tr>
        `;
        return;
    }

    data.forEach(r => {

        const bookTitle = r.book?.bookTitle ?? "Deleted book";
        const username = r.member?.user?.username ?? "Unknown user";
        const date = r.reservationDate ?? "-";
        const status = r.status ?? "-";

        let statusClass = "";
        if (status === "PENDING") statusClass = "text-warning";
        else if (status === "APPROVED") statusClass = "text-success";
        else if (status === "REJECTED") statusClass = "text-danger";

        table.innerHTML += `
        <tr>
            <td>
                <img src="${r.book?.imageUrl ?? ''}"
                     alt="book cover"
                     style="width:60px; height:90px; object-fit:cover; border-radius:6px;">
            </td>

            <td>${bookTitle}</td>
            <td>${username}</td>
            <td>${date}</td>

            <td class="${statusClass} fw-bold">${status}</td>

            <td>
                <button class="btn btn-sm btn-danger"
                        onclick="cancelReservation(${r.reservationID})">
                    Cancel
                </button>
            </td>
        </tr>
        `;
    });
}

document.addEventListener("DOMContentLoaded", loadReservations);

async function cancelReservation(reservationID) {
    const confirmCancel = confirm("Are you sure you want to cancel this reservation?");
    if (!confirmCancel) return;

    try {
        const res = await fetch(`/reservations/${reservationID}`, {
            method: "DELETE"
        });

        if (!res.ok) {
            alert("Failed to cancel reservation");
            return;
        }

        alert("Reservation cancelled successfully");
        loadReservations();

    } catch (err) {
        console.error(err);
        alert("Error cancelling reservation");
    }
}