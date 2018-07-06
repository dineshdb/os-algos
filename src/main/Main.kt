package np.info.dineshdb.os

data class Process(var pid: Int, var burstTime: Int)
data class RunUnit(val pid: Int, val unit: Int)

fun SJF(list: List<Process>, unit: Int = 1): List<RunUnit>{
    var list = list.sortedWith(compareBy({ it.burstTime }))
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
// RR, FCFS

fun main(args: Array<String>){
    var list = listOf(Process(1, 2), Process(2, 3), Process(3, 2))
    val units = 2

    var runtime = SJF(list, units)
    print(runtime)

    var runtime = SJF(list, units)
    print(runtime)

    var runtime = SJF(list, units)
    print(runtime)
}
