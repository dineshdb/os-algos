package np.info.dineshdb.os

data class Process(var pid: Int, var burstTime: Int)
data class RunUnit(val pid: Int, val unit: Int)

fun SJF(list: List<Process>, unit: Int = 1): List<RunUnit>{
    var _list = list.sortedWith(compareBy({ it.burstTime }))
    return FCFS(_list, unit)
}

fun FCFS(list: List<Process>, unit: Int = 1): List<RunUnit>{
    var list = list
    var _list: MutableList<RunUnit> = ArrayList()
    
    while(true){
        val cur = list.get(0)
        val decreaseBy = if (cur.burstTime >= 2) 2 else 1

        cur.burstTime -= decreaseBy

        val runUnit = RunUnit(cur.pid, decreaseBy)
        _list.add(runUnit)
        if(cur.burstTime <= 0){
               list = list.drop(1)
        }        
                
        if(list.isEmpty()){
            break
        }
    }    
    return _list
}
fun RoundRobin(list: List<Process>, unit: Int = 1): List<RunUnit>{
    var list = list
    var _list: MutableList<RunUnit> = ArrayList()
    
    while(true){
        val cur = list.get(0)
        val decreaseBy = if (cur.burstTime >= 2) 2 else 1

        cur.burstTime -= decreaseBy

        val runUnit = RunUnit(cur.pid, decreaseBy)
        _list.add(runUnit)
        
        list = list.drop(1)
        if(cur.burstTime > 0){
               list +=cur
        }      
                
        if(list.isEmpty()){
            break
        }
    }    
    return _list
}

fun main(args: Array<String>){
    val units = 2

    var list = listOf(Process(1, 2), Process(2, 3), Process(3, 2))
    print(SJF(list, units))
    println()

    var list2 = listOf(Process(1, 2), Process(2, 3), Process(3, 2))
    print(FCFS(list2, units))
    println()

    var list3 = listOf(Process(1, 2), Process(2, 3), Process(3, 2))
    print(RoundRobin(list3, units))
    println()
    
    val r = 5           // No of resources
    val p = 4           // No of processes   
    // Maximum resources available
    val maxRes = arrayOf(4, 5, 3, 5, 4)
  
    // Allocated resource
    val curr = Array(p) { IntArray(r) }
    curr[0] = intArrayOf(1, 0, 0, 1, 0)
    curr[0] = intArrayOf(0, 0, 1, 1, 0)
    curr[0] = intArrayOf(0, 1, 0, 1, 0)
    curr[0] = intArrayOf(1, 0, 0, 0, 1)

    // Maximum clain
    val maxClaim = Array(p) { IntArray(r) }
    maxClaim[0] = intArrayOf(1, 1, 0, 1, 0)
    maxClaim[0] = intArrayOf(0, 0, 2, 1, 0)
    maxClaim[0] = intArrayOf(0, 3, 0, 1, 0)
    maxClaim[0] = intArrayOf(1, 0, 1, 0, 1)
    
    BankersAlgo(maxRes, maxClaim, curr)
}

fun BankersAlgo(maxRes: Array<Int>, maxClaim: Array<IntArray>, curr: Array<IntArray>) {
    val r = maxRes.size           // No of resources
    val p = curr.size           // No of processes
    
    val alloc = IntArray(r)
    for (i in 0 until p) {
        for (j in 0 until r) alloc[j] += curr[i][j]
    }
    println("Allocated Resources: ${alloc.joinToString(" ")}")
 
    val avl = IntArray(r) { maxRes[it] - alloc[it] }
    println("Available Resources: ${avl.joinToString(" ")}")
 
    val running = BooleanArray(p) { true }
    var count = p
    while (count != 0) {
        var safe = false
        for (i in 0 until p) {
            if (running[i]) {
                var exec = true
                for (j in 0 until r) {
                    if (maxClaim[i][j] - curr[i][j] > avl[j]) {
                        exec = false
                        break
                    }
                }
 
                if (exec) {
                    print("\nProcess ${i + 1} is executing.\n")
                    running[i] = false
                    count--
                    safe = true
                    for (j in 0 until r) avl[j] += curr[i][j]
                    break
                }
            }
        }
 
        if (!safe) {
            print("The processes are in an unsafe state.\n")
            break
        }
 
        print("The process is in a safe state.\n")
        println("Available Vector: ${avl.joinToString(" ")}")
    }
}
