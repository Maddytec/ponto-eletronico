package br.com.maddytec.pontoeletronico.response

data class Response<T>(
    val erros: ArrayList<String> = arrayListOf(),
    val data: T? = null
)
