package com.theberdakh.russiannumbers

class RussianNumbers {
    companion object {
        fun getRussianNumber(number: Int): String {
            return when (number) {
                0 -> "ноль"
                1 -> "один"
                2 -> "два"
                3 -> "три"
                4 -> "четыре"
                5 -> "пять"
                6 -> "шесть"
                7 -> "семь"
                8 -> "восемь"
                9 -> "девять"
                10 -> "десять"
                11 -> "одиннадцать"
                12 -> "двенадцать"
                13 -> "тринадцать"
                14 -> "четырнадцать"
                15 -> "пятнадцать"
                16 -> "шестнадцать"
                17 -> "семнадцать"
                18 -> "восемнадцать"
                19 -> "девятнадцать"
                20 -> "двадцать"
                30 -> "тридцать"
                40 -> "сорок"
                50 -> "пятьдесят"
                60 -> "шестьдесят"
                70 -> "семьдесят"
                80 -> "восемьдесят"
                90 -> "девяносто"
                100 -> "сто"
                200 -> "двести"
                300 -> "триста"
                400 -> "четыреста"
                500 -> "пятьсот"
                600 -> "шестьсот"
                700 -> "семьсот"
                800 -> "восемьсот"
                900 -> "девятьсот"
                in 21..99 -> {
                    val tens = number / 10 * 10
                    val ones = number % 10
                    getRussianNumber(tens) + " " + getRussianNumber(ones)
                }
                in 101..999 -> {
                    val hundreds = number / 100 * 100
                    val tens = number % 100
                    getRussianNumber(hundreds) + " " + getRussianNumber(tens)
                }
                else -> "Число не входит в диапазон от 0 до 999"
            }
        }
    }
}
