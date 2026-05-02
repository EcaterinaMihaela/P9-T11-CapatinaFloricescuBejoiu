document.addEventListener("DOMContentLoaded", () => {
    loadLoans();
});

async function loadLoans() {
    const table = document.getElementById("loansTable");
    if (!table) return;

    try {
        // Preluăm toate împrumuturile de la backend
        const res = await fetch("/loans");
        if (!res.ok) throw new Error("Failed to fetch loans");
        
        const data = await res.json();

        table.innerHTML = "";

        if (data.length === 0) {
            table.innerHTML = "<tr><td colspan='5' class='text-center'>No loans found.</td></tr>";
            return;
        }

        data.forEach(l => {
            // Verificăm dacă statusul este BORROWED pentru a afișa butonul de Return
            const isBorrowed = l.status === "BORROWED";
            
            table.innerHTML += `
            <tr>
                <td>
                    <div class="d-flex align-items-center">
                        <img src="${l.book?.imageUrl || 'https://placehold.co/40x60'}" 
                             style="width:40px; height:60px; margin-right:10px; border-radius:4px;">
                        <strong>${l.book?.bookTitle ?? "Unknown Book"}</strong>
                    </div>
                </td>
                <td>${l.borrowDate}</td>
                <td>${l.dueDate}</td>
                <td>
                    <span class="badge ${isBorrowed ? 'bg-warning text-dark' : 'bg-success'}">
                        ${l.status}
                    </span>
                </td>
                <td>
                    ${isBorrowed ? `
                        <button onclick="returnBook(${l.loanID})" class="btn btn-sm btn-primary">
                            Return Book
                        </button>
                    ` : `<span class="text-muted">Returned on ${l.returnDate || '-'}</span>`}
                </td>
            </tr>
            `;
        });
    } catch (err) {
        console.error("Error loading loans:", err);
        table.innerHTML = "<tr><td colspan='5' class='text-center text-danger'>Error loading data.</td></tr>";
    }
}

async function returnBook(loanId) {
    if (!confirm("Are you sure you want to return this book?")) return;

    try {


        const res = await fetch(`/loans/${loanId}/return`, {
            method: "PUT"
        });

        if (res.ok) {
            alert("Book returned successfully!");
            loadLoans(); // Reîncărcăm tabelul
        } else {
            const errorMsg = await res.text();
            alert("Error: " + errorMsg);
        }
    } catch (err) {
        console.error("Error returning book:", err);
        alert("Server error. Please try again.");
    }
}