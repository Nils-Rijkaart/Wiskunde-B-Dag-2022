class Step(val stepType: MultipleCans.StepType, val can: Int, val amount : Int)

class MultipleCans {


    /*
        consider n cans with different capacities k. All cans can be filled with water and emptied completely. Water can be poured from one can to another. What steps are needed to create a third can with a given volume using these n cans?
     */
    fun solve(n: List<Int>, k: List<Int>, volume: Int): List<Step> {
        val result = mutableListOf<Step>()
        val cans = n.zip(k).map { it.first to it.second }.toMutableList()
        var currentVolume = 0
        while (currentVolume != volume) {
            val (can, capacity) = cans.first()
            if (currentVolume + capacity <= volume) {
                result.add(Step(StepType.FILL, can, capacity))
                currentVolume += capacity
            } else if (currentVolume >= capacity) {
                result.add(Step(StepType.EMPTY, can, capacity))
                currentVolume -= capacity

            } else {
                val diff = volume - currentVolume
                result.add(Step(StepType.FILL, can, diff))
                result.add(Step(StepType.POUR, can, diff))
                cans.removeAt(0)
                cans.add(can to diff)
                currentVolume = 0
            }

        }
        return result
    }

    init {
        solve(listOf(1, 2, 3,4), listOf(15, 10, 6,3), 1).forEach {
            println("${it.stepType} ${it.can} ${it.amount}")
        }
    }


    enum class StepType {
        FILL, EMPTY, POUR
    }


    fun getMinimumOperations(cans: List<Int>, target: Int) {

    }

    fun isPossible(cans: List<Int>, target: Int): Boolean {
        if (cans.none {
                it >= target
            }) return false
        val gcd = cans.reduce { acc, i -> gcd(acc, i) }
        return target % gcd == 0
    }

    fun gcd(a: Int, b: Int): Int {
        if (b == 0) return a
        return gcd(b, a % b)
    }
}