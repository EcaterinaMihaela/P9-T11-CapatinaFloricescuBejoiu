let allLoans = [];
let currentExtendingLoan = null;

document.addEventListener("DOMContentLoaded", async () => {
    await loadLoans();
});

async function loadLoans() {
    try {
        const res = await fetch("/loans");
        allLoans = await res.json();
        displayLoans(allLoans);
    } catch (error) {
        console.error("Error loading loans:", error);
        document.getElementById("loanTable").innerHTML = `
            <div class="alert alert-danger">Failed to load loans. Please try again.</div>
        `;
    }
}

function displayLoans(loans) {
    const container = document.getElementById("loanTable");

    if (!loans || loans.length === 0) {
        container.innerHTML = `
            <div class="empty-state">
                <h3>No Loans Found</h3>
                <p>There are currently no loans in the system.</p>
            </div>
        `;
        return;
    }

    const html = loans.map(loan => {
        const isOverdue = loan.status === "BORROWED" && new Date(loan.dueDate) < new Date();
        const actualStatus = isOverdue ? "OVERDUE" : loan.status;

        return `
            <div class="loan-card ${isOverdue ? 'overdue' : ''}">
                <div class="loan-details">
                    <img src="${loan.book.imageUrl || '/images/placeholder-book.jpg'}"
                         alt="${loan.book.bookTitle}">

                    <div class="loan-info">
                        <b>${loan.book.bookTitle}</b>
                        <span>📚 Author: ${loan.book.author.authorName}</span>
                        <span>👤 Member: ${loan.member.user.profile.firstName} ${loan.member.user.profile.lastName}</span>
                        <span class="status-${actualStatus.toLowerCase()}">${actualStatus}</span>
                    </div>
                </div>

                <div class="loan-dates">
                    <div class="date-box">
                        <label>Borrow Date</label>
                        <span>${loan.borrowDate}</span>
                    </div>
                    <div class="date-box">
                        <label>Due Date</label>
                        <span>${loan.dueDate}</span>
                    </div>
                    ${loan.returnDate ? `
                        <div class="date-box">
                            <label>Return Date</label>
                            <span>${loan.returnDate}</span>
                        </div>
                    ` : ''}
                </div>

                <div class="loan-actions">
                    ${loan.status === "BORROWED" ? `
                        <button class="btn-return" onclick="returnBook(${loan.loanID})">
                            Return Book
                        </button>
                        <button class="btn-extend" onclick="openExtendModal(${loan.loanID})">
                            Extend Period
                        </button>
                    ` : `
                        <button class="btn-view" onclick="viewLoanDetails(${loan.loanID})">
                            View Details
                        </button>
                    `}
                </div>
            </div>
        `;
    }).join("");

    container.innerHTML = html;
}

async function returnBook(loanId) {
    if (!confirm("Mark this book as returned?")) return;

    try {
        const res = await fetch(`/loans/${loanId}/return`, {
            method: "PUT"
        });

        if (res.ok) {
            alert("Book returned successfully!");
            await loadLoans();
        } else {
            alert("Failed to return book");
        }
    } catch (error) {
        console.error("Error returning book:", error);
        alert("Error returning book");
    }
}

function openExtendModal(loanId) {
    const loan = allLoans.find(l => l.loanID === loanId);
    if (!loan) return;

    currentExtendingLoan = loan;

    document.getElementById("extendBookTitle").textContent = loan.book.bookTitle;
    document.getElementById("extendMemberName").textContent =
        `${loan.member.user.profile.firstName} ${loan.member.user.profile.lastName}`;
    document.getElementById("extendCurrentDue").textContent = loan.dueDate;

    // Set minimum date to tomorrow
    const tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    document.getElementById("newDueDate").min = tomorrow.toISOString().split('T')[0];

    const modal = new bootstrap.Modal(document.getElementById('extendModal'));
    modal.show();
}

async function confirmExtend() {
    const newDueDate = document.getElementById("newDueDate").value;

    if (!newDueDate) {
        alert("Please select a new due date");
        return;
    }

    try {
        const res = await fetch(`/loans/${currentExtendingLoan.loanID}/extend`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ newDueDate })
        });

        if (res.ok) {
            alert("Loan period extended successfully!");
            bootstrap.Modal.getInstance(document.getElementById('extendModal')).hide();
            await loadLoans();
        } else {
            alert("Failed to extend loan period");
        }
    } catch (error) {
        console.error("Error extending loan:", error);
        alert("Error extending loan period");
    }
}

function applyFilters() {
    const statusFilter = document.getElementById("statusFilter").value.toLowerCase();
    const memberSearch = document.getElementById("memberSearch").value.toLowerCase();
    const bookSearch = document.getElementById("bookSearch").value.toLowerCase();

    const filtered = allLoans.filter(loan => {
        const isOverdue = loan.status === "BORROWED" && new Date(loan.dueDate) < new Date();
        const actualStatus = isOverdue ? "overdue" : loan.status.toLowerCase();

        const memberName = `${loan.member.user.profile.firstName} ${loan.member.user.profile.lastName}`.toLowerCase();
        const bookTitle = loan.book.bookTitle.toLowerCase();

        return (!statusFilter || actualStatus === statusFilter) &&
               (!memberSearch || memberName.includes(memberSearch)) &&
               (!bookSearch || bookTitle.includes(bookSearch));
    });

    displayLoans(filtered);
}

function viewLoanDetails(loanId) {
    const loan = allLoans.find(l => l.loanID === loanId);
    if (!loan) return;

    alert(`
Loan Details:
━━━━━━━━━━━━━━━
Book: ${loan.book.bookTitle}
Member: ${loan.member.user.profile.firstName} ${loan.member.user.profile.lastName}
Borrow Date: ${loan.borrowDate}
Due Date: ${loan.dueDate}
Return Date: ${loan.returnDate || 'Not returned yet'}
Status: ${loan.status}
    `);
}