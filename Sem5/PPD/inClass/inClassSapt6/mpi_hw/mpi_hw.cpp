//mpiexec -n 4 mpi_hw.exe
/*
#include "mpi.h"
#include <stdio.h>

int main(int argc, char** argv)
{
	int namelen, myid, numprocs;
	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
		MPI_Comm_rank(MPI_COMM_WORLD, &myid);
		printf("Process %d / %d : Hello world\n", myid, numprocs);
	MPI_Finalize();
	return 0;
}
*/
///////////////////////////////////////////////
/*
#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <string>

using namespace std;

MPI_Request recv_request[10];

int main1(int argc, char** argv) {

    int  namelen, myid, numprocs;
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &myid);
    //printf("Process %d / %d  : Hello world\n", myid, numprocs);

    char* mymessage = new char[50];
    strcpy_s(mymessage, 50, "Hello from ");

    char* sirTotal = new char[50 * numprocs];
    sirTotal[0] = '\0';

    if (myid == 0) {
        strcat_s(mymessage, 50, "0");
        strcat_s(sirTotal, 50, mymessage);
        MPI_Status status;
        for (int i = 1; i < numprocs; i++) {
            MPI_Recv(mymessage, 50, MPI_CHAR, i, 111, MPI_COMM_WORLD, &status);
            //printf("STATUS SOURCE %d\n", status.MPI_SOURCE);
            strcat_s(sirTotal, 50, mymessage);
        }
        printf("%s\n", sirTotal);
    }
    else {
        char* buf = new char[10];
        _itoa_s(myid, buf, 10, 10);
        strcat_s(mymessage, 50, buf);
        MPI_Send(mymessage, strlen(mymessage) + 1, MPI_CHAR, 0, 111, MPI_COMM_WORLD);
    }

    MPI_Finalize();
    return 0;
}
*/
///////////////////////////////////////////////
/*
#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <string>

using namespace std;

MPI_Request recv_request[10];

int main(int argc, char** argv) {

    int namelen, myid, numprocs, n;
    n = 100;
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &myid);
    int cat = n / numprocs;
    if (myid == 0) {
        int* a, * b, * c;
        a = new int[n];
        b = new int[n];
        c = new int[n];
        for (int i = 0;i < n;i++) {
            a[i] = b[i] = i;
        }
        for (int i = 1;i < numprocs;i++) {
            MPI_Send(a + i * cat, cat, MPI_INT, i, 111111, MPI_COMM_WORLD);
            MPI_Send(b + i * cat, cat, MPI_INT, i, 222222, MPI_COMM_WORLD);
        }
        for (int i = 0;i < cat;i++) {
            c[i] = a[i] + b[i];
        }
        MPI_Status status;
        for (int i = 1;i < numprocs;i++) {
            MPI_Recv(c + i * cat, cat, MPI_INT, i, 333333, MPI_COMM_WORLD, &status);
        }
        for (int i = 0; i < n;i++) {
            if (c[i] != 2 * i) {
                cout << "ERROR!" << endl;
            }
        }
    }else {
        int* a, * b, * c;
        a = new int[cat];
        b = new int[cat];
        c = new int[cat];
        MPI_Status status;
        MPI_Recv(a, cat, MPI_INT, 0, 111111, MPI_COMM_WORLD, &status);
        MPI_Recv(b, cat, MPI_INT, 0, 222222, MPI_COMM_WORLD, &status);
        for (int i = 0;i < cat;i++) {
            c[i] = a[i] + b[i];
        }
        MPI_Send(c, cat, MPI_INT, 0, 333333, MPI_COMM_WORLD);
    }
    MPI_Finalize();
    return 0;
}
*/
///////////////////////////////////////////////

#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <iostream>
#include <string>

using namespace std;

MPI_Request recv_request[10];

int main(int argc, char** argv) {

    int id, numprocs, tag=1;
    int size =100;
    MPI_Status status;
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
    MPI_Comm_rank(MPI_COMM_WORLD, &id);

    int chunk_size = size / numprocs;
    
    int* list1, * list2, * list3;
    list1 = (int*)malloc(size * sizeof(int));
    list2 = (int*)malloc(size * sizeof(int));
    list3 = (int*)malloc(size * sizeof(int));

    int* rec_list1, * rec_list2, * rec_list3;
    rec_list1 = (int*)malloc(size * sizeof(int));
    rec_list2 = (int*)malloc(size * sizeof(int));
    rec_list3 = (int*)malloc(size * sizeof(int));

    if (id == 0) {
        for (int i = 0;i < size;i++) {
            list1[i] = i;
            list2[i] = i;
        }
    }
    
    MPI_Scatter(list1, chunk_size, MPI_INT, rec_list1, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Scatter(list2, chunk_size, MPI_INT, rec_list2, chunk_size, MPI_INT, 0, MPI_COMM_WORLD);


    for (int i = 0;i < chunk_size;i++) {
        rec_list3[i] = rec_list1[i] + rec_list2[i];
    }

    MPI_Gather(rec_list3,chunk_size,MPI_INT,list3, chunk_size, MPI_INT,0,MPI_COMM_WORLD);

    if (id == 0) {
        printf("Final results: ");
        for (int i = 0;i < size;i++) {
            if (list3[i] != list1[i] + list2[i]) {
                printf("Error\n");
                break;
            }
        }
        printf("All tests passed!\n");
    }

    free(list1);
    free(list2);
    free(list3);
    free(rec_list1);
    free(rec_list2);
    free(rec_list3);
    MPI_Finalize();
    return 0;
}