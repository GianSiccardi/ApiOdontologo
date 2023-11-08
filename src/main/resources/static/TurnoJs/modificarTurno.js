window.addEventListener('load', function () {
  const formulario = document.querySelector('#update_turno_form');

  formulario.addEventListener('submit', function (event) {
    event.preventDefault();
    const turnoId = document.querySelector('#turno_id').value;

    const formData = {
     id: turnoId,
     paciente: document.querySelector('#paciente').value,
     odontologo: document.querySelector('#odontologo').value,
     fecha: document.querySelector('#fecha').value,
     hora: document.querySelector('#hora').value,
    };

    const url = '/turnos/' + turnoId;
    const settings = {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    };

    fetch(url, settings)
      .then(response => response.json())
      .then(data => {
        console.log('turno actualizado:', data);
        // Actualizar la tabla en el DOM
        updateTableRow(data);

        // Ocultar el formulario de modificación
        document.querySelector('#div_turno_updating').style.display = 'none';
      })
      .catch(error => {
        console.error('Error al actualizar el turno:', error);
      });
  });
});

function updateTableRow(turno) {
  const rowId = 'tr_' + turno.id;
  const existingRow = document.getElementById(rowId);

  if (existingRow) {
    // Actualizar los datos en la fila existente
    existingRow.cells[1].textContent = turno.paciente;
    existingRow.cells[2].textContent = turno.odontologo;
    existingRow.cells[3].textContent = turno.fecha;
    existingRow.cells[4].textContent = turno.hora;

  } else {
    // Crear una nueva fila con los datos actualizados
    const table = document.getElementById('turnoTable');
    const newRow = table.insertRow();
    newRow.id = rowId;

    const deleteButton = '<button id="btn_delete_' + turno.id + '" type="button" onclick="deleteBy(' + turno.id + ')" class="btn btn-danger btn_id">&times;</button>';
    const updateButton = '<button id="btn_update_' + turno.id + '" type="button" onclick="findBy(' + turno.id + ')" class="btn btn-primary btn_id">&times;++' + odontologo.id + '</button>';

    newRow.innerHTML = `
     <td>${turno.id}</td>
    <td>${paciente.nombre} ${paciente.apellido}</td>
    <td>${odontologo.nombre} ${odontologo.apellido}</td>
    <td>${turno.fecha}</td>
    <td>${turno.hora}</td>
      <td>${deleteButton} ${updateButton}</td>
    `;
  }
}

function findBy(id) {
  const url = '/turnos/' + id;
  const settings = {
    method: 'GET',
  };
  fetch(url, settings)
    .then(response => response.json())
    .then(data => {
      console.log('Odontólogo encontrado:', data);
      document.querySelector('#paciente').value = "";
      document.querySelector('#odontologo').value = "";
      document.querySelector('#fecha').value = "";
      document.querySelector('#hora').value = "";

      // Mostrar el formulario de modificación
      document.querySelector('#div_turno_updating').style.display = 'block';
    })
    .catch(error => {
      console.error('Error al buscar el turno:', error);
    });
}
