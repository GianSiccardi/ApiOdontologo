function deleteBy(id) {
  const url = '/pacientes/' + id;
  const settings = {
    method: 'DELETE'
  };

  fetch(url, settings)
    .then(() => {
      let row_id = "#tr_" + id;
      document.querySelector(row_id).remove();
      listarPacientes(); // Volver a cargar la lista de odontólogos después de eliminar uno
    })
    .catch(error => {
      console.error('Error al eliminar el pacientes:', error);
    });
}
