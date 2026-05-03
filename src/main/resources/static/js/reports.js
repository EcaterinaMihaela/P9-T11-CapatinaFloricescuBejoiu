async function generateReport() {
    const monthInput = document.getElementById("monthFilter");
    const selectedMonth = monthInput ? monthInput.value : "";

    try {

        // 1. Populăm "Most Borrowed Books"
        const response = await fetch(`/reports/dashboard?month=${selectedMonth}`);
        const data = await response.json();
        const topBooksUl = document.getElementById("topBooks");
        topBooksUl.innerHTML = data.topBooks.map(book =>
            `<li class="list-group-item d-flex justify-content-between align-items-center">
                ${book}
            </li>`
        ).join("");

        // 2. Populăm "Overdue Returns"
        const overdueUl = document.getElementById("overdueList");
        if (data.overdueReturns.length === 0) {
            overdueUl.innerHTML = `<li class="list-group-item text-muted">No overdue books at the moment.</li>`;
        } else {
            overdueUl.innerHTML = data.overdueReturns.map(item =>
                `<li class="list-group-item text-danger">${item}</li>`
            ).join("");
        }

        // 3. Actualizăm cifrele din grid
        document.getElementById("totalLoans").innerText = data.totalLoans;
        document.getElementById("newBooks").innerText = data.newBooks;
        document.getElementById("totalReservations").innerText = data.totalReservations;
        document.getElementById("activeUsers").innerText = data.activeUsers;

    } catch (error) {
        console.error("Error fetching report:", error);
    }
}
function exportPDF() {
    window.print();
}