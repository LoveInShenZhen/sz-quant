package com.sample

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default

//
// Created by kk on 2019-04-12.
//
class AppArgs(parser: ArgParser) {

    val destination by parser.positional(
            "DEST",
            help = "Tushare data dir path.").default("/Volumes/USBDATA/tushare_data")

}