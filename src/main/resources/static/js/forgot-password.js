document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("forgotForm");

    form.addEventListener("submit", async function (e) {
        e.preventDefault();

        const emailInput = document.getElementById("email");
        const email = emailInput.value.trim();

        // validare simplă
        if (!email) {
            alert("Please enter your email");
            return;
        }

        try {
            const response = await fetch("/auth/forgot-password", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ email: email })
            });

            const text = await response.text();

            if (response.ok) {
                alert("Reset code sent to your email!");
                emailInput.value = ""; // curăță inputul
            } else {
                alert(text); // ex: Email not found
            }

        } catch (error) {
            console.error("Error:", error);
            alert("Server error. Try again later.");
        }
    });

});