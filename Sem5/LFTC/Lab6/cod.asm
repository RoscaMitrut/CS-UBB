bits 32
global start

extern exit
import exit msvcrt.dll
extern scanf
import scanf msvcrt.dll
extern printf
import printf msvcrt.dll

segment data use32 class=data
	a		RESD		1
	b		RESD		1
	result		RESD		1
	temp		RESD		1
	str1	DB		'Enter two numbers:', 0
	str2	DB		'%d%d', 0
	str3	DB		'Result is: %d', 0
	tmp		RESD		2

segment data use32 class=code
	start:
	; output statement
		PUSH	DWORD		str1
		CALL	[printf]
		ADD		ESP,		4 * 1

	; input statement
		PUSH	DWORD		b
		PUSH	DWORD		a
		PUSH	DWORD		str2
		CALL	[scanf]
		ADD		ESP,		4 * 3

	; [tmp + 0] = [a] + [b]
		MOV		EAX,		[a]
		ADD		EAX,		[b]
		MOV		[tmp + 0],		EAX
	; [tmp + 4] = [tmp + 0] * 2
		MOV		EBX,		[tmp + 0]
		MOV		EAX,		2
		IMUL	EBX
		MOV		[tmp + 4],		EAX
	; temp = [tmp + 4]
		MOV		EAX,		[tmp + 4]
		MOV		[temp],		EAX

	; result = [temp]
		MOV		EAX,		[temp]
		MOV		[result],		EAX

	; output statement
		PUSH	DWORD		[result]
		PUSH	DWORD		str3
		CALL	[printf]
		ADD		ESP,		4 * 2

	;exit
		PUSH		DWORD		0
		CALL		[exit]
