package br.com.maddytec.pontoeletronico.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtils {
   companion object {
      val formmaterDateHourBr = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

      fun localDateTimeToStringBr(localDateTime: LocalDateTime): String {
         return localDateTime.format(formmaterDateHourBr)
      }
   }
}