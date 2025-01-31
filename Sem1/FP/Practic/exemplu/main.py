from console.ui import ui
from service.service import service
from repo.repo import repo
from domain.entities import examen
import datetime

repo = repo("fisier.txt")
service = service(repo)
ui = ui(service)
ui.rulare_initiala()
ui.main()