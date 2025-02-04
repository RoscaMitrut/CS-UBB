import sys
import subprocess

subprocess.run(f"bison -d -v yacc.y", shell=True, check=True)
subprocess.run(f"flex flex.l", shell=True, check=True)
subprocess.run(f"gcc lex.yy.c yacc.tab.c", shell=True, check=True)
subprocess.run(f"a.exe cod.pas cod.asm", shell=True, check=True)
subprocess.run(f"rm lex.yy.c yacc.tab.c yacc.tab.h yacc.output a.exe", shell=True, check=True)
