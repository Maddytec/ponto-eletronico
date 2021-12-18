package br.com.maddytec.pontoeletronico.controllers

import br.com.maddytec.pontoeletronico.exception.NotFoundExceptionHandle
import br.com.maddytec.pontoeletronico.response.ResponseMsg
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class AdviceController {

    @ExceptionHandler(NotFoundExceptionHandle::class)
    fun notFoundExceptionHandle(notFoundExceptionHandle: NotFoundExceptionHandle) =
        notFoundExceptionHandle.message?.let { ResponseMsg(it) }
}