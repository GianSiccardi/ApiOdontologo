window.addEventListener('load', function () {
  const formulario = document.querySelector('#update_paciente_form');

  formulario.addEventListener('submit', function (event) {
    event.preventDefault();
    const pacienteId = document.querySelector('#paciente_id').value;

    const formData = {
      id: pacienteId,
      nombre: document.querySelector('#nombre').value,
      apellido: document.querySelector('#apellido').value,
      fechaIngreso: document.querySelector('#fechaIngreso').value,
      dni: document.querySelector('#dni').value,
      email: document.querySelector('#mail').value,
    };

    const url = '/pacientes/' + pacienteId;
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
        console.log('Paciente actualizado:', data);
        // Actualizar la tabla en el DOM
        updateTableRow(data);

        // Ocultar el formulario de modificación
        document.querySelector('#div_paciente_updating').style.display = 'none';
      })
      .catch(error => {
        console.error('Error al actualizar el paciente:', error);
      });
  });

  function findBy1(id) {
     const url = '/pacientes/' + id;
     const settings = {
       method: 'GET',
     };
     fetch(url, settings)
       .then(response => response.json())
       .then(data => {
         console.log('Paciente encontrado:', data);
         document.querySelector('#paciente_id').value = data.id;
         document.querySelector('#nombre').value = data.nombre;
         document.querySelector('#apellido').value = data.apellido;
         document.querySelector('#fechaIngreso').value = data.fechaIngreso;
         document.querySelector('#dni').value = data.dni;
         document.querySelector('#mail').value = data.mail;

         // Mostrar el formulario de modificación
         document.querySelector('#div_paciente_updating').style.display = 'block';
       })
       .catch(error => {
         console.error('Error al buscar el paciente:', error);
       });
   }

  function updateTableRow(paciente) {
    const rowId = 'tr_' + paciente.id;
    const existingRow = document.getElementById(rowId);

    if (existingRow) {
      // Actualizar los datos en la fila existente
      existingRow.cells[1].textContent = paciente.nombre;
      existingRow.cells[2].textContent = paciente.apellido;
      existingRow.cells[3].textContent = paciente.fechaIngreso;
    } else {
      // Crear una nueva fila con los datos actualizados
      const table = document.getElementById('pacienteTable');
      const newRow = table.insertRow();
      newRow.id = rowId;

      const deleteButton = '<button id="btn_delete_' + paciente.id + '" type="button" onclick="deleteBy(' + paciente.id + ')" class="btn btn-danger btn_id">&times;</button>';
      const updateButton = '<button id="btn_update_' + paciente.id + '" type="button" onclick="findBy(' + paciente.id + ')" class="btn btn-primary btn_id">&times;++' + paciente.id + '</button>';

      newRow.innerHTML = `
        <td>${paciente.id}</td>
        <td>${paciente.nombre}</td>
        <td>${paciente.apellido}</td>
        <td>${paciente.fechaIngreso}</td>
        <td>${deleteButton} ${updateButton}</td>
      `;
    }
  }
function findBy1(id) {
     const url = '/pacientes/' + id;
     const settings = {
       method: 'GET',
     };
     fetch(url, settings)
       .then(response => response.json())
       .then(data => {
         console.log('Paciente encontrado:', data);
         document.querySelector('#paciente_id').value = data.id;
         document.querySelector('#nombre').value = data.nombre;
         document.querySelector('#apellido').value = data.apellido;
         document.querySelector('#fechaIngreso').value = data.fechaIngreso;
         document.querySelector('#dni').value = data.dni;
         document.querySelector('#mail').value = data.mail;

         // Mostrar el formulario de modificación
         document.querySelector('#div_paciente_updating').style.display = 'block';
       })
       .catch(error => {
         console.error('Error al buscar el paciente:', error);
       });
   }


});
