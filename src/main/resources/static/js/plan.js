
import {apiFetch} from "./api.js";

const BASE_URL = `/api/planes`;

// obtenemos todos los planes
export async function getPlanes() {
    return await apiFetch(BASE_URL);
}

// Obtener un plan por ID
export async function getPlanById(id){
    return await apiFetch(BASE_URL);
}

// Crear un nuevo plan
export async function createPlan(planData){
    return await apiFetch(BASE_URL, {
        method: 'POST',
        body: planData,
    });
}

// Actualizar un plan existente
export async function updatePlan(id, planData){
    return await apiFetch(`${BASE_URL}/${id}`, {
        method: "PUT",
        body: planData,
    });
}

// Eliminar un plan
export async function deletePlan(id){
    return await apiFetch(`${BASE_URL}/${id}`, {
        method: "DELETE",
    });
}
