package com.example.lemiforce.model

class Pitanje(var text: String, var ponudjeniOdgovori: List<String>, var tacniOdgovori: List<Int>, var slika: String = "img", var odgovoreni: List<Int>? = null, var kategorija: String = "ABCDTP") {}