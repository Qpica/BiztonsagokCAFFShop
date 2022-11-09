//
// Created by bence on 2022-11-08.
//

#ifndef BIZTHF_CAFF_H
#define BIZTHF_CAFF_H

#include <string>
#include <utility>
#include <vector>
#include "CIFF.h"

struct SimpleDate {

    int year, month, day, hour, minute;

    SimpleDate(int year, int month, int day, int hour, int minute) :
            year(year), month(month), day(day), hour(hour), minute(minute) {}
};


struct CAFF {
    SimpleDate creationDate;
    std::string creator;
    std::vector<std::pair<int, CIFF>> content;

    CAFF(const SimpleDate &creationDate, std::string creator,
         std::vector<std::pair<int, CIFF>> content)
            : creationDate(creationDate), creator(std::move(creator)),
              content(std::move(content)) {}
};

#endif //BIZTHF_CAFF_H
