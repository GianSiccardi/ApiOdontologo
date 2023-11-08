window.addEventListener('load', function () {
  const formulario = document.querySelector('#update_odontologo_form');

  formulario.addEventListener('submit', function (event) {
    event.preventDefault();
    const odontologoId = document.querySelector('#odontologo_id').value;

    const formData = {
      id: odontologoId,
      nombre: document.querySelector('#nombre').value,
      apellido: document.querySelector('#apellido').value,
      matricula: document.querySelector('#matricula').value,
    };

    const url = '/odontologos/' + odontologoId;
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
        console.log('Odontólogo actualizado:', data);
        // Actualizar la tabla en el DOM
        updateTableRow(data);

        // Ocultar el formulario de modificación
        document.querySelector('#div_odontologo_updating').style.display = 'none';
      })
      .catch(error => {
        console.error('Error al actualizar el odontólogo:', error);
      });
  });
});

function updateTableRow(odontologo) {
  const rowId = 'tr_' + odontologo.id;
  const existingRow = document.getElementById(rowId);

  if (existingRow) {
    // Actualizar los datos en la fila existente
    existingRow.cells[1].textContent = odontologo.nombre;
    existingRow.cells[2].textContent = odontologo.apellido;
  } else {
    // Crear una nueva fila con los datos actualizados
    const table = document.getElementById('odontologoTable');
    const newRow = table.insertRow();
    newRow.id = rowId;

    const deleteButton = '<button id="btn_delete_' + odontologo.id + '" type="button" onclick="deleteBy(' + odontologo.id + ')" class="btn btn-danger btn_id">&times;</button>';
    const updateButton = '<button id="btn_update_' + odontologo.id + '" type="button" onclick="findBy(' + odontologo.id + ')" class="btn btn-primary btn_id">&times;++' + odontologo.id + '</button>';

    newRow.innerHTML = `
      <td>${odontologo.id}</td>
      <td>${odontologo.nombre}</td>
      <td>${odontologo.apellido}</td>
      <td>${deleteButton} ${updateButton}</td>
    `;
  }
}

function findBy(id) {
  const url = '/odontologos/' + id;
  const settings = {
    method: 'GET',
  };
  fetch(url, settings)
    .then(response => response.json())
    .then(data => {
      console.log('Odontólogo encontrado:', data);
      document.querySelector('#odontologo_id').value = data.id;
      document.querySelector('#nombre').value = data.nombre;
      document.querySelector('#apellido').value = data.apellido;
      document.querySelector('#matricula').value = data.matricula;

      // Mostrar el formulario de modificación
      document.querySelector('#div_odontologo_updating').style.display = 'block';
    })
    .catch(error => {
      console.error('Error al buscar el odontólogo:', error);
    });
}
