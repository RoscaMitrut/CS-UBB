//bcast scatter gather
//MPI_Sendrecv (LE COMBINA PE AMBELE) (n-am eg. aici)

// MPI_Bcast (transmite de la 1 la toate)
// MPI_Barrier (asteapta toate procesele pana ajung acolo toate procesele)

// MPI_STATUS_IGNORE!

// Broadcast: timitem aceleasi date la toate thread-urile
// Scatter: impartim datele pe care vrem sa le trimitem in bucati, si 
// timitem fiecarui thread o bucata
// Gather: ca Scatter, doar ca invers (uneste toate bucatile primite)
// 
// Reduce: face o operatie asupra TUTUROR (ADICA DE LA TOATE THREAD-URILE) elementelor pe care
// vrem sa le trimitem. eg: Max/Suma/Min/ w/e
#include <mpi.h>
#include <barrier>
#include <cstdlib>
#include <time.h>
#include <stdio.h>
#include <windows.h>

void barrier(int rank, long random_value)
{
    int nap_time = random_value + (2 * rank);
    printf("%d: sleeping %ds\n",rank, nap_time);
    Sleep(nap_time);
    printf("%d: enter b-a-r-r-i-e-r\n", rank);
    MPI_Barrier(MPI_COMM_WORLD);
    printf("%d: leave barrier\n", rank);
}

int broadcast(int rank, int procs) {
    long random_value;
    int broadcaster_rank = procs - 1;

    if (rank == broadcaster_rank) {
        srand(time(NULL) + rank);
        random_value = rand() / (RAND_MAX / 10);
        printf("%d: broadcasting %ld\n", rank, random_value);
    }

    MPI_Bcast((void*)&random_value,
        1, MPI_LONG,
        broadcaster_rank,
        MPI_COMM_WORLD);

    if (rank != broadcaster_rank) {
        printf("%d: received %ld\n", rank, random_value);
    }

    return random_value;
}
int main2(int argc,
    char
    ** argv)
{
    int num_procs;
   
    int rank;
    
    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &num_procs);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);
    printf("%d: hello (p=%d)\n",rank, num_procs);
    long random_value = broadcast(rank, num_procs);
    barrier(rank, random_value);
    printf("%d: goodbye\n",rank);
    MPI_Finalize();
    return 0;
}