window.addEventListener('load', function () {

   const formulario = document.querySelector('#add_new_turno');

   formulario.addEventListener('submit', function (event) {
       event.preventDefault();
       console.log("se envia correctamente");

       console.log("se envia correctamente");

       const formData = {
           paciente: document.querySelector('#paciente').value,
           odontologo: document.querySelector('#odontologo').value,
           fecha: document.querySelector('#fecha').value,
           hora: document.querySelector('#hora').value,
       };

       console.log('FormData:', formData);

       const url = '/turnos';
       const settings = {
           method: 'POST',
           headers: {
               'Content-Type': 'application/json',
           },
           body: JSON.stringify(formData)
       };

       console.log('Request URL:', url);
       console.log('Request Settings:', settings);

       fetch(url, settings)
           .then(response => {
               console.log('Response:', response);
               return response.json();
           })
           .then(data => {
              console.log('Data:', data);
              let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" ' +
                    'data-dismiss="alert">&times;</button>' +
                    '<strong></strong> Turno guardado </div>';
              document.querySelector('#response').innerHTML = successAlert;
              document.querySelector('#response').style.display = "block";
              resetUploadForm();
           })
           .catch(error => {
              console.error('Error:', error);
              let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                '<button type="button" class="close"' +
                                'data-dismiss="alert">&times;</button>' +
                                '<strong> Error intente nuevamente</strong> </div>';
              document.querySelector('#response').innerHTML = errorAlert;
              document.querySelector('#response').style.display = "block";
              resetUploadForm();
           });

   });

   function resetUploadForm(){
       document.querySelector('#paciente').value = "";
       document.querySelector('#odontologo').value = "";
       document.querySelector('#fecha').value = "";
       document.querySelector('#hora').value = "";
   }
});
