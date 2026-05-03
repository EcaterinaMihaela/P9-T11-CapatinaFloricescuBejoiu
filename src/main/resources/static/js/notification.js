// 1. Îi spunem browserului să încarce notificările imediat ce se deschide pagina
document.addEventListener("DOMContentLoaded", () => {
    loadNotifications();
    updateNotificationBadge(); // Actualizează numărul de pe clopoțel dacă ai unul
});

async function loadNotifications() {
    const listElement = document.getElementById("notificationsList");
    if (!listElement) return;

    const currentUsername = localStorage.getItem("username");

    if (!currentUsername) {
        listElement.innerHTML = '<p class="text-center">Please login.</p>';
        return;
    }

    try {
        const response = await fetch(`/notifications/my-notifications?username=${currentUsername}`);

        if (!response.ok) {
            throw new Error(`Server error: ${response.status}`);
        }

        const notifications = await response.json();

        if (notifications.length === 0) {
            listElement.innerHTML = '<p class="text-center text-muted">You have no notifications at the moment.</p>';
            return;
        }

        listElement.innerHTML = notifications.map(notif => {
            // Formatare sigură pentru dată și oră (gestionăm Array-ul trimis de Spring)
            const d = notif.sendingDate;
            const t = notif.sendingTime;
            const dateStr = Array.isArray(d) ? `${d[0]}-${d[1]}-${d[2]}` : d;
            const timeStr = Array.isArray(t) ? `${t[0]}:${t[1]}` : t;

            return `
                <div class="notification-item ${notif.isRead ? '' : 'unread'}" id="notif-${notif.notificationID}">
                    <div class="notif-icon">🔔</div>
                    <div class="notif-content">
                        <h3>${notif.type}</h3>
                        <p>${notif.message}</p>
                        <span class="notif-date">${dateStr} ${timeStr}</span>
                    </div>
                </div>
            `;
        }).join('');

    } catch (error) {
        console.error("Error details:", error);
        listElement.innerHTML = `<p class="text-center text-danger">Could not load data.</p>`;
    }
}

// 2. Funcția pentru butonul "Mark all as read"
async function markAllAsRead() {
    const currentUsername = localStorage.getItem("username");

    try {
        const response = await fetch(`/notifications/mark-all-read?username=${currentUsername}`, {
            method: 'POST'
        });

        if (response.ok) {
            // Vizual: eliminăm clasa 'unread' de pe toate elementele din pagină
            const unreadItems = document.querySelectorAll('.notification-item.unread');
            unreadItems.forEach(item => item.classList.remove('unread'));

            // Ascundem și badge-ul (clopoțelul)
            const badge = document.getElementById("notification-badge");
            if (badge) badge.style.display = "none";
        }
    } catch (error) {
        console.error("Error at mark as read:", error);
    }
}

// 3. Funcția care actualizează numărul de pe clopoțel
async function updateNotificationBadge() {
    const username = localStorage.getItem("username");
    if (!username) return;

    try {
        const response = await fetch(`/notifications/my-notifications?username=${username}`);
        const notifications = await response.json();

        // Numărăm notificările care au isRead = false
        const unreadCount = notifications.filter(n => !n.isRead).length;

        const badge = document.getElementById("notification-badge");
        if (badge) {
            if (unreadCount > 0) {
                badge.innerText = unreadCount;
                badge.style.display = "block";
            } else {
                badge.style.display = "none";
            }
        }
    } catch (e) {
        console.log("Badge update failed");
    }
}