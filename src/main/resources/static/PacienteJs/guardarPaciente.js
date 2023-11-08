window.addEventListener('load', function () {





   const formulario = document.querySelector('#add_new_paciente');


   formulario.addEventListener('submit', function (event) {



       const formData = {

           nombre: document.querySelector('#nombre').value,

          apellido: document.querySelector('#apellido').value,

         fechaIngreso: document.querySelector('#fechaIngreso').value,

         dni: document.querySelector('#dni').value,

         mail: document.querySelector('#mail').value,


       };



       const url = '/pacientes';

       const settings = {

           method: 'POST',

           headers: {

               'Content-Type': 'application/json',

           },

           body: JSON.stringify(formData)

       }



       fetch(url, settings)

           .then(response => response.json())

           .then(data => {

                 console.log('Response:', data);

                let successAlert = '<div class="alert alert-success alert-dismissible">' +

                    '<button type="button" class="close" ' +

                    'data-dismiss="alert">&times;</button>' +

                    '<strong></strong> Paciente guardado </div>'



                document.querySelector('#response').innerHTML = successAlert;

                document.querySelector('#response').style.display = "block";

                resetUploadForm();



           })

           .catch(error => {


                    console.error('Error:', error);
                   let errorAlert = '<div class="alert alert-danger alert-dismissible">' +

                                    '<button type="button" class="close"' +

                                    'data-dismiss="alert">&times;</button>' +

                                    '<strong> Error intente nuevamente</strong> </div>'



                     document.querySelector('#response').innerHTML = errorAlert;

                     document.querySelector('#response').style.display = "block";

                    //se dejan todos los campos vacíos

                    //por si se quiere ingresar otra película

                    resetUploadForm();})

   });





   function resetUploadForm(){

       document.querySelector('#nombre').value = "";

       document.querySelector('#apellido').value = "";

       document.querySelector('#fechaIngreso').value = "";

       document.querySelector('#dni').value = "";

       document.querySelector('#mail').value = "";

   }
   })