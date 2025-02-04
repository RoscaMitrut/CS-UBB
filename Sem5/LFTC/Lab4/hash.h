#ifndef HASHMAP_H
#define HASHMAP_H

#define HASHMAP_CAPACITY 30

typedef struct {
    char *map[HASHMAP_CAPACITY]; // Array to store keys as strings
    int size;                    // Tracks the number of elements in the hash map
} HashMap;

// Initializes a HashMap
void init_hashmap(HashMap *hashmap);

// Computes the hash for a given key
int hash_function(const char *key);

// Inserts a key into the HashMap
int insert(HashMap *hashmap, const char *key);

// Retrieves the index of a given key
int get_index(const HashMap *hashmap, const char *key);

// Retrieves the key at a given index
const char *get_by_index(const HashMap *hashmap, int index);

// Prints the entire HashMap
void print_map(const HashMap *hashmap);

// Writes the HashMap to a file
int write_to_file(const HashMap *hashmap, const char *filename);

// Frees the memory allocated for the HashMap
void free_hashmap(HashMap *hashmap);

#endif // HASHMAP_H
