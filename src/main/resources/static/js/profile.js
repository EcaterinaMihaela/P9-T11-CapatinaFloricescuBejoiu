const isLibrarianPage = document.title.toLowerCase().includes("librarian");
const apiEndpoint = isLibrarianPage ? "/api/librarian-profile" : "/api/member-profile";

async function loadData() {
    try {
        const response = await fetch(`${apiEndpoint}/${userId}`);
        if (!response.ok) throw new Error("User not found");

        const user = await response.json();

        const fields = {
            'profileName': user.name,
            'profileEmail': user.email,
            'profilePhone': user.phone,
            'profileAddress': user.address
        };

        for (const [id, value] of Object.entries(fields)) {
            const el = document.getElementById(id);
            if (el) el.value = value || "";
        }

        const statusEl = document.getElementById('profileStatus');
        if (statusEl) statusEl.innerText = user.membershipStatus || "Active";

        const welcomeText = document.querySelector('.welcome-msg') || document.querySelector('.welcome-text');
        if (welcomeText) welcomeText.innerText = `Welcome, ${user.name || 'User'}`;

    } catch (error) {
        console.error("Error loading profile:", error);
    }
}

async function saveProfile() {
    const updatedData = {
        name: document.getElementById('profileName')?.value,
        email: document.getElementById('profileEmail')?.value,
        phone: document.getElementById('profilePhone')?.value
    };

    const addressField = document.getElementById('profileAddress');
    if (addressField) {
        updatedData.address = addressField.value;
    }

    try {
        const response = await fetch(`${apiEndpoint}/${userId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(updatedData)
        });

        if (response.ok) {
            alert("Profile saved successfully!");
            loadData();
        } else {
            alert("Failed to save profile.");
        }
    } catch (error) {
        console.error("Error saving:", error);
        alert("A connection error occurred.");
    }
}

document.addEventListener('DOMContentLoaded', loadData);