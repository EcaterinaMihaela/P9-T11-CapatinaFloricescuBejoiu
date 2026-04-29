document.getElementById("registerForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const fullName = document.getElementById("fullName").value;
    const email = document.getElementById("email").value;
    const phone = document.getElementById("phone").value;
    const address = document.getElementById("address").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return;
    }

    const names = fullName.split(" ");

    try {
        const response = await fetch("http://localhost:8080/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password,
                firstName: names[0],
                lastName: names.slice(1).join(" "),
                email: email,
                phoneNumber: phone,
                address: address
            })
        });

        if (!response.ok) {
                    const errorText = await response.text();
                    alert(errorText);
                    return;
                }

        alert("Account created!");

        //redirect
        window.location.href = "/login.html";

    } catch (err) {
          console.error(err);
          alert(err.message);
      }
});