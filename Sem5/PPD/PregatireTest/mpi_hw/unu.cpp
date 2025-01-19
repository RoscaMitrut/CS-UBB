//send_recv

// MPI_Send - trimite (asteapta pana se trimite)
// MPI_Recv - primeste (asteapta pana primeste)

// MPI_STATUS_IGNORE!

#include <mpi.h>
#include <stdio.h>
#include <ctime>
#include <random>

void round_robin(int rank, int procs)
{
    long int rand_mine, rand_prev;
    int rank_next = (rank + 1) % procs;
    int rank_prev = rank == 0 ? procs - 1 : rank - 1;
    MPI_Status status;

    srand(time(NULL) + rank);
    rand_mine = rand() / (RAND_MAX / 100);
    printf("%d: random is %ld\n", rank, rand_mine);

    if (rank % 2 == 0) {
        printf("%d: sending %ld to %d\n", rank, rand_mine,
            rank_next);
        MPI_Send((void*)&rand_mine, 1, MPI_LONG, rank_next,
            1, MPI_COMM_WORLD);
        printf("%d: receiving from %d\n", rank, rank_prev);
        MPI_Recv((void*)&rand_prev, 1, MPI_LONG, rank_prev,
            1, MPI_COMM_WORLD, &status);
    }
    else {
        printf("%d: receiving from %d\n", rank, rank_prev);
        MPI_Recv((void*)&rand_prev, 1, MPI_LONG,
            rank_prev, 1, MPI_COMM_WORLD, &status);
        printf("%d: sending %ld to %d\n", rank, rand_mine,
            rank_next);
        MPI_Send((void*)&rand_mine, 1, MPI_LONG, rank_next,
            1, MPI_COMM_WORLD);
    }
    printf("%d: I had %ld, %d had %ld\n",
        rank, rand_mine,
        rank_prev, rand_prev);
}

int main1(int argc, char** argv)
{
    int num_procs;
    int rank;

    MPI_Init(&argc, &argv);
    MPI_Comm_size(MPI_COMM_WORLD, &num_procs);
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);

    printf("%d: hello (p=%d)\n", rank, num_procs);
    round_robin(rank, num_procs);
    printf("%d: goodbye\n", rank);

    MPI_Finalize();
    return 0;
}