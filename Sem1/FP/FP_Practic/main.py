from service.service import serv
from domain.entities import tractor
from repo.repo import repo
from console.ui import ui
import datetime

nume_fisier = input("Introduceti nume fisier (nu introduceti nimic pentru fisier default): ")
if nume_fisier[-4:] != ".txt":
	nume_fisier = "fisier.txt"

repo = repo(nume_fisier)
service = serv(repo)
ui = ui(service)

ui.afisare()