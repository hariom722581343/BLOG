const loginForm = document.getElementById('login-form');
loginForm.addEventListener('submit', (e) => {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
    .then((res) => res.json())
    .then((data) => {
        localStorage.setItem('token', data.token);
        window.location.href = '/dashboard';
    })
    .catch((err) => console.error(err));
});
