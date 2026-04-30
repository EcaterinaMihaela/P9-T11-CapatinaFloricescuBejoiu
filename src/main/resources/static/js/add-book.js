// 1. Popularea dropdown-urilor la încărcarea paginii
document.addEventListener("DOMContentLoaded", function() {
    console.log("Pagina s-a încărcat. Începem popularea dropdown-urilor...");
    populateSelect("/authors", "authorID");
    populateSelect("/categories", "categoryID");
    populateSelect("/publishers", "publisherID");
});

// Variabilă globală pentru a stoca URL-ul imaginii găsite prin API
let generatedCoverUrl = "";

// 2. LOGICA API EXTERN: Căutăm coperta după ISBN
document.getElementById("isbn").addEventListener("blur", function() {
    const isbn = this.value.trim().replace(/-/g, ""); // Scoatem liniuțele dacă există

    if (isbn.length >= 10) {
        // Generăm link-ul către Open Library API
        generatedCoverUrl = `https://covers.openlibrary.org/b/isbn/${isbn}-L.jpg`;

        console.log("URL Copertă generat:", generatedCoverUrl);

        // OPȚIONAL: Dacă vrei să arăți poza pe pagină înainte de salvare
        // Adaugă în HTML un <img id="coverPreview" style="display:none; width:100px;">
        const preview = document.getElementById("coverPreview");
        if (preview) {
            preview.src = generatedCoverUrl;
            preview.style.display = "block";
        }
    }
});

async function populateSelect(endpoint, elementId) {
    try {
        const response = await fetch(endpoint);
        if (!response.ok) throw new Error(`Eroare la încărcarea datelor de la ${endpoint}`);

        const data = await response.json();
        const selectElement = document.getElementById(elementId);

        if (!selectElement) return;

        data.forEach(item => {
            const option = document.createElement("option");

            // MAPARE ID conform modelelor tale Java
            option.value = item.publisherID || item.categoryID || item.authorID || item.id;

            // MAPARE TEXT conform modelelor tale Java
            option.textContent = item.publisherName ||
                                 item.categoryTitle ||
                                 item.authorName ||
                                 item.name ||
                                 `ID: ${option.value}`;

            selectElement.appendChild(option);
        });
        console.log(`Dropdown-ul ${elementId} a fost populat.`);
    } catch (err) {
        console.error(`Eroare la popularea select-ului ${elementId}:`, err);
    }
}

// 3. Gestionarea trimiterii formularului (POST)
document.getElementById("addBookForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const titleEl = document.getElementById("title");
    const descEl = document.getElementById("description");
    const isbnEl = document.getElementById("isbn");
    const stockEl = document.getElementById("stock");
    const authorEl = document.getElementById("authorID");
    const categoryEl = document.getElementById("categoryID");
    const publisherEl = document.getElementById("publisherID");

    // Verificare de siguranță
    if (!titleEl || !stockEl || !authorEl || !categoryEl || !publisherEl) {
        console.error("Elemente lipsă în DOM!");
        alert("Eroare: Unul dintre câmpuri nu a fost găsit în pagină!");
        return;
    }

    // Construim obiectul cărții incluzând și imageUrl
    const book = {
        bookTitle: titleEl.value,
        bookDescription: descEl.value,
        isbn: isbnEl.value,
        availableStock: parseInt(stockEl.value) || 0,
        status: "AVAILABLE",
        authorID: parseInt(authorEl.value),
        categoryID: parseInt(categoryEl.value),
        publisherID: parseInt(publisherEl.value),
        imageUrl: generatedCoverUrl || "" // Aici se trimite link-ul de la API
    };

    console.log("Se trimite cartea cu imagine către server:", book);

    try {
        const response = await fetch("/books", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(book)
        });

        if (response.status === 403) {
            throw new Error("403 Forbidden - Verifică SecurityConfig.java!");
        }

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || "Eroare la salvare.");
        }

        alert("Cartea a fost adăugată cu succes!");
        window.location.href = "/librarian-ControlPanel.html";

    } catch (err) {
        console.error("Eroare la fetch:", err);
        alert("Eroare: " + err.message);
    }
});