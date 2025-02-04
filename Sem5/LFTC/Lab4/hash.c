#include "hash.h"
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void init_hashmap(HashMap *hashmap) {
    for (int i = 0; i < HASHMAP_CAPACITY; i++) {
        hashmap->map[i] = NULL;
    }
    hashmap->size = 0;
}

int hash_function(const char *key) {
    int hash_value = 0;
    while (*key) {
        hash_value += *key++;
    }
    return hash_value % HASHMAP_CAPACITY;
}

int insert(HashMap *hashmap, const char *key) {
    if (hashmap->size >= HASHMAP_CAPACITY) {
        fprintf(stderr, "HashMap is full. Cannot insert key: %s\n", key);
        return -1; // Indicates failure
    }

    int hash_value = hash_function(key);
    int index = hash_value;

    // Handle collisions
    while (hashmap->map[index] != NULL) {
        index = (index + 1) % HASHMAP_CAPACITY;
    }

    hashmap->map[index] = strdup(key); // Duplicate the key string
    hashmap->size++;
    return 0; // Indicates success
}

int get_index(const HashMap *hashmap, const char *key) {
    int hash_value = hash_function(key);
    int index = hash_value;

    // Handle collisions
    for (int i = 0; i < HASHMAP_CAPACITY; i++) {
        if (hashmap->map[index] == NULL) {
            break; // Key not found
        }
        if (strcmp(hashmap->map[index], key) == 0) {
            return index;
        }
        index = (index + 1) % HASHMAP_CAPACITY;
    }

    return -1; // Key not found
}

const char *get_by_index(const HashMap *hashmap, int index) {
    if (index < 0 || index >= HASHMAP_CAPACITY) {
        fprintf(stderr, "Invalid index: %d\n", index);
        return NULL;
    }
    return hashmap->map[index];
}

void print_map(const HashMap *hashmap) {
    for (int i = 0; i < HASHMAP_CAPACITY; i++) {
        printf("%d : %s\n", i, hashmap->map[i] ? hashmap->map[i] : "NULL");
    }
}

int write_to_file(const HashMap *hashmap, const char *filename) {
    FILE *file = fopen(filename, "w");
    if (!file) {
        perror("Error opening file");
        return -1; // Indicates failure
    }

    for (int i = 0; i < HASHMAP_CAPACITY; i++) {
        fprintf(file, "%d : %s\n", i, hashmap->map[i] ? hashmap->map[i] : "NULL");
    }

    fclose(file);
    return 0; // Indicates success
}

void free_hashmap(HashMap *hashmap) {
    for (int i = 0; i < HASHMAP_CAPACITY; i++) {
        if (hashmap->map[i] != NULL) {
            free(hashmap->map[i]); // Free allocated strings
            hashmap->map[i] = NULL;
        }
    }
    hashmap->size = 0;
}
