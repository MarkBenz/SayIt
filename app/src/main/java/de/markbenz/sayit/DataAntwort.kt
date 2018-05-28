package de.markbenz.sayit

data class DataAntwort (val idDesThemas : String, val content : String){
    constructor() : this("",""){}
}