function comprobarDatos() {
    var usuario = document.getElementById('usuario').value;
    if (usuario.trim() == '') {
        alert('RELLENA TODOS LOS CAMPOS');  
    } else {
        window.location.href = 'game.html?usuario=' + encodeURIComponent(usuario);
    }
}
