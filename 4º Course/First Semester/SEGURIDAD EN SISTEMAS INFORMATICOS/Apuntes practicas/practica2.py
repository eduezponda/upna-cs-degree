import subprocess
import base64
import hashlib
import re
from Cryptodome.Cipher import AES, DES
import tempfile

def encrypt(algoritmo, value):
    hash_alg = getattr(hashlib,algoritmo)(value.encode()).hexdigest()
    return hash_alg

def simetric(algoritmo, key, value):

    command = f"echo '{value}' | openssl enc -base64 -d | openssl enc -{algoritmo} -d -pass pass:{key}"
    process = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stdout, stderr = process.communicate()

    if process.returncode == 0:
        print("Decryption successful:")
        return stdout.decode("utf-8")
    else:
        print("Decryption failed:")
        return stderr.decode("utf-8")
    
def asimetric(value, private_key):

    with tempfile.NamedTemporaryFile(mode="w") as tmp_file:
        tmp_file.write(private_key)
        tmp_file.flush()

        command = f"echo '{value}' | openssl enc -base64 -d | openssl pkeyutl -decrypt -inkey {tmp_file.name}"
        process = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        stdout, stderr = process.communicate()

        if process.returncode == 0:
            print("Decryption successful:")
            return stdout.decode("utf-8")
        else:
            print("Decryption failed:")
            return stderr.decode("utf-8")


# Función para descifrar la contraseña en base64
def decode_base64(encoded_password):
    decoded_password = base64.b64decode(encoded_password).decode('utf-8').strip()
    return decoded_password



# Comando para conectarse al servidor
command = ["nc", "10.42.0.116", "54471"]

pattern = r"hash (\w+) of the last inserted password"
pattern2 = r"(\w+(-\w+)*) cipher algorithm"
pattern3 = r"key (\w+)"
pattern4 = r"(\w+(-\w+)*) symetric cipher algorithm"

password = ''

try:
    # Ejecutar el comando en un shell interactivo
    process = subprocess.Popen(command, stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)

    # Leer la salida del comando en tiempo real
    while True:
        output = process.stdout.readline()
        if output == b'' and process.poll() is not None:
            break
        if output:
            decoded_output = output.decode().strip()
            print(decoded_output)
            
            # Buscar la línea que contiene el string codificado en base64
            if "# Base 64 #" in decoded_output:
                # Leer la siguiente línea que contiene la contraseña codificada
                encoded_password = process.stdout.readline().decode().strip()
                print(f"Contraseña codificada: {encoded_password}")

                # Decodificar la contraseña
                password = decode_base64(encoded_password)
                print(f"Contraseña descodificada: {password}")

                # Enviar la contraseña al servidor
                process.stdin.write(f"{password}\n".encode())
                process.stdin.flush()
                
            elif re.search(pattern, decoded_output):
                # Leer la siguiente línea que contiene la contraseña codificada
                algoritmo = re.search(pattern, decoded_output)
                process.stdout.readline().decode().strip()

                # Decodificar la contraseña
                print(password)
                password = encrypt(algoritmo.group(1), password)
                print(f"Contraseña descodificada: {password}")

                # Enviar la contraseña al servidor
                process.stdin.write(f"{password}\n".encode())
                process.stdin.flush()
                
            elif re.search(pattern2, decoded_output):
                # Leer la siguiente línea que contiene la contraseña codificada
                algoritmo = re.search(pattern2, decoded_output).group(1)
                
                decoded_output = process.stdout.readline().decode().strip()
                
                key = re.search(pattern3, decoded_output).group(1)
                print(algoritmo)
                print(key)
                
                linea = process.stdout.readline().decode().strip() #ignorar Base 64
                encoded_password = process.stdout.readline().decode().strip()
                
                password = simetric(algoritmo, key, encoded_password)
                print(password)

                # Decodificar la contraseña
                print(f"Contraseña descodificada: {password}")

                # Enviar la contraseña al servidor
                process.stdin.write(f"{password}\n".encode())
                process.stdin.flush()
            elif "# key1.priv #" == decoded_output:
                private_key = process.stdout.readline().decode().strip()+"\n"
                string = ""
                while (string := process.stdout.readline().decode().strip()) != "-----END PRIVATE KEY-----":
                    private_key += string
                private_key += "\n"+string
                print(private_key)
                if (algorithm := re.search(pattern4,process.stdout.readline().decode().strip())):
                    algorithm = algorithm.group(1)
                    process.stdout.readline().decode().strip()
                    key = process.stdout.readline().decode().strip()
                    print(key)
                    
                    key2 = asimetric(key, private_key)
                    process.stdout.readline().decode().strip()
                    encoded_password =  process.stdout.readline().decode().strip()
                    
                    password = simetric(algoritmo, key2, encoded_password)
                    
                    print(f"Contraseña descodificada: {password}")

                    # Enviar la contraseña al servidor
                    process.stdin.write(f"{password}\n".encode())
                    process.stdin.flush()
                    
                    print(process.stdout.readline().decode().strip())
                    print(process.stdout.readline().decode().strip())
                    print(process.stdout.readline().decode().strip())
                    print(process.stdout.readline().decode().strip())
                    print(process.stdout.readline().decode().strip())
                    print(process.stdout.readline().decode().strip())
                    print(process.stdout.readline().decode().strip())
                    
                    
                else:
                    decoded_output = process.stdout.readline().decode().strip()
                    print(decoded_output)
                    string = process.stdout.readline().decode().strip()
                    print(string)
                    
                    password = asimetric(decoded_output, private_key)

                    # Decodificar la contraseña
                    print(f"Contraseña descodificada: {password}")

                    # Enviar la contraseña al servidor
                    process.stdin.write(f"{password}\n".encode())
                    process.stdin.flush()
            
            
                
                

    # Obtener cualquier error del comando
    stderr = process.stderr.read().decode()
    if stderr:
        print(f"Error: {stderr}")

except Exception as e:
    print(f"Error al ejecutar el comando: {e}")
    


