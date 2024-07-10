class student:

    def __init__(self,studentID,nume,grup):
        '''
        creeaza un student cu nume,id si grupa
        type studentID: int (id-ul studentului)
        type nume: string (numele studentului)
        type grup: int (grupa studentului)
        '''
        self.__studentID = studentID
        self.__nume = nume
        self.__grup = grup
#---------------------------------------------------------------------------------------------------    
    def get_studentID(self):
        return self.__studentID
    
    def get_nume(self):
        return self.__nume
    
    def get_grup(self):
        return self.__grup
#---------------------------------------------------------------------------------------------------    
    def set_studentID(self,val):
        self.__studentID = val
    
    def set_nume(self,val):
        self.__nume = val
    
    def set_grup(self,val):
        self.__grup = val


class pb_lab:

    def __init__(self,nr_lab_pb,descriere,deadline):
        '''
        creeaza o problema cu nr descriere si deadline
        type nr_lab_pb: int (nr problemei)
        type descriere: string (descrierea problemei)
        type deadline: int (deadline-ul problemei)
        '''        
        self.__nr_lab_pb = nr_lab_pb
        self.__descriere = descriere
        self.__deadline = deadline
#---------------------------------------------------------------------------------------------------
    def get_nr_lab_pb(self):
        return self.__nr_lab_pb

    def get_descriere(self):
        return self.__descriere

    def get_deadline(self):
        return self.__deadline
#---------------------------------------------------------------------------------------------------
    def set_nr_lab_pb(self,val):
        self.__nr_lab_pb = val

    def set_descriere(self,val):
        self.__descriere = val

    def set_deadline(self,val):
        self.__deadline = val


class note_lab:

    def __init__(self,id_student,nr_problema,nota):
        '''
        creeaza un o nota cu id-ul unui student, nr unei probleme si nota
        type id_student: int (id-ul studentului)
        type nr_problema: int (nr problemei)
        type nota: int (nota primita de student pentru problema respectiva)
        '''        
        self.__id_student = id_student
        self.__nr_problema = nr_problema
        self.__nota = nota
        
    def get_id_student(self):
        return self.__id_student
    def get_nr_problema(self):
        return self.__nr_problema
    def get_nota(self):
        return self.__nota

    def set_id_student(self,val):
        self.__id_student = val
    def set_nr_problema(self,val):
        self.__nr_problema = val
    def set_nota(self,val):
        self.__nota = val
