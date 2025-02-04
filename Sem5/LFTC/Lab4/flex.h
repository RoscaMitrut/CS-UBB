#ifndef FLEX_H
#define FLEX_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "hash.h"

#define MAX_LINE_LENGTH 100

typedef struct {
    int atomCode;
    int tsPosition;
} FipElem;

void printTS();
void printFIP();
void assignTsDataToFIP();

void initFlexResources();
void destroyFlexResources();

int getCurrentLine();
int getErrorsFound();

#endif