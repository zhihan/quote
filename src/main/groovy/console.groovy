package me.zhihan.quote

/** Utilities to work with the console */
class Console {
    static colorCodeToString(String s) {
        def m = ["k": "90", "r": "91", "g": "92", "y": "93", "b": "94", 
            "m": "95", "c": "96", "w": "97"]
        m[s]
    }

    static color(String s, String c) throws IllegalArgumentException {
        if (s[0] == '\33') {
            throw new IllegalArgumentException(
                "The string already has color controls")
        } 
        "\33[" + colorCodeToString(c) + "m" + s + "\33[0m"
    }
} 


class ConsoleMain {
    static void main(String[] args) {
        println(Console.color("Green", "g"))
        println(Console.color("Yello", "y"))
    }
}