import urllib.request
import os
from collections import namedtuple

DatosMeteo = namedtuple('DatosMeteo', ['nombre', 'url', 'yearInicio', 'yearFinal'])

datos1 = DatosMeteo(nombre="lerin", url="http://meteo.navarra.es/_data/datos_estaciones/estacion_267/datos%20diarios/ler%C3%ADn%20intia_2004.csv", yearInicio="2004", yearFinal="2022")
datos2 = DatosMeteo(nombre="aibar", url="http://meteo.navarra.es/_data/datos_estaciones/estacion_262/datos%20diarios/aibar%20mapama_2004.csv", yearInicio="2004", yearFinal="2022")
datos3 = DatosMeteo(nombre="pamplona", url="http://meteo.navarra.es/_data/datos_estaciones/estacion_455/datos%20diarios/pamplona%20gn_2010.csv", yearInicio="2010", yearFinal="2022")
datos4 = DatosMeteo(nombre="aoiz", url="http://meteo.navarra.es/_data/datos_estaciones/estacion_34/datos%20diarios/aoiz%20gn_2004.csv", yearInicio="2004", yearFinal="2022")
datos5 = DatosMeteo(nombre="tudela", url="http://meteo.navarra.es/_data/datos_estaciones/estacion_36/datos%20diarios/tudela%20(montes%20del%20cierzo)%20gn_2004.csv", yearInicio="2004", yearFinal="2022")
datos6 = DatosMeteo(nombre="estella", url="http://meteo.navarra.es/_data/datos_estaciones/estacion_7/datos%20diarios/estella%20gn_2004.csv", yearInicio="2004", yearFinal="2022")
datos7 = DatosMeteo(nombre="olite", url="http://meteo.navarra.es/_data/datos_estaciones/estacion_257/datos%20diarios/olite%20intia_2004.csv", yearInicio="2004", yearFinal="2022")

# Crear un array que contenga instancias de DatosMeteo
pueblos = [datos1, datos2, datos3, datos4, datos5, datos6, datos7]

for i in range(len(pueblos)):
    directory = pueblos[i].nombre
    if not os.path.exists(directory):
        os.mkdir(directory)
    for year in range(int(pueblos[i].yearInicio), int(pueblos[i].yearFinal) + 1):
        year_str = str(year)
        file = f"{pueblos[i].nombre}/{pueblos[i].nombre}{year_str}.csv"
        url = pueblos[i].url
        if (pueblos[i].nombre == "aibar" and year >= 2019): #el url cambia de aibar de mapama a mapa
            url = "http://meteo.navarra.es/_data/datos_estaciones/estacion_262/datos%20diarios/aibar%20mapa_2019.csv"
        if (pueblos[i].nombre != "pamplona" or year != 2021): #en pamplona no hay datos para 2021
            url = url.replace(pueblos[i].yearInicio, year_str)
        r = urllib.request.urlopen(url)
        with open(file, "wb") as f:
            f.write(r.read())
