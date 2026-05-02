function generateReport() {

    const category = document.getElementById("categoryFilter").value;
    const month = document.getElementById("monthFilter").value;

    console.log("Generating report:", category, month);

    // placeholder data (pana conectam backend)
    document.getElementById("topBooks").innerHTML = `
        <li class="list-group-item">Book A - 120 borrows</li>
        <li class="list-group-item">Book B - 95 borrows</li>
        <li class="list-group-item">Book C - 80 borrows</li>
    `;

    document.getElementById("overdueList").innerHTML = `
        <li class="list-group-item text-danger">User X - Book A</li>
        <li class="list-group-item text-danger">User Y - Book B</li>
    `;

    document.getElementById("totalLoans").innerText = 240;
    document.getElementById("newBooks").innerText = 15;
    document.getElementById("totalReservations").innerText = 32;
    document.getElementById("activeUsers").innerText = 18;
}

function exportPDF() {
    alert("PDF export will be connected to backend later (LIB-28)");
}