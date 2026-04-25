function getEmailFromUrl() {
    const params = new URLSearchParams(window.location.search);
    return params.get("email");
}

document.addEventListener("DOMContentLoaded", function () {

    const email = getEmailFromUrl();


    document.getElementById("email").value = email;
});

async function verifyCode() {

    const email = getEmailFromUrl();
    const code = document.getElementById("code").value;

    if (!code) {
        document.getElementById("message").innerText = "Please enter the code!";
        return;
    }

    try {
        const response = await fetch("/auth/verify-code", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, code })
        });

        if (response.ok) {

            document.getElementById("message").innerText = "Code verified!";


            setTimeout(() => {
                window.location.href = "/reset-password.html?email=" + encodeURIComponent(email);
            }, 500);

        } else {
            const text = await response.text();
            document.getElementById("message").innerText = text;
        }

    } catch (error) {
        document.getElementById("message").innerText = "Server error!";
    }
}