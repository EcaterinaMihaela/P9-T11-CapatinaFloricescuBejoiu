function getEmailFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get("email");
}

async function resetPassword() {

    const email = getEmailFromUrl();
    const password = document.getElementById("password").value;
    const confirm = document.getElementById("confirm").value;

    if (!password || !confirm) {
        document.getElementById("message").innerText = "Fill all fields!";
        return;
    }

    if (password !== confirm) {
        document.getElementById("message").innerText = "Passwords do not match!";
        return;
    }

    try {
        const response = await fetch("/auth/reset-password", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        if (response.ok) {
            document.getElementById("message").innerText = "Password changed successfully!";

              setTimeout(() => {
                    window.location.href = "/login.html";
                }, 1000);
        } else {
            const text = await response.text();
            document.getElementById("message").innerText = text;
        }

    } catch (error) {
        document.getElementById("message").innerText = "Server error!";
    }
}