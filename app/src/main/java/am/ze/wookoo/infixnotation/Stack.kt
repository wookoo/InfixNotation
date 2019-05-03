package am.ze.wookoo.infixnotation

class Stack{
    var top = -1
    var data = ArrayList<Int>()
    fun is_empty():Boolean{
        return this.top == -1
    }
    fun push(n:Int){
        this.data.add(n)
        this.top+=1
    }
    fun pop():Int{
        return  (this.data.get(this.top--))
    }
    fun peek():Int{
        return this.data.get(this.top)
    }

}