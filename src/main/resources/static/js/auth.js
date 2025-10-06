async function login(username, password){
    const res = await fetch("/auth/login", {
        method: "POST",
        headers: { "Content-type": "application/json" },
        body: JSON.stringify({ username, password })
    });

    if (res-ok){
       const data =await res.json();
       localStorage.setItem("jwtToken", data.token);
       window.location.href = "dashboard.html";
    } else {
        alert("Credenciales inválidas");
    }
}