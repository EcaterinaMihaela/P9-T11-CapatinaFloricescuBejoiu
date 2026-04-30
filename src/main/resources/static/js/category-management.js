document.addEventListener("DOMContentLoaded", () => {
    loadCategories();
    document.getElementById('categoryForm').addEventListener('submit', handleCategorySubmit);
});

// 1. Fetch and display all categories in the table
async function loadCategories() {
    try {
        const res = await fetch("/categories");
        if (!res.ok) throw new Error("Failed to load categories");

        const categories = await res.json();
        const tbody = document.getElementById("categoryTableBody");
        tbody.innerHTML = "";

        categories.forEach(cat => {
            // Using exact field names from your Java Model (Category.java)
            const id = cat.categoryID;
            const title = cat.categoryTitle;

            const row = `
                <tr>
                    <td>${id}</td>
                    <td><strong>${title}</strong></td>
                    <td>
                        <button onclick="editCategory(${id})" class="btn-edit">Edit</button>
                        <button onclick="deleteCategory(${id})" class="btn-delete">Delete</button>
                    </td>
                </tr>
            `;
            tbody.innerHTML += row;
        });
    } catch (err) {
        console.error("Error:", err);
    }
}

// 2. Handle Create (POST) or Update (PUT)
async function handleCategorySubmit(e) {
    e.preventDefault();
    const id = document.getElementById('category-id').value;

    // Key name must match 'categoryTitle' for Jackson/Spring mapping
    const categoryData = {
        categoryTitle: document.getElementById('category-name').value
    };

    const url = id ? `/categories/${id}` : "/categories";
    const method = id ? "PUT" : "POST";

    try {
        const res = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(categoryData)
        });

        if (res.ok) {
            resetCategoryForm();
            loadCategories();
        } else {
            alert("Server error: " + res.status);
        }
    } catch (err) {
        console.error("Network error:", err);
    }
}

// 3. Load category data into form for editing
window.editCategory = async function(id) {
    try {
        const res = await fetch(`/categories/${id}`);
        if (!res.ok) throw new Error("Category not found");

        const cat = await res.json();

        document.getElementById('category-id').value = cat.categoryID;
        document.getElementById('category-name').value = cat.categoryTitle;
        document.getElementById('form-title').innerText = "Edit Category #" + id;

        // Smooth scroll to the form at the top
        window.scrollTo({ top: 0, behavior: 'smooth' });
    } catch (err) {
        console.error("Error fetching category:", err);
        alert("Could not fetch category details.");
    }
};

// 4. Delete a category
window.deleteCategory = async function(id) {
    if (confirm("Are you sure you want to delete this category?")) {
        try {
            const res = await fetch(`/categories/${id}`, { method: "DELETE" });
            if (res.ok) {
                loadCategories();
            } else {
                alert("Could not delete. The category might be in use by some books.");
            }
        } catch (err) {
            console.error("Delete error:", err);
        }
    }
};

// 5. Reset form to initial state
window.resetCategoryForm = function() {
    document.getElementById('categoryForm').reset();
    document.getElementById('category-id').value = "";
    document.getElementById('form-title').innerText = "Add New Category";
};