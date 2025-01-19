from UI import UI

choice = input("fisier: 0\ncerinta1: 1\ncerinta2: 2\nInput: ")

if choice=="0":
	runner = UI(filename="fisier.txt")
elif choice=="1":
	runner = UI(filename="cerinta1.txt")
elif choice=="2":
	runner = UI(filename="cerinta2.txt")
    
runner.run()