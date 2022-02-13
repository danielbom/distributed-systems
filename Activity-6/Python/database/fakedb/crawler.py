'''

    Script utilizado para garimpar os cursos e disciplinas oferecido
    pela Universidade Tecnológica Federal do Paraná a partir do moodle.
    É necessário ter uma conta e o processo de garimpar é manual. Cada
    curso deve ser colocado manualmente na variável "NOME_CURSO".

    @autors daniel e mara

'''
from time import sleep
from selenium import webdriver
from bs4 import BeautifulSoup


NOME_CURSO="ciência da computação"

options = webdriver.ChromeOptions()
profile = {
    "plugins.plugins_list": [{
        "enabled":  False,
        "name": "Chrome PDF Viewer"
    }],
    "download.default_directory" : "/home/kds/"
}
options.add_experimental_option("prefs", profile)
driver = webdriver.Chrome(chrome_options=options)

URL = 'http://moodle.utfpr.edu.br/'


def login(driver):
    username = input('login: ')
    password = input('senha: ')
    sleep(1)

    username_input = driver.find_element_by_id('username')
    password_input = driver.find_element_by_id('password')

    username_input.send_keys(username)
    password_input.send_keys(password)

    username_input.submit()

def save(text: str):
    with open("page.html", "w") as file:
        file.write(text)

def get_disciplines(driver):
    find_by_css = driver.find_elements_by_css_selector
    URL = "http://moodle.utfpr.edu.br/course/index.php?categoryid=544"
    driver.get(URL)
    sleep(1)

    nome_curso = NOME_CURSO.upper()
    elements = find_by_css(".categoryname")
    curso = [e for e in elements if e.text == nome_curso][0]
    curso.click()
    sleep(1)

    elements = find_by_css(".categoryname")
    periods = [e for e in elements if "período" in e.text]
    for p in periods:
        p.click()
        sleep(1)

    elements = find_by_css(".coursebox")
    print("[")
    for i, e in enumerate(elements):
        try:
            codigo, nome = e.text.split(" - ")
            print(f'\t("{codigo}","{nome}","prof{i}",7),')
        except:
            pass
    print("]")


driver.get(URL)
login(driver)
get_disciplines(driver)
driver.quit()