let currentEditId = null;

document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const bookId = urlParams.get('id');

    const loggedUser = sessionStorage.getItem("username") || "Anonymous";
    const userRole = sessionStorage.getItem("role"); // 'MEMBER', 'LIBRARIAN', 'ADMIN'
    const userDisplay = document.getElementById("currentUserDisplay");

    if (userDisplay) userDisplay.innerText = loggedUser;

    const addReviewSection = document.querySelector(".add-review");
    if (addReviewSection) {
        if (userRole !== "MEMBER") {
            addReviewSection.style.display = "none";

            if (userRole === "LIBRARIAN" || userRole === "ADMIN") {
                const infoMsg = document.createElement("p");
                infoMsg.className = "text-muted small text-center py-2 border-top";
                infoMsg.innerHTML = "<em>View mode.</em>";
                addReviewSection.parentNode.appendChild(infoMsg);
            }
        }
    }
const actionButtons = document.querySelector(".action-buttons");
if (actionButtons) {
    if (userRole !== "MEMBER") {
        actionButtons.remove();
    }
}

    if (bookId) {
        loadBookDetails(bookId);
        loadReviews(bookId);
    } else {
        console.error("Book ID is missing from URL!");
    }

    const btnPost = document.getElementById("btnPostReview");
    if (btnPost) {
        btnPost.addEventListener("click", () => postReview(bookId));
    }
});

function getCurrentUserId() {
    return sessionStorage.getItem("userId");
}

async function loadBookDetails(id) {
    try {
        const response = await fetch(`/books/${id}`);
        if (!response.ok) throw new Error("Error loading book.");
        const book = await response.json();

        document.getElementById('bookTitle').innerText = book.bookTitle;
        document.getElementById('bookDescription').innerText = book.bookDescription || "No description available.";
        document.getElementById('bookAuthor').innerText = book.author?.authorName || "Unknown";
        document.getElementById('bookCategory').innerText = book.category?.categoryName || "General";
        document.getElementById('bookPublisher').innerText = book.publisher?.publisherName || "Not specified";

        const statusEl = document.getElementById('bookStatus');
        statusEl.innerText = book.availableStock > 0 ? "Available" : "Unavailable";
        statusEl.className = book.availableStock > 0 ? "text-success fw-bold" : "text-danger fw-bold";

        const coverImg = document.getElementById('bookCover');
        if (coverImg) coverImg.src = book.imageUrl || "https://placehold.co/200x300";
    } catch (err) {
        console.error(err);
    }
}

async function loadReviews(id) {
    const reviewsContainer = document.getElementById('reviewsList');
    const template = document.getElementById('review-template');
    const currentUser = sessionStorage.getItem("username");

    try {
        const response = await fetch(`/reviews/book/${id}`);
        const reviews = await response.json();
        reviewsContainer.innerHTML = "";

        if (!reviews || reviews.length === 0) {
            reviewsContainer.innerHTML = "<p class='text-muted text-center py-3'>Be the first to leave a review!</p>";
            return;
        }

        reviews.forEach(rev => {
            const clone = template.content.cloneNode(true);
            const rId = rev.id || rev.reviewID;

            clone.querySelector('.review-user').innerText = rev.userName || "User";
            clone.querySelector('.review-text').innerText = rev.reviewText;
            clone.querySelector('.stars-container').innerText = "★".repeat(rev.rating) + "☆".repeat(5 - rev.rating);

            if (rev.userName === currentUser && currentUser !== "Anonymous") {
                const actions = clone.querySelector('.review-actions');
                actions.classList.remove('d-none');

                clone.querySelector('.btn-edit').onclick = () => prepareEdit(rId, rev.reviewText, rev.rating);
                clone.querySelector('.btn-delete').onclick = () => deleteReview(rId);
            }

            reviewsContainer.appendChild(clone);
        });
    } catch (err) {
        reviewsContainer.innerHTML = "<p class='text-danger'>Error loading reviews.</p>";
    }
}

function prepareEdit(id, text, rating) {
    currentEditId = id;
    document.getElementById("reviewInput").value = text;
    document.getElementById("reviewRating").value = rating;

    const btn = document.getElementById("btnPostReview");
    btn.innerText = "Update";
    btn.classList.replace("btn-teal", "btn-warning-teal");
    document.getElementById("reviewInput").focus();
}

async function postReview(bookId) {
    const input = document.getElementById("reviewInput");
    const ratingSelect = document.getElementById("reviewRating");
    const text = input.value.trim();

    if (!text) return alert("Please write a comment.");

    const reviewData = {
        bookId: parseInt(bookId),
        memberId: parseInt(getCurrentUserId()),
        reviewText: text,
        rating: parseInt(ratingSelect.value),
        userName: sessionStorage.getItem("username")
    };

    const url = currentEditId ? `/reviews/${currentEditId}` : '/reviews';
    const method = currentEditId ? 'PUT' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(reviewData)
        });

        if (response.ok) {
            currentEditId = null;
            input.value = "";
            const btn = document.getElementById("btnPostReview");
            btn.innerText = "Post";
            btn.classList.replace("btn-warning-teal", "btn-teal");
            loadReviews(bookId);
        } else {
            alert("Error saving review. Only members can post.");
        }
    } catch (err) {
        alert("Server connection error.");
    }
}

async function deleteReview(reviewId) {
    if (!confirm("Are you sure you want to delete this review?")) return;

    try {
        const response = await fetch(`/reviews/${reviewId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            const urlParams = new URLSearchParams(window.location.search);
            loadReviews(urlParams.get('id'));
        } else {
            alert("Could not delete review.");
        }
    } catch (err) {
        console.error("Error:", err);
    }
}

async function borrowBook() {
    const urlParams = new URLSearchParams(window.location.search);
    const bookId = urlParams.get('id');
    const memberId = getCurrentUserId();

    if (!memberId || memberId === "null") {
        alert("You must be logged in as a member to borrow!");
        return;
    }

    const statusEl = document.getElementById('bookStatus');
    if (statusEl && statusEl.innerText.trim() === "Unavailable") {
        alert("This book is not available for borrowing.");
        return;
    }

    const today = new Date().toISOString().split("T")[0];
    const dueDate = new Date();
    dueDate.setDate(dueDate.getDate() + 14);
    const due = dueDate.toISOString().split("T")[0];

    try {
        const response = await fetch("/loans", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                borrowDate: today,
                dueDate: due,
                returnDate: null,
                status: "BORROWED",
                memberId: parseInt(memberId),
                librarianId: null,
                bookId: parseInt(bookId)
            })
        });

        if (response.ok) {
            alert("Book borrowed successfully!");
            location.reload();
        } else {
            const text = await response.text();
            alert("Error: " + text);
        }
    } catch (err) {
        console.error(err);
        alert("Server error");
    }
}

async function reserveBook() {
    const urlParams = new URLSearchParams(window.location.search);
    const bookId = urlParams.get('id');
    const memberId = getCurrentUserId();

    if (!memberId || memberId === "null") {
        alert("You must be logged in to reserve!");
        return;
    }

    try {
        const response = await fetch("/reservations", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                reservationDate: new Date().toISOString().split("T")[0],
                status: "PENDING",
                memberId: parseInt(memberId),
                bookId: parseInt(bookId)
            })
        });

        if (response.ok) {
            alert("Book reserved successfully!");
        } else {
            const text = await response.text();
            alert("Reservation failed: " + text);
        }
    } catch (err) {
        console.error(err);
        alert("Server error");
    }
}