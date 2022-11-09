//
// Created by bence on 2022-11-08.
//

#ifndef BIZTHF_CAFFPARSER_H
#define BIZTHF_CAFFPARSER_H

#include "../model/CAFF.h"

namespace parsers {

    CAFF parseCAFF(std::ifstream &filestream);


}


#endif //BIZTHF_CAFFPARSER_H
