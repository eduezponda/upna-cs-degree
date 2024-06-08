function comprobarDatos() {
    var usuario = document.getElementById('usuario').value;
    var personajeSeleccionado = document.querySelector('input[name="personaje"]:checked');
    if (usuario.trim() == '' || personajeSeleccionado == null){
        alert('RELLENA TODOS LOS CAMPOS');  
    }
    else{
        // Tu código en script1.js

        // Crear el elemento script
        var script = document.createElement('script');

        // Establecer el atributo src con el nombre del archivo
        script.src = 'script.js';

        // Establecer atributos de datos para pasar parámetros
        script.setAttribute('usuario', usuario);
        script.setAttribute('personaje', personajeSeleccionado);

        // Agregar el elemento script al encabezado del documento
        document.head.appendChild(script);
        script.onload = function() {
            iniciarJuego(usuario, personajeSeleccionado);
        };
    }
}
