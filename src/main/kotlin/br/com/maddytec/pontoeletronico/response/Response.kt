package br.com.maddytec.pontoeletronico.response

data class Response<T>(
    val erros: ArrayList<String> = arrayListOf(),
    var data: T? = null
)
