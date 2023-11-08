function deleteBy(turnoId) {
   const url = '/turnos/' + turnoId;
   const settings = {
      method: 'DELETE'
   };

   fetch(url, settings)
      .then(response => {
         console.log('Respuesta de la API:', response);
         if (response.ok) {
            const turnoRow = document.getElementById('tr_' + turnoId);
            if (turnoRow) {
               turnoRow.remove(); // Eliminar el elemento de la tabla
               console.log('Turno eliminado correctamente');
            } else {
               console.error('No se encontró el elemento de la tabla');
            }
         } else {
            console.error('Error al eliminar el turno');
         }
      })
      .catch(error => {
         console.error('Error al eliminar el turno:', error);
      });
}




