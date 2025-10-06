import { apiFetch } from './api.js';

export async function cargarMetas(){
    const metas = await apiFetch("/api/metas/listar");
    const  tabla = document.getElementById("tablaMetas");
    tabla.innerHTML = "";
    metas.forEach(meta => {
        tabla.innerHTML += `
            <tr>
                <td>${meta.nombre}</td>
                <td>${meta.estado}</td>
                <td>${meta.nombrePlan || "Sin plan"}</td>
                <td>
                    <button onclick="editarMeta(${meta.id})">Editar</button>
                    <button onclick="eliminarMeta(${meta.id})">Eliminar</button>                    
                </td>
            </tr>
        `;
    });
}

export async function crearMeta(){
    await apiFetch("/api/metas", "POST", meta);
    alert("Meta creada de forma correcta");
    document.getElementById("crearMetaForm").reset();
    bootstrap.Modal.getInstance(document.getElementById("crearMetaModal")).hide();
    cargarMetas();
}

export async function editarMeta(id) {
    const  meta = await apiFetch(`/api/metas/${id}`);
    document.getElementById("editarIdMeta").value = meta.id;
    document.getElementById("editarNombreMeta").value = meta.nombre;
    document.getElementById("editarDescriptionMeta").value = meta.descripcion;
    document.getElementById("editarResponsableMeta").value = meta.responsable;
    document.getElementById("editarEstado").value = meta.estado;
    document.getElementById("editarIdPlan").value = meta.planId;

    const modal = new bootstrap.Modal(document.getElementById("editarMetaModal"));
    modal.show();
}

export async function actualizarMeta(){
    const id = document.getElementById("editarMetaId").value;
    const meta = {
        nombre: document.getElementById("EditarNombre").value,
        descripcion: document.getElementById("editarDescripcion").value,
        responsable: document.getElementById("editarResponsable").value,
        estado: document.getElementById("editarEstado").value,
        planId:document.getElementById("editarPlanId").value,
    };

    await apiFetch(`/api/metas/${id}`, "PUT", meta);
    alert("Meta actualizada de forma correcta");
    document.getElementById("editarMetaForm").reset();
    bootstrap.Modal.getInstance(document.getElementById("editarMetaModal")).hide();
    cargarMetas();
}

export async function eliminarMeta(id){
    if (!confirm("¿Está seguro que desea eliminar esta meta?")) return;
    await apiFetch(`/api/metas/${id}`, "DELETE");
    cargarMetas();
}