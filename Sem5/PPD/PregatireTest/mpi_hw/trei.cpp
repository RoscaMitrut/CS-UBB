//KINDA EXTRA

// !NON-BLOCKING!
// MPI_Isend
// MPI_Irecv
//
// MPI_Test - test whether request complete
// MPI_Wait - wait for request to complete

// Daca dam receive si inca nu s-a dat send, nu ne intereseaza, folosim ce avem acolo in memorie
// Nu se asteapta





// EVEN MORE EXTRA
// Topologies
// WAY TOO ADVANCED FOR THIS COURSE lmao
// Basically, impartirea thread-urilor in mai multe dimensiuni, si 
// setarea felului in care acestea comunica intre ele
// eg.
// sa ziceam ca avem un grid de procese 3x3x3
// daca vrem ca procesul din mijloc sa comunice cu vecinii,
// vecinii sunt considerati aia 6 (catre aialalti 20 de pe margini
// tre sa specificam noi dace vrem sa se comunice)