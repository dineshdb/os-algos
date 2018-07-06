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
}
