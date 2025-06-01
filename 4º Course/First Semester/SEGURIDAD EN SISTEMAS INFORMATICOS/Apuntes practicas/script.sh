#!/bin/bash

# Archivo de diccionario con posibles entradas
diccionario="lista.txt"

# URL base del servidor
url_base="http://localhost:5000"

# Intervalo de tiempo entre cada solicitud (en segundos)
intervalo=31

# Leer cada línea del archivo de diccionario
while IFS= read -r linea; do
    # Construir la URL con el valor de TEST desde la línea del diccionario
    url="${url_base}/${linea}"

    # Ejecutar la solicitud CURL con un límite de tiempo de 30 segundos
    echo "Probando URL: $url"
    curl --max-time 31 "$url"

    # Esperar antes de la siguiente solicitud para no sobrecargar el servidor
    echo "Esperando ${intervalo} segundos antes de la siguiente solicitud..."
    sleep "$intervalo"
done < "$diccionario"
