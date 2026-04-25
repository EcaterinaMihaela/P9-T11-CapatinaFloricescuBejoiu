document.addEventListener("DOMContentLoaded", () => {

    const logoutBtn = document.getElementById("logoutBtn");

    if (logoutBtn) {
        logoutBtn.addEventListener("click", (e) => {
            e.preventDefault();

            // curățăm tot localStorage-ul
            localStorage.clear();

            // (opțional debug)
            console.log("Logged out, storage cleared");

            // redirect
            window.location.href = "/login.html";
        });
    }

});