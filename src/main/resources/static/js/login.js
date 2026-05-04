console.log("login.js loaded");

document.addEventListener("DOMContentLoaded", () => {

    const form = document.getElementById("loginForm");

    form.onsubmit = async function (e) {
        e.preventDefault();

        console.log("submit triggered");

        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        try {
            const response = await fetch("http://localhost:8080/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ username, password })
            });

            if (!response.ok) {
                alert("Invalid username or password");
                return;
            }

            const data = await response.json();

            console.log("response:", data);

            if (!data || !data.username || !data.role || !data.userId) {
                alert("Invalid credentials");
                return;
            }

            if (data.username !== username) {
                alert("Wrong credentials");
                return;
            }

            sessionStorage.setItem("role", data.role);
            sessionStorage.setItem("userId", data.userId);
            sessionStorage.setItem("username", data.username);
            sessionStorage.setItem("email", data.email);
            sessionStorage.setItem("memberId", data.memberId);

            window.location.replace("/dashboard.html");

        } catch (err) {
            console.error("Login error:", err);
            alert("Server error");
        }
    };

});