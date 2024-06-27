//https://www.youtube.com/watch?v=DkeG4MVeNzI

function initCanvas(){
    var ctx = document.getElementById('my_canvas').getContext('2d');
    var backgroundImage = new Image();
    var naveImage   = new Image(); 
    var enemiespic1  = new Image(); 
    var enemiespic2 = new Image(); 
    var misilImage = new Image();
    var lionArgetina = new Image();

    misilImage.src = "images/huevo.png";
    backgroundImage.src = "images/banderaArgentina.jpg";
    enemiespic1.src     = "images/infeccion.png";
    enemiespic2.src     = "images/infeccion2.png"; 
    naveImage.src = 'images/pavo.png'
    lionArgetina.src = 'images/leonArgentina.jpeg';
    var cW = ctx.canvas.width; 
    var cH = ctx.canvas.height;
    var score = 0;

    var enemyTemplate = function(options){
        return {
            id: options.id || '',
            x: options.x || '',
            y: options.y || '',
            w: options.w || '',
            h: options.h || '',
            image: options.image || enemiespic1
        }
    }

    var enemies = createEnemies();

    function obtenerNombreDeUsuario() {
        var urlParams = new URLSearchParams(window.location.search);
        var usuario = urlParams.get('usuario');
        return usuario;
    }

    function createEnemies() {
        var enemies = [
            new enemyTemplate({id: "enemy1", x: 100, y: -20, w: 50, h: 30 }),
            new enemyTemplate({id: "enemy2", x: 225, y: -20, w: 50, h: 30 }),
            new enemyTemplate({id: "enemy3", x: 350, y: -20, w: 80, h: 30 }),
            new enemyTemplate({id: "enemy4", x:100,  y:-70,  w:80,  h: 30}),
            new enemyTemplate({id: "enemy5", x:225,  y:-70,  w:50,  h: 30}),
            new enemyTemplate({id: "enemy6", x:350,  y:-70,  w:50,  h: 30}),
            new enemyTemplate({id: "enemy7", x:475,  y:-70,  w:50,  h: 30}),
            new enemyTemplate({id: "enemy8", x:600,  y:-70,  w:80,  h: 30}),
            new enemyTemplate({id: "enemy9", x:475,  y:-20,  w:50,  h: 30}),
            new enemyTemplate({id: "enemy10",x: 600, y: -20, w: 50, h: 30}),

            // Segundo grupo de enemigos
            new enemyTemplate({ id: "enemy11", x: 100, y: -220, w: 50, h: 30, image: enemiespic2 }),
            new enemyTemplate({ id: "enemy12", x: 225, y: -220, w: 50, h: 30, image: enemiespic2 }),
            new enemyTemplate({ id: "enemy13", x: 350, y: -220, w: 80, h: 50, image: enemiespic2 }),
            new enemyTemplate({ id: "enemy14", x: 100, y: -270, w: 80, h: 50, image: enemiespic2 }),
            new enemyTemplate({ id: "enemy15", x: 225, y: -270, w: 50, h: 30, image: enemiespic2 }),
            new enemyTemplate({ id: "enemy16", x: 350, y: -270, w: 50, h: 30, image: enemiespic2 }),
            new enemyTemplate({ id: "enemy17", x: 475, y: -270, w: 50, h: 30, image: enemiespic2 }),
            new enemyTemplate({ id: "enemy18", x: 600, y: -270, w: 80, h: 50, image: enemiespic2 }),
            new enemyTemplate({ id: "enemy19", x: 475, y: -200, w: 50, h: 30, image: enemiespic2 }),
            new enemyTemplate({ id: "enemy20", x: 600, y: -200, w: 50, h: 30, image: enemiespic2 })
        ];

        return enemies;
    }

    var renderEnemies = function (enemyList) {
        for (var i = 0; i < enemyList.length; i++) {
            ctx.drawImage(enemyList[i].image, enemyList[i].x, enemyList[i].y += 0.5, enemyList[i].w, enemyList[i].h);
            launcher.hitDetectLowerLevel(enemyList[i]);
        }
    }
    function Launcher(){
        this.y = 500;
        this.x = cW*0.5-25; 
        this.w = 100; 
        this.h = 100;   
        this.direccion; 
        this.bg="white"; 
        this.misiles = [];

         this.gameStatus = {
            over: false, 
            message: "",
            fillStyle: 'red',
            font: 'italic bold 36px Arial, sans-serif',
            fontUser: 'italic bold 20px Arial, sans-serif',
        }

        this.render = function () {
            if(this.direccion == 'left'){
                this.x-=5;
            } else if(this.direccion == 'right'){
                this.x+=5;
            }else if(this.direccion == "downArrow"){
                this.y+=5;
            }else if(this.direccion == "upArrow"){
                this.y-=5;
            }
            ctx.drawImage(backgroundImage, 0, 0, ctx.canvas.width,ctx.canvas.height); 
            ctx.drawImage(naveImage,this.x,this.y, 100, 90); 
            ctx.font = this.gameStatus.fontUser;
            ctx.fillStyle = 'black';
            ctx.fillText(obtenerNombreDeUsuario(), cW - 130, 40);
            ctx.fillText('Score: ' + score, 10, 40);
            

            for(var i=0; i < this.misiles.length; i++){
                var misil = this.misiles[i];
                ctx.drawImage(misilImage, misil.x, misil.y -= 5, misil.w, misil.h);
                this.hitDetect(this.misiles[i],i);
                if(misil.y <= 0){ // If a missile goes past the canvas boundaries, remove it
                    this.misiles.splice(i,1); // splice that missile out of the misiles array
                }
            }
            // This happens if you win
            if (enemies.length == 0) {
                clearInterval(animateInterval); // Stop the game animation loop
                ctx.fillStyle = 'yellow';
                ctx.font = this.gameStatus.font;
                ctx.fillText('You win!', cW * 0.5 - 80, 50);
                var modal = document.getElementById('gameOverModal');
                modal.style.display = 'block';
                document.getElementById('gameFinished').innerHTML = 'GAME FINISHED! <br><br> Score: ' + score;
            }
        }
        // Detectar impacto de bullet (bala)
        this.hitDetect = function (m, mi) {
            for (var i = 0; i < enemies.length; i++) {
                var enemy = enemies[i];
                if(m.x+m.w >= enemy.x && 
                   m.x <= enemy.x+enemy.w && 
                   m.y >= enemy.y && 
                   m.y <= enemy.y+enemy.h){

                    score+=10;
                    this.misiles.splice(this.misiles[mi],1); 
                    enemies.splice(i, 1); 
                    document.querySelector('.barra').innerHTML = "Destroyed "+ enemy.id + " ";
                    cell = document.getElementById(enemy.id);
                    cell.innerHTML = "Death";
                    cell.style.backgroundColor = "red";
                }
            }
        }
        this.hitDetectLowerLevel = function(enemy){
            ctx.fillStyle = this.gameStatus.fillStyle; 
            ctx.font = this.gameStatus.font;

            if(enemy.y > 550){
                this.gameStatus.over = true;
                this.gameStatus.message = 'Infection has passed!';
                ctx.fillText(this.gameStatus.message, cW * 0.5-140, 50);
            }

            if ((enemy.y < this.y + 25 && enemy.y > this.y - 25) &&
                (enemy.x < this.x + 45 && enemy.x > this.x - 45)) { 
                    this.gameStatus.over = true;
                    this.gameStatus.message = 'You Died!'
                    ctx.fillText(this.gameStatus.message, cW * 0.5-90, 50);
            }

            if(this.gameStatus.over === true){  
                clearInterval(animateInterval);          
                var modal = document.getElementById('gameOverModal');
                document.getElementById('gameFinished').innerHTML = 'GAME FINISHED! <br><br> Score: ' + score;
                modal.style.display = 'block';
            }
        }
    }

    function animate(){
        ctx.clearRect(0, 0, cW, cH);
        launcher.render();
        renderEnemies(enemies);
    }

    var launcher = new Launcher();
    var animateInterval = setInterval(animate, 6);  
    var canvas = document.getElementById('my_canvas');

    canvas.addEventListener('mousedown', function(event) {
        var nuevoEnemigo = new enemyTemplate({
            id: "newEnemy",
            x: event.clientX - canvas.getBoundingClientRect().left, 
            y: -20,
            w: 50,
            h: 50,
            image: lionArgetina
        });
        enemies.push(nuevoEnemigo);
    });

    document.getElementById('restartButton').addEventListener('click', function() {
        var modal = document.getElementById('gameOverModal');
        modal.style.display = 'none';

        var celdasSegundaFila = document.querySelectorAll('#table tr:nth-child(2) td');
        celdasSegundaFila.forEach(function(celda) {
            celda.textContent = "Alive";
            celda.style.backgroundColor = "green";
        });
        score = 0;
        document.querySelector('.barra').innerHTML = '';
        restartShowText();
        launcher = new Launcher();
        enemies = createEnemies();
        animateInterval = setInterval(animate, 6);
    });
    
    document.getElementById('historyButton').addEventListener('click', function() {
        var modal = document.getElementById('gameOverModal');
        modal.style.display = 'none';
        var table = document.getElementById('tableModal');
        table.style.display = 'block';
    });


    document.addEventListener('keydown', function(event) {
        switch (event.keyCode) {
            case 37: // Izquierda
                launcher.direccion = 'left';  
                if(launcher.x < cW*0.2-130){
                    launcher.x+=0;
                    launcher.direccion = '';
                }
                break;
            case 39: // Derecha
                launcher.direccion = 'right';
                if(launcher.x > cW-110){
                launcher.x-=0;
                launcher.direccion = '';
                }
                break;
            case 38: // Arriba
                launcher.direccion = 'upArrow';  
                if(launcher.y < cH*0.2-80){
                    launcher.y += 0;
                    launcher.direccion = '';
                }
                break;
            case 40: // Abajo
                launcher.direccion = 'downArrow';  
                if(launcher.y > cH - 110){
                    launcher.y -= 0;
                    launcher.direccion = '';
                }
                break;
            case 80: // Reiniciar juego (tecla 'P')
                location.reload();
                break;
            case 32: // Disparar misil (tecla de espacio)
                launcher.misiles.push({ x: launcher.x + launcher.w * 0.25, y: launcher.y, w: 50, h: 50 });
                break;
        }
    });
    
    document.addEventListener('keyup', function(event) {
        switch (event.keyCode) {
            case 37: // Izquierda
                launcher.x+=0;
                launcher.direccion = '';
                break;
            case 39: // Derecha
                launcher.x-=0;   
                launcher.direccion = '';
                break;
            case 38: // Arriba
                launcher.y -= 0;
                launcher.direccion = '';
                break;
            case 40: // Abajo
                launcher.y += 0;
                launcher.direccion = '';
                break;
        }
    }); 

}
function showText() {
    var dropdownContent = document.getElementById("dropdownContent");
    dropdownContent.style.display = (dropdownContent.style.display === "block") ? "none" : "block";
}
function restartShowText() {
    var dropdownContent = document.getElementById("dropdownContent");
    dropdownContent.style.display = 'none';
}

window.addEventListener('load', function(event) {
    initCanvas();
});
