//
// Created by chan on 2017/12/11.
//
#include "dex_killer.h"
#include <sstream>

pid_t find_pid(const std::string &pkg) {
    if (pkg.empty()) {
        return 0;
    }

    DIR *proc_dir = NULL;
    if ((proc_dir = opendir("/proc")) == NULL) {
        return 0;
    }

    struct dirent *entry = NULL;
    while ((entry = readdir(proc_dir)) != NULL) {
        if (strcmp(entry->d_name, "self") == 0) {
            continue;
        }

        std::ostringstream cmd_file_name_os;
        cmd_file_name_os << "/proc/" << entry->d_name << "/cmdline";
        std::string cmd_file_name = cmd_file_name_os.str();
        FILE *cmd_file = fopen(cmd_file_name.c_str(), "r");
        if (cmd_file == NULL) {
            continue;
        }

        char cmd_file_content[1024] = {0};
        fscanf(cmd_file, "%s", cmd_file_content);
        fclose(cmd_file);
        if (strcmp(cmd_file_content, pkg.c_str()) == 0) {
            return atoi(entry->d_name);
        }
    }

    closedir(proc_dir);
    return 0;
}

pid_t find_tid(const pid_t pid) {
    if (pid == 0) {
        return 0;
    }

    std::ostringstream process_task_dir_name_os;
    process_task_dir_name_os << "/proc/" << pid << "/task";
    std::string process_task_dir_name = process_task_dir_name_os.str();
    DIR *process_task_dir = opendir(process_task_dir_name.c_str());
    if (process_task_dir == NULL) {
        LOGI("open %s failed", process_task_dir_name.c_str());
        return 0;
    }

    struct dirent *entry = NULL;
    pid_t tid = 0;
    while ((entry = readdir(process_task_dir)) != NULL) {
        tid = atoi(entry->d_name);
    }
    closedir(process_task_dir);

    return tid;
}

int find_mem_file(const pid_t tid) {
    std::ostringstream tid_mem_file_name_os;
    tid_mem_file_name_os << "/proc/" << tid << "/mem";
    std::string tid_mem_file_name = tid_mem_file_name_os.str();

    int attach_result = ptrace(PTRACE_ATTACH, tid, NULL, NULL);
    if (attach_result != 0) {
        return -1;
    }

    return open(tid_mem_file_name.c_str(), O_RDONLY);
}