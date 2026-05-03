document.addEventListener("DOMContentLoaded", () => {
    // Încărcăm notificările imediat ce se deschide pagina
    loadNotifications();
});

async function loadNotifications() {
    const listElement = document.getElementById("notificationsList");

    try {
        // Pasul 1: Preluăm datele de la Backend (Java)
        const response = await fetch('/api/notifications');

        if (!response.ok) {
            throw new Error("Nu am putut încărca notificările");
        }

        const notifications = await response.json();

        // Pasul 2: Verificăm dacă sunt notificări
        if (notifications.length === 0) {
            listElement.innerHTML = '<p class="text-center text-muted">Nu ai nicio notificare momentan.</p>';
            return;
        }

        // Pasul 3: Generăm HTML-ul pentru fiecare notificare din baza de date
        listElement.innerHTML = notifications.map(notif => `
            <div class="notification-item ${notif.read ? '' : 'unread'}" id="notif-${notif.id}">
                <div class="notif-icon">${notif.icon || '🔔'}</div>
                <div class="notif-content">
                    <h3>${notif.title}</h3>
                    <p>${notif.message}</p>
                    <span class="notif-date">${formatDate(notif.createdAt)}</span>
                </div>
            </div>
        `).join('');

    } catch (error) {
        console.error("Error:", error);
        listElement.innerHTML = '<p class="text-center text-danger">Eroare la încărcarea notificărilor.</p>';
    }
}

// Funcție pentru a marca totul ca citit
async function markAllAsRead() {
    try {
        const response = await fetch('/api/notifications/mark-all-read', {
            method: 'POST'
        });

        if (response.ok) {
            // Dacă serverul a confirmat, scoatem vizual clasa 'unread'
            const unreadItems = document.querySelectorAll('.notification-item.unread');
            unreadItems.forEach(item => item.classList.remove('unread'));
            console.log("Toate notificările au fost marcate ca citite.");
        }
    } catch (error) {
        console.error("Eroare la marcarea ca citit:", error);
    }
}


function formatDate(dateString) {
    const options = { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' };
    return new Date(dateString).toLocaleDateString('ro-RO', options);
}