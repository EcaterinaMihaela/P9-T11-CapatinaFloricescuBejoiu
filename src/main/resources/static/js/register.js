document.getElementById("registerForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const fullName = document.getElementById("fullName").value;
    const email = document.getElementById("email").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return;
    }

    const names = fullName.split(" ");

    try {
        const response = await fetch("/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password,
                firstName: names[0],
                lastName: names.slice(1).join(" "),
                email: email
            })
        });

        if (!response.ok) {
            throw new Error("Register failed");
        }

        alert("Account created!");

        //redirect
        window.location.href = "/login.html";

    } catch (err) {
        console.error(err);
        alert("Error creating account");
    }
});