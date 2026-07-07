
const API = "http://localhost:8080/api";

let authHeader = null;


function headers(conJson) {
    const h = { "Authorization": authHeader };
    if (conJson) h["Content-Type"] = "application/json";
    return h;
}

async function login() {
    const u = document.getElementById("username").value;
    const p = document.getElementById("password").value;
    authHeader = "Basic " + btoa(u + ":" + p);
    try {
        const res = await fetch(API + "/me", { headers: headers(false) });
        if (!res.ok) throw new Error("Credenciales incorrectas");
        const data = await res.json();
        document.getElementById("infoUsuario").innerText = data.username + " " + data.roles;
        document.getElementById("loginBox").style.display = "none";
        document.getElementById("panel").style.display = "block";
        listarMedicos();
        listarPacientes();
    } catch (e) {
        authHeader = null;
        const err = document.getElementById("loginError");
        err.innerText = "Usuario o contrasena incorrectos.";
        err.style.display = "block";
    }
}

function logout() {
    authHeader = null;
    document.getElementById("panel").style.display = "none";
    document.getElementById("loginBox").style.display = "block";
}


async function listarMedicos() {
    const res = await fetch(API + "/medicos", { headers: headers(false) });
    const medicos = await res.json();
    let html = "<tr><th>ID</th><th>Nombre</th><th>Especialidad</th></tr>";
    medicos.forEach(m => {
        const esp = m.perfil ? m.perfil.especialidad : "-";
        html += `<tr><td>${m.id}</td><td>${m.nombre}</td><td>${esp}</td></tr>`;
    });
    document.getElementById("tablaMedicos").innerHTML = html;
}

async function crearMedico() {
    const body = {
        nombre: document.getElementById("mNombre").value,
        perfil: {
            especialidad: document.getElementById("mEspecialidad").value,
            telefono: document.getElementById("mTelefono").value,
            correo: document.getElementById("mCorreo").value
        }
    };
    const res = await fetch(API + "/medicos", {
        method: "POST", headers: headers(true), body: JSON.stringify(body)
    });
    if (res.status === 403) { alert("Solo el rol ADMIN puede crear."); return; }
    if (!res.ok) { alert("Error al crear el medico (revise los campos)."); return; }
    document.getElementById("mNombre").value = "";
    document.getElementById("mEspecialidad").value = "";
    listarMedicos();
}

async function listarPacientes() {
    const res = await fetch(API + "/pacientes", { headers: headers(false) });
    const pacientes = await res.json();
    let html = "<tr><th>ID</th><th>Nombre</th><th>Edad</th></tr>";
    pacientes.forEach(p => {
        html += `<tr><td>${p.id}</td><td>${p.nombre}</td><td>${p.edad}</td></tr>`;
    });
    document.getElementById("tablaPacientes").innerHTML = html;
}

async function crearPaciente() {
    const body = {
        nombre: document.getElementById("pNombre").value,
        edad: parseInt(document.getElementById("pEdad").value)
    };
    const res = await fetch(API + "/pacientes", {
        method: "POST", headers: headers(true), body: JSON.stringify(body)
    });
    if (res.status === 403) { alert("Solo el rol ADMIN puede crear."); return; }
    if (!res.ok) { alert("Error al crear el paciente (revise los campos)."); return; }
    document.getElementById("pNombre").value = "";
    document.getElementById("pEdad").value = "";
    listarPacientes();
}
