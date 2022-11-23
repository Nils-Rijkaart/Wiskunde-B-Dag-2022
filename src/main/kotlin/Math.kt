fun main() {
    (0..5).forEach {
        val m = Formula.minimumStepsEfficient(5.0, 3.0, it.toDouble()).toMutableMap() as MutableMap<String, Any>
        m["possible"] = Formula.isPossible(5.0, 3.0, it.toDouble())
        m["make"] = it
        println(m)
    }

    MultipleCans()
    Formula.approximate()
}

class Formula {
    companion object {
        /*
        We have two cans of water, one with capacity 5.0 and the other with capacity pi.
        It is impossible to measure exactly 1.0 liters of water using these two cans.
      However, we can get an approximate value of 1.0 by filling the 5.0 liter can, and then pouring over the 3.1415 liter can.
        The remaining water in the 3.1415 liter can is 1.8585 liters.
         */

        fun approximate() {
            val pi = 3.141592653589793 * 1000000
            val capacity1 = 5.0 * 1000000
            getPossibleOutcomes(capacity1, pi).forEach {
                if (it / 1000000.0 < 1.006 && (it / 1000000.0 != 1.0))
                    println(it / 1000000.0)
            }
        }

        fun getPossibleOutcomes(x: Double, y: Double): List<Int> {
            val sum = x + y
            return (0..(sum).toInt()).filter { isPossible(x, y, it.toDouble()) }
        }


        fun isPossible(a: Double, b: Double, answer: Double): Boolean {
            if (answer > a && answer > b) return false
            if (answer % gcd(a, b) == 0.0) return true
            return false
        }

        fun minimumStepsEfficient(a: Double, b: Double, answer: Double): Map<String, Double> {
            if (!isPossible(a, b, answer)) return emptyMap()
            var x = a
            var y = 0.0
            var fillX = 1.0
            var emptyY = 0.0
            var swap = 0.0
            var steps = 1.0
            while (x != answer && y != answer) {
                val temp = x.coerceAtMost(b - y)
                y += temp
                x -= temp
                if (temp > 0) {
                    swap++
                }
                steps++
                if (x == answer || y == answer) {
                    break
                }
                if (x == 0.0) {
                    x = a
                    fillX += 1
                    steps++
                }
                if (y == b) {
                    y = 0.0
                    emptyY += 1;
                    steps++
                }
            }
            return mapOf("fill" to fillX, "empty" to emptyY, "swap" to swap, "steps" to steps)
        }
    }
}

fun gcd(a: Double, b: Double): Double {
    if (a == 0.0) return b
    return gcd(b % a, a)
}