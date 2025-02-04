import sys
import subprocess

subprocess.run(f"bison -d yacc.y", shell=True, check=True)
subprocess.run(f"flex flex.l", shell=True, check=True)
subprocess.run(f"gcc lex.yy.c yacc.tab.c hash.c", shell=True, check=True)
subprocess.run(f"a.exe cerc.pas", shell=True, check=True)
subprocess.run(f"rm lex.yy.c yacc.tab.c yacc.tab.h a.exe", shell=True, check=True)