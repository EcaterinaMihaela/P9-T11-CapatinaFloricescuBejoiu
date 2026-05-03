document.addEventListener("DOMContentLoaded", () => {
    loadAllReviews();
});

async function loadAllReviews() {
    const tableBody = document.getElementById('adminReviewTable');
    const template = document.getElementById('admin-review-row');

    try {
        const response = await fetch('/reviews');
        if (!response.ok) throw new Error("Failed to fetch reviews.");

        const reviews = await response.json();
        tableBody.innerHTML = "";

        if (reviews.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="5" class="text-center py-4 text-muted">No reviews found.</td></tr>`;
            return;
        }

        reviews.forEach(rev => {
            const clone = template.content.cloneNode(true);
            const rId = rev.id || rev.reviewID;

            // User
            clone.querySelector('.row-user').innerText = rev.userName || `User #${rev.memberId}`;

            // Book Title logic
            let bookDisplayName = "Unknown Book";
            if (rev.book && rev.book.bookTitle) {
                bookDisplayName = rev.book.bookTitle;
            } else if (rev.bookTitle) {
                bookDisplayName = rev.bookTitle;
            } else if (rev.bookId) {
                bookDisplayName = `ID: ${rev.bookId}`;
            }
            clone.querySelector('.row-book-title').innerText = bookDisplayName;

            // Rating & Text
            clone.querySelector('.row-rating').innerText = "★".repeat(rev.rating || 0) + "☆".repeat(5 - (rev.rating || 0));
            clone.querySelector('.row-text').innerText = rev.reviewText;
            clone.querySelector('.row-text').title = rev.reviewText;

            // Delete Action
            clone.querySelector('.btn-delete').onclick = () => deleteReviewAdmin(rId);

            tableBody.appendChild(clone);
        });
    } catch (err) {
        console.error("Error:", err);
        tableBody.innerHTML = `<tr><td colspan="5" class="text-center py-4 text-danger">Error loading reviews.</td></tr>`;
    }
}

async function deleteReviewAdmin(reviewId) {
    if (!confirm("Are you sure you want to delete this review?")) return;

    try {
        const response = await fetch(`/reviews/${reviewId}`, { method: 'DELETE' });
        if (response.ok) {
            loadAllReviews();
        } else {
            alert("Delete failed.");
        }
    } catch (err) {
        console.error("Delete Error:", err);
    }
}