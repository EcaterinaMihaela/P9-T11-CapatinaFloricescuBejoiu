document.addEventListener("DOMContentLoaded", async () => {

    const userId = localStorage.getItem("userId");

    if (!userId) {
        window.location.href = "/login.html";
        return;
    }

    const res = await fetch(`/profiles/user/${userId}`);
    const profile = await res.json();

    document.getElementById("username").textContent = profile.username;
    document.getElementById("role").textContent = profile.role;

    document.getElementById("firstName").value = profile.firstName || "";
    document.getElementById("lastName").value = profile.lastName || "";
    document.getElementById("email").value = profile.email || "";
    document.getElementById("phone").value = profile.phoneNumber || "";
    document.getElementById("address").value = profile.address || "";

    // doar astea sunt editabile
    const editableFields = ["firstName", "lastName", "phone", "address"];

    let editMode = false;

    const editBtn = document.getElementById("editBtn");
    const saveBtn = document.getElementById("saveBtn");

    // initial: save ascuns
    saveBtn.style.display = "none";

    function setEditMode(state) {
        editMode = state;

        editableFields.forEach(id => {
            document.getElementById(id).readOnly = !state;
        });

        saveBtn.style.display = state ? "inline-block" : "none";
        editBtn.textContent = state ? "Cancel" : "Edit Profile";
    }

    // EDIT BUTTON
    editBtn.addEventListener("click", () => {
        setEditMode(!editMode);
    });

    // SAVE BUTTON
    saveBtn.addEventListener("click", async () => {

        const dto = {
            firstName: document.getElementById("firstName").value,
            lastName: document.getElementById("lastName").value,
            phoneNumber: document.getElementById("phone").value,
            address: document.getElementById("address").value
        };

        const response = await fetch(`/profiles/user/${userId}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(dto)
        });

        if (response.ok) {
            alert("Profile updated successfully!");
        } else {
            const errText = await response.text();
            console.log("UPDATE ERROR:", errText);
            alert("Update failed: " + errText);
        }
    });

});