package br.com.maddytec.pontoeletronico

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class PontoEletronicoApplication

	fun main(args: Array<String>) {
		runApplication<PontoEletronicoApplication>(*args)
	}