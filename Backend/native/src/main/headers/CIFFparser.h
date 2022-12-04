//
// Created by bence on 2022-11-08.
//

#ifndef BIZTHF_CIFFPARSER_H
#define BIZTHF_CIFFPARSER_H

#include <fstream>
#include "CIFF.h"

namespace parsers {

    CIFF parseCIFF(std::ifstream &filestream);

}

#endif //BIZTHF_CIFFPARSER_H
