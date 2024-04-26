import csv
import mysql.connector
from datetime import datetime, timedelta

# Function to get ID_FECHA from LK_FECHA table based on date
def get_id_fecha_from_date(date, cursor):
    query = "SELECT ID_FECHA FROM LK_FECHA WHERE DS_FECHA = %s"
    date = date.replace("/", "-")
    date = datetime.strptime(date, "%d-%m-%Y")
    date = date.strftime("%Y-%m-%d")
    if (date == "24-04-2006"):print(date)
    cursor.execute(query, (date,))
    result = cursor.fetchone()
    return result[0] if result else None

# Function to get ID_COMARCA from LK_COMARCA table based on nombreComarca
def get_id_comarca_from_nombre(nombre_comarca, cursor):
    query = "SELECT ID_COMARCA FROM LK_COMARCA WHERE DS_NOMBRECOMARCA = %s"
    cursor.execute(query, (nombre_comarca,))
    result = cursor.fetchone()
    return result[0] if result else None
    
def comprobarAtributoVacio(atributo):
    if atributo == "":
        return None
    return atributo

# Connect to the database
connection = mysql.connector.connect(
    user='aaee_24', password='aaee_24', host='eim-srv-mysql.lab.unavarra.es',
    database='aaee_24'
)
print("DB connected")

# Create a cursor
cursor = connection.cursor()

comarcas = ["Ribera Alta", "Zona Noroeste", "Pamplona", "Zona Pirineo", "Tudela", "Tierra Estella", "Navarra Media Oriental"]
pueblos = ["lerin", "aibar", "pamplona", "aoiz", "tudela", "estella", "olite"]
start_date = datetime(2004, 1, 1)
end_date = datetime(2022, 12, 31)
current_date = start_date

# Insert data into LK_FECHA table
while current_date <= end_date:
    year = current_date.year
    month = current_date.month
    day = current_date.day
    week_number = current_date.isocalendar()[1]

    insert_query = "INSERT INTO LK_FECHA (DS_FECHA, IND_YEAR, IND_MES, IND_DIA, IND_SEMANA) VALUES (%s, %s, %s, %s, %s);"
    insert_values = (current_date.strftime('%Y-%m-%d'), year, month, day, week_number)
    cursor.execute(insert_query, insert_values)
    current_date += timedelta(days=1)

# Insert data into LK_COMARCA and DT_DATOS tables
for i in range(len(pueblos)):
    cursor.execute("INSERT INTO LK_COMARCA (DS_NOMBRECOMARCA, IND_NOMBREPUEBLO) VALUES (%s, %s);", (comarcas[i], pueblos[i].capitalize()))
    if pueblos[i] == "pamplona":
        initYear = 2010
    else:
        initYear = 2004
    finalYear = 2022
    for year in range(initYear, finalYear + 1):
        year_str = str(year)
        file = pueblos[i] + '/' + pueblos[i] + year_str + '.csv'
        with open(file, 'r', encoding='ISO-8859-1') as csv_file:
            csv_reader = csv.reader(csv_file, delimiter=';')
            line_count = 0
            for row in csv_reader:
                if line_count != 0:  # cabecera line_count 0
                    characters = " 00:00:00"
                    row[0] = row[0].replace(characters, "")
                    fecha = row[0]
                    id_fecha = get_id_fecha_from_date(fecha, cursor)
                    nombre_comarca = comarcas[i]
                    id_comarca = get_id_comarca_from_nombre(nombre_comarca, cursor)
                    insert_query = "INSERT INTO DT_DATOS (ID_FECHA, ID_COMARCA, IND_PRECIPITACION, IND_TEMPERATURAMAXIMA, IND_TEMPERATURAMINIMA, IND_TEMPERATURAMEDIA) VALUES (%s, %s, %s, %s, %s, %s);"
                    insert_values = (id_fecha, id_comarca, row[7], row[1], row[3], row[2])
                    cursor.execute(insert_query, insert_values)
                else:  # cabecera
                    line_count = 1
        cadena_texto = "Insertado file {}."
        archivo = pueblos[i] + year_str + '.csv'
        print(cadena_texto.format(archivo))

connection.commit()
connection.close()
