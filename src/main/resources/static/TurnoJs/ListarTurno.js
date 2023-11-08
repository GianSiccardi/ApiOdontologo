// ListarTurno.js

document.addEventListener('DOMContentLoaded', () => {
    const turnoTableBody = document.getElementById('turnoTableBody');

    function listarTurnos() {
        // Llamar a la API para obtener los turnos
        const url='/turnos';
        fetch(url)
            .then(response => response.json())
            .then(data => {
                // Limpiar la tabla antes de agregar los nuevos datos
                turnoTableBody.innerHTML = '';

                // Recorrer los datos de los turnos y agregarlos a la tabla
                data.forEach(turno => {
                    const row = turnoTableBody.insertRow();

                    // Agregar las celdas con los datos del turno
                    row.insertCell().innerText = turno.id;
                    row.insertCell().innerText = turno.paciente;
                    row.insertCell().innerText = turno.odontologo;
                    row.insertCell().innerText = turno.fecha;
                    row.insertCell().innerText = turno.hora;

                    // Agregar el botón "Eliminar" en la última celda de la fila
                    const eliminarButton = document.createElement('button');
                    eliminarButton.innerText = 'Eliminar';
                    eliminarButton.className = 'btn btn-danger';
                    eliminarButton.addEventListener('click', () => {
                        eliminarTurno(turno.id); // Llamar a la función para eliminar el turno
                    });

                    const cell = row.insertCell();
                    cell.appendChild(eliminarButton);
                });
            })
            .catch(error => {
                console.error('Error al obtener los turnos:', error);
            });
    }



    listarTurnos(); // Llamar a la función para listar los turnos al cargar la página

});

