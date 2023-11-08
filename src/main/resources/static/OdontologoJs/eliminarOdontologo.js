function deleteBy(id) {
  const url = '/odontologos/' + id;
  const settings = {
    method: 'DELETE'
  };

  fetch(url, settings)
    .then(() => {
      let row_id = "#tr_" + id;
      document.querySelector(row_id).remove();
      listarOdontologos(); // Volver a cargar la lista de odontólogos después de eliminar uno
    })
    .catch(error => {
      console.error('Error al eliminar el odontólogo:', error);
    });
}
