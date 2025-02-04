import sys
import subprocess

subprocess.run(f"flex flex.l", shell=True, check=True)
subprocess.run(f"gcc lex.yy.c hash.c", shell=True, check=True)
subprocess.run(f"a.exe cerc.pas cerc_temp.txt", shell=True, check=True)
subprocess.run(f"rm lex.yy.c a.exe", shell=True, check=True)