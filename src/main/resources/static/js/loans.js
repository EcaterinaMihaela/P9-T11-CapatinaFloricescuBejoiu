async function loadLoans() {

    const res = await fetch("/loans");
    const data = await res.json();

    const table = document.getElementById("loansTable");
    table.innerHTML = "";

    data.forEach(l => {

        table.innerHTML += `
        <tr>
            <td>${l.book?.bookTitle ?? "Unknown"}</td>
            <td>${l.borrowDate}</td>
            <td>${l.dueDate}</td>
            <td>${l.status}</td>
            <td>
                ${l.status === "BORROWED" ? `
                    <button onclick="returnBook(${l.loanID})" class="btn btn-primary">
                        Return
                    </button>
                ` : "Returned"}
            </td>
        </tr>
        `;
    });
}

async function returnBook(id) {

    const res = await fetch(`/loans/${id}/return`, {
        method: "PUT"
    });

    if (res.ok) {
        loadLoans();
    } else {
        alert("Error returning book");
    }
}

document.addEventListener("DOMContentLoaded", loadLoans);