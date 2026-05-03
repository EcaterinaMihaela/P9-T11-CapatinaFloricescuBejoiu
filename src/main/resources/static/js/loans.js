document.addEventListener("DOMContentLoaded", () => {
    loadLoans();
});

async function loadLoans() {
    const table = document.getElementById("loansTable");
        if (!table) return;

        // 1. Luăm username-ul salvat la login în localStorage
        const currentUsername = localStorage.getItem("username");

    try {
            // 2. Schimbăm URL-ul către noul nostru endpoint /my-loans
            // Adăugăm și parametrul username pentru a filtra rezultatele
            const res = await fetch(`/loans/my-loans?username=${currentUsername}`);

            if (!res.ok) throw new Error("Failed to fetch loans");

            const data = await res.json();

        table.innerHTML = "";

        if (data.length === 0) {
            table.innerHTML = "<tr><td colspan='5' class='text-center'>No loans found.</td></tr>";
            return;
        }

       const today = new Date();
               today.setHours(0, 0, 0, 0); // Resetăm ora pentru o comparare corectă

               data.forEach(l => {
                   const isBorrowed = l.status === "BORROWED";

                   // Verificăm dacă este întârziată
                   const dueDate = new Date(l.dueDate);
                   const isOverdue = isBorrowed && dueDate < today;

                   table.innerHTML += `
                   <tr class="${isOverdue ? 'table-danger' : ''}"> <!-- Înroșim rândul dacă e Overdue -->
                       <td>
                           <div class="d-flex align-items-center">
                               <img src="${l.book?.imageUrl || 'https://placehold.co/40x60'}"
                                    style="width:40px; height:60px; margin-right:10px; border-radius:4px;">
                               <div>
                                   <strong>${l.book?.bookTitle ?? "Unknown Book"}</strong>
                                   ${isOverdue ? '<br><small class="text-danger fw-bold">OVERDUE</small>' : ''}
                               </div>
                           </div>
                       </td>
                       <td>${l.borrowDate}</td>
                       <td class="${isOverdue ? 'text-danger fw-bold' : ''}">${l.dueDate}</td>
                       <td>
                           <span class="badge ${isOverdue ? 'bg-danger' : (isBorrowed ? 'bg-warning text-dark' : 'bg-success')}">
                               ${isOverdue ? 'OVERDUE' : l.status}
                           </span>
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