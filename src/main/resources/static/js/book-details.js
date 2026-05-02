document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const bookId = urlParams.get('id');

    if (bookId) {
        loadBookDetails(bookId);
        loadReviews(bookId);
    } else {
        console.error("Book ID is missing from URL!");
        document.querySelector('.details-container').innerHTML =
            "<h2 class='text-center mt-5'>Error: Book not found.</h2>";
    }

    const btnPost = document.getElementById("btnPostReview");
    if (btnPost) {
        btnPost.addEventListener("click", () => postReview(bookId));
    }
});

async function loadBookDetails(id) {
    try {
        const response = await fetch(`/books/${id}`);
        if (!response.ok) throw new Error("Book does not exist in the database.");

        const book = await response.json();

        document.getElementById('bookTitle').innerText = book.bookTitle || "Title unavailable";
        document.getElementById('bookDescription').innerText = book.bookDescription || "No description available for this book.";

        const categoryDisplay = book.category ?
            (book.category.categoryName || book.category.categoryTitle || book.category.name || "General")
            : "Unspecified";
        document.getElementById('bookCategory').innerText = categoryDisplay;

        document.getElementById('bookAuthor').innerText = book.author?.authorName || "Unknown Author";
        document.getElementById('bookPublisher').innerText = book.publisher?.publisherName || "Unspecified";

        const coverImg = document.getElementById('bookCover');
        if (coverImg) {
            coverImg.src = book.imageUrl || "https://placehold.co/300x450?text=No+Cover";
        }

        const statusEl = document.getElementById('bookStatus');
        if (statusEl) {
            const isAvailable = book.availableStock > 0;
            statusEl.innerText = isAvailable ? "Available" : "Borrowed";
            statusEl.className = isAvailable ? "text-success fw-bold" : "text-danger fw-bold";
        }

    } catch (err) {
        console.error("Error fetching details:", err);
    }
}

async function loadReviews(id) {
    const reviewsList = document.getElementById('reviewsList');
    if (!reviewsList) return;

    try {
        const response = await fetch(`/api/reviews/book/${id}`);
        const reviews = await response.json();

        reviewsList.innerHTML = "";

        if (!reviews || reviews.length === 0) {
            reviewsList.innerHTML = "<p class='text-muted text-center'>No reviews yet. Be the first to leave one!</p>";
            return;
        }

        reviews.forEach(rev => {
            const div = document.createElement("div");
            div.className = "review-item mb-3 pb-2 border-bottom";

            const starCount = rev.rating || 5;
            const stars = "★".repeat(starCount) + "☆".repeat(5 - starCount);

            div.innerHTML = `
                <div class="d-flex justify-content-between align-items-center">
                    <strong>${rev.userName || "User"}</strong>
                    <span style="color: #f1c40f;">${stars}</span>
                </div>
                <p class="mb-0 text-secondary" style="font-size: 0.9rem;">${rev.commentText}</p>
            `;
            reviewsList.appendChild(div);
        });
    } catch (err) {
        console.error("Error loading reviews:", err);
        reviewsList.innerHTML = "<p class='text-danger'>Reviews could not be loaded.</p>";
    }
}

async function postReview(bookId) {
    const input = document.getElementById("reviewInput");
    const comment = input.value.trim();

    if (!comment) {
        alert("Please write a comment.");
        return;
    }

    const reviewData = {
        bookId: parseInt(bookId),
        commentText: comment,
        rating: 5,
        userName: localStorage.getItem("username") || "Anonymous"
    };

    try {
        const response = await fetch('/api/reviews', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(reviewData)
        });

        if (response.ok) {
            input.value = "";
            loadReviews(bookId);
        } else {
            alert("Error saving review.");
        }
    } catch (err) {
        console.error("Error posting:", err);
        alert("Server did not respond.");
    }
}

function getCurrentUserId() {
    return localStorage.getItem("userId"); // sau memberId dacă așa ai salvat
}

async function borrowBook() {

    const urlParams = new URLSearchParams(window.location.search);
    const bookId = urlParams.get('id');
    const memberId = getCurrentUserId();

    if (!memberId) {
        alert("You must be logged in!");
        return;
    }

    const today = new Date().toISOString().split("T")[0];
    const dueDate = new Date();
    dueDate.setDate(dueDate.getDate() + 14); // 14 zile
    const due = dueDate.toISOString().split("T")[0];

    try {
        const response = await fetch("/loans", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                borrowDate: today,
                dueDate: due,
                returnDate: null,
                status: "BORROWED",
                memberId: memberId,
                librarianId: 1, // temporar (hardcod)
                bookId: bookId
            })
        });

        if (response.ok) {
            alert("Book borrowed successfully!");
        } else {
            const text = await response.text();
            alert(text);
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

console.log("memberId:", memberId);
console.log("bookId:", bookId);
    if (!memberId) {
        alert("You must be logged in!");
        return;
    }

    try {
        const response = await fetch("/reservations", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                reservationDate: new Date().toISOString().split("T")[0],
                status: "PENDING",
                memberId: memberId,
                bookId: bookId
            })
        });

        if (response.ok) {
            alert("Book reserved!");
        } else {
            const text = await response.text();
            alert(text);
        }

    } catch (err) {
        console.error(err);
        alert("Server error");
    }
}