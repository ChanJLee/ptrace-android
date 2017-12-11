//
// Created by chan on 2017/12/11.
//

#ifndef FUCKDEX_DEX_KILLER_H
#define FUCKDEX_DEX_KILLER_H

#include <stdlib.h>
#include <stdio.h>
#include <dirent.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdarg.h>
#include <string.h>
#include <errno.h>
#include <sys/ptrace.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <sys/user.h>
#include <vector>
#include <string>

#ifdef HAVE_STDINT_H
#include <stdint.h>    /* C99 */
typedef uint8_t             u1;
typedef uint16_t            u2;
typedef uint32_t            u4;
typedef uint64_t            u8;
typedef int8_t              s1;
typedef int16_t             s2;
typedef int32_t             s4;
typedef int64_t             s8;
#else
typedef unsigned char u1;
typedef unsigned short u2;
typedef unsigned int u4;
typedef unsigned long long u8;
typedef signed char s1;
typedef signed short s2;
typedef signed int s4;
typedef signed long long s8;
#endif

#undef SHA1_LEN
#define SHA1_LEN 20

#define  LOGI(...)  printf(__VA_ARGS__)

typedef struct {
    u1 magic[8];           /* includes version number */
    u4 checksum;           /* adler32 checksum */
    u1 signature[SHA1_LEN]; /* SHA-1 hash */
    u4 fileSize;           /* length of entire file */
    u4 headerSize;         /* offset to start of next section */
    u4 endianTag;
    u4 linkSize;
    u4 linkOff;
    u4 mapOff;
    u4 stringIdsSize;
    u4 stringIdsOff;
    u4 typeIdsSize;
    u4 typeIdsOff;
    u4 protoIdsSize;
    u4 protoIdsOff;
    u4 fieldIdsSize;
    u4 fieldIdsOff;
    u4 methodIdsSize;
    u4 methodIdsOff;
    u4 classDefsSize;
    u4 classDefsOff;
    u4 dataSize;
    u4 dataOff;
} DexHeader;


pid_t find_pid(const std::string &pkg);
pid_t find_tid(const pid_t pid);
int find_mem_file(const pid_t tid);


#endif //FUCKDEX_DEX_KILLER_H
