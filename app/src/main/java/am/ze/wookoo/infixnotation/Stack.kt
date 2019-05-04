package am.ze.wookoo.infixnotation

class Stack{
    var top = -1
    var data = ArrayList<Int>()
    fun is_empty():Boolean{
        return this.top == -1
    }
    fun push(n:Int){
        //top 이 이렇게 되면 안된다 수정!
        this.data.add(n)
        this.top+=1
    }
    fun pop():Int{
        this.top--;
        return  (this.data.removeAt(this.data.lastIndex))
    }
    fun peek():Int{
        return this.data.get(this.top)
    }

}