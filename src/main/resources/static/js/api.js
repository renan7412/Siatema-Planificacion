export async  function apiFetch(url, method = "GET", body = null){
    const  token = localStorage.getItem("jwtToken");
    const options = {
        method,
        headers: {
            "Authorization": `Bearer ${token}`,
            "Content-type": "application/json"
        }
    };

    if (body) options.body = JSON.stringify(body);
    const res = await fetch(url, options);
    if (!res.ok) throw new Error("Error de la petición");
    return  await res.json();

}