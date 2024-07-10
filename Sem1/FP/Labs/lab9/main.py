from domain.validators import Validatorul
from repository.repo import InMemoryRepository
from service.service import Service
from ui.console import console

"""
from test_main import *

test_add_student()
test_add_pb()
test_get_all_student()
test_get_all_problems()
test_delete_student()
test_delete_problem()
test_get_student_by_id()
test_get_problem_by_number()
test_create_student()
test_create_problem()
test_create_note()
"""

#repo = InMemoryRepository()
repo = InMemoryRepository('studenti.txt','probleme.txt','note.txt')

val = Validatorul()
srv = Service(repo,val)
ui = console(srv)
ui.program_ui()
