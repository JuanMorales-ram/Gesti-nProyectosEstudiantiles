/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


const BASE = 'http://localhost:8081/GestionProyectosEstudiantiles/api';

function mostrarSeccion(nombre) {
    document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
    document.querySelectorAll('nav button').forEach(b => b.classList.remove('active'));

    document.getElementById('sec-' + nombre).classList.add('active');

    
    const boton = document.querySelector(`nav button[data-seccion="${nombre}"]`);
    if (boton) boton.classList.add('active');

    if (nombre === 'proyectos') cargarSelectDocentes();
    if (nombre === 'actividades') cargarSelectProyectos();
    if (nombre === 'entregables') {
        cargarSelectActividades();
        cargarSelectEstudiantes();
    }

    cargarTabla(nombre);
}

function mostrarMsg(id, texto, tipo) {
    const el = document.getElementById(id);
    el.textContent = texto;
    el.className = 'msg ' + tipo;
    setTimeout(() => el.className = 'msg', 3000);
}

async function apiFetch(url, method = 'GET', body = null) {
    const opts = { method, headers: { 'Content-Type': 'application/json' } };
    if (body) opts.body = JSON.stringify(body);
    const res = await fetch(BASE + url, opts);
    return res.ok ? res.json() : Promise.reject(await res.text());
}

async function crearDocente() {
    const nombre = document.getElementById('doc-nombre').value.trim();
    const correo = document.getElementById('doc-correo').value.trim();
    const area   = document.getElementById('doc-area').value.trim();
    if (!nombre || !correo || !area) { mostrarMsg('msg-docente','Complete todos los campos','error'); return; }
    try {
        await apiFetch('/docentes', 'POST', { nombre, correo, area });
        mostrarMsg('msg-docente', 'Docente registrado correctamente', 'success');
        document.getElementById('doc-nombre').value = '';
        document.getElementById('doc-correo').value = '';
        document.getElementById('doc-area').value = '';
        cargarTabla('docentes');
    } catch(e) { mostrarMsg('msg-docente', 'Error: ' + e, 'error'); }
}

async function eliminarDocente(id) {
    if (!confirm('¿Eliminar este docente?')) return;
    try { await apiFetch('/docentes/' + id, 'DELETE'); cargarTabla('docentes'); }
    catch(e) { alert('Error al eliminar: ' + e); }
}

async function crearEstudiante() {
    const nombre = document.getElementById('est-nombre').value.trim();
    const correo = document.getElementById('est-correo').value.trim();
    const programaAcademico = document.getElementById('est-programa').value.trim();
    if (!nombre || !correo || !programaAcademico) { mostrarMsg('msg-estudiante','Complete todos los campos','error'); return; }
    try {
        await apiFetch('/estudiantes', 'POST', { nombre, correo, programaAcademico });
        mostrarMsg('msg-estudiante', 'Estudiante registrado correctamente', 'success');
        document.getElementById('est-nombre').value = '';
        document.getElementById('est-correo').value = '';
        document.getElementById('est-programa').value = '';
        cargarTabla('estudiantes');
    } catch(e) { mostrarMsg('msg-estudiante', 'Error: ' + e, 'error'); }
}

async function eliminarEstudiante(id) {
    if (!confirm('¿Eliminar este estudiante?')) return;
    try { await apiFetch('/estudiantes/' + id, 'DELETE'); cargarTabla('estudiantes'); }
    catch(e) { alert('Error al eliminar: ' + e); }
}

async function cargarSelectDocentes() {
    const docentes = await apiFetch('/docentes');
    const sel = document.getElementById('proy-docente');
    sel.innerHTML = '<option value="">Seleccione docente...</option>';
    docentes.forEach(d => sel.innerHTML += `<option value="${d.idDocente}">${d.nombre}</option>`);
}

async function crearProyecto() {
    const nombre       = document.getElementById('proy-nombre').value.trim();
    const descripcion  = document.getElementById('proy-desc').value.trim();
    const fechaInicio  = document.getElementById('proy-inicio').value;
    const fechaFin     = document.getElementById('proy-fin').value;
    const idDocente    = document.getElementById('proy-docente').value;
    if (!nombre || !fechaInicio || !fechaFin || !idDocente) { mostrarMsg('msg-proyecto','Complete todos los campos','error'); return; }
    try {
        await apiFetch('/proyectos', 'POST', { nombre, descripcion, fechaInicio, fechaFin, docente: { idDocente: parseInt(idDocente) } });
        mostrarMsg('msg-proyecto', 'Proyecto registrado correctamente', 'success');
        cargarTabla('proyectos');
    } catch(e) { mostrarMsg('msg-proyecto', 'Error: ' + e, 'error'); }
}

async function eliminarProyecto(id) {
    if (!confirm('¿Eliminar este proyecto?')) return;
    try { await apiFetch('/proyectos/' + id, 'DELETE'); cargarTabla('proyectos'); }
    catch(e) { alert('Error al eliminar: ' + e); }
}

async function cargarSelectProyectos() {
    const proyectos = await apiFetch('/proyectos');
    const sel = document.getElementById('act-proyecto');
    sel.innerHTML = '<option value="">Seleccione proyecto...</option>';
    proyectos.forEach(p => sel.innerHTML += `<option value="${p.idProyecto}">${p.nombre}</option>`);
}

async function crearActividad() {
    const nombre       = document.getElementById('act-nombre').value.trim();
    const descripcion  = document.getElementById('act-desc').value.trim();
    const fechaEntrega = document.getElementById('act-fecha').value;
    const idProyecto   = document.getElementById('act-proyecto').value;
    if (!nombre || !fechaEntrega || !idProyecto) { mostrarMsg('msg-actividad','Complete todos los campos','error'); return; }
    try {
        await apiFetch('/actividades', 'POST', { nombre, descripcion, fechaEntrega, proyecto: { idProyecto: parseInt(idProyecto) } });
        mostrarMsg('msg-actividad', 'Actividad registrada correctamente', 'success');
        cargarTabla('actividades');
    } catch(e) { mostrarMsg('msg-actividad', 'Error: ' + e, 'error'); }
}

async function eliminarActividad(id) {
    if (!confirm('¿Eliminar esta actividad?')) return;
    try { await apiFetch('/actividades/' + id, 'DELETE'); cargarTabla('actividades'); }
    catch(e) { alert('Error al eliminar: ' + e); }
}

async function cargarSelectActividades() {
    const actividades = await apiFetch('/actividades');
    const sel = document.getElementById('ent-actividad');
    sel.innerHTML = '<option value="">Seleccione actividad...</option>';
    actividades.forEach(a => sel.innerHTML += `<option value="${a.idActividad}">${a.nombre}</option>`);
}

async function cargarSelectEstudiantes() {
    const estudiantes = await apiFetch('/estudiantes');
    const sel = document.getElementById('ent-estudiante');
    sel.innerHTML = '<option value="">Seleccione estudiante...</option>';
    estudiantes.forEach(e => sel.innerHTML += `<option value="${e.idEstudiante}">${e.nombre}</option>`);
}

async function crearEntregable() {
    const idActividad  = document.getElementById('ent-actividad').value;
    const idEstudiante = document.getElementById('ent-estudiante').value;
    const estado       = document.getElementById('ent-estado').value;
    const fechaSubida  = document.getElementById('ent-fecha').value;
    if (!idActividad || !idEstudiante) { mostrarMsg('msg-entregable','Seleccione actividad y estudiante','error'); return; }
    try {
        await apiFetch('/entregables', 'POST', {
            estado, fechaSubida: fechaSubida || null,
            actividad: { idActividad: parseInt(idActividad) },
            estudiante: { idEstudiante: parseInt(idEstudiante) }
        });
        mostrarMsg('msg-entregable', 'Entregable registrado correctamente', 'success');
        cargarTabla('entregables');
    } catch(e) { mostrarMsg('msg-entregable', 'Error: ' + e, 'error'); }
}

async function eliminarEntregable(id) {
    if (!confirm('¿Eliminar este entregable?')) return;
    try { await apiFetch('/entregables/' + id, 'DELETE'); cargarTabla('entregables'); }
    catch(e) { alert('Error al eliminar: ' + e); }
}

async function cargarTabla(entidad) {
    try {
        const datos = await apiFetch('/' + entidad);
        const tbody = document.getElementById('tabla-' + entidad);
        tbody.innerHTML = '';
        if (datos.length === 0) {
            tbody.innerHTML = '<tr><td colspan="10" style="text-align:center;color:#999">No hay registros</td></tr>';
            return;
        }
        datos.forEach(d => {
            let fila = '';
            if (entidad === 'docentes')
                fila = `<tr><td>${d.idDocente}</td><td>${d.nombre}</td><td>${d.correo}</td><td>${d.area}</td>
                    <td><button class="btn btn-danger" onclick="eliminarDocente(${d.idDocente})">Eliminar</button></td></tr>`;
            else if (entidad === 'estudiantes')
                fila = `<tr><td>${d.idEstudiante}</td><td>${d.nombre}</td><td>${d.correo}</td><td>${d.programaAcademico}</td>
                    <td><button class="btn btn-danger" onclick="eliminarEstudiante(${d.idEstudiante})">Eliminar</button></td></tr>`;
            else if (entidad === 'proyectos')
                fila = `<tr><td>${d.idProyecto}</td><td>${d.nombre}</td><td>${d.descripcion||''}</td>
                    <td>${d.fechaInicio}</td><td>${d.fechaFin}</td><td>${d.docente?.nombre||''}</td>
                    <td><button class="btn btn-danger" onclick="eliminarProyecto(${d.idProyecto})">Eliminar</button></td></tr>`;
            else if (entidad === 'actividades')
                fila = `<tr><td>${d.idActividad}</td><td>${d.nombre}</td><td>${d.descripcion||''}</td>
                    <td>${d.fechaEntrega}</td><td>${d.proyecto?.nombre||''}</td>
                    <td><button class="btn btn-danger" onclick="eliminarActividad(${d.idActividad})">Eliminar</button></td></tr>`;
            else if (entidad === 'entregables')
                fila = `<tr><td>${d.idEntregable}</td><td>${d.actividad?.nombre||''}</td>
                    <td>${d.estudiante?.nombre||''}</td><td>${d.estado}</td><td>${d.fechaSubida||''}</td>
                    <td><button class="btn btn-danger" onclick="eliminarEntregable(${d.idEntregable})">Eliminar</button></td></tr>`;
            tbody.innerHTML += fila;
        });
    } catch(e) { console.error('Error cargando ' + entidad, e); }
}

cargarTabla('docentes');