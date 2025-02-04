import sys
import subprocess

subprocess.run(f"flex main.l", shell=True, check=True)
subprocess.run(f"gcc lex.yy.c hash.c", shell=True, check=True)
subprocess.run(f"a.exe cerc.pas", shell=True, check=True)
subprocess.run(f"rm lex.yy.c a.exe", shell=True, check=True)