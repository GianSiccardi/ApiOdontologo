window.addEventListener('load', function() {
  (function() {
    const url = '/odontologos';
    const settings = {
      method: 'GET'
    };

    fetch(url, settings)
      .then(response => {
        console.log('Respuesta de la API:', response);
        return response.json();
      })
      .then(data => {
        for (odontologo of data) {
          var table = document.getElementById("odontologoTable");
          var odontologoRow = table.insertRow();
          let tr_id = 'tr_' + odontologo.id;
          odontologoRow.id = tr_id;

  let deleteButton = '<button ' +
    'id="btn_delete_' + odontologo.id + '" ' +
    'type="button" onclick="deleteBy(' + odontologo.id + ')" class="btn btn-danger btn_id">&times;</button>';

let updateButton = '<button ' +
  'id="btn_update_' + odontologo.id + '" ' +
  'type="button" onclick="findBy(' + odontologo.id + ')" class="btn btn-primary btn_id">&times;++'+ odontologo.id +'</button>';



          odontologoRow.innerHTML = `
            <td>${odontologo.id}</td>
            <td>${odontologo.nombre}</td>
            <td>${odontologo.apellido}</td>
            <td>${deleteButton} ${updateButton}</td>
          `;
        }
      })
      .catch(error => {
        console.error('Error al cargar los odontólogos:', error);
      });
  })();
});

