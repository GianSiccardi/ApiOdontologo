window.addEventListener('load', function() {
  (function() {
    const url = '/pacientes';
    const settings = {
      method: 'GET'
    };

    fetch(url, settings)
      .then(response => {
        console.log('Respuesta de la API:', response);
        return response.json();
      })
      .then(data => {
        for (paciente of data) {
          var table = document.getElementById("pacienteTable");
          var pacienteRow = table.insertRow();
          let tr_id = 'tr_' + paciente.id;
          pacienteRow.id = tr_id;

  let deleteButton = '<button ' +
    'id="btn_delete_' + paciente.id + '" ' +
    'type="button" onclick="deleteBy(' + paciente.id + ')" class="btn btn-danger btn_id">&times;</button>';

let updateButton = '<button ' +
  'id="btn_update_' + paciente.id + '" ' +
  'type="button" onclick=findBy1(' + paciente.id + ') class="btn btn-primary btn_id">&times;'+ paciente.id+'</button>';



         pacienteRow.innerHTML = `
            <td>${paciente.id}</td>
            <td>${paciente.nombre}</td>
            <td>${paciente.apellido}</td>
            <td>${paciente.fechaIngreso}</td>
            <td>${deleteButton} ${updateButton}</td>
          `;
        }
      })
      .catch(error => {
        console.error('Error al cargar los odontólogos:', error);
      });
  })();
});