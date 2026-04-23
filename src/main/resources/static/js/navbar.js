document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("navbar");

    fetch("/navbar.html")
        .then(res => res.text())
        .then(html => {
            container.innerHTML = html;
        })
        .catch(err => console.error(err));
});